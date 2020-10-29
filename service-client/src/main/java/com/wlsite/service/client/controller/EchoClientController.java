package com.wlsite.service.client.controller;

import com.wlsite.service.client.doman.Person;
import com.wlsite.service.client.service.EchoService;
import com.wlsite.service.client.service.LogService;
import com.wlsite.service.client.stream.PersonSink;
import com.wlsite.service.client.stream.PersonSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class EchoClientController {

    @Autowired
    private KafkaTemplate kafkaTemplate;

    @Autowired
    private Source source;

    @Autowired
    private EchoService echoService;

    @Autowired
    private LogService logService;

    @Autowired
    private PersonSource personSource;

    @Autowired
    private PersonSink personSink;

    @GetMapping("/feign/echo/{message}")
    public String feignEcho(@PathVariable("message") String message) {
        return echoService.echo(message);
    }

    @GetMapping("/feign/log/{message}")
    public String feignLog(@PathVariable("message") String message) {
        return logService.log(message);
    }

    @GetMapping("/feign/logAndEcho/{message}")
    public String feignLogAndEcho(@PathVariable("message") String message) {
        return logService.log(message) + "*****" + echoService.echo(message);
    }

    @GetMapping("/feign/echo2/{message}")
    public String feignEcho2(@PathVariable("message") String message) {
        return echoService.echo2(message);
    }


    //    // Kafka START
//    @GetMapping("/kafka/person/{name}")
//    public Person person(@PathVariable("name") String name) {
//        Person person = new Person();
//        person.setId(System.currentTimeMillis());
//        person.setName(name);
//        System.out.println("Kafka message was sent!");
//        kafkaTemplate.send("test", person);
//        return person;
//    }
//
//    @KafkaListener(topics = "test")
//    public void listenTest(ConsumerRecord consumerRecord) {
//        System.out.println("Kafka message was received by kafka listener!");
//        System.out.println(consumerRecord.toString());
//    }
//    // Kafka END
//
//
//    // Stream Source START
//    @GetMapping("/source-stream/person/{name}")
//    public Person streamPerson(@PathVariable("name") String name) {
//        Person person = new Person();
//        person.setId(System.currentTimeMillis());
//        person.setName(name);
//        final MessageChannel output = source.output();
//        System.out.println("Stream Source message was sent!");
//        output.send(MessageBuilder.withPayload(person).build());
//        return person;
//    }
//
//    @KafkaListener(topics = "output")
//    public void kafkaListenerOutput(ConsumerRecord consumerRecord) {
//        System.out.println("Stream Source message was received by kafka listener!");
//        System.out.println(consumerRecord.toString());
//    }
//
//    @StreamListener("output")
//    public void streamListenerOutput(Person person) {
//        System.out.println("Stream Source message was received by stream listener!");
//        System.out.println(person.toString());
//    }
//    // Stream Source End
//
//
    // Stream Custom Source START
    @GetMapping("/custom-source-stream/person/{name}")
    public Person streamPersonSource(@PathVariable("name") String name) {
        Person person = new Person();
        person.setId(System.currentTimeMillis());
        person.setName(name);
        final MessageChannel channel = personSource.channel();
        System.out.println("Stream Custom Source message was sent!");
        channel.send(MessageBuilder.withPayload(person).build());
        return person;
    }

//    @KafkaListener(topics = "person-source")
//    public void kafkaListenerStreamTest(ConsumerRecord consumerRecord) {
//        System.out.println("Stream Custom Source message was received by kafka listener!");
//        System.out.println(consumerRecord.toString());
//    }

    // 通过注解方式监听
    @StreamListener("person-source")
    public void streamListenerStreamTest(Person person) {
        System.out.println("Stream Custom Source message was received by stream listener!");
        System.out.println(person.toString());
    }


    @Bean // 通过API方式监听
    public ApplicationRunner runner() {
        return arg -> {
            personSink.channel().subscribe(new MessageHandler() {
                @Override
                public void handleMessage(Message<?> message) throws MessagingException {
                    message.getHeaders();
                    message.getPayload();
                    message.getClass();
                    System.out.println(message);
                }
            });
        };
    }
    // Stream Custom Source END
}
