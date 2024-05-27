package com.internship.userservice.service.impl;

import com.internship.userservice.dao.UserProfilesRepository;
import com.internship.userservice.dao.UserRepository;
import com.internship.userservice.mapper.UserMapper;
import com.internship.userservice.model.User;
import com.internship.userservice.model.UserProfiles;
import com.internship.userservice.model.dto.UserCreationDTO;
import com.internship.userservice.model.dto.UserDTO;
import com.internship.userservice.model.dto.UserProfilesDTO;
import com.internship.userservice.service.UserProfilesService;
import com.internship.userservice.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UserServiceImpl implements UserService {


    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserProfilesService userProfilesService;

    @Autowired
    UserMapper userMapper;

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(userMapper::userToUserDTO).collect(Collectors.toList());

    }

    @Override
    public UserDTO getUserById(Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        return userMapper.userToUserDTO(user);
    }

    @Override
    public UserDTO createUser(UserCreationDTO userDetails) {

        userDetails.setCreatedAt(LocalDateTime.now());
        userDetails.setUpdatedAt(null);
        return userMapper.userToUserDTO(userRepository.save(userMapper.userCreationDTOToUser(userDetails)));
    }

    @Override
    public UserDTO updateUser(Long userId, UserCreationDTO userDetails) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setUserName(userDetails.getUserName());
        user.setPasswordHash(userDetails.getPasswordHash());
        user.setEmail(userDetails.getEmail());
        user.setRole(userDetails.getRole());
        user.setUpdatedAt(LocalDateTime.now());
        return userMapper.userToUserDTO(userRepository.save(user));
    }

    @Override
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        userProfilesService.deleteUserProfileByUser(user);
        userRepository.delete(user);
    }
}
