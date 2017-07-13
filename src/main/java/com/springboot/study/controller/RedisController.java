package com.springboot.study.controller;

import com.springboot.study.dao.UserRepository;
import com.springboot.study.pojo.User;
import com.springboot.study.util.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * Created by ps on 2017/7/11.
 */
@RestController
@RequestMapping("/redis")
public class RedisController {

    @Resource
    private RedisTemplate<Object, Object>  redisTemplate;
    @Resource
    private RedisUtil redisUtil;

    @Autowired
    private UserRepository userRepository;

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "/add")
    public String add(){
        User p=new User("zhangsan",19,'M');
        redisTemplate.opsForValue().set(p.getName(),p);
        return "OK";
    }

    @RequestMapping(value = "/qry")
    public String qry(){
        User p= (User) redisTemplate.opsForValue().get("zhangsan");

        return "OK"+p.getName()+p.getAge()+p.getSex();
    }

    @RequestMapping("/add2")
    public String add2(){
       stringRedisTemplate.opsForValue().set("Lisi","dddddddddd");
       return "OK" ;
    }

    @RequestMapping("/cache")
    @Cacheable(value="test")
    public String getSessionId(HttpSession session){
        redisUtil.set("789", "测试789");
        System.out.println("进入了方法");
        String string= redisUtil.get("123").toString();
        return string;
    }

    @RequestMapping("/addUser")
    public String addUser(){
        User user =new User("www",19,'M');
        userRepository.add(user);
        return "OK";
    }

    @RequestMapping("/findUser")
    public User findUser(){
        return userRepository.find("www");
    }
}
