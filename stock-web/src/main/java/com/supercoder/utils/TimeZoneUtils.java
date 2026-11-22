package com.supercoder.utils;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * 时区转换工具类
 * 支持北京时间（Asia/Shanghai）、GMT 时间、纽约时间（America/New_York）之间的相互转换
 */
public class TimeZoneUtils {

    public static final ZoneId ZONE_BJ = ZoneId.of("Asia/Shanghai");
    public static final ZoneId ZONE_GMT = ZoneId.of("GMT");
    public static final ZoneId ZONE_NY = ZoneId.of("America/New_York");

    public static final DateTimeFormatter DEFAULT_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private TimeZoneUtils() {
    }

    /**
     * 将指定时区的 LocalDateTime 转换为目标时区的 LocalDateTime
     */
    public static LocalDateTime convert(LocalDateTime dateTime, ZoneId fromZone, ZoneId toZone) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.atZone(fromZone).withZoneSameInstant(toZone).toLocalDateTime();
    }

    // ==================== 北京时间相关转换 ====================

    public static LocalDateTime bjToGmt(LocalDateTime bjTime) {
        return convert(bjTime, ZONE_BJ, ZONE_GMT);
    }

    public static LocalDateTime bjToNy(LocalDateTime bjTime) {
        return convert(bjTime, ZONE_BJ, ZONE_NY);
    }

    // ==================== GMT 时间相关转换 ====================

    public static LocalDateTime gmtToBj(LocalDateTime gmtTime) {
        return convert(gmtTime, ZONE_GMT, ZONE_BJ);
    }

    public static LocalDateTime gmtToNy(LocalDateTime gmtTime) {
        return convert(gmtTime, ZONE_GMT, ZONE_NY);
    }

    // ==================== 纽约时间相关转换 ====================

    public static LocalDateTime nyToBj(LocalDateTime nyTime) {
        return convert(nyTime, ZONE_NY, ZONE_BJ);
    }

    public static LocalDateTime nyToGmt(LocalDateTime nyTime) {
        return convert(nyTime, ZONE_NY, ZONE_GMT);
    }

    // ==================== Instant / Date 转换 ====================

    public static LocalDateTime ofInstant(Instant instant, ZoneId zone) {
        if (instant == null) {
            return null;
        }
        return LocalDateTime.ofInstant(instant, zone);
    }

    public static LocalDateTime instantToBj(Instant instant) {
        return ofInstant(instant, ZONE_BJ);
    }

    public static LocalDateTime instantToGmt(Instant instant) {
        return ofInstant(instant, ZONE_GMT);
    }

    public static LocalDateTime instantToNy(Instant instant) {
        return ofInstant(instant, ZONE_NY);
    }

    public static LocalDateTime dateToBj(Date date) {
        return date == null ? null : instantToBj(date.toInstant());
    }

    public static LocalDateTime dateToGmt(Date date) {
        return date == null ? null : instantToGmt(date.toInstant());
    }

    public static LocalDateTime dateToNy(Date date) {
        return date == null ? null : instantToNy(date.toInstant());
    }

    // ==================== 格式化和解析 ====================

    public static String format(LocalDateTime dateTime, ZoneId zone) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.atZone(zone).format(DEFAULT_FORMATTER);
    }

    public static String formatBj(LocalDateTime dateTime) {
        return format(dateTime, ZONE_BJ);
    }

    public static String formatGmt(LocalDateTime dateTime) {
        return format(dateTime, ZONE_GMT);
    }

    public static String formatNy(LocalDateTime dateTime) {
        return format(dateTime, ZONE_NY);
    }

    public static LocalDateTime parse(String text, ZoneId zone) {
        if (text == null || text.isEmpty()) {
            return null;
        }
        LocalDateTime local = LocalDateTime.parse(text, DEFAULT_FORMATTER);
        return convert(local, zone, ZoneId.systemDefault());
    }
}
