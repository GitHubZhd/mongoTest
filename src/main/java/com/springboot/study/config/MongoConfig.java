package com.springboot.study.config;

import com.mongodb.MongoClient;
import com.mongodb.ServerAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ps on 2017/9/30.
 */
@Configuration
public class MongoConfig {

    @Bean
    public MongoTemplate mongoTemplate(){

        List<ServerAddress> sends = new ArrayList<>();
        sends.add(new ServerAddress("localhost",27017));


        // 去掉_class
        MongoDbFactory mongoDbFactory=new SimpleMongoDbFactory(new MongoClient(sends),"MongoTest");

        MappingMongoConverter converter =
                new MappingMongoConverter(new DefaultDbRefResolver(mongoDbFactory), new MongoMappingContext());
        converter.setTypeMapper(new DefaultMongoTypeMapper(null));


        return new MongoTemplate(mongoDbFactory,converter);
    }

}
