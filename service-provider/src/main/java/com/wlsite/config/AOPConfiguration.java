package com.wlsite.config;

import com.wlsite.anno.Limited;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Semaphore;

@Aspect
@Configuration
public class AOPConfiguration {

    private Map<String, Semaphore> methodSemaphoreMap = new ConcurrentHashMap<>();
    Semaphore semaphore;

    @Pointcut("@annotation(com.wlsite.anno.Limited)")
    public void pointCutService() {
    }

    @Around("pointCutService()")
    public Object aroundService(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        Object returnValue = null;
        Signature signature = proceedingJoinPoint.getSignature();
        if (signature instanceof MethodSignature) {
            MethodSignature methodSignature = (MethodSignature) signature;
            Method method = methodSignature.getMethod();
            semaphore = initSemaphore(method);
            try {
                semaphore.acquire();
                returnValue = proceedingJoinPoint.proceed();
                System.out.println("DONE!");
            } finally {
                semaphore.release();
            }
        }
        return returnValue;
    }

    public Semaphore initSemaphore(Method method) {
        return methodSemaphoreMap.computeIfAbsent(method.getName(), k -> {
            Limited annotation = method.getAnnotation(Limited.class);
            return new Semaphore(annotation.value());
        });
    }

}
