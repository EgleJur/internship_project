package com.internship.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.userservice.model.Device;
import com.internship.userservice.model.dto.UserCreationDTO;
import com.internship.userservice.model.dto.UserDTO;
import com.internship.userservice.service.UserService;
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
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    public static String URL = "/api/users";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserService userServiceMock;

    @InjectMocks
    private UserController userControllerTarget;

    private UserDTO setUpUserDTO() {
        return UserDTO.builder()
                .userId(1L)
                .userName("Jim")
                .email("jim@jim.com")
                .role("admin")
                .createdAt(LocalDateTime.of(2024, 1, 1, 12, 00, 00))
                .updatedAt(LocalDateTime.of(2024, 2, 1, 12, 00, 00))
                .build();
    }

    private UserCreationDTO setUpUserCreationDTO() {
        return UserCreationDTO.builder()
                .userName("Jim")
                .passwordHash("Tsdghyt8")
                .email("jim@jim.com")
                .role("admin")
                .createdAt(LocalDateTime.of(2024, 1, 1, 12, 00, 00))
                .updatedAt(LocalDateTime.of(2024, 2, 1, 12, 00, 00))
                .build();
    }

    private Device setUpDevice() {
        return Device.builder()
                .deviceId(1L)
                .deviceName("Dev1")
                .deviceType("Type A")
                .status("Active")
                .build();
    }

    @Test
    void getAllUsers() throws Exception {
        List<UserDTO> userList = new ArrayList<>();
        UserDTO user = setUpUserDTO();
        userList.add(user);

        when(userServiceMock.getAllUsers()).thenReturn(userList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].userId").value(1L))
                .andExpect(jsonPath("$[0].userName").value("Jim"))
                .andExpect(jsonPath("$[0].email").value("jim@jim.com"))
                .andExpect(jsonPath("$[0].role").value("admin"))
                .andExpect(jsonPath("$[0].createdAt").value("2024-01-01T12:00:00"))
                .andExpect(jsonPath("$[0].updatedAt").value("2024-02-01T12:00:00"))
                .andExpect(status().isOk());

        verify(userServiceMock, times(1)).getAllUsers();

    }

    @Test
    void getUserById() throws Exception {
        UserDTO userDTO = setUpUserDTO();

        when(userServiceMock.getUserById(1L))
                .thenReturn(userDTO);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/{userId}", 1L)
                        .param("userId", "1")
                        .param("userName", "Jim")
                        .param("email", "jim@jim.com")
                        .param("role", "admin")
                        .param("createdAt", "2024-01-01T12:00:00")
                        .param("updatedAt", "2024-02-01T12:00:00")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertEquals(objectMapper.writeValueAsString(userDTO), responseBody);
    }

    @Test
    public void getUserById_whenInvalidRequest() throws Exception {
        when(userServiceMock.getUserById(999L)).thenThrow(new RuntimeException("User not found"));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/{userId}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());

    }

    @Test
    void createUser() throws Exception {
        UserCreationDTO userCreationDTO = setUpUserCreationDTO();
        UserDTO userDTO = setUpUserDTO();

        when(userServiceMock.createUser(userCreationDTO)).thenReturn(userDTO);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userCreationDTO)));

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value(1))
                .andExpect(jsonPath("$.userName").value("Jim"))
                .andExpect(jsonPath("$.email").value("jim@jim.com"))
                .andExpect(jsonPath("$.role").value("admin"))
                .andExpect(jsonPath("$.createdAt").value("2024-01-01T12:00:00"))
                .andExpect(jsonPath("$.updatedAt").value("2024-02-01T12:00:00"));

        verify(userServiceMock, times(1)).createUser(userCreationDTO);
    }

    @Test
    void updateUser() throws Exception {

        UserCreationDTO userCreationDTO = setUpUserCreationDTO();

        this.mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/{userId}", 1L)
                        .param("userId", "1")
                        .param("userName", "Jim")
                        .param("email", "jim@jim.com")
                        .param("role", "admin")
                        .param("createdAt", "2024-01-01T12:00:00")
                        .param("updatedAt", "2024-02-01T12:00:00")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreationDTO)))
                .andExpect(status().isOk());

        verify(userServiceMock, times(1))
                .updateUser(1L, userCreationDTO);
    }

    @Test
    void updateUser_UserNotFound() throws Exception {

        UserCreationDTO userCreationDTO = setUpUserCreationDTO();

        when(userServiceMock.updateUser(1L, userCreationDTO))
                .thenThrow(new RuntimeException("User not found"));

        this.mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/{userId}", 1L)
                        .param("userId", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userCreationDTO)))

                .andExpect(status().isNotFound())
                .andReturn();

    }

    @Test
    void deleteUser() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete(URL + "/{userId}", id))
                .andExpect(status().isNoContent());

        verify(userServiceMock, times(1)).deleteUser(id);

        when(userServiceMock.getUserById(999L)).thenThrow(new RuntimeException("User not found"));

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/{userId}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteUser_UserNotFound() throws Exception {

        doThrow(new RuntimeException("User not found")).when(userServiceMock).deleteUser(999L);

        mockMvc.perform(MockMvcRequestBuilders
                        .delete(URL + "/{userId}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    void getUserDevices_UserHasDevices() throws Exception {

        List<Device> deviceList = new ArrayList<>();
        deviceList.add(setUpDevice());

        when(userServiceMock.getUserDevices(1L)).thenReturn(deviceList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/{userId}/devices", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(deviceList.size()))
                .andReturn();

    }

    @Test
    void getUserDevices_UserHasNoDevices() throws Exception {

        when(userServiceMock.getUserDevices(1L)).thenReturn(Collections.emptyList());

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/{userId}/devices", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
}