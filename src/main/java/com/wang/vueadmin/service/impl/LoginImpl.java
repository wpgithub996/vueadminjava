package com.wang.vueadmin.service.impl;

import com.wang.vueadmin.common.PlatResult;
import com.wang.vueadmin.common.ResultStatus;
import com.wang.vueadmin.config.RedisCache;
import com.wang.vueadmin.manager.AsyncManager;
import com.wang.vueadmin.mapper.UserMapper;
import com.wang.vueadmin.pojo.LoginBody;
import com.wang.vueadmin.pojo.User;
import com.wang.vueadmin.service.LoginService;
import com.wang.vueadmin.utils.TimeUtils;
import com.wang.vueadmin.utils.SecretUtils;
import com.wang.vueadmin.utils.SendMsg;
import com.wang.vueadmin.utils.TokenUtil;
import com.wang.vueadmin.utils.timetask.AsyncFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Service
public class LoginImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private TokenUtil tokenUtil;
    @Autowired
    private SecretUtils secretUtils;
    @Autowired
    private SendMsg sendMsg;
    @Autowired
    private RedisCache redisCache;

    //账号密码登录
    @Override
    public PlatResult login(LoginBody loginBody) {
        return loginVer(loginBody);
    }

    /**
     * 二维码登录
     * @param verQRinfo
     * @return
     */
    @Override
    public PlatResult loginQR(String verQRinfo) {
        ArrayList list = redisCache.getCacheObject(verQRinfo);
        //1-用户验证通过
        if(null == list){
            return new PlatResult(PlatResult.FAIL(),"二维码失效,请重新获取", null);
        }else {
            if(list.get(0).equals("1")){
                HashMap<String, Object> map = new HashMap<>();
                map.put("token",list.get(1));
                return new PlatResult(ResultStatus.SUCCESS,"登录成功", map);
            }else if(list.get(0).equals("0")){
                return new PlatResult(301,"用户暂未确认登录", null);
            }
        }
        return new PlatResult(PlatResult.FAIL(),"二维码失效,请重新获取", null);
    }
    /*
        用户同意登录
     */
    @Override
    public PlatResult loginVerQR(LoginBody loginBody) {
        /**
         *  此处模拟客户端用户已经登录状态  实际会携带客户端传来的用户
         *  信息 需要验证
         */
        //调用公共方法验证账号
        PlatResult loginVer = loginVer(loginBody);
        if(loginVer.getCode().equals(200)){
            String qRinfo = loginBody.getQRinfo();
            ArrayList list = redisCache.getCacheObject(qRinfo);
            if(list.get(0).equals("0")){
                //1-用户确认登录
                list.set(0,"1");
                //将用户生成的token存到redis 等待页面获取
                list.add(loginVer.getData().get("token"));
                redisCache.setCacheObject(qRinfo,list,5,TimeUnit.MINUTES);
                return new PlatResult(PlatResult.SUCCESS(),"二维码验证成功",null);
            }else {
                return new PlatResult(PlatResult.FAIL(),"二维码验证失败",null);
            }
        }
        return loginVer;
    }

    //登出
    @Override
    public PlatResult logout(String token) {
        //从token中获取用户名
        String username = tokenUtil.getUsernameFromToken(token.replace("Bearer ",""));
        AsyncFactory asyncFactory = new AsyncFactory();
        //修改用户登录状态
        AsyncManager.me().recordlog(username,"1","退出成功");;
        //登记登出信息
        AsyncManager.me().execute(asyncFactory.updLoginStatus(username,"0"));
        return new PlatResult(ResultStatus.SUCCESS,"退出成功",null);
    }

    //注册
    @Override
    public PlatResult regAccount(User user) {
        //验证码是否正确
        String verifyNum = redisCache.getCacheObject(user.getEmail());
        redisCache.deleteObject(user.getEmail());
        if(verifyNum==null || verifyNum.equals("")){
            return new PlatResult(ResultStatus.FAIL,"验证码已过期,请重新获取",null);
        }
        if(!verifyNum.equals(user.getVerifyNum())){
            return new PlatResult(ResultStatus.FAIL,"验证码输入错误",null);
        }
        //账号注册初始化
        user.setStatus("0");
        user.setDel_flag("0");
        user.setCreate_time(TimeUtils.getDateTime());
        //对密码进行加密
        user.setPassword(secretUtils.encrypt(user.getPassword()));
        //判断账号是否已经存在
        if(userMapper.isexist(user.getUsername())>0){
            return new PlatResult(ResultStatus.FAIL,"该账号已经注册",null);
        }
        //写入数据库
        userMapper.saveAcc(user);
        return new PlatResult(ResultStatus.SUCCESS,"注册成功",null);
    }


    //发送邮箱验证码
    public PlatResult sendEmailMsg(String email) {
        if(StringUtils.isEmpty(email)){
            return new PlatResult(ResultStatus.FAIL,"邮箱不能为空",null);
        }
        SendEmailMsg sendEmailMsg = new SendEmailMsg();
        sendEmailMsg.send(email);
        return new PlatResult(ResultStatus.SUCCESS,"邮箱验证码发送成功",null);
    }

    //发送短信验证码
    @Override
    public PlatResult sendPhoneMsg(String phone) {
        if(!StringUtils.isEmpty(phone)){
            return new PlatResult(ResultStatus.SUCCESS,"短信验证码发送成功",null);
        }
        SendPhoneMsg sendPhoneMsg = new SendPhoneMsg();
        sendPhoneMsg.send(phone);
        return new PlatResult(ResultStatus.FAIL,"手机号不能为空",null);
    }

    //登录验证公共方法
    public PlatResult loginVer(LoginBody loginBody){
        AsyncFactory asyncFactory = new AsyncFactory();
        //对密码进行加密
        loginBody.setPassword(secretUtils.encrypt(loginBody.getPassword()));
        //到数据库验证账号密码是否正确
        List<User> users = userMapper.verifyAcc(loginBody);
        if(users.size()>0){
            //验证成功 生成token返回数据
            String token = tokenUtil.createToken(loginBody.getUsername());
            //将生成的token放入redis缓存
            redisCache.setCacheObject(loginBody.getUsername(),token,30, TimeUnit.MINUTES);
            HashMap<String, Object> map = new HashMap<>();
            map.put("token",token);
            AsyncManager.me().recordlog(loginBody.getUsername(),"1","登录成功");
            AsyncManager.me().execute(asyncFactory.updLoginStatus(loginBody.getUsername(),"1"));
            return new PlatResult(ResultStatus.SUCCESS,"登录成功", map);
        }
        //验证失败  提示错误信息
        //登记账户登录信息
        AsyncManager.me().execute(asyncFactory.recordLoginInfo(loginBody.getUsername(),"0","用户名或密码错误"));
        return new PlatResult(ResultStatus.FAIL,"用户名或密码错误",null);
    }

    class SendEmailMsg {
        //创建线程池
        private ExecutorService executor = Executors.newCachedThreadPool() ;

        public void send(String email){
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    sendMsg.sendEmail(email,null,null);
                }
            });
        }
    }


    class SendPhoneMsg {
        //创建线程池
        private ExecutorService executor = Executors.newCachedThreadPool() ;

        public void send(String phone){
            executor.submit(new Runnable() {
                @Override
                public void run() {
                    sendMsg.sendMessage(phone);
                }
            });
        }
    }
}

