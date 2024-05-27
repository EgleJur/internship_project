package com.internship.device_service.mapper;

import com.internship.device_service.model.Device;
import com.internship.device_service.model.dto.DeviceCreationDTO;
import com.internship.device_service.model.dto.DeviceDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DeviceMapper {
    Device deviceDTOToDevice(DeviceDTO deviceDTO);

    Device deviceCreationDTOToDevice(DeviceCreationDTO deviceCreationDTO);

    DeviceDTO deviceToDeviceDTO(Device device);
}
