package com.cn.app;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.core.env.Environment;

import java.io.PrintStream;

/**
 * Created by Administrator on 1/17/2017.
 */

@SpringBootApplication(scanBasePackages = "com.cn")
public class SampleApplication extends SpringBootServletInitializer {



    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(SampleApplication.class);
    }


    public static void main(String[] args) {
        //修改启动方式
        SpringApplication application=new SpringApplication(SampleApplication.class);

        //自己编写Banner的输出样式
//        application.setBanner(new Banner() {
//            @Override
//            public void printBanner(Environment environment, Class<?> sourceClass, PrintStream out) {
//
//            }
//        });
        //关闭Banner
        //application.setBannerMode(Banner.Mode.OFF);
        application.run(args);
        //SpringApplication.run(SampleApplication.class, args);


    }

}

