package com.springboot.study.daoImpl;

import com.springboot.study.dao.UserRepository;
import com.springboot.study.pojo.User;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by ps on 2017/7/11.
 */
@Component
public class UserRepositoryImpl implements UserRepository {

    private static final ConcurrentMap<String,Object> map=new ConcurrentHashMap<String,Object>();

    private static final Logger logger=Logger.getLogger(UserRepositoryImpl.class);

    @Override
    public void add(User user) {
        logger.info("1111111");
        map.put(user.getName(),user);
        logger.info("2222222222");
    }

    @Override
    public User find(String name) {
        logger.info("333333333");
        return (User) map.get(name);
    }
}
