package com.wang.vueadmin.mapper;

import com.wang.vueadmin.pojo.Item;

import java.util.List;

public interface ItemMapper {
    //查询成员
    List<Item> getItemInfo(Item item);

    //修改成员信息
    Integer updItemInfo(Item item);

    //根据Id删除成员
    Integer delItemById(Integer id);

    //添加成员
    Integer insItemInfo(Item item);

    //
}
