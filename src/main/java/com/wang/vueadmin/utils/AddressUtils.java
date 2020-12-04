package com.wang.vueadmin.utils;

import com.alibaba.fastjson.JSONObject;
import com.wang.vueadmin.utils.text.StringUtils;

/**
 * 获取地址类
 *
 * @author ruoyi
 */
public class AddressUtils
{
     static Boolean isAddressEnabled = true;
    // IP地址查询
    public static final String IP_URL = "http://whois.pconline.com.cn/ipJson.jsp";

    // 未知地址
    public static final String UNKNOWN = "XX XX";

    public static String getRealAddressByIP(String ip)
    {
        String address = UNKNOWN;
        // 内网不查询
//        if (IpUtils.internalIp(ip))
//        {
//            return "内网IP";
//        }
        if (isAddressEnabled)
        {
            try
            {
                String rspStr = HttpUtils.sendGet(IP_URL, "ip=" + ip + "&json=true", Constants.GBK);
                if (StringUtils.isEmpty(rspStr))
                {
                    System.out.print("获取地理位置异常 {}"+ ip);
                    return UNKNOWN;
                }
                JSONObject obj = JSONObject.parseObject(rspStr);
                String region = obj.getString("pro");
                String city = obj.getString("city");
                return String.format("%s %s", region, city);
            }
            catch (Exception e)
            {
                System.out.print("获取地理位置异常 {}"+ ip);
            }
        }
        return address;
    }
}
