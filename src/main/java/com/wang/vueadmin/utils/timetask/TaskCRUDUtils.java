package com.wang.vueadmin.utils.timetask;

import com.wang.vueadmin.mapper.UserMapper;
import com.wang.vueadmin.pojo.LoginInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class TaskCRUDUtils {

    private static UserMapper userMapper;

    @Autowired
    public void setUserMapper(UserMapper userMapper){
        TaskCRUDUtils.userMapper = userMapper;
    }


    public  void insLoginInfo(LoginInfo info){
        Integer integer = userMapper.saveLoginInfo(info);
    }

    public void updLoginStatus(LoginInfo loginInfo){
        userMapper.updLoginStatus(loginInfo);
    }
}
