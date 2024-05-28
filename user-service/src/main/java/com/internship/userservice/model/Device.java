package com.internship.userservice.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Device {
    private Long deviceId;
    private String deviceName;
    private String deviceType;
    private String status;
}
