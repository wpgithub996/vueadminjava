package com.wang.vueadmin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.wang.vueadmin.mapper")
public class VueadminApplication {

    public static void main(String[] args) {
        SpringApplication.run(VueadminApplication.class, args);
        System.out.println("项目启动成功 ...");
    }

}
