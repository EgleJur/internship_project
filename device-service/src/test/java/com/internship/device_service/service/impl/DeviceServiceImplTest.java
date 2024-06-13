package com.internship.device_service.service.impl;

import com.internship.device_service.dao.DeviceRepository;
import com.internship.device_service.feign.ProducerService;
import com.internship.device_service.feign.UserClient;
import com.internship.device_service.mapper.DeviceMapper;
import com.internship.device_service.model.Device;
import com.internship.device_service.model.DeviceEvent;
import com.internship.device_service.model.DeviceLogEvent;
import com.internship.device_service.model.EventType;
import com.internship.device_service.model.LogEventType;
import com.internship.device_service.model.User;
import com.internship.device_service.model.dto.DeviceCreationDTO;
import com.internship.device_service.model.dto.DeviceDTO;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeviceServiceImplTest {
    @Mock
    private DeviceRepository deviceRepositoryMock;

    @Mock
    private DeviceMapper deviceMapperMock;
    @Mock
    UserClient userClient;
    @Mock
    ProducerService kafkaEventPublisher;

    @InjectMocks
    private DeviceServiceImpl deviceServiceTarget;
    private static final String DEVICE_TOPIC = "deviceService";
    private static final String DEVICE_LOG_TOPIC = "deviceLogService";

    private Device device;
    private DeviceDTO deviceDTO;
    private DeviceCreationDTO deviceCreationDTO;
    private User user;

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

        deviceCreationDTO = DeviceCreationDTO.builder()
                .userId(2L)
                .deviceName("Device A")
                .deviceType("Type B")
                .model("Model X")
                .location("Location Y")
                .status("Active")
                .createdAt(date)
                .updatedAt(date)
                .build();
        user = User.builder()
                .userId(2L)
                .username("User A")
                .build();

    }

    @Test
    void getAllDevices() {
        when(deviceRepositoryMock.findAll()).thenReturn(Arrays.asList(device));
        when(deviceMapperMock.deviceToDeviceDTO(any(Device.class))).thenReturn(deviceDTO);

        List<DeviceDTO> result = deviceServiceTarget.getAllDevices();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Device A", result.get(0).getDeviceName());

        verify(deviceRepositoryMock, times(1)).findAll();
        verify(deviceMapperMock, times(1)).deviceToDeviceDTO(any(Device.class));

    }

    @Test
    void getAllDevicesEmpty() {
        when(deviceRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        assertTrue(deviceServiceTarget.getAllDevices().isEmpty());

        verify(deviceRepositoryMock, times(1)).findAll();
    }


    @Test
    void getDeviceById() {
        when(deviceRepositoryMock.findById(1L)).thenReturn(Optional.of(device));
        when(deviceMapperMock.deviceToDeviceDTO(any(Device.class))).thenReturn(deviceDTO);

        DeviceDTO result = deviceServiceTarget.getDeviceById(1L);

        assertNotNull(result);
        assertEquals("Device A", result.getDeviceName());
        verify(deviceRepositoryMock, times(1)).findById(1L);
        verify(deviceMapperMock, times(1)).deviceToDeviceDTO(any(Device.class));

    }

    @Test
    void getDeviceByIdInvalid() {

        when(deviceRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                deviceServiceTarget.getDeviceById(1L));
        verify(deviceRepositoryMock, times(1)).findById(1L);
        verify(deviceMapperMock, times(0)).deviceToDeviceDTO(any(Device.class));

    }

    @Test
    void createDevice() {

        when(userClient.getUserById(2L)).thenReturn(user);
        when(deviceMapperMock.deviceCreationDTOToDevice(any(DeviceCreationDTO.class))).thenReturn(device);
        when(deviceRepositoryMock.save(any(Device.class))).thenReturn(device);
        when(deviceMapperMock.deviceToDeviceDTO(any(Device.class))).thenReturn(deviceDTO);
        DeviceDTO result = deviceServiceTarget.createDevice(deviceCreationDTO);

        assertNotNull(result);
        assertEquals("Device A", result.getDeviceName());
        verify(deviceMapperMock, times(1)).deviceCreationDTOToDevice(any(DeviceCreationDTO.class));
        verify(deviceRepositoryMock, times(1)).save(any(Device.class));
        verify(deviceMapperMock, times(1)).deviceToDeviceDTO(any(Device.class));

    }

    @Test
    void createDevice_UserNotFound() {
        Long nonExistentId = 999L;
        when(userClient.getUserById(nonExistentId)).thenReturn(null);
        deviceCreationDTO.setUserId(nonExistentId);
        assertThrows(RuntimeException.class, () -> deviceServiceTarget.createDevice(deviceCreationDTO));

        verify(userClient, times(1)).getUserById(nonExistentId);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            deviceServiceTarget.createDevice(deviceCreationDTO);
        });

        assertEquals("User not found", exception.getMessage());


    }

    @Test
    void updateDevice() {

        when(deviceRepositoryMock.findById(1L)).thenReturn(Optional.of(device));
        when(userClient.getUserById(2L)).thenReturn(user);
        when(deviceRepositoryMock.save(any(Device.class))).thenReturn(device);
        when(deviceMapperMock.deviceToDeviceDTO(any(Device.class))).thenReturn(deviceDTO);

        DeviceDTO result = deviceServiceTarget.updateDevice(1L, deviceCreationDTO);

        assertNotNull(result);
        assertEquals("Device A", result.getDeviceName());
        verify(deviceRepositoryMock, times(1)).findById(1L);
        verify(deviceRepositoryMock, times(1)).save(any(Device.class));
        verify(deviceMapperMock, times(1)).deviceToDeviceDTO(any(Device.class));

    }

    @Test
    void updateDevice_NotFound() {
        Long nonExistentId = 999L;
        when(deviceRepositoryMock.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> deviceServiceTarget.updateDevice(nonExistentId, deviceCreationDTO));

        verify(deviceRepositoryMock, times(1)).findById(nonExistentId);

    }

    @Test
    void updateDevice_UserNotFound() {
        Long nonExistentId = 999L;
        when(deviceRepositoryMock.findById(1L)).thenReturn(Optional.of(device));
        when(userClient.getUserById(nonExistentId)).thenReturn(null);
        deviceCreationDTO.setUserId(nonExistentId);
        assertThrows(RuntimeException.class, () -> deviceServiceTarget.updateDevice(1L, deviceCreationDTO));

        verify(userClient, times(1)).getUserById(nonExistentId);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            deviceServiceTarget.updateDevice(1L, deviceCreationDTO);
        });

        assertEquals("User not found", exception.getMessage());


    }

    @Test
    void deleteDevice() {
        when(deviceRepositoryMock.findById(1L)).thenReturn(Optional.of(device));
//        doNothing().when(kafkaEventPublisher).sendEvent(DEVICE_TOPIC, new DeviceEvent(EventType.DEVICE_ADDED, device));
        deviceServiceTarget.deleteDevice(1L);

        verify(deviceRepositoryMock, times(1)).findById(1L);
        verify(deviceRepositoryMock, times(1)).delete(device);
    }

    @Test
    void getDevicesByUserId() {
        when(deviceRepositoryMock.findByUserId(2L)).thenReturn(Arrays.asList(device));
        when(deviceMapperMock.deviceToDeviceDTO(any(Device.class))).thenReturn(deviceDTO);


        List<DeviceDTO> result = deviceServiceTarget.getDevicesByUserId(2L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Device A", result.get(0).getDeviceName());
        verify(deviceRepositoryMock, times(1)).findByUserId(2L);
        verify(deviceMapperMock, times(1)).deviceToDeviceDTO(any(Device.class));

    }
}