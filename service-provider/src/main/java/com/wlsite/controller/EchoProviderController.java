package com.wlsite.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.wlsite.anno.Limited;
import com.wlsite.anno.Timeout;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Random;
import java.util.concurrent.*;

@RestController
public class EchoProviderController {

    private Environment environment;
    private static volatile ExecutorService executorService;

    public EchoProviderController(Environment environment) {
        this.environment = environment;
    }

    static {
        if (executorService == null) {
            synchronized (EchoProviderController.class) {
                if (executorService == null) {
                    executorService = Executors.newFixedThreadPool(2);
                }
            }
        }
    }

    private String getPort() {
        return environment.getProperty("local.server.port");
    }

    // 配置手册: https://github.com/Netflix/Hystrix/wiki/configuration
//    @HystrixCommand(
////            fallbackMethod = "fallback",
//            threadPoolKey = "echo",
//            commandProperties = {
//                    @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
//                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "50")
//            })
    @GetMapping("/echo/{message}")
    public String echo(@PathVariable("message") String message) {
        Integer costedTime = await();
        return "[ECHO/" + getPort() + "]: " + message + "COSTS: " + costedTime + "MS";
    }

    @GetMapping("/echo2/{message}")
    public String echo2(@PathVariable("message") String message) {
        Integer costedTime = await();
        return "[ECHO/" + getPort() + "]: " + message + "COSTS: " + costedTime + "MS";
    }

    @HystrixCommand(
            fallbackMethod = "fallback",
            threadPoolKey = "echo",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.strategy", value = "THREAD"),
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "500000")
            })
    @GetMapping("/slow-echo/{message}")
    public String slowEcho2(@PathVariable("message") String message) {
        Integer costedTime = await(999999);
        return "[ECHO/" + getPort() + "]: " + message + "COSTS: " + costedTime + "MS";
    }


    @GetMapping("/custom/echo-via-aop/{message}")
    @Limited(2)
    public String customEchoViaAOP(@PathVariable("message") String message) {
        return doJob(message);
    }


    @GetMapping("/custom/echo-via-thread-pool/{message}")
    @Timeout(value = 50, fallback = "fallback")
    public String customEchoViaThreadPool(@PathVariable("message") String message) throws ExecutionException, InterruptedException {
        Future<String> future = executorService.submit(() -> doJob(message));
        try {
            return future.get(50, TimeUnit.MILLISECONDS);
        } catch (TimeoutException e) {
            return "TIME OUT...";
        }
    }

    @GetMapping("/custom/echo")
    @Timeout(value = 50, fallback = "fallbackCommon")
    public String customEcho() {
        return doJob();
    }


    public Integer await() {
        return await(3000);
    }

    public Integer await(int ms) {
        Random random = new Random();
        int workingTime = random.nextInt(ms);
        try {
            Thread.sleep(random.nextInt(workingTime));
            System.out.println("THREAD[" + Thread.currentThread().getName() + "] EXECUTE COSTS " + workingTime + " MS");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return workingTime;
    }

    public String doJob() {
        return "[ECHO/" + getPort() + "] COST: " + await() + "MS";
    }

    public String doJob(String message) {
        return "[ECHO/" + getPort() + "]: " + message + "COST: " + await() + "MS";
    }

    public String fallback(String failedMessage) {
        return "FALLBACK: " + failedMessage;
    }

    public String fallbackCommon() {
        return "FALLBACK";
    }
}
