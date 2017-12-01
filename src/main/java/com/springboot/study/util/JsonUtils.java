package com.springboot.study.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by ps on 2017/12/1.
 */
public class JsonUtils {

    private static Gson gson = new GsonBuilder().create();

    public static String obj2Json(Object obj){

        try{
            return gson.toJson(obj);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }

    public static <T> T json2Obj(String json,Class<T> entityClass){

        try{
            return gson.fromJson(json,entityClass);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
