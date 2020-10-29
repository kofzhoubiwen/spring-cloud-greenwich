package com.wlsite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableConfigServer // 激活SpringCloudConfigServer
@EnableEurekaClient // 高可用 激活EurekaClient
public class ConfigServer1Application {
    public static void main(String[] args) {
        SpringApplication.run(ConfigServer1Application.class);
    }
}
