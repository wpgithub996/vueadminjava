package com.wang.vueadmin.utils;

import java.util.UUID;

public class VerifyNumUtils {
    public static String getVerifyNum(){
        UUID uuid = UUID.randomUUID();
        //获取前八位UUID
        String[] split = uuid.toString().split("-")[0].split("");
        String result = "";
        for ( int i=0;i<6;i++) {
            result = result.concat(split[i]);
        }
        return result;
    }
    public static String getUUID(){
        return String.valueOf(UUID.randomUUID());
    }
}
