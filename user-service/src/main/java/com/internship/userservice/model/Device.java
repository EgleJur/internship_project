package com.internship.userservice.model;

import lombok.Data;

@Data
public class Device {
    private Long deviceId;
    private String deviceName;
    private String deviceType;
    private String status;
}
