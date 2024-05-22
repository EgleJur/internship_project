package com.internship.userservice.service.impl;

import com.internship.userservice.dao.UserProfilesRepository;
import com.internship.userservice.dao.UserRepository;
import com.internship.userservice.mapper.UserMapper;
import com.internship.userservice.mapper.UserProfilesMapper;
import com.internship.userservice.model.User;
import com.internship.userservice.model.UserProfiles;
import com.internship.userservice.model.dto.UserProfilesCreationDTO;
import com.internship.userservice.model.dto.UserProfilesDTO;
import com.internship.userservice.service.UserProfilesService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Log4j2
public class UserProfilesServiceImpl implements UserProfilesService {


    @Autowired
    private UserProfilesRepository userProfilesRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    UserProfilesMapper userProfilesMapper;
    @Autowired
    UserMapper userMapper;

    @Override
    public List<UserProfilesDTO> getAllUserProfiles() {
        List<UserProfiles> usersProfiles = userProfilesRepository.findAll();
        return usersProfiles.stream().map(userProfilesMapper::userProfilesToUserProfilesDTO).collect(Collectors.toList());
    }

    @Override
    public UserProfilesDTO getUserProfileById(Long userProfileId) {
        UserProfiles userProfiles = userProfilesRepository.findById(userProfileId).orElseThrow(() -> new RuntimeException("User Profile not found"));
        return userProfilesMapper.userProfilesToUserProfilesDTO(userProfiles);
    }

    @Override
    public UserProfilesDTO createUserProfile(UserProfilesCreationDTO userProfileDetails) {
        User user = userRepository.findById(userProfileDetails.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        UserProfiles userProfiles = userProfilesMapper.userProfilesCreationDTOToUserProfiles(userProfileDetails);
        userProfiles.setUser(user);
        userProfiles = userProfilesRepository.save(userProfiles);

        return userProfilesMapper.userProfilesToUserProfilesDTO(userProfiles);
    }

    @Override
    public UserProfilesDTO updateUserProfile(Long userProfileId, UserProfilesCreationDTO userProfileDetails) {
        UserProfiles userProfile = userProfilesRepository.findById(userProfileId).orElseThrow(() -> new RuntimeException("User Profile not found"));
        User user = userRepository.findById(userProfileDetails.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        userProfile.setUser(user);
        userProfile.setFirstName(userProfileDetails.getFirstName());
        userProfile.setLastName(userProfileDetails.getLastName());
        userProfile.setPhoneNumber(userProfileDetails.getPhoneNumber());
        userProfile.setAddress(userProfileDetails.getAddress());

        return userProfilesMapper.userProfilesToUserProfilesDTO(userProfilesRepository.save(userProfile));
    }

    @Override
    public void deleteUserProfile(Long userProfileId) {
        UserProfiles userProfile = userProfilesRepository.findById(userProfileId).orElseThrow(() -> new RuntimeException("User Profile not found"));
        userProfilesRepository.delete(userProfile);
    }
}
