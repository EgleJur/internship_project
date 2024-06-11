package com.internship.device_service.config;

import com.internship.device_service.model.DeviceEvent;
import com.internship.device_service.model.DeviceLogEvent;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.KafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String boostrapServers;

    public Map<String, Object> consumerConfig() {
        HashMap<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, boostrapServers);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return props;
    }

    @Bean
    public ConsumerFactory<String, DeviceEvent> deviceEventConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, DeviceEvent>> deviceEventFactory(
            ConsumerFactory<String, DeviceEvent> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, DeviceEvent> deviceEventFactory =
                new ConcurrentKafkaListenerContainerFactory<>();
        deviceEventFactory.setConsumerFactory(deviceEventConsumerFactory());
        return deviceEventFactory;
    }

    @Bean
    public ConsumerFactory<String, DeviceLogEvent> deviceLogEventConsumerFactory() {
        return new DefaultKafkaConsumerFactory<>(consumerConfig());
    }

    public KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String, DeviceLogEvent>> deviceLogEventFactory(
            ConsumerFactory<String, DeviceLogEvent> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, DeviceLogEvent> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(deviceLogEventConsumerFactory());
        return factory;
    }

}
