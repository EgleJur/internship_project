package com.internship.userservice.dao;

import com.internship.userservice.model.User;
import com.internship.userservice.model.UserProfiles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserProfilesRepository extends JpaRepository<UserProfiles, Long> {
    Optional<UserProfiles> findByUser(User user);
}
