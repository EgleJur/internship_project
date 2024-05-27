package com.internship.device_service.service.impl;

import com.internship.device_service.dao.DeviceLogRepository;
import com.internship.device_service.dao.DeviceRepository;
import com.internship.device_service.mapper.DeviceLogMapper;
import com.internship.device_service.model.Device;
import com.internship.device_service.model.DeviceLog;
import com.internship.device_service.model.dto.DeviceLogCreationDTO;
import com.internship.device_service.model.dto.DeviceLogDTO;
import com.internship.device_service.service.DeviceLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceLogServiceImpl implements DeviceLogService {
    @Autowired
    private DeviceLogRepository deviceLogRepository;
    @Autowired
    private DeviceRepository deviceRepository;
    @Autowired
    DeviceLogMapper deviceLogMapper;

    public List<DeviceLogDTO> getAllDeviceLogs() {
        List<DeviceLog> deviceLogs = deviceLogRepository.findAll();
        return deviceLogs.stream().map(deviceLogMapper::deviceLogToDeviceLogDTO)
                .collect(Collectors.toList());
    }

    public DeviceLogDTO getDeviceLogById(Long deviceLogId) {
        DeviceLog deviceLog = deviceLogRepository.findById(deviceLogId)
                .orElseThrow(() -> new RuntimeException("DeviceLog not found"));
        return deviceLogMapper.deviceLogToDeviceLogDTO(deviceLog);
    }

    public DeviceLogDTO createDeviceLog(DeviceLogCreationDTO deviceLogDetails) {
        Device device = deviceRepository.findById(deviceLogDetails.getDeviceId())
                .orElseThrow(() -> new RuntimeException("Device not found"));

        deviceLogDetails.setTimestamp(LocalDateTime.now());
        DeviceLog deviceLog = deviceLogMapper.deviceLogCreationDTOToDeviceLog(deviceLogDetails);
        deviceLog.setDevice(device);
        return deviceLogMapper.deviceLogToDeviceLogDTO(
                deviceLogRepository.save(deviceLog));
    }


    public void deleteDeviceLog(Long deviceLogId) {
        DeviceLog deviceLog = deviceLogRepository.findById(deviceLogId)
                .orElseThrow(() -> new RuntimeException("DeviceLog not found"));
        deviceLogRepository.delete(deviceLog);
    }


}
