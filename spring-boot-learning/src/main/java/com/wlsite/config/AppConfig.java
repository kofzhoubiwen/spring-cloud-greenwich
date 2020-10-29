package com.wlsite.config;

import com.wlsite.Group;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    @Group
    public String d(){
        return "string-d";
    }

}
