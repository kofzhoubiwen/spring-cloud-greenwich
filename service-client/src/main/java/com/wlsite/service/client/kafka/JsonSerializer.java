package com.wlsite.service.client.kafka;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.kafka.common.serialization.Serializer;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Map;

public class JsonSerializer implements Serializer<Object> {
    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }
    @Override
    public byte[] serialize(String topic, Object data) {
        return JSON.toJSONBytes(data);
    }
    @Override
    public void close() {
    }

    public byte[] toByteArray (Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray ();
            oos.close();
            bos.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return bytes;
    }
}