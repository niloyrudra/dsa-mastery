package com.dsamastery.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.dsamastery")
@EntityScan(basePackages = "com.dsamastery")
@EnableAsync
@EnableScheduling
@EnableJpaRepositories(basePackages = "com.dsamastery")
public class DsaMasteryApiApplication {
    
    public static void main(String[] args) {
        SpringApplication.run(DsaMasteryApiApplication.class, args);
    }
}