package com.internship.device_service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.device_service.model.DeviceEvent;
import com.internship.device_service.model.DeviceLogEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {
    @Autowired
    private ObjectMapper objectMapper; // Add this if ObjectMapper is not already autowired

    @KafkaListener(topics = "deviceService", groupId = "groupId")
    void listener(DeviceEvent deviceEvent) {
        try {
            String jsonData = objectMapper.writeValueAsString(deviceEvent);
            System.out.println("Listener received on deviceService " + jsonData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }

    @KafkaListener(topics = "deviceLogService", groupId = "groupId")
    void deviceLogEventListener(DeviceLogEvent deviceLogEvent) {
        try {
            String jsonData = objectMapper.writeValueAsString(deviceLogEvent);
            System.out.println("Listener received on deviceLogService: " + jsonData);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            // Handle the exception appropriately
        }
    }
//    @KafkaListener(topics = "deviceService", groupId = "groupId")
//    void listener(String data) {
//        System.out.println("Listener received on deviceService " + data);
//    }
//
//    @KafkaListener(topics = "deviceLogService", groupId = "groupId")
//    void deviceLogEventListener(String data) {
//        System.out.println("Listener received on deviceLogService: " + data);
//    }
}
