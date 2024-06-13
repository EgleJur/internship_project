package com.internship.producer.controller;

import com.internship.producer.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/producer")
public class ProducerController {
    @Autowired
    private KafkaProducerService producerService;

    @PostMapping
    public void sendDeviceEvent(@RequestParam String topic, @RequestBody Object event) {
        producerService.sendEvent(topic, event);
    }

}
