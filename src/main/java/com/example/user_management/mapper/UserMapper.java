package com.example.user_management.mapper;

import com.example.user_management.dto.request.UserRequestDTO;
import com.example.user_management.dto.response.UserResponseDTO;
import com.example.user_management.model.UserModel;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserMapper {

  public static UserResponseDTO toDTO(UserModel user) {
    return UserResponseDTO.builder()
        .id(user.getId())
        .email(user.getEmail())
        .role(user.getRole())
        .firstName(user.getFirstName())
        .lastName(user.getLastName())
        .address(user.getAddress())
        .phone(user.getPhone())
        .avatar(user.getAvatar())
        .build();
  }

  public static UserModel toEntity(UserRequestDTO dto, PasswordEncoder passwordEncoder) {
    return UserModel.builder()
        .email(dto.getEmail())
        .password(passwordEncoder.encode(dto.getPassword()))
        .role(dto.getRole())
        .firstName(dto.getFirstName())
        .lastName(dto.getLastName())
        .address(dto.getAddress())
        .phone(dto.getPhone())
        .avatar(dto.getAvatar())
        .build();
  }
}