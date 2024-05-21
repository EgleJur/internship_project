package com.internship.userservice.service;

import com.internship.userservice.model.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    List<User> getAllUsers();

    Optional<User> getUserById(String userId);

    User createUser(User user);

    User updateUser(String userId, User userDetails);

    void deleteUser(String userId);
}
