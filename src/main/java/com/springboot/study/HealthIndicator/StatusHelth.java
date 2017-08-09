package com.springboot.study.HealthIndicator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

/**
 * Created by ps on 2017/8/9.
 */
@Component
public class StatusHelth implements HealthIndicator {//实现HealthIndicator接口，并重写health()方法

    @Autowired
    StatusService statusService;

    @Override
    public Health health() {
        String status=statusService.getStatus();
        if(status==null || !status.equals("running")){
            return Health.down().withDetail("Error","Not Running").build();//当status非running时，构造失败
        }
        return Health.up().build();//其余情况运行成功
    }
}
