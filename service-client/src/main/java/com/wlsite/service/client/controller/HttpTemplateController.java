package com.wlsite.service.client.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HttpTemplateController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("/rest-template-call/echo/{message}")
    public String restTemplateCallEcho(@PathVariable("message") String message){
        return restTemplate.getForEntity("http://service-provider/echo/"+message, String.class).getBody();
    }
}
