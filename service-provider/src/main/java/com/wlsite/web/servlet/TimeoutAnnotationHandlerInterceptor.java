package com.wlsite.web.servlet;

import com.wlsite.anno.Timeout;
import com.wlsite.controller.EchoProviderController;
import org.springframework.core.MethodParameter;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.concurrent.*;
import java.util.stream.Stream;

/**
 * 注解处理拦截器 {@link com.wlsite.anno.Timeout}
 *
 * @see org.springframework.web.servlet.HandlerInterceptor
 * @see com.wlsite.anno.Timeout
 */

public class TimeoutAnnotationHandlerInterceptor implements HandlerInterceptor {

    private static volatile ExecutorService executorService;

    static {
        if (executorService == null) {
            synchronized (EchoProviderController.class) {
                if (executorService == null) {
                    executorService = Executors.newFixedThreadPool(2);
                }
            }
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Method method = handlerMethod.getMethod();
            Object bean = handlerMethod.getBean();
            Timeout timeout = method.getAnnotation(Timeout.class);
            if (timeout != null) {
                long value = timeout.value();
                String fallback = timeout.fallback();
                TimeUnit timeUnit = timeout.timeUnit();
                Future<Object> future = executorService.submit(new Callable<Object>() {
                    @Override
                    public Object call() throws Exception {
                        return method.invoke(bean);
                    }
                });
                try{
                    Object result = future.get(value, timeUnit);
                    response.getWriter().write(result.toString());
                } catch (TimeoutException exception){
                    Method fallbackMethod = findFallbackMethod(handlerMethod, bean, fallback);
                    response.getWriter().write(fallbackMethod.invoke(bean).toString());
                }
                return false;
            }
        }
        return true;
    }

    private Method findFallbackMethod(HandlerMethod handlerMethod, Object bean, String fallbackMethod) throws NoSuchMethodException {
        Class beanClass = bean.getClass();
        MethodParameter[] methodParameters = handlerMethod.getMethodParameters();
        Class[] classParameterTypes = Stream.of(methodParameters).map(MethodParameter::getParameterType).toArray(Class[]::new);
        return beanClass.getMethod(fallbackMethod, classParameterTypes);
    }

}
