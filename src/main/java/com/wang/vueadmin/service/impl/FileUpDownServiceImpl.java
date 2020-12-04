package com.wang.vueadmin.service.impl;

import com.wang.vueadmin.common.PlatResult;
import com.wang.vueadmin.service.FileUpDownService;
import com.wang.vueadmin.utils.QRCodeUtil;
import com.wang.vueadmin.utils.SftpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

@Service
@PropertySource({"classpath:vsftpd.properties"})
public class FileUpDownServiceImpl implements FileUpDownService {

    @Value("${vsftpd.username}")
    private String username;

    @Value("${vsftpd.password}")
    private String password;
    //    @Value("${vsftpd.host}")
    @Value("${vsftpd.host}")
    private String host;
    //    @Value("${vsftpd.port}")
    @Value("${vsftpd.port}")
    private int port;
    //    @Value("${vsftpd.directory}")
    @Value("${vsftpd.directory}")
    private String directory;
    @Value("${vsftpd.QRdirectory}")
    private String QRdirectory;
    @Value("${vsftpd.localfilepath}")
    private String localfilepath;

    @Override
    public PlatResult fileupload(MultipartFile file) {
        //返回文件路径
        String resultPath = "";
        try {
            SftpUtil sftpUtil = new SftpUtil(username,password,host,port);
            String uploadMultipartFilePath = sftpUtil.uploadMultipartFile(file, directory);
            //拼接路径
            resultPath = resultPath.concat(host).concat(":8083/").concat(uploadMultipartFilePath);
            HashMap<String, Object> map = new HashMap<>();
            map.put("filePath",resultPath);
            return new PlatResult(PlatResult.SUCCESS(),"上传成功",map);
        } catch (Exception e) {
            e.printStackTrace();
            HashMap<String, Object> map = new HashMap<>();
            map.put("errorinfo",e);
            return new PlatResult(PlatResult.FAIL(),"上传失败",map);
        }
    }

    @Override
    public File download(String directory, String remoteFileName, String localFile) {
        return null;
    }

    @Override
    public PlatResult createWordImage(String word,MultipartFile file,String fontType,String fontSize) {
        try {
            BufferedImage image = ImageIO.read((File) file);
            //获取图片属性
            BufferedImage newImage = new BufferedImage(image.getWidth(), image.getHeight(), image.getType());
            Graphics2D graphics2D=(Graphics2D)newImage.getGraphics();
            graphics2D.setFont(new Font(fontType,Font.BOLD,Integer.parseInt(fontSize)));
            int index=0;
            for (int y = 0; y <image.getHeight() ; y+=12) {
                for (int x = 0; x <image.getWidth() ; x+=12) {
                    int pxcolor = image.getRGB(x, y);
                    int r = (pxcolor & 0xff0000) >> 16,
                            g = (pxcolor & 0xff00) >> 8,
                            b = pxcolor & 0xff;
                    graphics2D.setColor(new Color(r, g, b));
                    graphics2D.drawString(String.valueOf(word.charAt(index % word.length())), x, y);
                    index++;
                }
            }
//            ImageIO.write(newImage,"JPG",new FileOutputStream("C:\\Users\\laowang\\Desktop\\temp.jpg"));
            return fileupload((MultipartFile) newImage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new PlatResult(PlatResult.FAIL(),"生成失败",null);
    }

    @Override
    public PlatResult getQrCode(String content) throws Exception {
        SftpUtil sftpUtil = new SftpUtil(username,password,host,port);
        String filename = sftpUtil.uploadQRCode(QRCodeUtil.getInputStream(content),QRdirectory);
        String resultPath = host.concat(":8083/QRImage/").concat(filename);
        HashMap<String, Object> map = new HashMap<>();
        map.put("filePath",resultPath);
        return new PlatResult(PlatResult.SUCCESS(),"业务成功",map);
    }
}
