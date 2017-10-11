package com.springboot.study.dao;

import com.springboot.study.pojo.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by ps on 2017/9/30.
 */
public interface CustomerRepository extends MongoRepository<User,Long> {
}
