package com.example.user_management.dto.response;

import lombok.Data;

@Data
public class UserResponseDTO {

    private String id;
    private String email;
    private String role;
    private String firstName;
    private String lastName;
    private String address;
    private String phone;
    private String avatar;
}
