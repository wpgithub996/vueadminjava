package com.wang.vueadmin.mapper;
import com.wang.vueadmin.pojo.LoginBody;
import com.wang.vueadmin.pojo.LoginInfo;
import com.wang.vueadmin.pojo.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

public interface UserMapper {
    //查询账号是否存在
    Integer isexist(String username);

    //用户登录  验证用户名和密码
    List<User> verifyAcc(LoginBody loginBody);

    //新增用户
    Integer saveAcc(User user);

    //修改用户信息
    Integer updAcc(User user);

    //删除用户(软删除)
    Integer delAcc(String username);

    //登记登录信息
    Integer saveLoginInfo(LoginInfo loginInfo);

    //修改登录信息
    Integer updLoginStatus(LoginInfo loginInfo);
}
