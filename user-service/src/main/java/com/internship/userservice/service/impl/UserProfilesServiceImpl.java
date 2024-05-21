package com.internship.userservice.service.impl;

import com.internship.userservice.dao.UserProfilesRepository;
import com.internship.userservice.model.UserProfiles;
import com.internship.userservice.service.UserProfilesService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class UserProfilesServiceImpl implements UserProfilesService {


    @Autowired
    private UserProfilesRepository userProfilesRepository;

    @Override
    public List<UserProfiles> getAllUserProfiles() {
        return userProfilesRepository.findAll();
    }

    @Override
    public Optional<UserProfiles> getUserProfileById(Long userProfileId) {
        return userProfilesRepository.findById(userProfileId);
    }

    @Override
    public UserProfiles createUserProfile(UserProfiles userProfile) {
        return userProfilesRepository.save(userProfile);
    }

    @Override
    public UserProfiles updateUserProfile(Long userProfileId, UserProfiles userProfileDetails) {
        UserProfiles userProfile = userProfilesRepository.findById(userProfileId).orElseThrow(() -> new RuntimeException("User Profile not found"));
        userProfile.setUser(userProfileDetails.getUser());
        userProfile.setFirstName(userProfileDetails.getFirstName());
        userProfile.setLastName(userProfileDetails.getLastName());
        userProfile.setPhoneNumber(userProfileDetails.getPhoneNumber());
        userProfile.setAddress(userProfileDetails.getAddress());

        return userProfilesRepository.save(userProfile);
    }

    @Override
    public void deleteUserProfile(Long userProfileId) {
        UserProfiles userProfile = userProfilesRepository.findById(userProfileId).orElseThrow(() -> new RuntimeException("User Profile not found"));
        userProfilesRepository.delete(userProfile);
    }
}
