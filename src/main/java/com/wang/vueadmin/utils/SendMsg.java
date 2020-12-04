package com.wang.vueadmin.utils;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.wang.vueadmin.config.RedisCache;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.concurrent.TimeUnit;


@Component
public class SendMsg {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private JavaMailSender sender;
    @Value("${spring.mail.host}")
    private String host;
    @Value("${spring.mail.username}")
    private String username;
    @Value("${spring.mail.password}")
    private String password;
    private static final Logger log = LoggerFactory.getLogger(HttpUtils.class);
    public void sendMessage(String phone) {
        String[] split = phone.split("=");
        // 短信应用 SDK AppID
        int appid = 1400259682;

        // 短信应用 SDK AppKey
        String appkey = "1ae80e71c7696655fb090eb237010edb";

        // 需要发送短信的手机号码
        String phoneNumber =split[0]+"";

        // 短信模板 ID，需要在短信应用中申请
        int templateId = 425584;

        // 签名参数使用的是`签名内容`，而不是`签名ID`
        //需要在“腾讯云”申请
        String smsSign = "王王多鱼公众号";
        //获得发送的验证码
        String verifyNum = VerifyNumUtils.getVerifyNum();
        //将验证码放到redis中
        redisCache.setCacheObject(phone,verifyNum);
        //6. 发送参数，为数组格式；与短信模板需要对应
        String[] params = {verifyNum,"5"};

        //7. 封装短信应用码
        SmsSingleSender ssender = new SmsSingleSender(appid,appkey);
        //地区，电话，模板ID，验证码，签名
        try {
            SmsSingleSenderResult result = ssender.sendWithParam(
                    "86", phoneNumber, templateId, params, smsSign, "", "");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendEmail(String email,String subject,String msg) {
        //获得发送的验证码
        String verifyNum = VerifyNumUtils.getVerifyNum();
        //将验证码放到redis中
        redisCache.setCacheObject(email,verifyNum,60, TimeUnit.SECONDS);
        if(StringUtils.isEmpty(subject)){
            subject = "注册验证码";
        }
        if(StringUtils.isEmpty(msg)){
            msg = "本次验证按为 "+verifyNum+" ,请在一分钟内输入";
        }
        //创建邮件内容
        SimpleMailMessage message=new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(email);
        message.setSubject(subject);
        message.setText(msg);
        //发送邮件
        sender.send(message);
        log.info("邮件发送成功! 邮箱号: "+ email +",验证码为: "+ verifyNum);
    }
}
