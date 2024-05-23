package com.internship.userservice.mapper;

import com.internship.userservice.model.User;
import com.internship.userservice.model.dto.UserCreationDTO;
import com.internship.userservice.model.dto.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User userDTOToUser(UserDTO userDTO);

    User userCreationDTOToUser(UserCreationDTO userCreationDTO);

    UserDTO userToUserDTO(User user);

}
