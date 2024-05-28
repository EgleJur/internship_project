package com.internship.userservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.internship.userservice.model.dto.UserDTO;
import com.internship.userservice.model.dto.UserProfilesCreationDTO;
import com.internship.userservice.model.dto.UserProfilesDTO;
import com.internship.userservice.service.UserProfilesService;
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

@WebMvcTest(UserProfilesController.class)
class UserProfilesControllerTest {

    public static String URL = "/api/userProfile";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserProfilesService userProfilesServiceMock;

    @InjectMocks
    private UserProfilesController userProfilesControllerTarget;

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

    private UserProfilesDTO setUpUserProfilesDTO() {
        return UserProfilesDTO.builder()
                .profileId(1L)
                .user(setUpUserDTO())
                .firstName("Jim")
                .lastName("Jam")
                .phoneNumber("865436798")
                .address("Vilnius")
                .build();
    }

    private UserProfilesCreationDTO setUpUserProfilesCreationDTO() {
        return UserProfilesCreationDTO.builder()
                .userId(1L)
                .firstName("Jim")
                .lastName("Jam")
                .phoneNumber("865436798")
                .address("Vilnius")
                .build();
    }

    @Test
    void getAllUserProfiles() throws Exception {
        List<UserProfilesDTO> userProfilesList = new ArrayList<>();
        UserProfilesDTO userProfiles = setUpUserProfilesDTO();
        userProfilesList.add(userProfiles);

        when(userProfilesServiceMock.getAllUserProfiles()).thenReturn(userProfilesList);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(URL))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].profileId").value(1L))
                .andExpect(jsonPath("$[0].user.userName").value("Jim"))
                .andExpect(jsonPath("$[0].firstName").value("Jim"))
                .andExpect(jsonPath("$[0].lastName").value("Jam"))
                .andExpect(jsonPath("$[0].phoneNumber").value("865436798"))
                .andExpect(jsonPath("$[0].address").value("Vilnius"))
                .andExpect(status().isOk());

        verify(userProfilesServiceMock, times(1)).getAllUserProfiles();

    }

    @Test
    void getUserProfileById() throws Exception {
        UserProfilesDTO userProfilesDTO = setUpUserProfilesDTO();

        when(userProfilesServiceMock.getUserProfileById(1L))
                .thenReturn(userProfilesDTO);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .get(URL + "/{userProfileId}", 1L)
                        .param("profileId", "1")
                        .param("user.userName", "Jim")
                        .param("firstName", "Jim")
                        .param("lastName", "Jam")
                        .param("phoneNumber", "865436798")
                        .param("address", "Vilnius")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userProfilesDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertEquals(objectMapper.writeValueAsString(userProfilesDTO), responseBody);
    }

    @Test
    void createUserProfile() throws Exception {
        UserProfilesCreationDTO userProfilesCreationDTO = setUpUserProfilesCreationDTO();
        UserProfilesDTO userProfilesDTO = setUpUserProfilesDTO();
        when(userProfilesServiceMock.createUserProfile(userProfilesCreationDTO)).thenReturn(userProfilesDTO);

        ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.post(URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(userProfilesCreationDTO)));

        resultActions
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.profileId").value(1L))
                .andExpect(jsonPath("$.user.userName").value("Jim"))
                .andExpect(jsonPath("$.firstName").value("Jim"))
                .andExpect(jsonPath("$.lastName").value("Jam"))
                .andExpect(jsonPath("$.phoneNumber").value("865436798"))
                .andExpect(jsonPath("$.address").value("Vilnius"));

        verify(userProfilesServiceMock, times(1)).createUserProfile(userProfilesCreationDTO);

    }

    @Test
    void updateUserProfile() throws Exception {
        UserProfilesCreationDTO userProfilesCreationDTO = setUpUserProfilesCreationDTO();
        UserProfilesDTO userProfilesDTO = setUpUserProfilesDTO();

        when(userProfilesServiceMock.updateUserProfile(1L, userProfilesCreationDTO)).thenReturn(userProfilesDTO);

        MvcResult result = this.mockMvc.perform(MockMvcRequestBuilders
                        .put(URL + "/{userProfileId}", 1L)
                        .param("profileId", "1")
                        .param("user.userName", "Jim")
                        .param("firstName", "Jim")
                        .param("lastName", "Jam")
                        .param("phoneNumber", "865436798")
                        .param("address", "Vilnius")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(userProfilesCreationDTO)))
                .andExpect(status().isOk())
                .andReturn();

        String responseBody = result.getResponse().getContentAsString();
        Assertions.assertEquals(objectMapper.writeValueAsString(userProfilesDTO), responseBody);

    }

    @Test
    void deleteUserProfile() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete(URL + "/{userProfileId}", id))
                .andExpect(status().isNoContent());

        verify(userProfilesServiceMock, times(1)).deleteUserProfile(id);
    }

}