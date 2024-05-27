package com.internship.device_service.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DeviceLogDTO {

    private Long logId;

    private DeviceDTO device;
    private LocalDateTime timestamp;
    private String logMessage;
}
