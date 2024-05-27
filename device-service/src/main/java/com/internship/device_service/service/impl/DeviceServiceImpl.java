package com.internship.device_service.service.impl;

import com.internship.device_service.dao.DeviceRepository;
import com.internship.device_service.mapper.DeviceMapper;
import com.internship.device_service.model.Device;
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
    DeviceMapper deviceMapper;

    public List<DeviceDTO> getAllDevices() {
        List<Device> devices = deviceRepository.findAll();
        return devices.stream().map(deviceMapper::deviceToDeviceDTO)
                .collect(Collectors.toList());
    }

    public DeviceDTO getDeviceById(Long deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));
        return deviceMapper.deviceToDeviceDTO(device);
    }

    public DeviceDTO createDevice(DeviceCreationDTO deviceDetails) {
        deviceDetails.setCreatedAt(LocalDateTime.now());
        return deviceMapper.deviceToDeviceDTO(
                deviceRepository.save(deviceMapper.deviceCreationDTOToDevice(deviceDetails)));
    }

    public DeviceDTO updateDevice(Long deviceId, DeviceCreationDTO deviceDetails) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));

        device.setUserId(deviceDetails.getUserId());
        device.setDeviceName(deviceDetails.getDeviceName());
        device.setDeviceType(deviceDetails.getDeviceType());
        device.setModel(deviceDetails.getModel());
        device.setLocation(deviceDetails.getLocation());
        device.setStatus(deviceDetails.getStatus());
        device.setUpdatedAt(LocalDateTime.now());

        return deviceMapper.deviceToDeviceDTO(deviceRepository.save(device));
    }

    public void deleteDevice(Long deviceId) {
        Device device = deviceRepository.findById(deviceId)
                .orElseThrow(() -> new RuntimeException("Device not found"));
        deviceRepository.delete(device);
    }
}
