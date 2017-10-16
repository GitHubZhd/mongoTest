package com.springboot.study.aop.aopStudy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by ps on 2017/10/16.
 * InvocationHandler is the interface implemented by the invocation handler of a proxy instance.

 invoke 调用

 Each proxy instance has an associated invocation handler.
 When a method is invoked on a proxy instance, the method invocation is encoded and dispatched to the invoke method of its invocation handler.
 */
public class ProxyFactory implements InvocationHandler {

    private Object delegate;

    public Object bind(Object delegate){
        this.delegate=delegate;
        return Proxy.newProxyInstance(delegate.getClass().getClassLoader(),delegate.getClass().getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("start-----------------");
        Object result=null;
        try {
            result=method.invoke(delegate,args);
        }catch (Exception e){

        }
        System.out.println("end====================");
        return result;
    }
}
