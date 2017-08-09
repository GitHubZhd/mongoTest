package com.springboot.study.mock;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ps on 2017/8/9.
 */
public interface PersonRepository extends JpaRepository<Person,Long> {
}
