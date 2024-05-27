package com.internship.device_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.device_service.model.DeviceLog;
import com.internship.device_service.model.dto.DeviceCreationDTO;
import com.internship.device_service.model.dto.DeviceDTO;
import com.internship.device_service.model.dto.DeviceLogCreationDTO;
import com.internship.device_service.model.dto.DeviceLogDTO;
import com.internship.device_service.service.DeviceLogService;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeviceLogController.class)
class DeviceLogControllerTest {
    public static String URL = "/api/deviceLogs";
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private DeviceLogService deviceLogServiceMock;

    @InjectMocks
    private DeviceLogController deviceLogControllerTarget;

    private DeviceDTO setUpDeviceDTO() {

        LocalDateTime date = LocalDateTime.of(2024, 1, 1, 12, 00, 00);

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

    private DeviceLogDTO setUpDeviceLogDTO() {

        LocalDateTime date = LocalDateTime.of(2024, 1, 1, 12, 00, 00);

        return DeviceLogDTO.builder()
                .logId(1L)
                .device(setUpDeviceDTO())
                .logMessage("Active")
                .timestamp(date)
                .build();
    }

    private DeviceLogCreationDTO setUpDeviceLogCreationDTO() {

        LocalDateTime date = LocalDateTime.of(2024, 1, 1, 12, 00, 00);

        return DeviceLogCreationDTO.builder()
                .deviceId(1L)
                .logMessage("Active")
                .timestamp(date)
                .build();
    }

    @Test
    void getAllDeviceLogs() throws Exception {
        List<DeviceLogDTO> deviceLogsList = new ArrayList<>();
        DeviceLogDTO deviceLogDTO = setUpDeviceLogDTO();
        deviceLogsList.add(deviceLogDTO);

        when(deviceLogServiceMock.getAllDeviceLogs()).thenReturn(deviceLogsList);


        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].logId").value(1L))
                .andExpect(jsonPath("$[0].device.deviceId").value(1L))
                .andExpect(jsonPath("$[0].logMessage").value("Active"))
                .andExpect(jsonPath("$[0].timestamp").value("2024-01-01T12:00:00"))
                .andExpect(status().isOk());

        verify(deviceLogServiceMock, times(1)).getAllDeviceLogs();

    }

    @Test
    void getDeviceLogById() throws Exception {
        DeviceLogDTO deviceLogDTO = setUpDeviceLogDTO();

        when(deviceLogServiceMock.getDeviceLogById(1L))
                .thenReturn(deviceLogDTO);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/{id}", 1L)
                        .param("$.logId", "1")
                        .param("$.device.deviceId", "1")
                        .param("$.logMessage", "Active")
                        .param("$.timestamp", "2024-01-01T12:00:00")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(deviceLogDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertEquals(objectMapper.writeValueAsString(deviceLogDTO), responseBody);

    }

    @Test
    void createDeviceLog() throws Exception {
        DeviceLogCreationDTO deviceLogCreationDTO = setUpDeviceLogCreationDTO();
        DeviceLogDTO deviceLogDTO = setUpDeviceLogDTO();

        when(deviceLogServiceMock.createDeviceLog(deviceLogCreationDTO))
                .thenReturn(deviceLogDTO);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(deviceLogCreationDTO)));

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.logId").value(1L))
                .andExpect(jsonPath("$.device.deviceId").value(1L))
                .andExpect(jsonPath("$.logMessage").value("Active"))
                .andExpect(jsonPath("$.timestamp").value("2024-01-01T12:00:00"));

        verify(deviceLogServiceMock, times(1)).createDeviceLog(deviceLogCreationDTO);

    }

    @Test
    void deleteDeviceLog() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete(URL + "/{id}", id))
                .andExpect(status().isNoContent());

        verify(deviceLogServiceMock, times(1)).deleteDeviceLog(id);

    }
}