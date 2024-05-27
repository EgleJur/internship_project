package com.internship.device_service.service;

import com.internship.device_service.model.Device;
import com.internship.device_service.model.dto.DeviceCreationDTO;
import com.internship.device_service.model.dto.DeviceDTO;

import java.util.List;
import java.util.Optional;

public interface DeviceService {
    List<DeviceDTO> getAllDevices();

    DeviceDTO getDeviceById(Long deviceId);

    DeviceDTO createDevice(DeviceCreationDTO deviceDetails);

    DeviceDTO updateDevice(Long deviceId, DeviceCreationDTO deviceDetails);

    void deleteDevice(Long deviceId);

}
