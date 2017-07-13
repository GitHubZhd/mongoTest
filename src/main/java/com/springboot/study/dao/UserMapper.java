package com.springboot.study.dao;

import com.springboot.study.pojo.User;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Created by ps on 2017/7/12.
 */
@Mapper
@Component
public interface UserMapper {

    @Select("select * from user where name =#{name}")
    User findByName(@Param("name") String name);

    @Insert("insert into user(name,age,sex) values (#{name},#{age},#{sex})")
    int insert(@Param("name") String name,@Param("age") int age,@Param("sex") char sex);

    @Insert("insert into user(name,age,sex) values (#{name},#{age},#{sex})")
    int insertByMap(Map<String, Object> map);

    @Insert("insert into user(name,age,sex) values (#{name},#{age},#{sex})")
    int insertByUser(User user);

    @Update("update user set age=#{age} where name=#{name}")
    void update(User user);

    @Delete("delete from user where name=#{name}")
    void delete(String name);
}
