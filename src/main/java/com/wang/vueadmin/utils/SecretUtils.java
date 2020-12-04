package com.wang.vueadmin.utils;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

/**
 * 密码加密
 */
@ConfigurationProperties(prefix = "pwd")
@Data
@Component
public class SecretUtils {
    private  String secretstr;


    public  String encrypt(String agrs){
        return DigestUtils.md5DigestAsHex(agrs.concat(secretstr).getBytes());
    }
}
