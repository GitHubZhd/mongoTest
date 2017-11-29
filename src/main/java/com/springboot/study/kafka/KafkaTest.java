package com.springboot.study.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.PartitionInfo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Created by ps on 2017/11/24.
 */
public class KafkaTest {

    @Test
    public void producer(){

        Properties props = new Properties();
        props.put("serializer.class", "kafka.serializer.StringEncoder");
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.20.68:9092");
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
        props.put(ProducerConfig.ACKS_CONFIG, "1");//
        props.put(ProducerConfig.RETRIES_CONFIG, "3");
        props.put(ProducerConfig.BUFFER_MEMORY_CONFIG, 33554432);//32M
        props.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "none");//
        props.put(ProducerConfig.CLIENT_ID_CONFIG,"Controller");

        Producer<String, String> producer = new KafkaProducer<>(props);
        for (int i = 0; i < 10; i++) {
            producer.send(new ProducerRecord<>("my-topic", Integer.toString(i), Integer.toString(i)));
        }

        producer.close();

    }



    @Test
    public void consumer(){

        List<String> list=new ArrayList();
        list.add("my-topic");
        list.add("topic-1");

        Properties props = new Properties();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "192.168.20.68:9092");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
        props.put(ConsumerConfig.CLIENT_ID_CONFIG,"Controller");
        props.put(ConsumerConfig.GROUP_ID_CONFIG,"Controller");

        Consumer<String, String> consumer = new KafkaConsumer<>(props);

        Map<String, List<PartitionInfo>> map= consumer.listTopics();
        for (Map.Entry entry:map.entrySet()){
            System.out.println(entry.getKey());
            System.out.println(entry.getValue());
        }

        consumer.subscribe(list);

        ConsumerRecords<String, String> records = consumer.poll(100);
        System.out.println("-------------------------------------------------------------------------------------");
        System.out.println(records.isEmpty());
        for (ConsumerRecord<String, String> record : records)
            System.out.printf("offset = %d, key = %s, value = %s", record.offset(), record.key(), record.value());


    }
}
