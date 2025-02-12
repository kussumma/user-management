package com.example.user_management.dto.request;

import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class UserRequestDTO {

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(min = 8, message = "Password must be at least 8 characters")
    private String password;

    @NotBlank(message = "Role is required")
    private String role;

    @NotBlank(message = "First name is required")
    @Size(
        min = 2,
        max = 50,
        message = "First name must be between 2 and 50 characters"
    )
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(
        min = 2,
        max = 50,
        message = "Last name must be between 2 and 50 characters"
    )
    private String lastName;

    private String address;

    @Pattern(
        regexp = "^[0-9\\-\\+]{9,15}$",
        message = "Invalid phone number format"
    )
    private String phone;

    private String avatar;
}
