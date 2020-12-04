package com.wang.vueadmin.pojo;

import lombok.Data;
import java.io.Serializable;

@Data
public class Item implements Serializable {
    private Integer id;
    private String name;
    private Integer age;
    private String phone;
    private String idCard;
    private String gender;
    private String city;
    private String address;
    private String isOut;
    private String isHost;
    private Integer hostNum;//户主编号为所属户主的id
    private Integer pageNum;
    private Integer total;//总页数
    private String pictPath;
}
