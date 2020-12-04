package com.wang.vueadmin.service;

import com.wang.vueadmin.common.PlatResult;
import com.wang.vueadmin.exception.MyException;
import com.wang.vueadmin.pojo.Item;

import javax.servlet.http.HttpServletResponse;

public interface ItemService {
    //查询成员
    PlatResult getItemInfo(Item item);

    //修改成员信息
    PlatResult updItemInfo(Item item);

    //根据Id删除成员
    PlatResult delItemById(Integer id);

    //添加成员
    PlatResult insItemInfo(Item item);

    //导出
    void exportItemInfo(HttpServletResponse response) throws MyException;
}
