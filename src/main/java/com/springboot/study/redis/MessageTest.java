package com.springboot.study.redis;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Created by ps on 2017/11/20.
 */
public class MessageTest{
    protected static Logger logger = LoggerFactory.getLogger(MessageTest.class);

    @Autowired
    private MyJedisPubSub jedis;
    /**
     * SUBSCRIBE [channel...] 订阅一个匹配的通道
     * PSUBSCRIBE [pattern...] 订阅匹配的通道
     * PUBLISH [channel] [message] 将value推送到channelone通道中
     * UNSUBSCRIBE [channel...] 取消订阅消息
     * PUNSUBSCRIBE [pattern ...] 取消匹配的消息订阅
     * web环境中可以编写一个JedisPubSub 继承 @see redis.clients.jedis.JedisPubSub来实现监听
     * Jedis中通过使用 JedisPubSub.UNSUBSCRIBE/PUNSUBSCRIBE 来取消订阅
     */

}
