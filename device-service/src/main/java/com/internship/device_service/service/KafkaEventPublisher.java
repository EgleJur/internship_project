package com.internship.device_service.service;

import com.internship.device_service.model.Device;
import com.internship.device_service.model.DeviceEvent;
import com.internship.device_service.model.DeviceLogEvent;
import com.internship.device_service.model.EventType;
import com.internship.device_service.model.LogEventType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class KafkaEventPublisher {
    private static final String DEVICE_TOPIC = "deviceService";
    private static final String DEVICE_LOG_TOPIC = "deviceLogService";
    private final KafkaTemplate<String, DeviceEvent> kafkaTemplate;
    private final KafkaTemplate<String, DeviceLogEvent> logKafkaTemplate;

    public KafkaEventPublisher(KafkaTemplate<String, DeviceEvent> kafkaTemplate, KafkaTemplate<String, DeviceLogEvent> logKafkaTemplate) {

        if (kafkaTemplate == null || logKafkaTemplate == null) {
            throw new IllegalArgumentException("KafkaTemplate cannot be null");
        }
        this.kafkaTemplate = kafkaTemplate;
        this.logKafkaTemplate = logKafkaTemplate;
    }

    public void sendDeviceEvent(EventType eventType, Device device) {
        DeviceEvent deviceEvent = new DeviceEvent(eventType, device);
        kafkaTemplate.send(DEVICE_TOPIC, deviceEvent);
    }

    public void sendDeviceLogEvent(LogEventType eventType, Long id, LocalDateTime timestamp) {
        DeviceLogEvent deviceLogEvent = new DeviceLogEvent(id, timestamp, eventType);
        logKafkaTemplate.send(DEVICE_LOG_TOPIC, deviceLogEvent);
    }
}
