package com.wang.vueadmin.pojo;

import lombok.Data;
import java.io.Serializable;

@Data
public class User implements Serializable {
    private Integer userid;
    private String username;
    private String nickname;
    private String password;
    private String usertype;
    private String phone;
    private String email;
    private String sex;
    private String status;
    private String del_flag;
    private String remark;
    private String login_date;
    private String create_time;
    private String login_ip;
    private String verifyNum;
    private String isonline;

}
