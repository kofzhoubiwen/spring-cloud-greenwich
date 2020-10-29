package com.wlsite.anno;


import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Timeout {
    /**
     * 超时时长
     * @return
     */
    long value() default  10l;

    /**
     * 超时单位
     * @return
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS; // 单位

    /**
     * 补偿方案
     * @return
     */
    String fallback() default "";
}
