package com.springboot.study.security.java;

import java.security.MessageDigest;

/**
 * 信息摘要算法
 * 信息摘要是安全的单向哈希函数，它接收任意大小的数据，并输出固定长度的哈希值。
 * Created by ps on 2017/8/9.
 */
public class Encrypt01 {

    private static final String encryModel="MD5";
    private static final String encryModel02="SHA";

    public static void main(String[] args) {

        String md5=encrypt("MD5","中国_china");
        System.out.println(md5);

        System.out.println(encrypt02("MD5","中国_china"));


        System.out.println(encrypt02("SHA","中国_china"));
        System.out.println(encrypt("SHA","中国_china"));

    }

    /**
     * 32λmd5.
     * 32位小写md5加密
     * @param str
     * @return
     */
    public  static String md5(String str) {
        return encrypt(encryModel, str);
    }

    public static String encrypt(String algorithm, String str) {
        try {



            //返回实现指定摘要算法的 MessageDigest 对象
            MessageDigest md = MessageDigest.getInstance(algorithm);
            //使用指定的 byte 数组更新摘要
            md.update(str.getBytes());
            StringBuffer sb = new StringBuffer();
            //通过执行诸如填充之类的最终操作完成哈希计算
            byte[] bytes = md.digest();


            System.out.println(bytes.length);
            for (int i = 0; i < bytes.length; i++) {
                int b = bytes[i] & 0xFF;
                if (b < 0x10) {
                    sb.append('0');
                }
                sb.append(Integer.toHexString(b));
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }


    public static String encrypt02(String algorithm, String str) {
        try {

            //返回实现指定摘要算法的 MessageDigest 对象
            MessageDigest md = MessageDigest.getInstance(algorithm);
            StringBuffer sb = new StringBuffer();
            //通过执行诸如填充之类的最终操作完成哈希计算
            byte[] bytes = md.digest(str.getBytes());



            for (int i = 0; i < bytes.length; i++) {
                int b = bytes[i] & 0xFF;
                if (b < 0x10) {
                    sb.append('0');
                }
                sb.append(Integer.toHexString(b));
            }
            return sb.toString();
        } catch (Exception e) {
            return "";
        }
    }
}
