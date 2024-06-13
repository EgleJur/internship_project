package com.internship.device_service.dao;

import com.internship.device_service.model.Device;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByUserId(Long userId);

    void deleteByUserId(Long userId);

    @Transactional
    void deleteDeviceByUserId(Long userId);
}
