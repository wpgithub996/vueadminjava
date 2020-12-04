package com.wang.vueadmin.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Map;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PlatResult{
    private Integer code;
    private String msg;
    private Map<String,Object> data;

    private static final Integer SUCCESS_CODE = 200;
    private static final Integer FAIL_CODE = 300;

    public static Integer SUCCESS(){
        return SUCCESS_CODE;
    }

    public static Integer FAIL(){
        return FAIL_CODE;
    }
}
