package com.internship.producer.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class DeviceTopicConfig {
    @Bean
    public NewTopic deviceServiceTopic() {
        return TopicBuilder.name("deviceService")
                .build();
    }

    @Bean
    public NewTopic deviceLogServiceTopic() {
        return TopicBuilder.name("deviceLogService")
                .build();
    }

    @Bean
    public NewTopic userDeletionTopic() {
        return TopicBuilder.name("user-deletion")
                .build();
    }
}
