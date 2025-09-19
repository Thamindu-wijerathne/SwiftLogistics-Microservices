package com.cms.demo.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class DeliveryPackageProducer {
    private final NewTopic wmsPackageTopic;
    private final KafkaTemplate<String, String> kafkaTemplate;

    private static final Logger log = LoggerFactory.getLogger(DeliveryPackageProducer.class);


    public DeliveryPackageProducer(NewTopic wmsPackageTopic1, KafkaTemplate<String, String> kafkaTemplate) {
        this.wmsPackageTopic = wmsPackageTopic1;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void SendMessageOnCreateOrder(String OrderID){
        Message<String> message = MessageBuilder
                .withPayload(OrderID)
                .setHeader(KafkaHeaders.TOPIC,wmsPackageTopic.name())
                .build();

        kafkaTemplate.send(message);
        log.info("Message with Create Order ID : {}",message);

    }


}
