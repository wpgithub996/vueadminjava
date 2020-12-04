package com.wang.vueadmin.service.impl;

import com.wang.vueadmin.common.PlatResult;
import com.wang.vueadmin.config.RedisCache;
import com.wang.vueadmin.service.FileUpDownService;
import com.wang.vueadmin.service.ORCodeSerive;
import com.wang.vueadmin.utils.VerifyNumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

@Service
public class ORCodeSeriveImpl implements ORCodeSerive {
    @Autowired
    private FileUpDownService fileUpDownService;
    @Autowired
    private RedisCache redisCache;

    @Value("${vsftpd.host}")
    private String host;
    /**
     * 获取登录二维码
     * @return
     */
    @Override
    public PlatResult getQRCodeLogin() {
        try {
            String QRInfo = VerifyNumUtils.getUUID().concat(VerifyNumUtils.getVerifyNum());
            ArrayList<Object> list = new ArrayList<>();
            list.add("0");
            //将二维码信息存入redis,超时时间5分钟
            redisCache.setCacheObject(QRInfo,list,5, TimeUnit.MINUTES);
            PlatResult platResult = fileUpDownService.getQrCode("https://www.maxinyu.wang/QRLogin?info="+QRInfo);
            //用户扫码后会携带此信息到后台进行验证
            platResult.getData().put("loginKey",QRInfo);
            return platResult;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new PlatResult(PlatResult.FAIL(),"获取二维码失败",null);
    }
}
