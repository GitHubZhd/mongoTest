package com.springboot.study.controller;

import com.springboot.study.dao.UserMapper;
import com.springboot.study.pojo.User;
import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ps on 2017/7/12.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class MyBatisTest {

    @Autowired
    private UserMapper userMapper;

    private static final Logger logger=Logger.getLogger(MyBatisTest.class);

    @Test
    public void mybatisTest(){

//        int i=userMapper.insert("zhangsan",19,'M');
//        logger.info(i);

//        Map<String,Object> map=new HashMap<String,Object>();
//        map.put("name","lisi");
//        map.put("age",20);
//        map.put("sex",'M');
//        int j=userMapper.insertByMap(map);
//        logger.info(j);
//
        User user=new User();
//        user.setName("wangwu");
//        user.setAge(26);
//        user.setSex('F');
//       int k=userMapper.insertByUser(user);
//        logger.info(k);
//
        User userr=userMapper.findByName("zhangsan");
        logger.info(userr);
//
        user.setName("lisi");
        user.setAge(88);
        userMapper.update(user);
//
        userMapper.delete("zzz");


    }




}
/**
 * 创建一个数据库
 create database pengshu;
 选择你所创建的数据库
 use pengshu;

 创建表
 CREATE  TABLE user (
 name VARCHAR(45) NOT NULL ,
 age int NOT NULL ,
 sex char(1) NOT NULL,
 PRIMARY KEY (name)
 );
 */
