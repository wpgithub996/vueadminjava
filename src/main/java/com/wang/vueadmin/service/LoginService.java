package com.wang.vueadmin.service;

import com.wang.vueadmin.common.PlatResult;
import com.wang.vueadmin.pojo.LoginBody;
import com.wang.vueadmin.pojo.User;

public interface LoginService {
    PlatResult login(LoginBody loginBody);

    PlatResult sendEmailMsg(String email);

    PlatResult sendPhoneMsg(String phone);

    PlatResult regAccount(User user);

    PlatResult logout(String token);

    PlatResult loginQR(String verQRinfo);

    PlatResult loginVerQR(LoginBody loginBody);
}
