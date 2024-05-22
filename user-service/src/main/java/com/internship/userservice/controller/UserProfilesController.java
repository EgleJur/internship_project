package com.internship.userservice.controller;

import com.internship.userservice.model.UserProfiles;
import com.internship.userservice.model.dto.UserProfilesCreationDTO;
import com.internship.userservice.model.dto.UserProfilesDTO;
import com.internship.userservice.service.UserProfilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/userProfile")
public class UserProfilesController {
    @Autowired
    private UserProfilesService userProfilesService;

    @GetMapping
    public List<UserProfilesDTO> getAllUserProfiles() {
        return userProfilesService.getAllUserProfiles();
    }

    @GetMapping("/{userProfileId}")
    public UserProfilesDTO getUserProfileById(@PathVariable Long userProfileId) {
        return userProfilesService.getUserProfileById(userProfileId);
    }

    @PostMapping
    public UserProfilesDTO createUserProfile(@RequestBody UserProfilesCreationDTO userProfileDetails) {
        return userProfilesService.createUserProfile(userProfileDetails);
    }

    @PutMapping("/{userProfileId}")
    public ResponseEntity<UserProfilesDTO> updateUserProfile(@PathVariable Long userProfileId, @RequestBody UserProfilesCreationDTO userProfileDetails) {
        return ResponseEntity.ok(userProfilesService.updateUserProfile(userProfileId, userProfileDetails));
    }

    @DeleteMapping("/{userProfileId}")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable Long userProfileId) {
        userProfilesService.deleteUserProfile(userProfileId);
        return ResponseEntity.noContent().build();
    }

}
