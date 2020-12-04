package com.wang.vueadmin.service;

import com.wang.vueadmin.common.PlatResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;

public interface FileUpDownService {
    //文件上传
    PlatResult fileupload(MultipartFile file);

    //文件下载
    File download(String directory, String remoteFileName, String localFile);

    //生成文字图片

    PlatResult createWordImage(String word,MultipartFile file,String fontType,String fontSize);

    //获得二维码
    PlatResult getQrCode(String content) throws Exception;
}
