package com.springboot.study.integration;

import com.rometools.rome.feed.synd.SyndEntry;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.channel.MessageChannels;
import org.springframework.integration.dsl.core.Pollers;
import org.springframework.integration.dsl.file.Files;
import org.springframework.integration.dsl.mail.Mail;
import org.springframework.integration.feed.inbound.FeedEntryMessageSource;
import org.springframework.integration.scheduling.PollerMetadata;
import org.springframework.integration.file.support.FileExistsMode;

import java.io.File;
import java.io.IOException;

/**
 * Created by ps on 2017/8/10.
 */
@Configuration
public class IntegrationConfig {

    @Value("https://spring.io/blog.atom")
    Resource resource;

    @Bean(name = PollerMetadata.DEFAULT_POLLER)
    public PollerMetadata poller(){
        return Pollers.fixedRate(500).get();
    }

    @Bean
    public FeedEntryMessageSource feedMessageSource() throws IOException {
        FeedEntryMessageSource feedEntryMessageSource=new FeedEntryMessageSource(resource.getURL(),"news");
        return feedEntryMessageSource;
    }

    @Bean
    public IntegrationFlow myFlow() throws IOException {
        return IntegrationFlows.from(feedMessageSource()).<SyndEntry,String>route(
                payload->payload.getCategories().get(0).getName(),
                mapping->mapping.channelMapping("releases","releasesChannel")
                        .channelMapping("engineering","engineeringChannel")
                        .channelMapping("news","newsChannel")).get();

    }


    @Bean
    public IntegrationFlow releaseFlow(){
        return IntegrationFlows.from(MessageChannels.queue("releasesChannel",10))
                .<SyndEntry,String>transform(payload->"《"+payload.getTitle()+"》"+payload.getLink()+System.getProperty("line.separator"))
                .handle(Files.outboundAdapter(new File("d:/springblog"))
                   .fileExistsMode(FileExistsMode.APPEND)
                   .charset("UTF-8")
                   .fileNameGenerator(message->"releases.txt")
                   .get())
                .get();
    }


    @Bean
    public IntegrationFlow engineeringFlow(){
        return IntegrationFlows.from(MessageChannels.queue("engineeringChannel",10))
                .<SyndEntry,String>transform(payload->"《"+payload.getTitle()+"》"+payload.getLink()+System.getProperty("line.separator"))
                .handle(Files.outboundAdapter(new File("d:/springblog"))
                        .fileExistsMode(FileExistsMode.APPEND)
                        .charset("UTF-8")
                        .fileNameGenerator(message->"engineering.txt")
                        .get())
                .get();
    }

    @Bean
    public IntegrationFlow newsFlow(){
        return IntegrationFlows.from(MessageChannels.queue("newsChannel",10))
                .<SyndEntry,String>transform(payload->"《"+payload.getTitle()+"》"+payload.getLink()+System.getProperty("line.separator"))
                .enrichHeaders(
                        Mail.headers()
                        .subject("来自Spring的新闻")
                        .to("zhang_haidong258@163.com")
                        .from("zhang_haidong258@163.com"))
                .handle(Mail.outboundAdapter("smtp.163.com")
                        .port(25)
                        .protocol("smtp")
                        .credentials("zhang_haidong258@163.com","********60z**")//账户和授权登录密码
                        .javaMailProperties(p->p.put("mail.smtp.auth","true").put("mail.debug","false")),
                        e->e.id("smtpOut"))
                .get();
    }
}
/**
 *
 * header 消息头
 *
 * payload 消息体 ：可以是任何数据类型 如 xml json  java
 *
 */
