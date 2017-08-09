package com.springboot.study.mock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Created by ps on 2017/8/9.
 */
@RequestMapping("/person_mock")
@RestController
public class PersonController {

    @Autowired
    PersonRepository personRepository;

    @RequestMapping(method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Person> findAll(){
        System.out.println("================");
        return personRepository.findAll();
    }
}
