package com.internship.device_service.service;

import com.internship.device_service.model.dto.DeviceCreationDTO;
import com.internship.device_service.model.dto.DeviceDTO;

import java.util.List;

public interface DeviceService {
    List<DeviceDTO> getAllDevices();

    DeviceDTO getDeviceById(Long deviceId);

    DeviceDTO createDevice(DeviceCreationDTO deviceDetails);

    DeviceDTO updateDevice(Long deviceId, DeviceCreationDTO deviceDetails);

    void deleteDevice(Long deviceId);

    List<DeviceDTO> getDevicesByUserId(Long userId);
}
