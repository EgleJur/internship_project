package com.internship.userservice.service;

import com.internship.userservice.model.Device;
import com.internship.userservice.model.dto.UserCreationDTO;
import com.internship.userservice.model.dto.UserDTO;

import java.util.List;

public interface UserService {
    List<UserDTO> getAllUsers();

    UserDTO getUserById(Long userId);

    UserDTO createUser(UserCreationDTO userDetails);

    UserDTO updateUser(Long userId, UserCreationDTO userDetails);

    void deleteUser(Long userId);

    List<Device> getUserDevices(Long userId);
}
