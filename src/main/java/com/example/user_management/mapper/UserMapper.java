package com.example.user_management.mapper;

import com.example.user_management.dto.request.UserRequestDTO;
import com.example.user_management.dto.response.UserResponseDTO;
import com.example.user_management.model.UserModel;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Convert UserModel to UserResponseDTO
    UserResponseDTO toDTO(UserModel user);

    // Convert UserRequestDTO to UserModel
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    UserModel toEntity(UserRequestDTO userRequestDTO);

    // Update existing UserModel with UserRequestDTO
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true)
    void updateEntityFromDTO(
        UserRequestDTO userRequestDTO,
        @MappingTarget UserModel user
    );
}
