package com.springboot.study.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.IOException;

/**
 * Created by ps on 2017/10/9.
 */
public class JsonConvert {

    public static String objectToJson(Object obj) throws JsonProcessingException {

        ObjectMapper mapper = getInstance();

        return mapper.writeValueAsString(obj);
    }

    public static <T> T jsonToObject(String json,Class<T> entityClass) throws IOException {

        ObjectMapper mapper=getInstance();

        return mapper.readValue(json,entityClass);
    }

    public static <T> T jsonToObject(String json) throws IOException {

        ObjectMapper mapper=getInstance();
        return mapper.readValue(json, (Class<T>) Object.class);
    }

    public static ObjectMapper getInstance(){

        ObjectMapper mapper=new ObjectMapper();
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

        //设置null值不参与序列化(字段不被显示)
        //mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        //禁用空对象转换json校验
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS,false);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);

        return mapper;

    }
}
