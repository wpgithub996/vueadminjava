package com.wang.vueadmin.config;

import com.wang.vueadmin.common.ResultStatus;
import com.wang.vueadmin.exception.MyException;
import com.wang.vueadmin.utils.TokenUtil;
import com.wang.vueadmin.utils.VerifyNumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.TimeUnit;

@Component
public class MyInterceptor implements HandlerInterceptor{
    @Resource
    private TokenUtil tokenUtil;
    @Autowired
    private RedisCache redisCache;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String requrl = request.getRequestURI();
        //请求登录和注册放行
        if(requrl.contains("/api/getQRCode") || requrl.contains("/api/loginQR") || requrl.contains("/api/loginVerQR") ||requrl.equals("/api/login") || requrl.equals("/api/regAccount") || requrl.equals("/api/sendPhoneMsg") || requrl.equals("/api/sendEmailMsg")){
            response.addHeader("UUID",VerifyNumUtils.getUUID());
            return true;
        }

        try {
            //获取token
            String token = request.getHeader("Authorization").replace("Bearer ","");
            //手机端验证
            if(token.equals("GHDK65161rtdtyxGFD")){
                return true;
            }
            String UUID = request.getHeader("UUID");
            //UUID可防止前端重复提交
            redisCache.deleteObject(UUID);
            //刷新token有效期
            redisCache.expire(tokenUtil.getUsernameFromToken(token),30, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new MyException(ResultStatus.SEE_OTHER, "非法操作,请重新登录");
        }

        response.addHeader("UUID",VerifyNumUtils.getUUID());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
