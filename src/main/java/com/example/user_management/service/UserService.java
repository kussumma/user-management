package com.example.user_management.service;

import com.example.user_management.dto.request.UserRequestDTO;
import com.example.user_management.dto.response.UserResponseDTO;
import com.example.user_management.mapper.UserMapper;
import com.example.user_management.model.UserModel;
import com.example.user_management.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Page<UserResponseDTO> getAllUsers(int page, int size, String sortBy, String sortDirection) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirection), sortBy);
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        return userRepository.findAll(pageable).map(UserMapper::toDTO);
    }

    public UserResponseDTO getUserById(String id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public UserResponseDTO createUser(UserRequestDTO user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User with email already exists");
        }

        UserModel newUser = UserMapper.toEntity(user, passwordEncoder);
        userRepository.save(newUser);
        return UserMapper.toDTO(newUser);
    }

    public UserResponseDTO updateUser(String id, UserRequestDTO userPayload) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        userRepository.findByEmail(userPayload.getEmail())
                .ifPresent(existingUser -> {
                    if (!existingUser.getId().equals(id)) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
                    }
                });

        user.setFirstName(userPayload.getFirstName());
        user.setLastName(userPayload.getLastName());

        // Address is optional
        if (userPayload.getAddress() != null) {
            user.setAddress(userPayload.getAddress());
        }

        // Phone is optional
        if (userPayload.getPhone() != null) {
            user.setPhone(userPayload.getPhone());
        }

        return UserMapper.toDTO(userRepository.save(user));
    }

    public UserResponseDTO updateUserRole(String id, String role) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setRole(role);
        return UserMapper.toDTO(userRepository.save(user));
    }

    public UserResponseDTO updateUserAvatar(String id, String avatar) {
        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setAvatar(avatar);
        return UserMapper.toDTO(userRepository.save(user));
    }

    public UserResponseDTO updateUserEmail(String code, String email) {
        UserModel user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        userRepository.findByEmail(email)
                .ifPresent(existingUser -> {
                    if (!existingUser.getEmail().equals(email)) {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use");
                    }
                });

        user.setEmail(email);
        return UserMapper.toDTO(userRepository.save(user));
    }

    public UserResponseDTO updateUserPassword(String id, String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }

        UserModel user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        user.setPassword(passwordEncoder.encode(password));
        return UserMapper.toDTO(userRepository.save(user));
    }

    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }
}