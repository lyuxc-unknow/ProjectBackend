package cn.lyuxc.projectb.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class DashboardController {
    @GetMapping("/api/dashboard")
    public List<Map<String, Object>> dashboard() {
        List<Map<String, Object>> result = new ArrayList<>();
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        if (osBean instanceof com.sun.management.OperatingSystemMXBean sunOsBean) {

            // CPU 使用率（0.0 ~ 1.0 之间）
            double cpuLoad = sunOsBean.getCpuLoad();

            result.add(Map.of("title", "CPU使用率","value",(cpuLoad >= 0 ? Math.round(cpuLoad * 1000) / 10.0 : -1) + "%"));

            // 内存信息（单位：MB）
            long totalMemory = sunOsBean.getTotalMemorySize();
            long freeMemory = sunOsBean.getFreeMemorySize();
            long usedMemory = totalMemory - freeMemory;
            double memoryUsage = (double) usedMemory / totalMemory;

            result.add(Map.of("title","内存使用率","value", Math.round(memoryUsage * 1000) / 10.0 + "%"));
            result.add(Map.of("title","内存总量", "value", totalMemory / (1024 * 1024) + "MiB"));
            result.add(Map.of("title", "已使用内存总量","value", usedMemory / (1024 * 1024) + "MiB"));
        }
//        log.info(LogUtils.mapListToString(result).toString());
        return result;
    }
}
