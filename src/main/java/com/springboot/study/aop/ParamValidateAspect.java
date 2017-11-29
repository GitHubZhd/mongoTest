package com.springboot.study.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.junit.BeforeClass;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * Created by ps on 2017/10/16.
 */
@Component
@Aspect
public class ParamValidateAspect {

    private static Validator validator;

    @BeforeClass
    public static void setUpValidator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    //定义切点表达式
    @Pointcut("execution(* com.springboot.study.controller..*(..))")
    @Order(2)
    public void pointcutControlller(){
    }


    //的定义前置通知
    @Before("pointcutControlller()")
    public void before(JoinPoint joinPoint) {
        System.out.println("方法执行前执行.....before");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        //validator.validate()
    }
}
