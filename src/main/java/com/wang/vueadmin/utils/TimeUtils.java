package com.wang.vueadmin.utils;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeUtils {
    public static String getDateTime(){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
