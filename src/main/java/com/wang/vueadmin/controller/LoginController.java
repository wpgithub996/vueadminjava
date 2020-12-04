package com.wang.vueadmin.controller;

import com.wang.vueadmin.common.PlatResult;
import com.wang.vueadmin.pojo.LoginBody;
import com.wang.vueadmin.pojo.User;
import com.wang.vueadmin.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
@ResponseBody
@RequestMapping("/api")
public class LoginController {
    @Autowired
    private LoginService loginService;
    /*
        账号密码登录调用的方法
     */
    @RequestMapping("/login")
    public PlatResult login(@RequestBody LoginBody loginBody, HttpServletRequest request){
        request.getParameter("");
        return loginService.login(loginBody);
    }

    /*
     用户扫码确认登录调用的方法
   */
    @RequestMapping("/loginVerQR/{QRinfo}")
    public PlatResult loginVerQR(@RequestBody LoginBody loginBody,@PathVariable("QRinfo") String QRinfo){
      loginBody.setQRinfo(QRinfo);
      return   loginService.loginVerQR(loginBody);
    }
    /*
        页面定时发送请求 判断用户是否确定登录
     */
    @RequestMapping("/loginQR/{QRinfo}")
    public PlatResult loginQR(@PathVariable("QRinfo") String QRinfo){
        return   loginService.loginQR(QRinfo);
    }
    /**
     * 登出调用的方法
     */
    @RequestMapping("/logout/{token}")
    public PlatResult logout(@PathVariable("token") String token){
        return loginService.logout(token);
    }
    /**
     * 账户注册
     */
    @RequestMapping("/regAccount")
    public PlatResult registryAccount(@RequestBody User user){
        return loginService.regAccount(user);
    }
    /**
     * 发送短信验证码
     */
    @RequestMapping("/sendPhoneMsg")
    public PlatResult sendPhoneMsg(@RequestBody User  user){
        return loginService.sendPhoneMsg(user.getPhone());
    }

    /**
     * 发送邮箱验证码
     */
    @RequestMapping("/sendEmailMsg")
    public PlatResult sendEmailMsg(@RequestBody User user){
        return loginService.sendEmailMsg(user.getEmail());
    }

}
