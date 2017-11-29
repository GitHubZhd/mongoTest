package com.springboot.study.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import redis.clients.jedis.JedisPubSub;

/**
 * Created by ps on 2017/11/20.
 */
@Component
public class MyJedisPubSub extends JedisPubSub {

    protected static Logger logger = LoggerFactory.getLogger(MyJedisPubSub.class);

    // 取得订阅的消息后的处理
    @Override
    public void onMessage(String channel, String message) {
        logger.info("取得订阅的消息后的处理 : " + channel + "=" + message);
    }

    // 初始化订阅时候的处理
    @Override
    public void onSubscribe(String channel, int subscribedChannels) {
        logger.info("初始化订阅时候的处理 : " + channel + "=" + subscribedChannels);
    }

    // 取消订阅时候的处理
    @Override
    public void onUnsubscribe(String channel, int subscribedChannels) {
        logger.info("取消订阅时候的处理 : " + channel + "=" + subscribedChannels);
    }

    // 初始化按表达式的方式订阅时候的处理
    @Override
    public void onPSubscribe(String pattern, int subscribedChannels) {
        logger.info("初始化按表达式的方式订阅时候的处理 : " + pattern + "=" + subscribedChannels);
    }

    // 取消按表达式的方式订阅时候的处理
    @Override
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        logger.info(" 取消按表达式的方式订阅时候的处理 : " + pattern + "=" + subscribedChannels);
    }

    // 取得按表达式的方式订阅的消息后的处理
    @Override
    public void onPMessage(String pattern, String channel, String message) {
        logger.info("取得按表达式的方式订阅的消息后的处理 :" + pattern + "=" + channel + "=" + message);
    }
}
