package com.internship.userservice.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDTO {

    private Long userId;

    private String userName;

    private String email;

    private String role;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;
}
