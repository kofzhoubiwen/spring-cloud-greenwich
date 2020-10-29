package com.wlsite.service.client.kafka;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class JsonDeserializer implements Deserializer<Object> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }
    @Override
    public Object deserialize(String topic, byte[] data) {
        return JSON.parseObject(data,Object.class);
    }
    @Override
    public void close() {
    }
}