package com.wlsite.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogProviderController {

    @GetMapping("/log/{message}")
    public String log(@PathVariable("message") String message) {
        return "[LOG/HELLO]: " + message;
    }

}
