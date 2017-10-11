package com.springboot.study.test;

import com.google.gson.Gson;
import com.mongodb.*;
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.springboot.study.pojo.User;
import com.springboot.study.util.JsonConvert;
import org.bson.Document;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static com.mongodb.client.model.Accumulators.*;
import static com.mongodb.client.model.Aggregates.*;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Projections.excludeId;
import static com.mongodb.client.model.Projections.fields;
import static com.mongodb.client.model.Projections.include;
import static com.mongodb.client.model.Sorts.*;

/**
 * Created by ps on 2017/10/9.
 */
public class MongoDBTest {

    public static void main(String[] args) {

        operate();
        if(1==1){
            return;
        }


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

             //修改第一个符合条件的数据(两种更新方式)
            // $set 为修改
//             mongoCollection.updateOne(eq("i", 9), new Document("$set", new
//             Document("i", 110)));
             mongoCollection.updateOne(eq("i", 2), Updates.set("i",2.1));
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

    public static void connect() {

        MongoClientOptions.Builder build = new MongoClientOptions.Builder();
        //与数据最大连接数50
        build.connectionsPerHost(50);
        //如果当前所有的connection都在使用中，则每个connection上可以有50个线程排队等待
        build.threadsAllowedToBlockForConnectionMultiplier(50);
        build.connectTimeout(1 * 60 * 1000);
        build.maxWaitTime(2 * 60 * 1000);
        MongoClientOptions options = build.build();
        MongoClient mongoClient = null;

//        //SSL
//        new MongoClientURI("mongodb://localhost/?ssl=true")
//        MongoClientOptions.builder().sslEnabled(true).build()
        try {
//            ServerAddress sa = new ServerAddress("localhost", 27017);
//            List<MongoCredential> mongoCredentialList = new ArrayList<MongoCredential>();
//            mongoCredentialList.add(MongoCredential.createCredential("zhou", "zhou", "zhou".toCharArray()));
//            mongoClient = new MongoClient(sa, mongoCredentialList,options);


            MongoClientURI uri = new MongoClientURI("mongodb://zhou:zhou@127.0.0.1:27017/?authSource=zhou", build);
            mongoClient = new MongoClient(uri);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * MongoDB的3.x版本Java驱动相对2.x做了全新的设计，类库和使用方法上有很大区别。
     * 例如用Document替换BasicDBObject、通过Builders类构建Bson替代直接输入$命令等，本文整理了基于3.2版本的常用增删改查操作的使用方法。
     * 为了避免冗长的篇幅，分为增删改、查询、聚合、地理索引等几部分。
       聚合用于统计文档个数、求和、最大最小值、求平均值等，功能和函数名称和SQL中的count、distinct、group等关键字非常类似，
       此外，还可以通过javascript编写MapReduce实现复杂的计算(性能损耗也会非常严重)。

     首先来看3.x驱动中的聚合方法的声明：
         AggregateIterable<TDocument> aggregate(List<? extends Bson> pipeline)
         参数类型是一个Bson的列表，而参数名称是pipeline，其构建方式正如其名，是以多个Bson建立起一条管道，前一个Bson的输出将作为后一个Bson的输入
     例如：
         mc.aggregate(Arrays.asList(match(eq("owner", "tom")), group("$author", sum("totalWords", "$words"))));
         首先用$match查找出owner=tom的文档，并将结果集传递给$group并对字数求和。

     自己总结的：---------------以哪个字段Field分组，哪个字段Field就是唯一主键，即有：_id=Field
     */
    public static void operate(){

        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase db = mongoClient.getDatabase("MongoTest");//databaseName

        MongoCollection<Document> mc=db.getCollection("Collection_Two");//collectionName
        //每次执行前清空集合以方便重复运行
        mc.drop();

        Block<Document> printBlock = document -> System.out.println(document.toJson());
        //聚合函数
        //插入用于测试的文档
        Document doc1 = new Document("title", "good day").append("owner", "tom").append("words", 300)
                .append("comments", Arrays.asList(new Document("author", "joe").append("score", 3).append("comment", "good"), new Document("author", "white").append("score", 1).append("comment", "oh no")));
        Document doc2 = new Document("title", "good").append("owner", "john").append("words", 400)
                .append("comments", Arrays.asList(new Document("author", "william").append("score", 4).append("comment", "good"), new Document("author", "white").append("score", 6).append("comment", "very good")));
        Document doc3 = new Document("title", "good night").append("owner", "mike").append("words", 200)
                .append("tag", Arrays.asList(1, 2, 3, 4));
        Document doc4 = new Document("title", "happiness").append("owner", "tom").append("words", 1480)
                .append("tag", Arrays.asList(2, 3, 4));
        Document doc5 = new Document("title", "a good thing").append("owner", "tom").append("words", 180)
                .append("tag", Arrays.asList(1, 2, 3, 4, 5));
        mc.insertMany(Arrays.asList(doc1, doc2, doc3, doc4, doc5));

        //统计owner=tom ,根据title分组，统计各组的总字数
        AggregateIterable<Document> iterable = mc.aggregate(Arrays.asList(match(eq("owner", "tom")),
                group("$title", sum("totalWords", "$words"))));
        printResult("", iterable);


        // $match 确定复合条件的文档, 可组合多个条件
        iterable = mc.aggregate(Arrays.asList(match(and(eq("owner", "tom"), gt("words", 180)))));
        printResult("$match only", iterable);
        /**
         * Document{{_id=59dd8e2179a7da36980d285a, title=good day, owner=tom, words=300, comments=[Document{{author=joe, score=3, comment=good}}, Document{{author=white, score=1, comment=oh no}}]}}
         Document{{_id=59dd8e2179a7da36980d285d, title=happiness, owner=tom, words=1480, tag=[2, 3, 4]}}
         */

        // $sum求和 $avg平均值 $max最大值 $min最小值    owner=tom 有三条数据
        iterable = mc.aggregate(Arrays.asList(
                match(in("owner", "tom", "john", "mike")),
                group("$owner",
                        sum("totalWords", "$words"),
                        avg("averageWords", "$words"),
                        max("maxWords", "$words"),
                        min("minWords", "$words")
                )
        ));
        printResult("$sum $avg $max $min", iterable);
        /**
         * Document{{_id=mike, totalWords=200, averageWords=200.0, maxWords=200, minWords=200}}
         Document{{_id=john, totalWords=400, averageWords=400.0, maxWords=400, minWords=400}}
         Document{{_id=tom, totalWords=1960, averageWords=653.3333333333334, maxWords=1480, minWords=180}}
         */

        // $out 把聚合结果输出到集合
        iterable = mc.aggregate(Arrays.asList(
                match(in("owner", "tom", "john", "mike")),
                group("$owner",
                        sum("totalWords", "$words"),
                        avg("averageWords", "$words"),
                        max("maxWords", "$words"),
                        min("minWords", "$words")
                ),
                out("wordsCount")));
        iterable = db.getCollection("wordsCount").aggregate(
                Arrays.asList(sample(2)));//插入三条，显示两条
        printResult("$out", iterable);

        // 随机取3个文档, 仅返回title和owner字段
        iterable = mc.aggregate(Arrays.asList(sample(3),
                project(fields(include("title", "owner"), excludeId()))));
        printResult("sample(3)", iterable);

        // 从第2个文档开始取2个文档, 仅返回title和owner字段
        iterable = mc.aggregate(Arrays.asList(skip(1), limit(2),
                project(fields(include("title", "owner"), excludeId()))));
        printResult("skip(1), limit(2)", iterable);

        // $lookup 和另一个集合关联
        db.getCollection("scores").drop();
        db.getCollection("scores").insertMany(
                Arrays.asList(
                        new Document("writer", "tom").append("score", 100),
                        new Document("writer", "joe").append("score", 95),
                        new Document("writer", "john").append("score", 80)));
        //lookup(final String from, final String localField, final String foreignField, final String as)
        //scores的writer和当前的owner
        iterable = mc.aggregate(Arrays.asList(lookup("scores", "owner",
                "writer", "joinedOutput")));
        printResult("lookup", iterable);
        /**
         * Document{{_id=59dd950979a7da16dcdff318, title=good day, owner=tom, words=300, comments=[Document{{author=joe, score=3, comment=good}}, Document{{author=white, score=1, comment=oh no}}], joinedOutput=[Document{{_id=59dd950979a7da16dcdff31d, writer=tom, score=100}}]}}
         Document{{_id=59dd950979a7da16dcdff319, title=good, owner=john, words=400, comments=[Document{{author=william, score=4, comment=good}}, Document{{author=white, score=6, comment=very good}}], joinedOutput=[Document{{_id=59dd950979a7da16dcdff31f, writer=john, score=80}}]}}
         Document{{_id=59dd950979a7da16dcdff31a, title=good night, owner=mike, words=200, tag=[1, 2, 3, 4], joinedOutput=[]}}
         Document{{_id=59dd950979a7da16dcdff31b, title=happiness, owner=tom, words=1480, tag=[2, 3, 4], joinedOutput=[Document{{_id=59dd950979a7da16dcdff31d, writer=tom, score=100}}]}}
         Document{{_id=59dd950979a7da16dcdff31c, title=a good thing, owner=tom, words=180, tag=[1, 2, 3, 4, 5], joinedOutput=[Document{{_id=59dd950979a7da16dcdff31d, writer=tom, score=100}}]}}
         */

        // 拆分comments为单个文档，仅返回comments
        iterable = mc.aggregate(Arrays.asList(match(size("comments", 2)),
                project(fields(include("comments"), excludeId())),
                unwind("$comments")));
        printResult("unwind comments", iterable);
        /**
         * Document{{comments=Document{{author=joe, score=3, comment=good}}}}
         Document{{comments=Document{{author=white, score=1, comment=oh no}}}}
         Document{{comments=Document{{author=william, score=4, comment=good}}}}
         Document{{comments=Document{{author=white, score=6, comment=very good}}}}
         */

        System.out.println("distinct");
        DistinctIterable<String> di = mc.distinct("owner", String.class);
        di.forEach(new Block<String>() {
            public void apply(final String str) {
                System.out.println(str);
            }
        });
        System.out.println("------------------------------------------------------");
        System.out.println();

        System.out.println("count");
        long count = mc.count(Filters.eq("owner", "tom"));
        System.out.println("count=" + count);
        System.out.println("------------------------------------------------------");
        System.out.println();

        //可以通过javascript编写MapReduce实现复杂的计算
        System.out.println("mapreduce");
        String map = "function() { var category; "
                + "if ( this.words >= 280 ) category = 'Long blogs'; "
                + "else category = 'Short blogs'; "
                + "emit(category, {title: this.title});}";

//        String reduce = "function(key, values) { var cnt = 0; "
//                + "values.forEach(function(doc) { cnt += 1; }); "
//                + "return {count: cnt};} ";
        /**
         * Document{{_id=Long blogs, value=Document{{count=3.0}}}}
         Document{{_id=Short blogs, value=Document{{count=2.0}}}}
         */
//        String reduce = "function(key, values) { "
//                + "return {values};} ";
        /**
         * Document{{_id=Long blogs, value=Document{{values=[Document{{title=good day}}, Document{{title=good}}, Document{{title=happiness}}]}}}}
         Document{{_id=Short blogs, value=Document{{values=[Document{{title=good night}}, Document{{title=a good thing}}]}}}}
         */
//        String reduce = "function(key, values) { "
//                + "return {val:values};} ";
        /**
         * Document{{_id=Long blogs, value=Document{{val=[Document{{title=good day}}, Document{{title=good}}, Document{{title=happiness}}]}}}}
         Document{{_id=Short blogs, value=Document{{val=[Document{{title=good night}}, Document{{title=a good thing}}]}}}}
         */
        String reduce = "function(key, values) { "
                + "return {cate:key,val:values};} ";
        /**
         * Document{{_id=Long blogs, value=Document{{cate=Long blogs, val=[Document{{title=good day}}, Document{{title=good}}, Document{{title=happiness}}]}}}}
         Document{{_id=Short blogs, value=Document{{cate=Short blogs, val=[Document{{title=good night}}, Document{{title=a good thing}}]}}}}
         */
        MapReduceIterable<Document> mi = mc.mapReduce(map, reduce);
        mi.forEach(new Block<Document>() {
            public void apply(final Document str) {
                System.out.println(str);
            }
        });
        System.out.println("------------------------------------------------------");
        System.out.println();
    }

    //打印聚合结果
    public static void printResult(String doing, AggregateIterable<Document> iterable) {
        System.out.println(doing);
        iterable.forEach(new Block<Document>() {
            public void apply(final Document document) {
                System.out.println(document);
            }
        });
        System.out.println("------------------------------------------------------");
        System.out.println();
    }
    /**
     * -------------------------聚合MapReduce------------------------------------------------
     *
     * map: function() {emit(this.cat_id,this.goods_number); }, # 函数内部要调用内置的emit函数,cat_id代表根据cat_id来进行分组,goods_number代表把文档中的goods_number字段映射到cat_id分组上的数据,其中this是指向向前的文档的,这里的第二个参数可以是一个对象,如果是一个对象的话,也是作为数组的元素压进数组里面;

     reduce: function(cat_id,all_goods_number) {return Array.sum(all_goods_number)}, # cat_id代表着cat_id当前的这一组,all_goods_number代表当前这一组的goods_number集合,这部分返回的就是结果中的value值;

     out: , # 输出到某一个集合中,注意本属性来还支持如果输出的集合如果已经存在了,那是替换,合并还是继续reduce? 另外还支持输出到其他db的分片中,具体用到时查阅文档,筛选出现的键名分别是_id和value;

     query: , # 一个查询表达式,是先查询出来,再进行mapReduce的

     sort: , # 发往map函数前先给文档排序

     limit: , # 发往map函数的文档数量上限,该参数貌似不能用在分片模式下的mapreduce

     finalize: function(key, reducedValue) {return modifiedObject; }, # 从reduce函数中接受的参数key与reducedValue,并且可以访问scope中设定的变量

     scope: , # 指定一个全局变量,能应用于finalize和reduce函数

     jsMode: , # 布尔值，是否减少执行过程中BSON和JS的转换，默认true,true时BSON-->js-->map-->reduce-->BSON,false时 BSON-->JS-->map-->BSON-->JS-->reduce-->BSON,可处理非常大的mapreduce。

     verbose:  # 是否产生更加详细的服务器日志，默认true
     */
}
