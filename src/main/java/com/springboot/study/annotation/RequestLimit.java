package com.springboot.study.annotation;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.lang.annotation.*;

/**
 * Created by ps on 2017/7/7.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented

@Order(Ordered.LOWEST_PRECEDENCE)
public @interface RequestLimit {

    int count() default Integer.MAX_VALUE;

    long time() default 60000;
}
