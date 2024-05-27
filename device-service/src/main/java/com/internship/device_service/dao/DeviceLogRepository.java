package com.internship.device_service.dao;


import com.internship.device_service.model.DeviceLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeviceLogRepository extends JpaRepository<DeviceLog, Long> {
}
