package com.springboot.study.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

/**
 * Created by ps on 2017/11/29.
 */
public class KafkaListenerPractices {


    @KafkaListener(topics = "jop-kafka", containerFactory = "kafkaListenerContainerFactory")
    public void listener(ConsumerRecord record) {
        System.out.println(record);
        System.out.println(record.value());
    }
}
