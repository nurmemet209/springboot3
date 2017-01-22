# springboot3

* 本节要点
1. @ControllerAdvice注解的使用
2. 全局捕获异常
3. SpringBoot Application中指定扫描目录
4. @Configuration注解
5. WebBindingInitializer 的全局配置与局部配置
6. 拦截器
7. 文件上传



* @ControllerAdvice  
[官网文档](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/web/bind/annotation/ControllerAdvice.html)  
是对Controller的增强,用来定义 @ExceptionHandler, @InitBinder, and @ModelAttribute
方法,是运行时注解，过多使用可能导致性能问题
* 全局捕获异常
```java
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
        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前把该方法的返回值放入Model" +
                "，jsp页面可以通过${city.cityName},${city.cityCode}获取其值");
        City city=new City();
        city.setName("北京");
        city.setCityCode("010");
        return city;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        System.out.println("============应用到所有@RequestMapping注解方法，在其执行之前把http数据转换到对应的Java对象");
    }
}

```
* SpringBoot Application中指定扫描目录
```java
package com.cn.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.support.SpringBootServletInitializer;

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
        SpringApplication.run(SampleApplication.class, args);


    }

}


```
* @Configuration注解
用于配置Bean,简化SpringMVC的xml配置方式,项目中可以有多个@Configuration类
* WebBindingInitializer 的全局配置与局部配置  
上面的@ControllerAdvice里面的@InitBinder方法是每次调用Controller的
@RequestMapping修饰的方式掉用是因为运行时通过反射调用所以效率有些低
下面是全局的配置方式
```java
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

```

* 拦截器  
不能修改reques的内容但是可以通过抛出异常或者return false来停止本次请求
编写一个自定义拦截器继承HandlerInterceptor
```java
package com.cn.configuare;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 1/22/2017.
 */

public class LoginInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("登录拦截器");
        //这里必须返回true返回false表示拦截次请求
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}

```
```java
package com.cn.configuare;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by Administrator on 1/22/2017.
 */
@Configuration
public class CustomWebConfiguare extends WebMvcConfigurerAdapter {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LoginInterceptor loginInterceptor = new LoginInterceptor();
        registry.addInterceptor(loginInterceptor);
        super.addInterceptors(registry);
    }
}

```

* 文件上传
