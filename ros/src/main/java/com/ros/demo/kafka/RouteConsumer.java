package com.ros.demo.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class RouteConsumer {

    @KafkaListener(topics = "ros-routes", groupId = "ros-service-group")
    public void consumeMessage(String message) {
        System.out.println("Received from Kafka: " + message);
        // TODO: handle updates, e.g., route completed, package delivered
    }
}

