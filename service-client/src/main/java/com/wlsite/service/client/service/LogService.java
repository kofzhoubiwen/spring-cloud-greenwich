package com.wlsite.service.client.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-provider-2")
public interface LogService {
    @GetMapping("/log/{message}")
    String log(@PathVariable("message") String message);
}



