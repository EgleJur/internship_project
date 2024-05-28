package com.internship.device_service.feign;

import com.internship.device_service.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


@FeignClient("USER-SERVICE")
public interface UserClient {
    @GetMapping("/api/users/{userId}")
    User getUserById(@PathVariable("userId") Long userId);
}
