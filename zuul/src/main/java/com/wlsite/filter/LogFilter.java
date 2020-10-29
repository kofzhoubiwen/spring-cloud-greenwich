package com.wlsite.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Component
public class LogFilter extends ZuulFilter {
    @Override
    public String filterType() {
        return FilterConstants.PRE_TYPE; // 前置过滤器
    }
    @Override
    public int filterOrder() {
        return FilterConstants.PRE_DECORATION_FILTER_ORDER-1; // IP过滤 尽量在前面 使用-1 | 获取路由后地址 使用+1
    }
    @Override
    public boolean shouldFilter() {
        return true;
    }
    @Override
    public Object run() throws ZuulException {
        RequestContext context = RequestContext.getCurrentContext();
        HttpServletRequest request = context.getRequest();
        String method = request.getMethod();//氢气的类型，post get ..
        final Map<String, String[]> parameterMap = request.getParameterMap();
        long startTime = (long) context.get("startTime");//请求的开始时间
        Throwable throwable = context.getThrowable();//请求的异常，如果有的话
        request.getRequestURI();//请求的uri
        context.getResponseStatusCode();//请求的状态
        long duration=System.currentTimeMillis() - Long.valueOf(context.get("startTime").toString());//请求耗时
        return null;
    }
}
