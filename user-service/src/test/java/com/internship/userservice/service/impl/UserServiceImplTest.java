package com.internship.userservice.service.impl;

import com.internship.userservice.dao.UserRepository;
import com.internship.userservice.mapper.UserMapper;
import com.internship.userservice.model.User;
import com.internship.userservice.model.dto.UserCreationDTO;
import com.internship.userservice.model.dto.UserDTO;
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
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepositoryMock;

    @Mock
    private UserMapper userMapperMock;

    @InjectMocks
    private UserServiceImpl userServiceTarget;

    @Mock
    private UserProfilesServiceImpl userProfilesServiceTarget;

    private User user;
    private UserDTO userDTO;
    private UserCreationDTO userCreationDTO;

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

        userCreationDTO = UserCreationDTO.builder()
                .userName("Jim")
                .passwordHash("Tsdghyt8")
                .email("jim@jim.com")
                .role("admin")
                .createdAt(LocalDateTime.of(2024, 1, 1, 12, 00, 00))
                .updatedAt(LocalDateTime.of(2024, 2, 1, 12, 00, 00))
                .build();

    }

    @Test
    void getAllUsers() {
        when(userRepositoryMock.findAll()).thenReturn(Arrays.asList(user));
        when(userMapperMock.userToUserDTO(any(User.class))).thenReturn(userDTO);

        List<UserDTO> result = userServiceTarget.getAllUsers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Jim", result.get(0).getUserName());

        verify(userRepositoryMock, times(1)).findAll();
        verify(userMapperMock, times(1)).userToUserDTO(any(User.class));
    }

    @Test
    void getAllUsersEmpty() {
        when(userRepositoryMock.findAll()).thenReturn(Collections.emptyList());

        assertTrue(userServiceTarget.getAllUsers().isEmpty());

        verify(userRepositoryMock, times(1)).findAll();
    }

    @Test
    void getUserById() {

        when(userRepositoryMock.findById(1L)).thenReturn(Optional.of(user));
        when(userMapperMock.userToUserDTO(any(User.class))).thenReturn(userDTO);

        UserDTO result = userServiceTarget.getUserById(1L);

        assertNotNull(result);
        assertEquals("Jim", result.getUserName());
        verify(userRepositoryMock, times(1)).findById(1L);
        verify(userMapperMock, times(1)).userToUserDTO(any(User.class));
    }

    @Test
    void getUserByIdInvalid() {

        when(userRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                userServiceTarget.getUserById(1L));
        verify(userRepositoryMock, times(1)).findById(1L);
        verify(userMapperMock, times(0)).userToUserDTO(any(User.class));

    }

    @Test
    void createUser() {
        when(userMapperMock.userCreationDTOToUser(any(UserCreationDTO.class))).thenReturn(user);
        when(userRepositoryMock.save(any(User.class))).thenReturn(user);
        when(userMapperMock.userToUserDTO(any(User.class))).thenReturn(userDTO);

        UserDTO result = userServiceTarget.createUser(userCreationDTO);

        assertNotNull(result);
        assertEquals("Jim", result.getUserName());
        verify(userMapperMock, times(1)).userCreationDTOToUser(any(UserCreationDTO.class));
        verify(userRepositoryMock, times(1)).save(any(User.class));
        verify(userMapperMock, times(1)).userToUserDTO(any(User.class));
    }

    @Test
    void updateUser() {
        when(userRepositoryMock.findById(1L)).thenReturn(Optional.of(user));
        when(userRepositoryMock.save(any(User.class))).thenReturn(user);
        when(userMapperMock.userToUserDTO(any(User.class))).thenReturn(userDTO);

        UserDTO result = userServiceTarget.updateUser(1L, userCreationDTO);

        assertNotNull(result);
        assertEquals("Jim", result.getUserName());
        verify(userRepositoryMock, times(1)).findById(1L);
        verify(userRepositoryMock, times(1)).save(any(User.class));
        verify(userMapperMock, times(1)).userToUserDTO(any(User.class));
    }

    @Test
    void updateUser_NotFound() {
        Long nonExistentId = 999L;
        when(userRepositoryMock.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userServiceTarget.updateUser(nonExistentId, new UserCreationDTO()));

        verify(userRepositoryMock, times(1)).findById(nonExistentId);

    }

    @Test
    void deleteUser() {

        when(userRepositoryMock.findById(1L)).thenReturn(Optional.of(user));

        userServiceTarget.deleteUser(1L);

        verify(userRepositoryMock, times(1)).findById(1L);
        verify(userProfilesServiceTarget, times(1)).deleteUserProfileByUser(user);
        verify(userRepositoryMock, times(1)).delete(user);
    }

    @Test
    void deleteUser_NotFound() {
        Long nonExistentId = 999L;

        when(userRepositoryMock.findById(nonExistentId)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userServiceTarget.deleteUser(nonExistentId));

        verify(userRepositoryMock, times(1)).findById(nonExistentId);
        verify(userRepositoryMock, never()).delete(any(User.class));
    }
}