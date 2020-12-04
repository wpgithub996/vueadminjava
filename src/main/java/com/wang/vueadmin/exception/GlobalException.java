package com.wang.vueadmin.exception;

import com.wang.vueadmin.common.PlatResult;
import com.wang.vueadmin.common.ResultStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public PlatResult catchex(MyException e) { ;
        return new PlatResult(e.getCode(),e.getMessage(),null);
    }

}
