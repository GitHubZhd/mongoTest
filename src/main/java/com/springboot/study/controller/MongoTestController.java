package com.springboot.study.controller;

import com.springboot.study.annotation.RequestLimit;
import com.springboot.study.pojo.Car;
import com.springboot.study.pojo.Person;
import org.apache.log4j.Logger;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by ps on 2017/7/7.
 */
@RestController
public class MongoTestController {

    //private static Log log= LogFactory.getLog(MongoTestController.class);
    private static Logger logger=Logger.getLogger(MongoTestController.class);

    @Resource
    private MongoTemplate mongoTemplate;

    @RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response){
        System.out.println(request.getMethod());
        return "mongo";
    }

    @RequestMapping("/add")
    public String add(){
        Map map=new HashMap();
        map.put("aaa","cccc");
        mongoTemplate.indexOps(map.toString());
        return "success";
    }

    @RequestMapping("/add2")
    public String add2(@RequestParam String name,@RequestParam int age,@RequestParam char sex){

        Person person=new Person();
        person.setName(name);
        person.setAge(age);
        person.setSex(sex);
//        person.setName("pengshu");
//        person.setAge(2);
//        person.setSex('M');
        mongoTemplate.save(person);
        return "success";
    }

    @RequestMapping("/")
    public Person show(){
        Person person=new Person();
        person.setName("pengshu");
        return mongoTemplate.findOne(Query.query(Criteria.where("sex").is('M')),Person.class);
    }

    @RequestMapping("/list/{sex}")
    public List<Person> show1(@PathVariable(value = "sex") char sex){
        Person person=new Person();
        person.setName("pengshu");
        return mongoTemplate.find(Query.query(Criteria.where("sex").is(sex)),Person.class);
    }

    @RequestMapping("/all")
    public List<Car> showAll(){
//        logger.debug("debug日志模式");
        logger.info("info日志模式");
//        logger.trace("trace日志模式");
        logger.error("error日志模式");
        logger.fatal("fatal日志模式");

        //return mongoTemplate.findAll(Person.class);
        return mongoTemplate.findAll(Car.class,"car");
    }

    @RequestLimit(count=10,time=5000)
    @RequestMapping("/user")
    public String showuser(){
        Set<String> set =mongoTemplate.getCollectionNames();
        Iterator<String> iterator=set.iterator();
        while (iterator.hasNext()){
            System.out.println("Collection Name："+iterator.next());
        }
        return "zhanghaidong";
    }

    @RequestMapping("/update")
    public String update(){
        mongoTemplate.updateFirst(Query.query(Criteria.where("name").is("pengshu")), Update.update("age","222"),Person.class);
        return "success";
    }

    @RequestMapping("/addCar")
    public String addCar(){
        Car car=new Car();
        car.setColor("red");
        car.setPrice("100000");
        mongoTemplate.save(car);
        return "success";
    }

    @RequestMapping("/addP")
    public String addPerson(){

        Person person=new Person();
        person.setName("li");
        person.setAge(19);
        person.setSex('M');

        mongoTemplate.insert(person,"car");
        return "success";
    }

    @RequestMapping("/addCollection")
    public String addCollection(){

        Car car=new Car();
        car.setColor("red");
        car.setPrice("100000");

        mongoTemplate.insert(car,"Collection_New");
        return "success";
    }

    @RequestMapping("/allCar")
    public List<Car> showAllCar(){
        return mongoTemplate.findAll(Car.class);
    }

    @RequestMapping("/delete")
    public String delete(){
        mongoTemplate.remove(Query.query(Criteria.where("name").is("li")),Person.class,"car");
        return "success";
    }

    @RequestMapping("Modify")
    public String modify(){
        mongoTemplate.findAndModify(Query.query(Criteria.where("color").is("red")),Update.update("price","500万"), Car.class,"car");
        return "OK";
    }
}
