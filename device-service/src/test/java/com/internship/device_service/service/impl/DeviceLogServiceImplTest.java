package com.internship.device_service.service.impl;

import com.internship.device_service.dao.DeviceLogRepository;
import com.internship.device_service.dao.DeviceRepository;
import com.internship.device_service.mapper.DeviceLogMapper;
import com.internship.device_service.model.Device;
import com.internship.device_service.model.DeviceLog;
import com.internship.device_service.model.dto.DeviceDTO;
import com.internship.device_service.model.dto.DeviceLogCreationDTO;
import com.internship.device_service.model.dto.DeviceLogDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeviceLogServiceImplTest {
    @Mock
    private DeviceLogRepository deviceLogRepositoryMock;
    @Mock
    private DeviceRepository deviceRepositoryMock;
    @Mock
    private DeviceLogMapper deviceLogMapperMock;

    @InjectMocks
    private DeviceLogServiceImpl deviceLogServiceTarget;

    private Device device;
    private DeviceDTO deviceDTO;
    private DeviceLog deviceLog;
    private DeviceLogDTO deviceLogDTO;
    private DeviceLogCreationDTO deviceLogCreationDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        LocalDateTime date = LocalDateTime.of(2024, 1, 1, 12, 0, 0);

        device = Device.builder()
                .deviceId(1L)
                .userId(2L)
                .deviceName("Device A")
                .deviceType("Type B")
                .model("Model X")
                .location("Location Y")
                .status("Active")
                .createdAt(date)
                .updatedAt(date)
                .build();

        deviceDTO = DeviceDTO.builder()
                .deviceId(1L)
                .userId(2L)
                .deviceName("Device A")
                .deviceType("Type B")
                .model("Model X")
                .location("Location Y")
                .status("Active")
                .createdAt(date)
                .updatedAt(date)
                .build();

        deviceLog = DeviceLog.builder()
                .logId(1L)
                .device(device)
                .logMessage("Active")
                .timestamp(date)
                .build();

        deviceLogDTO = DeviceLogDTO.builder()
                .logId(1L)
                .device(deviceDTO)
                .logMessage("Active")
                .timestamp(date)
                .build();

        deviceLogCreationDTO = DeviceLogCreationDTO.builder()
                .deviceId(1L)
                .logMessage("Active")
                .timestamp(date)
                .build();

    }

    @Test
    void getAllDeviceLogs() {
        when(deviceLogRepositoryMock.findAll()).thenReturn(Arrays.asList(deviceLog));
        when(deviceLogMapperMock.deviceLogToDeviceLogDTO(any(DeviceLog.class))).thenReturn(deviceLogDTO);

        List<DeviceLogDTO> result = deviceLogServiceTarget.getAllDeviceLogs();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Active", result.get(0).getLogMessage());

        verify(deviceLogRepositoryMock, times(1)).findAll();
        verify(deviceLogMapperMock, times(1)).deviceLogToDeviceLogDTO(any(DeviceLog.class));

    }

    @Test
    void getAllDeviceLogsEmpty() {
        when(deviceLogRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        assertTrue(deviceLogServiceTarget.getAllDeviceLogs().isEmpty());

        verify(deviceLogRepositoryMock, times(1)).findAll();
    }

    @Test
    void getDeviceLogById() {
        when(deviceLogRepositoryMock.findById(1L)).thenReturn(Optional.of(deviceLog));
        when(deviceLogMapperMock.deviceLogToDeviceLogDTO(any(DeviceLog.class))).thenReturn(deviceLogDTO);

        DeviceLogDTO result = deviceLogServiceTarget.getDeviceLogById(1L);

        assertNotNull(result);
        assertEquals("Active", result.getLogMessage());
        verify(deviceLogRepositoryMock, times(1)).findById(1L);
        verify(deviceLogMapperMock, times(1)).deviceLogToDeviceLogDTO(any(DeviceLog.class));

    }

    @Test
    void getDeviceLogByIdInvalid() {

        when(deviceLogRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                deviceLogServiceTarget.getDeviceLogById(1L));
        verify(deviceLogRepositoryMock, times(1)).findById(1L);
        verify(deviceLogMapperMock, times(0)).deviceLogToDeviceLogDTO(any(DeviceLog.class));

    }

    @Test
    void createDeviceLog() {
        when(deviceRepositoryMock.findById(1L)).thenReturn(Optional.of(device));
        when(deviceLogMapperMock.deviceLogCreationDTOToDeviceLog(any(DeviceLogCreationDTO.class))).thenReturn(deviceLog);
        when(deviceLogRepositoryMock.save(any(DeviceLog.class))).thenReturn(deviceLog);
        when(deviceLogMapperMock.deviceLogToDeviceLogDTO(any(DeviceLog.class))).thenReturn(deviceLogDTO);

        DeviceLogDTO result = deviceLogServiceTarget.createDeviceLog(deviceLogCreationDTO);

        assertNotNull(result);
        assertEquals("Active", result.getLogMessage());
        verify(deviceLogMapperMock, times(1)).deviceLogCreationDTOToDeviceLog(any(DeviceLogCreationDTO.class));
        verify(deviceLogRepositoryMock, times(1)).save(any(DeviceLog.class));
        verify(deviceLogMapperMock, times(1)).deviceLogToDeviceLogDTO(any(DeviceLog.class));

    }

    @Test
    void deleteDeviceLog() {
        when(deviceLogRepositoryMock.findById(1L)).thenReturn(Optional.of(deviceLog));

        deviceLogServiceTarget.deleteDeviceLog(1L);

        verify(deviceLogRepositoryMock, times(1)).findById(1L);
        verify(deviceLogRepositoryMock, times(1)).delete(deviceLog);

    }
}