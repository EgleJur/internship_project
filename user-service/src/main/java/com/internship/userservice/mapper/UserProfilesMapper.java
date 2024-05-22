package com.internship.userservice.mapper;

import com.internship.userservice.model.UserProfiles;
import com.internship.userservice.model.dto.UserProfilesCreationDTO;
import com.internship.userservice.model.dto.UserProfilesDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface UserProfilesMapper {

    UserProfiles userProfilesCreationDTOToUserProfiles(UserProfilesCreationDTO userProfilesCreationDTO);

    UserProfilesDTO userProfilesToUserProfilesDTO(UserProfiles userProfiles);

}
