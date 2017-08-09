package com.springboot.study.HealthIndicator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.endpoint.Endpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by ps on 2017/8/9.
 */
@RestController
@Configuration
public class Controller {

    @Autowired
    StatusService statusService;

    @Bean
    public Endpoint<String> status(){
        Endpoint<String> status=new StatusEndPoint();
        return status;
    }

    @RequestMapping("/change")
    public String changeStatus(String status){
        statusService.setStatus(status);
        return "OK";

    }
}
