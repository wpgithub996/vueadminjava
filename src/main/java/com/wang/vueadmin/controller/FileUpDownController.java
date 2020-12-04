package com.wang.vueadmin.controller;

import com.wang.vueadmin.common.PlatResult;
import com.wang.vueadmin.service.FileUpDownService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/api/file")
@ResponseBody
public class FileUpDownController {
    @Autowired
    private FileUpDownService fileUpDownService;

    @RequestMapping("/upLoad")
    public PlatResult fileUpLoad(@RequestParam("file") MultipartFile file){
        return fileUpDownService.fileupload(file);
    }

    @RequestMapping()
    public PlatResult createWordImage(@RequestParam("word")String word,@RequestParam("file") MultipartFile file
            ,@RequestParam("fontType")String fontType,@RequestParam("fontSize")String fontSize){
        return fileUpDownService.createWordImage(word, file, fontType, fontSize);
    }
}
