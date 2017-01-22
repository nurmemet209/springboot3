package com.cn.configuare;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.springframework.boot.autoconfigure.web.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Created by Administrator on 1/21/2017.
 */
@Configuration
public class CustomConfiguration {


    /**
     * HttpMessageConverters
     * springboot 官方文档：http://docs.spring.io/spring-boot/docs/2.0.0.BUILD-SNAPSHOT/reference/htmlsingle/
     * Spring MVC uses the HttpMessageConverter interface to convert HTTP requests and responses.
     * Sensible defaults are included out of the box, for example Objects can be automatically converted to
     * JSON (using the Jackson library) or XML (using the Jackson XML extension if available, else using JAXB).
     * Strings are encoded using UTF-8 by default.
     *If you need to add or customize converters you can use Spring Boot’s HttpMessageConverters class:
     * SpringMVC使用HttpMessageConverter接口将HTTP requests and responses转换成JSON数据
     * 如果你想自定义这个转换器你可以使用SpringBoot的 HttpMessageConverters类
     * @return
     */
    @Bean
    public HttpMessageConverters customConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.PrettyFormat);
        fastConverter.setFastJsonConfig(fastJsonConfig);
        HttpMessageConverter<?> converter = fastConverter;
        return new HttpMessageConverters(converter);
    }

    /**
     * Spring MVC uses a WebBindingInitializer to initialize a WebDataBinder for a particular request.
     * If you create your own ConfigurableWebBindingInitializer @Bean,
     *  Spring Boot will automatically configure Spring MVC to use it.
     *
     * @return
     */
    @Bean
    WebBindingInitializer getCustomDataBinder() {
        ConfigurableWebBindingInitializer dataBinder = new ConfigurableWebBindingInitializer();
        return dataBinder;
    }




}
