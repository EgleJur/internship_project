package com.internship.device_service.service.impl;

import com.internship.device_service.dao.DeviceRepository;
import com.internship.device_service.feign.ProducerService;
import com.internship.device_service.feign.UserClient;
import com.internship.device_service.mapper.DeviceMapper;
import com.internship.device_service.model.Device;
import com.internship.device_service.model.DeviceEvent;
import com.internship.device_service.model.EventType;
import com.internship.device_service.model.User;
import com.internship.device_service.model.dto.DeviceCreationDTO;
import com.internship.device_service.model.dto.DeviceDTO;
import com.internship.device_service.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private ProducerService producerService;
    @Autowired
    DeviceMapper deviceMapper;
    @Autowired
    UserClient userClient;

    private static final String DEVICE_TOPIC = "deviceService";


    public DeviceDTO getDeviceById(Long deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));
        return deviceMapper.deviceToDeviceDTO(device);
    }


    public List<DeviceDTO> getAllDevices() {
        List<Device> devices = deviceRepository.findAll();
        return devices.stream().map(deviceMapper::deviceToDeviceDTO)
                .collect(Collectors.toList());
    }

    public DeviceDTO createDevice(DeviceCreationDTO deviceDetails) {
        User user = userClient.getUserById(deviceDetails.getUserId());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        deviceDetails.setCreatedAt(LocalDateTime.now());

        Device savedDevice = deviceRepository.save(deviceMapper.deviceCreationDTOToDevice(deviceDetails));
        producerService.sendEvent(DEVICE_TOPIC, new DeviceEvent(EventType.DEVICE_ADDED, savedDevice));
        return deviceMapper.deviceToDeviceDTO(savedDevice);
    }

    public DeviceDTO updateDevice(Long deviceId, DeviceCreationDTO deviceDetails) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));
        User user = userClient.getUserById(deviceDetails.getUserId());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        device.setUserId(deviceDetails.getUserId());
        device.setDeviceName(deviceDetails.getDeviceName());
        device.setDeviceType(deviceDetails.getDeviceType());
        device.setModel(deviceDetails.getModel());
        device.setLocation(deviceDetails.getLocation());
        device.setStatus(deviceDetails.getStatus());
        device.setUpdatedAt(LocalDateTime.now());

        Device updatedDevice = deviceRepository.save(device);
        // kafkaEventPublisher.sendDeviceEvent(EventType.DEVICE_UPDATED, updatedDevice);
        return deviceMapper.deviceToDeviceDTO(updatedDevice);
    }

    public void deleteDevice(Long deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));
        deviceRepository.delete(device);
//        kafkaEventPublisher.sendDeviceLogEvent(LogEventType.DEVICE_DELETED, deviceId, LocalDateTime.now());
//        kafkaEventPublisher.sendDeviceEvent(EventType.DEVICE_DELETED, device);
    }

    public List<DeviceDTO> getDevicesByUserId(Long userId) {
        List<Device> devices = deviceRepository.findByUserId(userId);
        return devices.stream().map(deviceMapper::deviceToDeviceDTO)
                .collect(Collectors.toList());
    }
}
