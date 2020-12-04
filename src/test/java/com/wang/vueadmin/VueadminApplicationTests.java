package com.wang.vueadmin;

import com.wang.vueadmin.mapper.ItemMapper;
import com.wang.vueadmin.mapper.UserMapper;
import com.wang.vueadmin.pojo.Item;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class VueadminApplicationTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private ItemMapper itemMapper;
    @Test
    void contextLoads() {
    }
    @Test
    void test1(){
//        String aa ="sdfsdfsdf";
//        String[] temp = aa;
//        temp[0] = "a";
//        System.out.println("======"+temp);
//        String s = new String(temp,"UTF-8");
    }
    @Test
    void login(){
        Item item = new Item();
        for (int i = 0; i < 50 ; i++) {
            item.setName("周无"+i);
            item.setAge(26+i);
            item.setGender("女");
            item.setIdCard("1307319"+i+"0623456"+i);
            item.setIsHost("否");
            item.setHostNum(1);
            item.setAddress("小吃街");
            item.setCity("辽宁省沈阳市");
            item.setIsOut("是");
            item.setPhone("13"+i+"656"+i+"75");
            itemMapper.insItemInfo(item);
        }
        for (int i = 0; i < 50 ; i++) {
            item.setName("赵四"+i);
            item.setAge(18+i);
            item.setGender("女");
            item.setIdCard("1307239"+i+"0623456"+i);
            item.setIsHost("否");
            item.setHostNum(1);
            item.setAddress("武城街");
            item.setCity("河北省张家口市");
            item.setIsOut("是");
            item.setPhone("18"+i+"655"+i+"32");
            itemMapper.insItemInfo(item);
        }

    }
}
