package com.wlsite;

import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@EnableDiscoveryClient
//@EnableCircuitBreaker // 启用Hystrix
@EnableHystrix // 启用Hystrix
@EnableAspectJAutoProxy // 启用AspectJ
@EnableBinding(Sink.class) // 启用KafkaStream
public class ServiceProviderApplication {

    @StreamListener(Sink.INPUT)
    public void listener(JSONPObject jsonpObject){
        System.out.println(jsonpObject);
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceProviderApplication.class, args);
    }
}
