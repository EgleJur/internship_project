package com.internship.device_service.controller;

import com.internship.device_service.model.dto.DeviceCreationDTO;
import com.internship.device_service.model.dto.DeviceDTO;
import com.internship.device_service.service.DeviceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api/devices")
public class DeviceController {
    @Autowired
    private DeviceService deviceService;

    @GetMapping
    public List<DeviceDTO> getAllDevices() {
        return deviceService.getAllDevices();
    }

    @GetMapping("/{deviceId}")
    public DeviceDTO getDeviceById(@PathVariable Long deviceId) {
        return deviceService.getDeviceById(deviceId);
    }

    @PostMapping
    public ResponseEntity<?> createDevice(@RequestBody DeviceCreationDTO deviceDetails) {
        try {
            DeviceDTO deviceDTO = deviceService.createDevice(deviceDetails);
            log.info("Device created with id: " + deviceDTO.getDeviceId());
            return ResponseEntity.ok(deviceDTO);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("User not found")) {
                log.info("User not found with id: " + deviceDetails.getUserId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found with id: " + deviceDetails.getUserId());
            }
            log.warn("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error");
        }
    }

    @PutMapping("/{deviceId}")
    public ResponseEntity<?> updateDevice(@PathVariable Long deviceId, @RequestBody DeviceCreationDTO deviceDetails) {
        try {
            DeviceDTO deviceDTO = deviceService.updateDevice(deviceId, deviceDetails);
            log.info("Device updated with id: " + deviceId);
            return ResponseEntity.ok(deviceDTO);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("User not found")) {
                log.info("User not found with id: " + deviceDetails.getUserId());
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("User not found with id: " + deviceDetails.getUserId());
            }
            log.warn("Internal Server Error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Internal Server Error");
        }
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long deviceId) {
        deviceService.deleteDevice(deviceId);
        log.info("Device deleted successfully with id: " + deviceId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<DeviceDTO>> getDevicesByUserId(@PathVariable Long userId) {
        log.info("Searching devices for user with id: " + userId);
        return ResponseEntity.ok(deviceService.getDevicesByUserId(userId));
    }

    @DeleteMapping("/user/{userId}")
    public void deleteDeviceByUserId(@PathVariable Long userId) {
        deviceService.deleteDeviceByUserId(userId);
    }
}
