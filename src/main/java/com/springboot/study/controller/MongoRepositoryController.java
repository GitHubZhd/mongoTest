package com.springboot.study.controller;

import com.springboot.study.dao.CustomerRepository;
import com.springboot.study.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ps on 2017/9/30.
 */
@RestController
public class MongoRepositoryController {

    @Autowired
    private CustomerRepository customerRepository;

    @RequestMapping("/user/info")
    public Object userInfo(){

        User user=new User();
        user.setAge(10);
        user.setName("ZZZZ");
        user.setSex('M');

        customerRepository.save(user);


        List<User> list=customerRepository.findAll();

        System.out.println(list);

        return list;
    }
}
