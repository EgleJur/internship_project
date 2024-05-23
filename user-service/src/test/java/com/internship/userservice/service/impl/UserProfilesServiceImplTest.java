package com.internship.userservice.service.impl;

import com.internship.userservice.dao.UserProfilesRepository;
import com.internship.userservice.dao.UserRepository;
import com.internship.userservice.mapper.UserProfilesMapper;
import com.internship.userservice.model.User;
import com.internship.userservice.model.UserProfiles;
import com.internship.userservice.model.dto.UserDTO;
import com.internship.userservice.model.dto.UserProfilesCreationDTO;
import com.internship.userservice.model.dto.UserProfilesDTO;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserProfilesServiceImplTest {

    @Mock
    private UserProfilesRepository userProfilesRepositoryMock;
    @Mock
    private UserRepository userRepositoryMock;
    @Mock
    private UserProfilesMapper userProfilesMapperMock;

    @InjectMocks
    private UserProfilesServiceImpl userProfilesServiceTarget;
    private User user;
    private UserDTO userDTO;
    private UserProfiles userProfiles;
    private UserProfilesDTO userProfilesDTO;
    private UserProfilesCreationDTO userProfilesCreationDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        user = User.builder()
                .userId(1L)
                .userName("Jim")
                .passwordHash("Tsdghyt8")
                .email("jim@jim.com")
                .role("admin")
                .createdAt(LocalDateTime.of(2024, 1, 1, 12, 00, 00))
                .updatedAt(LocalDateTime.of(2024, 2, 1, 12, 00, 00))
                .build();

        userDTO = UserDTO.builder()
                .userId(1L)
                .userName("Jim")
                .email("jim@jim.com")
                .role("admin")
                .createdAt(LocalDateTime.of(2024, 1, 1, 12, 00, 00))
                .updatedAt(LocalDateTime.of(2024, 2, 1, 12, 00, 00))
                .build();

        userProfiles = UserProfiles.builder()
                .profileId(1L)
                .user(user)
                .firstName("Jim")
                .lastName("Jam")
                .phoneNumber("865436798")
                .address("Vilnius")
                .build();

        userProfilesDTO = UserProfilesDTO.builder()
                .profileId(1L)
                .user(userDTO)
                .firstName("Jim")
                .lastName("Jam")
                .phoneNumber("865436798")
                .address("Vilnius")
                .build();

        userProfilesCreationDTO = UserProfilesCreationDTO.builder()
                .userId(1L)
                .firstName("Jim")
                .lastName("Jam")
                .phoneNumber("865436798")
                .address("Vilnius")
                .build();

    }

    @Test
    void getAllUserProfiles() {
        when(userProfilesRepositoryMock.findAll()).thenReturn(Arrays.asList(userProfiles));
        when(userProfilesMapperMock.userProfilesToUserProfilesDTO(any(UserProfiles.class))).thenReturn(userProfilesDTO);

        List<UserProfilesDTO> result = userProfilesServiceTarget.getAllUserProfiles();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Jim", result.get(0).getFirstName());

        verify(userProfilesRepositoryMock, times(1)).findAll();
        verify(userProfilesMapperMock, times(1)).userProfilesToUserProfilesDTO(any(UserProfiles.class));

    }

    @Test
    void getAllUserProfilesEmpty() {
        when(userProfilesRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        assertTrue(userProfilesServiceTarget.getAllUserProfiles().isEmpty());

        verify(userProfilesRepositoryMock, times(1)).findAll();
    }

    @Test
    void getUserProfileById() {
        when(userProfilesRepositoryMock.findById(1L)).thenReturn(Optional.of(userProfiles));
        when(userProfilesMapperMock.userProfilesToUserProfilesDTO(any(UserProfiles.class))).thenReturn(userProfilesDTO);

        UserProfilesDTO result = userProfilesServiceTarget.getUserProfileById(1L);

        assertNotNull(result);
        assertEquals("Jim", result.getFirstName());
        verify(userProfilesRepositoryMock, times(1)).findById(1L);
        verify(userProfilesMapperMock, times(1)).userProfilesToUserProfilesDTO(any(UserProfiles.class));

    }

    @Test
    void getUserProfileByIdInvalid() {

        when(userProfilesRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                userProfilesServiceTarget.getUserProfileById(1L));
        verify(userProfilesRepositoryMock, times(1)).findById(1L);
        verify(userProfilesMapperMock, times(0)).userProfilesToUserProfilesDTO(any(UserProfiles.class));

    }

    @Test
    void createUserProfile() {
        when(userProfilesMapperMock.userProfilesCreationDTOToUserProfiles(any(UserProfilesCreationDTO.class))).thenReturn(userProfiles);
        when(userRepositoryMock.findById(1L)).thenReturn(Optional.of(user));
        userProfiles.setUser(user);
        when(userProfilesRepositoryMock.save(any(UserProfiles.class))).thenReturn(userProfiles);
        when(userProfilesMapperMock.userProfilesToUserProfilesDTO(any(UserProfiles.class))).thenReturn(userProfilesDTO);

        UserProfilesDTO result = userProfilesServiceTarget.createUserProfile(userProfilesCreationDTO);

        assertNotNull(result);
        assertEquals("Jim", result.getFirstName());
        verify(userProfilesMapperMock, times(1)).userProfilesCreationDTOToUserProfiles(any(UserProfilesCreationDTO.class));
        verify(userProfilesRepositoryMock, times(1)).save(any(UserProfiles.class));
        verify(userProfilesMapperMock, times(1)).userProfilesToUserProfilesDTO(any(UserProfiles.class));

    }

    @Test
    void updateUserProfile() {
        when(userProfilesRepositoryMock.findById(1L)).thenReturn(Optional.of(userProfiles));
        when(userRepositoryMock.findById(1L)).thenReturn(Optional.of(user));
        userProfiles.setUser(user);
        when(userProfilesRepositoryMock.save(any(UserProfiles.class))).thenReturn(userProfiles);
        when(userProfilesMapperMock.userProfilesToUserProfilesDTO(any(UserProfiles.class))).thenReturn(userProfilesDTO);

        UserProfilesDTO result = userProfilesServiceTarget.updateUserProfile(1L, userProfilesCreationDTO);

        assertNotNull(result);
        assertEquals("Jim", result.getFirstName());
        verify(userProfilesRepositoryMock, times(1)).findById(1L);
        verify(userProfilesRepositoryMock, times(1)).save(any(UserProfiles.class));
        verify(userProfilesMapperMock, times(1)).userProfilesToUserProfilesDTO(any(UserProfiles.class));

    }

    @Test
    void updateUserProfile_NotFound() {
        Long nonExistentId = 999L;
        when(userProfilesRepositoryMock.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userProfilesServiceTarget.updateUserProfile(nonExistentId, new UserProfilesCreationDTO()));

        verify(userProfilesRepositoryMock, times(1)).findById(nonExistentId);

    }

    @Test
    void deleteUserProfile() {
        when(userProfilesRepositoryMock.findById(1L)).thenReturn(Optional.of(userProfiles));

        userProfilesServiceTarget.deleteUserProfile(1L);

        verify(userProfilesRepositoryMock, times(1)).findById(1L);
        verify(userProfilesRepositoryMock, times(1)).delete(any(UserProfiles.class));

    }

    @Test
    void deleteUserProfile_NotFound() {
        Long nonExistentId = 999L;

        when(userProfilesRepositoryMock.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userProfilesServiceTarget.deleteUserProfile(nonExistentId));

        verify(userProfilesRepositoryMock, times(1)).findById(nonExistentId);
        verify(userProfilesRepositoryMock, never()).delete(any(UserProfiles.class));
    }
}