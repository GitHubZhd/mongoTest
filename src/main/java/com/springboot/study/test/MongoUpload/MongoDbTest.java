package com.springboot.study.test.MongoUpload;


import java.io.File;

/**
 * MongoDbTest
 */
public class MongoDbTest {
    public static void main(String[] args) {
        upload();
//        download();
//        delete();
    }

    /**
     * 先上传
     */
    public static void upload() {
        File file = new File("D:\\gitTest\\crawler-dispatch-front\\dist.zip");
        String objectId = MongoDbUtil.uploadFileToGridFSByUUID(file);
        System.out.println(objectId);//59ddbf8e79a7da06b4301498
    }

    /**
     * 再测试下载
     */
    public static void download() {
        File file = new File("D:\\tmp\\dist.zip");
        MongoDbUtil.downloadFile("59ddbf8e79a7da06b4301498", file);
    }

    /**
     * 最后将上传的信息删除
     */
    public static void delete() {
        MongoDbUtil.deleteByObjectId("59ddbf8e79a7da06b4301498");
    }
}