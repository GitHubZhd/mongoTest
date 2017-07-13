package com.springboot.study.Interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Created by ps on 2017/7/7.
 * 注册拦截器
 */
@Component
public class WebMvcConfigurerConfig extends WebMvcConfigurerAdapter {

    public void addInterceptors(InterceptorRegistry registry) {

        InterceptorRegistration lr=registry.addInterceptor(new MyInterceptor());
        //配置拦截的路径
        lr.addPathPatterns("/**");
        //配置不拦截的路径
        lr.excludePathPatterns("/**.html");
        super.addInterceptors(registry);
    }
}
