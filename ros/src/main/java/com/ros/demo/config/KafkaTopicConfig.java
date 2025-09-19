package com.ros.demo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaTopicConfig {

    public static final String ROUTE_TOPIC = "ros-routes";

    @Bean
    public NewTopic routeTopic() {
        return TopicBuilder.name(ROUTE_TOPIC)
                .partitions(3)
                .replicas(1)
                .build();
    }
}
