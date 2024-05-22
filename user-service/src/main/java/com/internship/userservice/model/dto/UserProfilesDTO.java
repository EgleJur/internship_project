package com.internship.userservice.model.dto;


import com.internship.userservice.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class UserProfilesDTO {

    private Long profileId;

    private User user;

    private String firstName;

    private String lastName;

    private String phoneNumber;

    private String address;

}
