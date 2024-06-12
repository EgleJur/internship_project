package com.internship.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class KafkaListeners {

    @KafkaListener(topics = "deviceService", groupId = "groupId")
    void listener(String data) {
        System.out.println("Listener received on deviceService " + data);
    }

//    @KafkaListener(topics = "deviceLogService", groupId = "groupId")
//    void deviceLogEventListener(String data) {
//        System.out.println("Listener received on deviceLogService: " + data);
//    }
}
