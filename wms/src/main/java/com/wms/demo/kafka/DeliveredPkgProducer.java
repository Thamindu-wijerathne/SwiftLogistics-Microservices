package com.wms.demo.kafka;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DeliveredPkgProducer {
    private final NewTopic DeliveredOrderTopic;
    private final KafkaTemplate<String, String> kafkaTemplate;

    public DeliveredPkgProducer(NewTopic deliveredOrderTopic, KafkaTemplate<String, String> kafkaTemplate) {
        DeliveredOrderTopic = deliveredOrderTopic;
        this.kafkaTemplate = kafkaTemplate;
    }
    public void SendMessage(String event){

        Message<String> message = MessageBuilder
                .withPayload(event)
                .setHeader(KafkaHeaders.TOPIC,DeliveredOrderTopic.name())
                .build();

        kafkaTemplate.send(message);
        log.info("Message produce to change the ROS Status : {}",message);

    }

}
