package com.springboot.study.controller;

import com.springboot.study.annotation.RedisCache;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ps on 2017/7/10.
 */
@RestController
public class AopController {

    @RequestMapping(value = "/hello")
    public String hello(@RequestParam(value = "name", required = true) String name) {
        String result = "hello  " + name;
        System.out.println(result);
        return result;
    }

    @RequestMapping(value = "/world")
    public String world(@RequestParam(value = "arg", required = true) String arg){
        String result = "world  " + arg;
        System.out.println(result);
        return result;
    }

    @RequestMapping(value="/annotation")
    @RedisCache(type=String.class)
    public String annotation(){
        return "annotation1111111";
    }

    @RequestMapping(value="/annotationInt")
    @RedisCache(type=Integer.class)//自定义注解有默认值就不是必输的，type是必输的，type2不是
    public int annotationInt(){
        int a=0;
        return 6;
    }
}
