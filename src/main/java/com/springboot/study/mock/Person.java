package com.springboot.study.mock;


import javax.persistence.*;

/**
 * Created by ps on 2017/8/9.
 */
@Entity
public class Person {

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    public Person() {
        super();
    }

    public Person(String name) {
        super();
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
