package com.springboot.demo.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理
 */
//@ControllerAdvice
public class ExceptionConfig {
    // 处理异常
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    public Map<String, Object> handlerException(Exception ex){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code",-1);
        map.put("msg",ex.toString());
        return map;
    }

    // 对自定义异常处理
    @ResponseBody
    @ExceptionHandler(value = BusinessException.class)
    public Map<String, Object> errorHandler(BusinessException ex){
        Map<String,Object> map=new HashMap<String,Object>();
        map.put("code",ex.getCode());
        map.put("msg",ex.getMsg());
        return map;
    }
}
