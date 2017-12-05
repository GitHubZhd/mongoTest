package com.springboot.study.test;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

import static com.mongodb.client.model.Filters.eq;

/**
 * Created by ps on 2017/12/4.
 */
public class MongoTest {


    public static void main(String[] args) {

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase db = mongoClient.getDatabase("MongoTest");//databaseName

        MongoCollection<Document> mongoCollection=db.getCollection("Collection_New");//collectionName

        try {

            Document p=new Document();
            p.put("Name","zhangsan");
            p.put("Age",10);
            Map<String,Object> map=new HashMap();
            Map<String,Object> map1=new HashMap();
            map1.put("Time","20171204151726");
            map1.put("State",1);

            Map<String,Object> map2=new HashMap();
            map2.put("Time","20171204151725");
            map2.put("State",1);

            map.put("Contat1",map1);
            map.put("Contat2",map2);
            p.put("STask",map);

//            mongoCollection.insertOne(p);


            // // 根据条件获取某分文档 eq:==
            Document myDoc1 = mongoCollection.find(eq("Name", "zhangsan")).first();
            System.out.println(myDoc1.toJson());


            Document dd=new Document();

            Map<String,Object> map4=new HashMap();

            Map<String,Object> map3=new HashMap();
            map3.put("Time","20171204151727");
            map3.put("State",1);
            map4.put("Contat3",map3);


//            dd.put("Contat2",map2);
            mongoCollection.updateOne(eq("Name", "zhangsan"), new Document("$setOnInsert", dd));



        }catch(Exception e){
            e.printStackTrace();
        }


    }


    @org.springframework.data.mongodb.core.mapping.Document
    static class PersonNew{

        private String name;
        private int age;
        private Map<String,Object> map;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public Map<String, Object> getMap() {
            return map;
        }

        public void setMap(Map<String, Object> map) {
            this.map = map;
        }
    }
}
