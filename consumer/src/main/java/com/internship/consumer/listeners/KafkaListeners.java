package com.internship.consumer.listeners;

import com.internship.consumer.feign.DeviceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class KafkaListeners {

    private static final Logger log = LoggerFactory.getLogger(KafkaListeners.class);

    @Autowired
    private DeviceInterface deviceInterface;

    @KafkaListener(topics = "deviceService", groupId = "groupId")
    void listener(String data) {
        System.out.println("Listener received on deviceService " + data);
    }

    @KafkaListener(topics = "deviceLogService", groupId = "groupId")
    void deviceLogEventListener(String data) {
        System.out.println("Listener received on deviceLogService: " + data);
    }

    @KafkaListener(topics = "user-deletion", groupId = "groupId")
    void userDeletionListener(String message) {
        Long userId = Long.parseLong(message);
        log.info("Received user deletion message for userId: {}", userId);
        System.out.println("Listener received on user-deletion: " + userId);
        try {
            deviceInterface.deleteDeviceByUserId(userId);
        } catch (Exception e) {
            log.info("Device was not found with user id: " + userId);
        }

    }
}
