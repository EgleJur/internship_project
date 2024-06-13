package com.internship.consumer.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("DEVICE-SERVICE")
public interface DeviceInterface {

    @DeleteMapping("/api/devices/user/{userId}")
    void deleteDeviceByUserId(@PathVariable("userId") Long userId);
}
