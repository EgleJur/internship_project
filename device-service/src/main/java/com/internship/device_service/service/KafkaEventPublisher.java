package com.internship.device_service.service;

import com.internship.device_service.model.Device;
import com.internship.device_service.model.DeviceEvent;
import com.internship.device_service.model.EventType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventPublisher {
    private static final String TOPIC = "deviceService";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaEventPublisher(KafkaTemplate<String, String> kafkaTemplate) {
        if (kafkaTemplate == null) {
            throw new IllegalArgumentException("KafkaTemplate cannot be null");
        }
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendDeviceEvent(EventType eventType, Device device) {
        DeviceEvent deviceEvent = new DeviceEvent(eventType, device);
        kafkaTemplate.send(TOPIC, deviceEvent.toString());
    }
}
