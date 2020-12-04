package com.wang.vueadmin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.Resource;

/**
 * 注册拦截器
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Resource
    private MyInterceptor MyInterceptor ;
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(MyInterceptor).addPathPatterns("/**");
    }
}
