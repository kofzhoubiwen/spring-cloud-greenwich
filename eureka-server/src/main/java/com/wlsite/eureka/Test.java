package com.wlsite.eureka;

import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.MyDataCenterInfo;
import com.netflix.discovery.shared.resolver.DefaultEndpoint;
import com.netflix.discovery.shared.resolver.EurekaEndpoint;
import com.netflix.discovery.shared.transport.EurekaHttpClient;
import com.netflix.discovery.shared.transport.EurekaHttpResponse;
import com.netflix.discovery.shared.transport.TransportClientFactory;
import com.netflix.discovery.shared.transport.jersey.JerseyEurekaHttpClientFactory;

public class Test {
    public static void main(String[] args) {
        TransportClientFactory factory = JerseyEurekaHttpClientFactory.newBuilder()
                .withClientName("Test-Client") //必填
                .build();

        // 准备远端Server的地址
        EurekaEndpoint endpoint = new DefaultEndpoint("http://127.0.0.1:11111/eureka/");
        EurekaHttpClient client = factory.newClient(endpoint);

        // 注册服务
        EurekaHttpResponse<Void> response = client.register(InstanceInfo.Builder.newBuilder()
                .setInstanceId("test-instance-01")
                .setHostName("localhost")
                .setIPAddr("127.0.0.1")
                .setDataCenterInfo(new MyDataCenterInfo(DataCenterInfo.Name.MyOwn))
                .setAppName("test") // 大小写无所谓
                .build());
        System.out.println("注册成功，状态码：" + response.getStatusCode());
    }
}
