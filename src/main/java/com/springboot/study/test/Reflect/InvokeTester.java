package com.springboot.study.test.Reflect;

import java.lang.reflect.Method;

/**
 * Created by ps on 2017/10/11.
 */
public class InvokeTester {

    public int add(int param1, int param2) {
        return param1 + param2;
    }

    public String echo(String msg) {
        return "echo: " + msg;
    }

    public static void main(String[] args) throws Exception {
        Class<?> classType = InvokeTester.class;
        Object invokeTester = classType.newInstance();

        // Object invokeTester = classType.getConstructor(new
        // Class[]{}).newInstance(new Object[]{});


        //获取InvokeTester类的add()方法
        Method addMethod = classType.getMethod("add", new Class[]{int.class, int.class});
        //调用invokeTester对象上的add()方法
        Object result = addMethod.invoke(invokeTester, new Object[]{new Integer(100), new Integer(200)});
        System.out.println((Integer) result);


        //获取InvokeTester类的echo()方法
        Method echoMethod = classType.getMethod("echo", new Class[]{String.class});
        //调用invokeTester对象的echo()方法
        result = echoMethod.invoke(invokeTester, new Object[]{"Hello"});
        System.out.println((String) result);
    }
}
