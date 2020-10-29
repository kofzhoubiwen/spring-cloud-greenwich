package com.wlsite.service.client.kafka;

import org.apache.kafka.common.serialization.Serializer;

import java.io.*;
import java.util.Map;

public class ObjectSerializer implements Serializer<Serializable> {
    @Override
    public void configure(Map<String, ?> map, boolean b) {

    }

    @Override
    public byte[] serialize(String topic, Serializable data) {

        System.out.printf("CURRENT TOPIC: %s, SERIALIZED OBJECT:%s\n", topic, data);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        byte[] dataArray = null;
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)){
            objectOutputStream.writeObject(data);
            dataArray = outputStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }

        return new byte[0];
    }

    @Override
    public void close() {

    }
}
