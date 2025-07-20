package cn.lyuxc.projectb.controller;

import cn.lyuxc.projectb.utils.ByteUnitFormatter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.*;

@Slf4j
@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    private static final String SYSTEM_PATH = System.getProperty("user.dir");

    @GetMapping("/getHardwareValue")
    public List<Map<String, Object>> getHardwareValue() {
        List<Map<String, Object>> response = new ArrayList<>();
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        if (osBean instanceof com.sun.management.OperatingSystemMXBean sunOsBean) {

            // CPU 使用率（0.0 ~ 1.0 之间）
            double cpuLoad = sunOsBean.getCpuLoad();

            response.add(Map.of("title", "CPU使用率","value",(cpuLoad >= 0 ? Math.round(cpuLoad * 1000) / 10.0 : -1) + "%"));

            // 内存信息（单位：MB）
            long totalMemory = sunOsBean.getTotalMemorySize();
            long freeMemory = sunOsBean.getFreeMemorySize();
            long usedMemory = totalMemory - freeMemory;
            double memoryUsage = (double) usedMemory / totalMemory;

            response.add(Map.of("title","内存使用率","value", Math.round(memoryUsage * 1000) / 10.0 + "%"));
            response.add(Map.of("title","内存总量", "value", totalMemory / (1024 * 1024) + "MiB"));
            response.add(Map.of("title", "已使用内存总量","value", usedMemory / (1024 * 1024) + "MiB"));
        }
        return response;
    }

    @GetMapping("/getFilesCount")
    public List<Map<String, Object>> getFilesCount() {
        List<Map<String, Object>> response = new ArrayList<>();

        try {
            File dataDir = new File(SYSTEM_PATH, "files");
            if (dataDir.exists()) {
                File[] files = dataDir.listFiles();
                if (files != null) {
                    long filesByteSize = Arrays.stream(files).mapToLong(File::length).sum();
                    long fileCount = Arrays.stream(files).count();
                    response.add(Map.of("title", "已存储文件数量","value",  fileCount));
                    response.add(Map.of("title", "已使用空间大小","value", ByteUnitFormatter.formatBytes(filesByteSize)));
                }
            }
        } catch (Exception e) {
            response.add(Map.of("title", "服务器异常","value", e.getMessage()));
        }
        return response;
    }
}
