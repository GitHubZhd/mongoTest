package com.springboot.study.HealthIndicator;

import org.springframework.stereotype.Service;

/**
 * Created by ps on 2017/8/9.
 */
@Service
public class StatusService {

    private String status;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
