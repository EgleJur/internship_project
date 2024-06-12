package com.internship.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class ProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class, args);
    }

//    @Bean
//    CommandLineRunner commandLineRunner(KafkaTemplate<String, Object> kafkaTemplate) {
//        return args -> {
//            for (int i = 0; i < 100; i++) {
//                kafkaTemplate.send("deviceService", "hello kafka :)" + i);
//            }
//        };
//    }
}
