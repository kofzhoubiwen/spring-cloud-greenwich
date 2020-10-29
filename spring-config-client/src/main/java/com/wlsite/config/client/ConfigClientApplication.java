package com.wlsite.config.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@EnableAutoConfiguration
@EnableDiscoveryClient
@RestController
public class ConfigClientApplication {
    public static void main(String[] args) {
        SpringApplication.run(ConfigClientApplication.class, args);
    }


    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping("/")
    public String index() {
        return "Hello Eureka!";
    }

    @GetMapping("/services")
    public Set<String> getServices() {
        return new LinkedHashSet<>(discoveryClient.getServices());
    }

    @GetMapping("/services/{serviceName}")
    public List<ServiceInstance> getServiceInstances(@PathVariable String serviceName) {
        return discoveryClient.getInstances(serviceName);
    }

    @GetMapping("/services/{serviceName}/{instanceId}")
    public ServiceInstance getServiceInstance(@PathVariable String serviceName, @PathVariable String instanceId) {
        return getServiceInstances(serviceName)
                .stream()
                .filter(serviceInstance -> instanceId.equals(serviceInstance.getInstanceId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("No Such service instance"));
    }
}
