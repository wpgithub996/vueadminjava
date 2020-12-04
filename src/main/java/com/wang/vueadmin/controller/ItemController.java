package com.wang.vueadmin.controller;

import com.wang.vueadmin.common.PlatResult;
import com.wang.vueadmin.exception.MyException;
import com.wang.vueadmin.pojo.Item;
import com.wang.vueadmin.service.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

/**
 * 成员
 */
@Controller
@RequestMapping("/api/item")
@ResponseBody
public class ItemController {
    @Autowired
    private ItemService itemService;

    @RequestMapping("/getItemInfo")
    public PlatResult getItemInfo(@RequestBody Item item) {
        return itemService.getItemInfo(item);
    }

    @RequestMapping("/updItemInfo")
    public PlatResult updItemInfo(@RequestBody Item item) {
        return itemService.updItemInfo(item);
    }

    @RequestMapping("/delItemById/{id}")
    public PlatResult delItemById(@PathVariable("id") Integer id) {
        return itemService.delItemById(id);
    }

    @RequestMapping("/insItemInfo")
    public PlatResult insItemInfo(@RequestBody(required = false) Item item) {
        return itemService.insItemInfo(item);
    }

    //导出信息
    @RequestMapping("/exportInfo")
    @ResponseBody
    public void getExcel(HttpServletResponse response) {
        try {
            itemService.exportItemInfo(response);
//            response.setContentType("application/vnd.ms-excel;charset=utf-8");
//            response.setHeader("Content-Disposition", "attachment; filename=人员信息表.xlsx");
        } catch (MyException e) {
            e.printStackTrace();
        }
    }
}
