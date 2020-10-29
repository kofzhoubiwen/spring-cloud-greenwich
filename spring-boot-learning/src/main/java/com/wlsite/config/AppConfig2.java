package com.wlsite.config;

import com.wlsite.Group;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig2 {

    @Bean
    public String a(){
        return "string-a";
    }

    @Bean
    @Group
    public String b(){
        return "string-b";
    }

    @Bean
    @Group
    public String c(){
        return "string-c";
    }

}
