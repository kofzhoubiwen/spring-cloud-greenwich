package com.wlsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class ConfigServerTestApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServerTestApplication.class);
    }
}
