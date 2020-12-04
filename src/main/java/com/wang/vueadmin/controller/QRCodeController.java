package com.wang.vueadmin.controller;

import com.wang.vueadmin.common.PlatResult;
import com.wang.vueadmin.service.FileUpDownService;
import com.wang.vueadmin.service.ORCodeSerive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/api")
@ResponseBody
public class QRCodeController {
    @Autowired
    private FileUpDownService fileUpDownService;
    @Autowired
    private ORCodeSerive qrcodeservice;

//    @RequestMapping(value = "/getQRCode",produces = MediaType.IMAGE_JPEG_VALUE)
//    public void getQRCode(String url, HttpServletResponse response) throws IOException {
//        ServletOutputStream stream = null;
//        try {
//            stream = response.getOutputStream();
//            QRCodeUtil.encode(url,stream);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }finally {
//            stream.flush();
//            stream.close();
//        }
//    }
    @RequestMapping(value = "/getQRCode/loginQRCode")
    public PlatResult getQRCode() throws Exception {
        return qrcodeservice.getQRCodeLogin();
    }
}
