package com.internship.device_service.feign;

import com.internship.device_service.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient(name = "USER-SERVICE", url = "http://51.20.54.141:8080")
public interface UserClient {
    @GetMapping("/api/users/{userId}")
    User getUserById(@PathVariable("userId") Long userId);
}
