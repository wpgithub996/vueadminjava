package com.wang.vueadmin.service.impl;

import com.wang.vueadmin.common.PlatResult;
import com.wang.vueadmin.common.ResultStatus;
import com.wang.vueadmin.exception.MyException;
import com.wang.vueadmin.mapper.ItemMapper;
import com.wang.vueadmin.pojo.Item;
import com.wang.vueadmin.service.ItemService;
import com.wang.vueadmin.utils.ToExclUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

@Service
public class ItemServiceImpl implements ItemService {
    protected static Logger log = Logger.getLogger(com.wang.vueadmin.service.impl.ItemServiceImpl.class);
    @Autowired
    private ItemMapper itemMapper;
    private Date date;
    @Override
    public PlatResult getItemInfo(Item item) {
        try {
            HashMap<String, Object> map = new HashMap<>();
            //分页起始值赋值
            item.setPageNum((item.getPageNum()-1)*10);
            map.put("itemInfo",itemMapper.getItemInfo(item));
            return new PlatResult(ResultStatus.SUCCESS,"处理成功", map);
        }catch (Exception e){
            return new PlatResult(ResultStatus.ERROR,"内部错误",null);
        }
    }

    @Override
    public PlatResult updItemInfo(Item item) {
        Integer code = itemMapper.updItemInfo(item);
        if(code>0){
            return new PlatResult(ResultStatus.SUCCESS,"处理成功",null);
        }else {
            return new PlatResult(ResultStatus.ERROR,"修改失败,请重试",null);
        }
    }

    @Override
    public PlatResult delItemById(Integer id) {
        Integer code = itemMapper.delItemById(id);
        if(code>0){
            log.info(date+" ,删除 ID ="+ id + "成员成功");
            return new PlatResult(ResultStatus.SUCCESS,"处理成功",null);
        }else {
            log.error(date+" ,删除 ID ="+ id + "成员失败");
            return new PlatResult(ResultStatus.ERROR,"删除失败,请重试",null);
        }
    }

    @Override
    public PlatResult insItemInfo(Item item) {
        Item itemcopy = item;
        Integer code = itemMapper.insItemInfo(item);
        if(code>0){
            if(null != item.getIdCard()) {
                //判断是否户主
                if (item.getIsHost().equals("是")) {
                    //将hostnum设为自己的id
                    itemcopy.setHostNum(item.getId());
                    itemcopy.setId(item.getId());
                    itemMapper.updItemInfo(itemcopy);
                }
            }
            return new PlatResult(ResultStatus.SUCCESS,"处理成功",null);
        }else {
            return new PlatResult(ResultStatus.ERROR,"新增成员失败,请重试",null);
        }
    }

    @Override
    public void exportItemInfo(HttpServletResponse response) throws MyException {
        //标题
        ArrayList<String> titles = new ArrayList<>();
        titles.add("编号");
        titles.add("姓名");
        titles.add("性别");
        titles.add("身份证号");
        titles.add("年龄");
        titles.add("电话");
        titles.add("所在地区");
        titles.add("详细地址");
        titles.add("是否外出");
        titles.add("是否户主");

        try {
            ToExclUtils.toExcel(response,titles,itemMapper.getItemInfo(new Item()));
        } catch (Exception e) {
            log.error(e);
            throw new MyException(ResultStatus.ERROR,"导出失败");
        }
    }
}
