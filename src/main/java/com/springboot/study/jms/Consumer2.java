package com.springboot.study.jms;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

/**
 * Created by ps on 2017/7/26.
 */
@Component
public class Consumer2 {

    @JmsListener(destination = "mytest.queue")
    @SendTo("out.queue")
    public String receiveQueue(String text) {
        System.out.println("Consumer2收到的报文为:"+text);
        return "return message"+text;
    }
}
