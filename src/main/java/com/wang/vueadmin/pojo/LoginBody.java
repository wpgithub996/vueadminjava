package com.wang.vueadmin.pojo;

import lombok.Data;

@Data
public class LoginBody {
    private String username;
    private String password;
    private String verifyNum;
    private String QRinfo;
}
