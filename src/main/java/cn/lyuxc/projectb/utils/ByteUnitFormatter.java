package cn.lyuxc.projectb.utils;

public class ByteUnitFormatter {

    private static final String[] UNITS = {"B", "KB", "MB", "GB", "TB", "PB", "EB"};
    private static final long BASE = 1024;

    /**
     * 将字节数转换为最大合适单位的字符串，保留两位小数
     * @param bytes 要转换的字节数
     * @return 格式化后的字符串，如 "1.23 GB"
     */
    public static String formatBytes(long bytes) {
        return formatBytes(bytes, 2);
    }

    /**
     * 将字节数转换为最大合适单位的字符串
     * @param bytes 字节数
     * @param decimal 保留的小数位数
     * @return 格式化后的字符串
     */
    public static String formatBytes(long bytes, int decimal) {
        if (bytes < 0) return "Invalid size";
        if (bytes == 0) return "0 B";

        int unitIndex = 0;
        double value = bytes;

        while (value >= BASE && unitIndex < UNITS.length - 1) {
            value /= BASE;
            unitIndex++;
        }

        String formatString = "%." + decimal + "f %s";
        return String.format(formatString, value, UNITS[unitIndex]);
    }
}

