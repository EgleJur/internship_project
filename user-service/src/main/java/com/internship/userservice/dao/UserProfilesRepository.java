package com.internship.userservice.dao;

import com.internship.userservice.model.UserProfiles;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfilesRepository extends JpaRepository<UserProfiles, Long> {

}
