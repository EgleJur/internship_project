package com.internship.userservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
@Builder
public class UserProfilesDTO {

    private Long profileId;

    private UserDTO user;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

}
