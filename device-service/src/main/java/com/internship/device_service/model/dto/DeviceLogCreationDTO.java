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
public class DeviceLogCreationDTO {

    private Long deviceId;
    private LocalDateTime timestamp;
    private String logMessage;
}
