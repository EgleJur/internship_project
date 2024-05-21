package com.internship.userservice.service;

import com.internship.userservice.model.User;
import com.internship.userservice.model.UserProfiles;

import java.util.List;
import java.util.Optional;

public interface UserProfilesService {
    List<UserProfiles> getAllUserProfiles();

    Optional<UserProfiles> getUserProfileById(Long userId);

    UserProfiles createUserProfile(UserProfiles user);

    UserProfiles updateUserProfile(Long userId, UserProfiles userDetails);

    void deleteUserProfile(Long userId);
}
