package com.cn.controller;

import com.cn.bean.City;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.Map;

/**
 * Created by Administrator on 1/18/2017.
 */
@Controller
public class MyController implements EnvironmentAware{

    @GetMapping("/index")
    public String index(Map<String, Object> model) {
        model.put("msg", "welocme");
        return "index";
    }

    @GetMapping("/getCity")
    @ResponseBody
    public City getCity() {
        City city = new City();
        city.setName("上海");
        city.setCityCode("0212");
        return city;
    }

    @GetMapping("/testerror")
    public String errortest(Map<String, Object> model) {
        model.put("msg", "welocme");
        int m = 100 / 0;
        return "index";
    }


    @Override
    public void setEnvironment(Environment environment) {
        String s = environment.getProperty("JAVA_HOME");
        System.out.println("下面是通过实现EnvironmentAware读取的JAVA_HOME的值");
        System.out.println(s);
    }
}
