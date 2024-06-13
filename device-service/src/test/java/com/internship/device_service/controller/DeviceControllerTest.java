package com.internship.device_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.device_service.model.dto.DeviceCreationDTO;
import com.internship.device_service.model.dto.DeviceDTO;
import com.internship.device_service.service.DeviceService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@WebMvcTest(DeviceController.class)
class DeviceControllerTest {
    public static String URL = "/api/devices";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DeviceService deviceServiceMock;

    @InjectMocks
    private DeviceController deviceControllerTarget;

    private LocalDateTime date = LocalDateTime.of(2024, 1, 1, 12, 0, 0);

    private DeviceDTO setUpDeviceDTO() {

        return DeviceDTO.builder()
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
    }

    private DeviceCreationDTO setUpDeviceCreationDTO() {

        return DeviceCreationDTO.builder()
                .userId(2L)
                .deviceName("Device A")
                .deviceType("Type B")
                .model("Model X")
                .location("Location Y")
                .status("Active")
                .createdAt(date)
                .updatedAt(date)
                .build();
    }

    @Test
    void getAllDevices() throws Exception {
        List<DeviceDTO> deviceList = new ArrayList<>();
        DeviceDTO deviceDTO = setUpDeviceDTO();
        deviceList.add(deviceDTO);

        when(deviceServiceMock.getAllDevices()).thenReturn(deviceList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].deviceId").value(1L))
                .andExpect(jsonPath("$[0].userId").value(2L))
                .andExpect(jsonPath("$[0].deviceName").value("Device A"))
                .andExpect(jsonPath("$[0].deviceType").value("Type B"))
                .andExpect(jsonPath("$[0].model").value("Model X"))
                .andExpect(jsonPath("$[0].location").value("Location Y"))
                .andExpect(jsonPath("$[0].status").value("Active"))
                .andExpect(jsonPath("$[0].createdAt").value("2024-01-01T12:00:00"))
                .andExpect(jsonPath("$[0].updatedAt").value("2024-01-01T12:00:00"))
                .andExpect(status().isOk());

        verify(deviceServiceMock, times(1)).getAllDevices();
    }

    @Test
    void getDeviceById() throws Exception {
        DeviceDTO deviceDTO = setUpDeviceDTO();

        when(deviceServiceMock.getDeviceById(1L))
                .thenReturn(deviceDTO);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/{id}", 1L)
                        .param("$.deviceId", "1")
                        .param("$.userId", "2")
                        .param("$.deviceName", "Device A")
                        .param("$.deviceType", "Type B")
                        .param("$.model", "Model X")
                        .param("$.location", "Location Y")
                        .param("$.status", "Active")
                        .param("$.createdAt", "2024-01-01T12:00:00")
                        .param("$.updatedAt", "2024-01-01T12:00:00")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deviceDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertEquals(objectMapper.writeValueAsString(deviceDTO), responseBody);
    }

    @Test
    void createDevice() throws Exception {
        DeviceCreationDTO deviceCreationDTO = setUpDeviceCreationDTO();
        DeviceDTO deviceDTO = setUpDeviceDTO();

        when(deviceServiceMock.createDevice(deviceCreationDTO)).thenReturn(deviceDTO);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deviceCreationDTO)));

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.deviceId").value(1L))
                .andExpect(jsonPath("$.userId").value(2L))
                .andExpect(jsonPath("$.deviceName").value("Device A"))
                .andExpect(jsonPath("$.deviceType").value("Type B"))
                .andExpect(jsonPath("$.model").value("Model X"))
                .andExpect(jsonPath("$.location").value("Location Y"))
                .andExpect(jsonPath("$.status").value("Active"))
                .andExpect(jsonPath("$.createdAt").value("2024-01-01T12:00:00"))
                .andExpect(jsonPath("$.updatedAt").value("2024-01-01T12:00:00"));

        verify(deviceServiceMock, times(1)).createDevice(deviceCreationDTO);
    }

    @Test
    void createDevice_UserNotFound() throws Exception {
        when(deviceServiceMock.createDevice(setUpDeviceCreationDTO()))
                .thenThrow(new RuntimeException("User not found with id: 2"));
        mockMvc.perform(MockMvcRequestBuilders
                        .post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(setUpDeviceCreationDTO())))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with id: 2"));
    }


    @Test
    void updateDevice() throws Exception {
        DeviceCreationDTO deviceCreationDTO = setUpDeviceCreationDTO();
        DeviceDTO deviceDTO = setUpDeviceDTO();

        when(deviceServiceMock.updateDevice(1L, deviceCreationDTO)).thenReturn(deviceDTO);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/{id}", 1L)
                        .param("$.deviceId", "1")
                        .param("$.userId", "2")
                        .param("$.deviceName", "Device A")
                        .param("$.deviceType", "Type B")
                        .param("$.model", "Model X")
                        .param("$.location", "Location Y")
                        .param("$.status", "Active")
                        .param("$.createdAt", "2024-01-01T12:00:00")
                        .param("$.updatedAt", "2024-01-01T12:00:00")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deviceCreationDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertEquals(objectMapper.writeValueAsString(deviceDTO), responseBody);
    }

    @Test
    void updateDevice_UserNotFound() throws Exception {
        when(deviceServiceMock.updateDevice(1L, setUpDeviceCreationDTO()))
                .thenThrow(new RuntimeException("User not found with id: 2"));
        mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/{deviceId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(setUpDeviceCreationDTO())))
                .andExpect(status().isNotFound())
                .andExpect(content().string("User not found with id: 2"));
    }

    @Test
    void deleteDevice() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete(URL + "/{id}", id))
                .andExpect(status().isNoContent());

        verify(deviceServiceMock, times(1)).deleteDevice(id);
    }

    @Test
    void getDevicesByUserId() throws Exception {

        List<DeviceDTO> deviceList = new ArrayList<>();
        DeviceDTO deviceDTO = setUpDeviceDTO();

        deviceList.add(deviceDTO);

        when(deviceServiceMock.getDevicesByUserId(2L)).thenReturn(deviceList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/user/{userId}", 2L))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].deviceId").value(1L))
                .andExpect(jsonPath("$[0].userId").value(2L))
                .andExpect(jsonPath("$[0].deviceName").value("Device A"))
                .andExpect(jsonPath("$[0].deviceType").value("Type B"))
                .andExpect(jsonPath("$[0].model").value("Model X"))
                .andExpect(jsonPath("$[0].location").value("Location Y"))
                .andExpect(jsonPath("$[0].status").value("Active"))
                .andExpect(jsonPath("$[0].createdAt").value("2024-01-01T12:00:00"))
                .andExpect(jsonPath("$[0].updatedAt").value("2024-01-01T12:00:00"))
                .andExpect(status().isOk());

        verify(deviceServiceMock, times(1)).getDevicesByUserId(2L);

    }
}