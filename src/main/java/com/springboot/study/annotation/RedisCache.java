package com.springboot.study.annotation;

import java.lang.annotation.*;

/**
 * Created by ps on 2017/7/10.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface RedisCache {
    Class<?> type();//被代理类的全类名，在之后会做为redis hash 的key
    Class<?> type2() default String.class;
}
