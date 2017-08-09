package com.springboot.study.HealthIndicator;

import org.springframework.beans.BeansException;
import org.springframework.boot.actuate.endpoint.AbstractEndpoint;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by ps on 2017/8/9.
 */
//通过@ConfigurationProperties的设置，我们可以在application.properties 中通过 endpoints.status 配置我们的端点
@ConfigurationProperties(prefix = "endpoints.status",ignoreInvalidFields = false)
public class StatusEndPoint extends AbstractEndpoint<String> implements ApplicationContextAware {

    ApplicationContext applicationContext;

    public StatusEndPoint() {
        super("status");
    }

    @Override
    public String invoke() {//重写invoke() ,返回我们要监控的内容
        StatusService statusService=applicationContext.getBean(StatusService.class);
        return "This Current Status is "+ statusService.getStatus();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }
}
