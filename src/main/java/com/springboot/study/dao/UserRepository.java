package com.springboot.study.dao;

import com.springboot.study.pojo.User;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;

/**
 * Created by ps on 2017/7/11.
 */
@CacheConfig(cacheNames = "users")
public interface UserRepository {

    @CachePut(keyGenerator = "keyGenerator")
    void add(User user);
    @Cacheable
    User find(String name);

}
