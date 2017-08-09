package com.springboot.study.mock;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

/**
 * Created by ps on 2017/8/9.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = PersonTest.class)
@WebAppConfiguration
@Transactional
public class PersonTest {

    @Autowired
    PersonRepository personRepository;

    MockMvc mvc;

    @Autowired
    WebApplicationContext webApplicationContext;

    String exceptedJson;

    @Before
    public void SetUp() throws JsonProcessingException {
        Person p1=new Person("sss");
        Person p2=new Person("ddd");
        personRepository.save(p1);
        personRepository.save(p2);
        exceptedJson=Obj2Json(personRepository.findAll());
        mvc= MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    public String Obj2Json(Object obj) throws JsonProcessingException {
        ObjectMapper objectMapper=new ObjectMapper();
        return objectMapper.writeValueAsString(obj);
    }

    @Test
    public void testPersonController() throws Exception {
        String url="/person";
        MvcResult result=mvc.perform(MockMvcRequestBuilders.get(url).accept(MediaType.APPLICATION_JSON)).andReturn();

        int status=result.getResponse().getStatus();

        String content=result.getResponse().getContentAsString();

        Assert.assertEquals("错误，正确的返回值为200",200,status);
        Assert.assertEquals("错误，返回值和预期值不一致",exceptedJson,status);

    }

}
