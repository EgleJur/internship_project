package com.internship.device_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("PRODUCER")
public interface ProducerService {
    @PostMapping("/api/producer")
    void sendEvent(@RequestParam("topic") String topic, @RequestBody Object event);
}
