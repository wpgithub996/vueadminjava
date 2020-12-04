package com.wang.vueadmin.utils.timetask;

import com.wang.vueadmin.pojo.LoginInfo;
import com.wang.vueadmin.utils.AddressUtils;
import com.wang.vueadmin.utils.IpUtils;
import com.wang.vueadmin.utils.ServletUtils;
import com.wang.vueadmin.utils.TimeUtils;
import eu.bitwalker.useragentutils.UserAgent;

import javax.servlet.http.HttpServletRequest;
import java.util.TimerTask;

public class AsyncFactory {

    /**
     * 记录登录信息
     */
    public  TimerTask recordLoginInfo(String username,String status,String message,Object ... args){
        //用户代理 可以获得客户使用的操纵系统  版本 浏览器 等等
        HttpServletRequest request = ServletUtils.getRequest();
        final UserAgent userAgent = UserAgent.parseUserAgentString(request.getHeader("User-Agent"));
        //
        String ip = IpUtils.getIpAddr(request);
        return new TimerTask() {
            @Override
            public void run() {
                //获取登录地区
                String address = AddressUtils.getRealAddressByIP(ip);
                //获取客户端浏览器
                String browers = userAgent.getBrowser().getName();
                //获取客户端操作系统
                String os = userAgent.getOperatingSystem().getName();
                LoginInfo loginInfo = new LoginInfo();
                loginInfo.setBrowser(browers);
                loginInfo.setIpaddr(ip);
                loginInfo.setMsg(message);
                loginInfo.setStatus(status);
                loginInfo.setUsername(username);
                loginInfo.setOs(os);
                loginInfo.setLoginLocation(address);
                loginInfo.setLoginTime(TimeUtils.getDateTime());
                //将数据插入数据库
                TaskCRUDUtils taskCRUDUtils = new TaskCRUDUtils();
                taskCRUDUtils.insLoginInfo(loginInfo);
            }
        };
    }

    /**
     * 修改登录状态
     */
    public TimerTask updLoginStatus(String username,String isonline){
        HttpServletRequest request = ServletUtils.getRequest();
        String ip = IpUtils.getIpAddr(request);
        return new TimerTask() {
            @Override
            public void run() {
                LoginInfo loginInfo = new LoginInfo();
                loginInfo.setUsername(username);
                loginInfo.setLoginTime(TimeUtils.getDateTime());
                loginInfo.setIsonline(isonline);
                loginInfo.setIpaddr(ip);

                TaskCRUDUtils taskCRUDUtils = new TaskCRUDUtils();
                taskCRUDUtils.updLoginStatus(loginInfo);
            }
        };
    }
}
