package com.wlsite.service.client.service;

import feign.hystrix.FallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "service-provider",
        fallback = EchoServiceFallback.class
)
public interface EchoService {
    @GetMapping("/echo/{message}")
    String echo(@PathVariable("message") String message);
    @GetMapping("/echo2/{message}")
    String echo2(@PathVariable("message") String message);

}
@Component
class EchoServiceFallback implements EchoService{
    @Override
    public String echo(String message) {
        return "echo1: FEIGN CUSTOM FALLBACK";
    }
    @Override
    public String echo2(String message) {
        return "echo2: FEIGN CUSTOM FALLBACK";
    }
}
@Component
class EchoServiceFallbackFactory implements FallbackFactory<EchoService> {
    @Override
    public EchoService create(Throwable throwable) {
        return new EchoService() {
            @Override
            public String echo(String message) {
                return "echo: FEIGN CUSTOM FALLBACK BY EchoServiceFallbackFactory";
            }

            @Override
            public String echo2(String message) {
                return "echo2: FEIGN CUSTOM FALLBACK BY EchoServiceFallbackFactory";
            }
        };
    }
}




