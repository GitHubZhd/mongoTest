package com.springboot.study.test;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.Block;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.springboot.study.pojo.User;
import com.springboot.study.util.JsonConvert;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;

/**
 * Created by ps on 2017/10/9.
 */
public class MongoDBTest {

    public static void main(String[] args) {


        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase db = mongoClient.getDatabase("MongoTest");//databaseName

        MongoCollection<Document> mongoCollection=db.getCollection("Collection_New");//collectionName

        try {

            /********************** 数据插入 ****************************/
            // 创建新文档
             Document doc = new Document("name", "MongoDB")
             .append("type", "database").append("count", 1)
             .append("info", new Document("x", 203).append("y", 102));
            // 将文档插入文档集合
//            mongoCollection.insertOne(doc);

            // 创建一个包含多个文档的列表
             List<Document> documents = new ArrayList<Document>();
             for (int i = 0; i < 10; i++) {
             documents.add(new Document("i", i));
             }
            // 向文档中插入列表
//            mongoCollection.insertMany(documents);

            /***************** 数据读取 ****************************************/
             // 显示集合中的文档的数量
             System.out.println(mongoCollection.count());
            // 查询集合中的第一个文档
             Document myDoc = mongoCollection.find().first();
             System.out.println(myDoc.toJson());

            //获取集合中的全部文档
             MongoCursor<Document> cursor = mongoCollection.find().iterator();
             try {
                while (cursor.hasNext()) {
                System.out.println(cursor.next().toJson());
                }
             } finally {
                cursor.close();
             }

            //获取全部文档的另一种方法
             for (Document cur : mongoCollection.find()) {
                System.out.println(cur.toJson());
             }

            // // 根据条件获取某分文档 eq:==
             Document myDoc1 = mongoCollection.find(eq("i", 7)).first();
             System.out.println(myDoc1.toJson());

            // 通过查询语句一次性获取多个数据
             Block<Document> printBlock = document -> System.out.println(document.toJson());
            // 获得所有大于5的
             mongoCollection.find(gt("i", 5)).forEach(printBlock);
            // 大于2 小于等于 8
             mongoCollection.find(and(gt("i", 2), lte("i", 8))).forEach(printBlock);

            // 对输出文档进行排序,-1为递减，1为递增
            // 官方文档的例子有误：http://mongodb.github.io/mongo-java-driver/3.0/driver/getting-started/quick-tour/#sorting-documents
             Document myDoc2 = mongoCollection.find(exists("i"))
             .sort(new BasicDBObject("i", 1)).first();
             System.out.println(myDoc2.toJson());

            // 选择性输出结果中的元素，0为不显示，1为显示
            // 官方文档中的例子又不能用：http://mongodb.github.io/mongo-java-driver/3.0/driver/getting-started/quick-tour/#projecting-fields
             BasicDBObject exclude1 = new BasicDBObject();
             exclude1.append("_id", 0);
             exclude1.append("count", 0);
            //0和1不能同时出现，默认都是1，不然会报错：Projection cannot have a mix of inclusion and exclusion.
//             exclude1.append("name", 1);
//             exclude1.append("info", 1);
             Document myDoc3 = mongoCollection.find(eq("count",1)).projection(exclude1).first();
             System.out.println(myDoc3.toJson());

            /************************* 修改数据库中数据 *************************************/

            // 修改时的参数：
            // $inc 对指定的元素加
            // $mul 乘
            // $rename 修改元素名称
            // $setOnInsert 如果以前没有这个元素则增加这个元素，否则不作任何更改
            // $set 修改制定元素的值
            // $unset 移除特定的元素
            // $min 如果原始数据更大则不修改，否则修改为指定的值
            // $max 与$min相反
            // $currentDate 修改为目前的时间

            // //修改第一个符合条件的数据
            // $set 为修改
//             mongoCollection.updateOne(eq("i", 9), new Document("$set", new
//             Document("i", 110)));
             // 获取全部文档,可以看到以前9的地方变成了110
             for (Document cur : mongoCollection.find()) {
             System.out.println(cur.toJson());
             }

            // 批量修改数据并且返回修改的结果，讲所有小于100的结果都加1
//             UpdateResult updateResult = mongoCollection.updateMany(lt("i", 100),
//             new Document("$inc", new Document("i", 1)));
             // 显示发生变化的行数
//             System.out.println(updateResult.getModifiedCount());
             // 获取全部文档,可以看到除了刚才修改的110其他的全加1
             for (Document cur : mongoCollection.find()) {
                System.out.println(cur.toJson());
             }

            /************************** 删除数据 *****************************/
            // 删除第一个符合条件的数据
             mongoCollection.deleteOne(eq("i", 110));
             // 获取全部文档,可以看到没有110这个数了
             for (Document cur : mongoCollection.find()) {
                 System.out.println(cur.toJson());
             }

            // 删除所有符合条件的数据，并且返回结果
             DeleteResult deleteResult = mongoCollection.deleteMany(gte("i", 9));
             // 输出删除的行数
             System.out.println(deleteResult.getDeletedCount());
             // 获取全部文档,所有i>=100的数据都没了
             for (Document cur : mongoCollection.find()) {
             System.out.println(cur.toJson());
             }

            /*************************** 程序块，一次执行多条语句 ********************************/
            // 按照语句先后顺序执行
//             mongoCollection.bulkWrite(Arrays.asList(
//                     new InsertOneModel<>(new Document("_id", 4)),
//                     new InsertOneModel<>(new Document("_id", 5)),
//                     new InsertOneModel<>(new Document("_id", 6)),
//                     new InsertOneModel<>(new Document("_id", 1)),
//                     new InsertOneModel<>(new Document("_id", 2)),
//                     new InsertOneModel<>(new Document("_id", 3)),
//                     new UpdateOneModel<>(new Document("_id", 1), new Document("$set", new Document("x", 2))),//没有就追加
//                     new DeleteOneModel<>(new Document("_id", 2)),
//                     new ReplaceOneModel<>(new Document("_id", 3), new Document("_id", 3).append("x", 10))
//             ));
             // 获取全部文档
             for (Document cur : mongoCollection.find()) {
             System.out.println(cur.toJson());
             }

            // 不按照语句先后顺序执行
//             mongoCollection.bulkWrite(
//                     Arrays.asList(
//                         new InsertOneModel<>(new Document("_id", 11)),
//                         new InsertOneModel<>(new Document("_id", 12)),
//                         new InsertOneModel<>(new Document("_id", 13)),
//                         new UpdateOneModel<>(new Document("_id", 11), new Document("$set", new Document("x", 2))),
//                         new DeleteOneModel<>(new Document("_id", 12)),
//                         new ReplaceOneModel<>(new Document("_id", 13), new Document("_id", 13).append("x", 4))
//                     ),
//                     new BulkWriteOptions().ordered(false)
//             );

            /*************************** 其他查询 ********************************/
            FindIterable<Document> iterable=mongoCollection.find();
            MongoCursor<Document> mongoCursor;
            Document document=iterable.first();
            User user1=JsonConvert.jsonToObject(document.toJson(),User.class);
            System.out.println(user1.getName()+"/"+user1.getSex());


            iterable=mongoCollection.find(eq("name","Zhd"));
            System.out.println("-------------------------------");
            mongoCursor=iterable.iterator();
            while (mongoCursor.hasNext()){
                System.out.println(mongoCursor.next().toString());
            }
            iterable=mongoCollection.find(Filters.exists("name",true));
            System.out.println("-------------------------------");
            mongoCursor=iterable.iterator();
            while (mongoCursor.hasNext()){
                System.out.println(mongoCursor.next().toString());
            }

            //查询指定字段
            BasicDBObject exclude = new BasicDBObject();
            exclude.append("_id",0);//0 不显示
            exclude.append("name",0);//0 不显示
            iterable=mongoCollection.find().projection(exclude);
            System.out.println("-------------------------------");
            mongoCursor=iterable.iterator();
            while (mongoCursor.hasNext()){
                System.out.println(mongoCursor.next().toString());
            }

            //排序
            iterable=mongoCollection.find().sort(orderBy(descending("age")));//降序
//            iterable=mongoCollection.find().sort(orderBy(ascending("age")));//升序
            System.out.println("-------------------------------");
            mongoCursor=iterable.iterator();
            while (mongoCursor.hasNext()){
                System.out.println(mongoCursor.next().toString());
            }


            //更新文档   将文档中age=18的文档修改为age=24
            mongoCollection.updateMany(eq("age", 19), new Document("$set",new Document("age",29)));
            FindIterable<Document> findIterable=mongoCollection.find();
            mongoCursor=findIterable.iterator();
            while (mongoCursor.hasNext()){
                System.out.println(mongoCursor.next());
            }

            Document document1=findIterable.first();
            User uu= new Gson().fromJson(document1.toJson(), User.class);
            System.out.println(uu.toString());

            // 关闭数据库连接
            mongoClient.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
