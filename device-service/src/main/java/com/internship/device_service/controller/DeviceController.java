package com.internship.device_service.controller;

import com.internship.device_service.model.Device;
import com.internship.device_service.model.dto.DeviceCreationDTO;
import com.internship.device_service.model.dto.DeviceDTO;
import com.internship.device_service.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public DeviceDTO createDevice(@RequestBody DeviceCreationDTO deviceDetails) {
        return deviceService.createDevice(deviceDetails);
    }

    @PutMapping("/{deviceId}")
    public ResponseEntity<DeviceDTO> updateDevice(@PathVariable Long deviceId, @RequestBody DeviceCreationDTO deviceDetails) {
        return ResponseEntity.ok(deviceService.updateDevice(deviceId, deviceDetails));
    }

    @DeleteMapping("/{deviceId}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long deviceId) {
        deviceService.deleteDevice(deviceId);
        return ResponseEntity.noContent().build();
    }
}
