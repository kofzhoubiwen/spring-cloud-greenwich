package com.wlsite;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Map;

@SpringBootApplication
public class App {

    @Autowired
    @Group
    private Map<String,String> groupStringBeanMap = Collections.emptyMap();


    @Bean
    public ApplicationRunner runner(){
        return args -> System.out.println(groupStringBeanMap);
    }


    public static void main(String[] args) {
        new SpringApplicationBuilder(App.class)
                .web(WebApplicationType.NONE)
                .run();
    }
}


