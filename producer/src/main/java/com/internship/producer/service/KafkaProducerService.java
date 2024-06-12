package com.internship.producer.service;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProducerService {
    private static final String DEVICE_TOPIC = "deviceService";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, Object> kafkaTemplate, KafkaTemplate<String, Object> logKafkaTemplate) {

        if (kafkaTemplate == null) {
            throw new IllegalArgumentException("KafkaTemplate cannot be null");
        }
        this.kafkaTemplate = kafkaTemplate;
    }

    public <T> void sendEvent(String topic, T event) {
        kafkaTemplate.send(DEVICE_TOPIC, event);
    }

}
