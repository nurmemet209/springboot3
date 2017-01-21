package com.cn.app;

import com.cn.bean.City;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;

/**
 * Created by Administrator on 1/21/2017.
 */
@ControllerAdvice
public class GlobalDefaultExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public void defaultErrorHandler(Exception e){
        e.printStackTrace();
        System.out.println("custom  glabol error handler");


    }

    @ModelAttribute
    public City getUserName(){
        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前把返回值放入Model");
        City city=new City();
        city.setName("北京");
        city.setCityCode("010");
        return city;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {

        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前初始化数据绑定器");
    }
}
