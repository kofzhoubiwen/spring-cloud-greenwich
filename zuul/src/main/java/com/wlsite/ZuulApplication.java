package com.wlsite;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@SpringBootApplication
@EnableZuulProxy //@EnableZuulServer 有区别
public class ZuulApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder().web(WebApplicationType.SERVLET)
                .sources(ZuulApplication.class)
                .run(args);
    }
}
