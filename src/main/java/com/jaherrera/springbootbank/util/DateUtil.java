package com.jaherrera.springbootbank.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtil {

    private static final DateTimeFormatter DTF = DateTimeFormatter
            .ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
            .withZone(ZoneId.of("UTC"));

    public static String formatToDateTimeString(LocalDateTime localDateTime){
        return DTF.format(localDateTime);
    }
}
