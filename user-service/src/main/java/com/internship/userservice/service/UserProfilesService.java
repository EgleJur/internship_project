package com.internship.userservice.service;

import com.internship.userservice.model.User;
import com.internship.userservice.model.dto.UserProfilesCreationDTO;
import com.internship.userservice.model.dto.UserProfilesDTO;

import java.util.List;

public interface UserProfilesService {
    List<UserProfilesDTO> getAllUserProfiles();

    UserProfilesDTO getUserProfileById(Long userId);

    UserProfilesDTO createUserProfile(UserProfilesCreationDTO userProfileDetails);

    UserProfilesDTO updateUserProfile(Long userId, UserProfilesCreationDTO userProfileDetails);

    void deleteUserProfile(Long userId);

    void deleteUserProfileByUser(User user);
}
