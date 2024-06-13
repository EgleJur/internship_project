package com.internship.userservice.feign;

import com.internship.userservice.model.Device;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient("DEVICE-SERVICE")
public interface UserInterface {
    @GetMapping("/api/devices/user/{userId}")
    List<Device> getDevicesByUserId(@PathVariable("userId") Long userId);

    @DeleteMapping("/api/devices/user/{userId}")
    void deleteDeviceByUserId(@PathVariable("userId") Long userId);
}
