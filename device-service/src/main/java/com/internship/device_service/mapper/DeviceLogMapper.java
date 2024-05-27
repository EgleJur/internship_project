package com.internship.device_service.mapper;

import com.internship.device_service.model.DeviceLog;
import com.internship.device_service.model.dto.DeviceLogCreationDTO;
import com.internship.device_service.model.dto.DeviceLogDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {DeviceMapper.class})
public interface DeviceLogMapper {
    DeviceLog deviceLogDTOToDeviceLog(DeviceLogDTO deviceLogDTO);

    DeviceLog deviceLogCreationDTOToDeviceLog(DeviceLogCreationDTO deviceLogCreationDTO);

    DeviceLogDTO deviceLogToDeviceLogDTO(DeviceLog deviceLog);
}
