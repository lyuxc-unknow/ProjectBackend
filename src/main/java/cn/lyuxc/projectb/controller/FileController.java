package cn.lyuxc.projectb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/file")
public class FileController {
    private static final String SYSTEM_PATH = System.getProperty("user.dir");

    @GetMapping("/getFiles")
    public Map<String, Object> listFiles() {
        Map<String, Object> response = new HashMap<>();

        try {
            File dataDir = new File(SYSTEM_PATH, "files");

            // 如果文件被占用/不存在或是文件夹则返回空列表
            if (!dataDir.exists() || !dataDir.isDirectory()) {
                response.put("files", Collections.emptyList());
                return response;
            }

            // 遍历文件
            List<String> fileNames = Arrays.stream(Objects.requireNonNull(dataDir.listFiles()))
                    .filter(File::isFile)
                    .map(File::getName)
                    .collect(Collectors.toList());

            response.put("files", fileNames);
            return response;
        } catch (Exception e) {
            response.put("files", Collections.emptyList());
            response.put("error", "服务器异常：" + e.getMessage());
            return response;
        }
    }

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadFile(@RequestParam("filename") String filename) {
        try {
            File file = new File(SYSTEM_PATH + File.separator + "files", filename);
            // 如果文件被占用/不存在或不是文件则返回未找到
            if (!file.exists() ||  !file.isFile()) {
                return ResponseEntity.notFound().build();
            }

            // 文件系统资源
            FileSystemResource resource = new FileSystemResource(file);
            String encodedFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8);

            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + encodedFilename + "\"")
                    .contentLength(file.length())
                    .contentType(MediaType.APPLICATION_OCTET_STREAM)
                    .body(resource);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping("/upload")
    public Map<String, Object> upload(@RequestParam("file") MultipartFile file) {
        try {
            File dataDir = new File(SYSTEM_PATH + File.separator + "files");
            // 如果目标文件夹不存在则创建文件夹
            if (!dataDir.exists()) {
                if (dataDir.mkdirs()) {
                    log.info("can't find files directory, created: {}", dataDir.getAbsolutePath());
                }
            }

            // 判断文件是否错误（空文件或者无法取得文件名的文件）
            if (file.isEmpty() || file.getOriginalFilename() == null) {
                return Map.of("result","文件错误");
            }

            //拼接目标文件
            File targetFile = new File(dataDir, file.getOriginalFilename());
            file.transferTo(targetFile);
            return Map.of("result", "上传成功");
        } catch (IOException e) {
            e.fillInStackTrace();
            return Map.of("result", "文件上传失败" + e.getMessage());
        }
    }
}

