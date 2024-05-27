package com.internship.device_service.controller;


import com.internship.device_service.model.dto.DeviceLogCreationDTO;
import com.internship.device_service.model.dto.DeviceLogDTO;
import com.internship.device_service.service.DeviceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/deviceLogs")
public class DeviceLogController {
    @Autowired
    private DeviceLogService deviceLogService;

    @GetMapping
    public List<DeviceLogDTO> getAllDeviceLogs() {
        return deviceLogService.getAllDeviceLogs();
    }

    @GetMapping("/{deviceLogId}")
    public DeviceLogDTO getDeviceLogById(@PathVariable Long deviceLogId) {
        return deviceLogService.getDeviceLogById(deviceLogId);
    }

    @PostMapping
    public DeviceLogDTO createDeviceLog(@RequestBody DeviceLogCreationDTO deviceLogDetails) {
        return deviceLogService.createDeviceLog(deviceLogDetails);
    }

    @DeleteMapping("/{deviceLogId}")
    public ResponseEntity<Void> deleteDeviceLog(@PathVariable Long deviceLogId) {
        deviceLogService.deleteDeviceLog(deviceLogId);
        return ResponseEntity.noContent().build();
    }
}
