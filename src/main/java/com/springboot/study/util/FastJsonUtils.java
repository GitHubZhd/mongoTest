package com.springboot.study.util;

import com.alibaba.fastjson.JSON;

/**
 * Created by ps on 2017/12/1.
 */
public class FastJsonUtils {

    public static String obj2Json(Object obj){

        try{
            return JSON.toJSONString(obj);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T json2Obj(String json,Class<T> entityClass){

        try{
            return JSON.parseObject(json,entityClass);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
