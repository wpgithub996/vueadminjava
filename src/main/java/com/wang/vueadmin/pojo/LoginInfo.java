package com.wang.vueadmin.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class LoginInfo  implements Serializable {
    private Integer infoid;
    private String username;
    private String ipaddr;
    private String loginLocation;
    private String browser;
    private String os;
    private String status;
    private String msg;
    private String loginTime;
    private String isonline;
}
