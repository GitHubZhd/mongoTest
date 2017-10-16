package com.springboot.study.test.Reflect;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Created by ps on 2017/10/11.
 *
 *
 一、通过Class类获取成员变量、成员方法、接口、超类、构造方法等

 在java.lang.Object 类中定义了getClass()方法，因此对于任意一个Java对象，都可以通过此方法获得对象的类型。Class类是Reflection API 中的核心类，它有以下方法
 getName()：获得类的完整名字。
 getFields()：获得类的public类型的属性。
 getDeclaredFields()：获得类的所有属性。
 getMethods()：获得类的public类型的方法。
 getDeclaredMethods()：获得类的所有方法。
 getMethod(String name, Class[] parameterTypes)：获得类的特定方法，name参数指定方法的名字，parameterTypes 参数指定方法的参数类型。
 getConstructors()：获得类的public类型的构造方法。
 getConstructor(Class[] parameterTypes)：获得类的特定构造方法，parameterTypes 参数指定构造方法的参数类型。
 newInstance()：通过类的不带参数的构造方法创建这个类的一个对象。
 */
public class RefConstructor {

    public static void main(String[] args) {


        RefConstructor ref = new RefConstructor();
        try {
            ref.getConstructor();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void getConstructor() throws Exception {
        Class c;
        c = Class.forName("java.lang.Long");
        Class cs[] = {java.lang.String.class};

        System.out.println("\n-------------------------------\n");

        Constructor cst1 = c.getConstructor(cs);
        System.out.println("1、通过参数获取指定Class对象的构造方法：");
        System.out.println(cst1.toString());

        Constructor cst2 = c.getDeclaredConstructor(cs);
        System.out.println("2、通过参数获取指定Class对象所表示的类或接口的构造方法：");
        System.out.println(cst2.toString());

        Constructor cst3 = c.getEnclosingConstructor();
        System.out.println("3、获取本地或匿名类Constructor 对象，它表示基础类的立即封闭构造方法。");
        if (cst3 != null) System.out.println(cst3.toString());
        else System.out.println("-- 没有获取到任何构造方法！");

        Constructor[] csts = c.getConstructors();
        System.out.println("4、获取指定Class对象的所有构造方法：");
        for (int i = 0; i < csts.length; i++) {
            System.out.println(csts[i].toString());
        }

        System.out.println("\n-------------------------------\n");

        Type types1[] = c.getGenericInterfaces();
        System.out.println("1、返回直接实现的接口：");
        for (int i = 0; i < types1.length; i++) {
            System.out.println(types1[i].toString());
        }

        Type type1 = c.getGenericSuperclass();
        System.out.println("2、返回直接超类：");
        System.out.println(type1.toString());

        Class[] cis = c.getClasses();
        System.out.println("3、返回超类和所有实现的接口：");
        for (int i = 0; i < cis.length; i++) {
            System.out.println(cis[i].toString());
        }

        Class cs1[] = c.getInterfaces();
        System.out.println("4、实现的接口");
        for (int i = 0; i < cs1.length; i++) {
            System.out.println(cs1[i].toString());
        }

        System.out.println("\n-------------------------------\n");

        Field fs1[] = c.getFields();
        System.out.println("1、类或接口的所有可访问公共字段：");
        for (int i = 0; i < fs1.length; i++) {
            System.out.println(fs1[i].toString());
        }

        Field f1 = c.getField("MIN_VALUE");
        System.out.println("2、类或接口的指定已声明指定公共成员字段：");
        System.out.println(f1.toString());

        Field fs2[] = c.getDeclaredFields();
        System.out.println("3、类或接口所声明的所有字段：");
        for (int i = 0; i < fs2.length; i++) {
            System.out.println(fs2[i].toString());
        }

        Field f2 = c.getDeclaredField("serialVersionUID");
        System.out.println("4、类或接口的指定已声明指定字段：");
        System.out.println(f2.toString());

        System.out.println("\n-------------------------------\n");

        Method m1[] = c.getMethods();
        System.out.println("1、返回类所有的公共成员方法：");
        for (int i = 0; i < m1.length; i++) {
            System.out.println(m1[i].toString());
        }

        Method m2 = c.getMethod("longValue", new Class[]{});
        System.out.println("2、返回指定公共成员方法：");
        System.out.println(m2.toString());

    }
}
