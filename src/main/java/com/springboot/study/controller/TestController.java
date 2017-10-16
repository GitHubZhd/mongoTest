package com.springboot.study.controller;

import com.springboot.study.util.JsonConvert;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by ps on 2017/10/12.
 */
@RestController
public class TestController {

    @RequestMapping(value = "/test/args",produces = MediaType.APPLICATION_JSON_VALUE)
    public Object test(HttpServletRequest request){

        Map map=new HashMap();
        map.put("country","中国");
        if(1==1){
            throw new RuntimeException("request error");
        }
        try {
//            return JsonConvert.objectToJson(map);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    @RequestMapping(value = "/test/args1",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void test1(HttpServletRequest request, HttpServletResponse response){

        Map map=new HashMap();
        map.put("country","中国");

        try {
            response.getOutputStream().write(JsonConvert.objectToJson(map).getBytes("UTF-8"));
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
    @RequestMapping(value = "/test/args2",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public Object test2(HttpServletRequest request, HttpServletResponse response){

        Map map=new HashMap();
        map.put("country","中国");

        try {
            response.getOutputStream().write(JsonConvert.objectToJson(map).getBytes("UTF-8"));
            response.setHeader("Content-Type","application/json");
            return null;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * ExceptionHandlerExceptionResolver处理过程总结一下：根据用户调用Controller中相应的方法得到HandlerMethod，之后构造ExceptionHandlerMethodResolver，构造ExceptionHandlerMethodResolver有2种选择，
     * 1.通过HandlerMethod拿到Controller，找出Controller中带有@ExceptionHandler注解的方法(局部)
     * 2.找到@ControllerAdvice注解配置的类中的@ExceptionHandler注解的方法(全局)。
     * 这2种方式构造的ExceptionHandlerMethodResolver中都有1个key为Throwable，value为Method的缓存。之后通过发生的异常找出对应的Method，然后调用这个方法进行处理。这里异常还有个优先级的问题，比如发生的是NullPointerException，但是声明的异常有Throwable和Exception，这时候ExceptionHandlerMethodResolver找Method的时候会根据异常的最近继承关系找到继承深度最浅的那个异常，即Exception。
     */
}
