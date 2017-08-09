package com.springboot.study.security.java;

import java.security.Security;
import java.util.Map;

/**
 * Created by ps on 2017/8/9.
 */
public class Provider {

    public static void main(String[] args) {


        //遍历环境中的安全提供者
        for(java.security.Provider provider : Security.getProviders()){
            //打印当前提供者信息
            System.out.println(provider);
            //遍历提供者set实体
            for (Map.Entry<Object,Object> p:provider.entrySet()){
                //打印提供者键值
                System.out.println("key:>>>>>>>>>>"+p.getKey()+">>>>>>>>>>>>"+p.getValue());
            }

        }
    }
}
