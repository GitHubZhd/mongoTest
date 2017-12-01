package com.springboot.study.util;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.Iterator;
import java.util.Properties;

/**
 * Created by ps on 2017/12/1.
 */
public class PropertyUtil {

    /**
     * 加载属性文件内容到Properties
     * @param filePath
     * @return
     */
    public static Properties  loadProperty(String filePath){

         Properties prop = new Properties();
         InputStream in = null;
         try{
             //读取属性文件a.properties
             in = new BufferedInputStream(new FileInputStream(filePath));
             prop.load(in);     ///加载属性列表
             Iterator<String> it=prop.stringPropertyNames().iterator();
             while(it.hasNext()){
                 String key=it.next();
                 System.out.println(key+":"+prop.getProperty(key));
             }
             in.close();
             return prop;
         }
         catch(Exception e){
             System.out.println(e);
             return null;
         }finally {
             if(in!=null){
                 try{
                     in.close();
                 }catch (Exception e){
                     e.printStackTrace();
                 }
             }
         }
    }

    /**
     * 保存键值对到属性文件
     * @return
     */
    public static Boolean uploadProperty(){

        Properties prop = new Properties();
        FileOutputStream oFile = null;
        try{
            //保存属性到b.properties文件
            oFile = new FileOutputStream("b.properties", true);//true表示追加打开
            prop.setProperty("phone", "10086");
            prop.store(oFile, "The New properties file");//后面内容是注释
            oFile.close();
            return true;

        }catch (Exception e){
            e.printStackTrace();
            return false;
        }finally {
            if(oFile!=null){
                try{
                    oFile.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
}
