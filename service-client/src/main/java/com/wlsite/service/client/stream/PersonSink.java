package com.wlsite.service.client.stream;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface PersonSink {
    @Input("person-sink")
    SubscribableChannel channel();
}
