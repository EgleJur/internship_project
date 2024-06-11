package com.internship.device_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceLogEvent {

    private Long deviceId;
    private LocalDateTime timestamp;
    private LogEventType logMessage;
}
