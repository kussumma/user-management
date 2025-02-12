package com.example.user_management.controller;

import com.example.user_management.dto.request.UserRequestDTO;
import com.example.user_management.dto.response.UserResponseDTO;
import com.example.user_management.service.UserService;
import com.example.user_management.utility.ResponseWrapper;
import com.example.user_management.utility.ResponseWrapper.Meta;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<ResponseWrapper<List<UserResponseDTO>>> getAllUsers(
        @RequestParam(defaultValue = "1") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(defaultValue = "id") String sortBy,
        @RequestParam(defaultValue = "asc") String sortDirection
    ) {
        // Fetch the paginated users
        Page<UserResponseDTO> users = userService.getAllUsers(
            page,
            size,
            sortBy,
            sortDirection
        );

        // Calculate metadata for pagination
        Meta meta = new Meta(
            page,
            size,
            (int) users.getTotalElements(),
            users.getTotalPages(),
            users.hasPrevious() ? Optional.of(page - 1) : Optional.empty(),
            users.hasNext() ? Optional.of(page + 1) : Optional.empty()
        );

        // Create the response wrapper
        ResponseWrapper<List<UserResponseDTO>> responseWrapper =
            new ResponseWrapper<>(
                "Users fetched successfully",
                200,
                meta,
                users.getContent()
            );

        return ResponseEntity.ok(responseWrapper);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseWrapper<UserResponseDTO>> getUserById(
        @PathVariable String id
    ) {
        UserResponseDTO user = userService.getUserById(id);
        ResponseWrapper<UserResponseDTO> responseWrapper =
            new ResponseWrapper<>("User fetched successfully", 200, user);
        return ResponseEntity.ok(responseWrapper);
    }

    @PostMapping
    public ResponseEntity<ResponseWrapper<UserResponseDTO>> createUser(
        @RequestBody UserRequestDTO user
    ) {
        UserResponseDTO createdUser = userService.createUser(user);
        ResponseWrapper<UserResponseDTO> responseWrapper =
            new ResponseWrapper<>(
                "User created successfully",
                201,
                createdUser
            );
        return ResponseEntity.status(201).body(responseWrapper);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseWrapper<UserResponseDTO>> updateUser(
        @PathVariable String id,
        @RequestBody UserRequestDTO userDetails
    ) {
        UserResponseDTO updatedUser = userService.updateUser(id, userDetails);
        ResponseWrapper<UserResponseDTO> responseWrapper =
            new ResponseWrapper<>(
                "User updated successfully",
                200,
                updatedUser
            );
        return ResponseEntity.ok(responseWrapper);
    }

    @PatchMapping("/avatar/{id}")
    public ResponseEntity<ResponseWrapper<UserResponseDTO>> updateUserAvatar(
        @PathVariable String id,
        @RequestBody String avatar
    ) {
        UserResponseDTO patchedUser = userService.updateUserAvatar(id, avatar);
        ResponseWrapper<UserResponseDTO> responseWrapper =
            new ResponseWrapper<>(
                "User updated successfully",
                200,
                patchedUser
            );
        return ResponseEntity.ok(responseWrapper);
    }

    @PatchMapping("/role/{id}")
    public ResponseEntity<ResponseWrapper<UserResponseDTO>> updateUserRole(
        @PathVariable String id,
        @RequestBody String role
    ) {
        UserResponseDTO patchedUser = userService.updateUserRole(id, role);
        ResponseWrapper<UserResponseDTO> responseWrapper =
            new ResponseWrapper<>(
                "User updated successfully",
                200,
                patchedUser
            );
        return ResponseEntity.ok(responseWrapper);
    }

    @PatchMapping("/verify-email/{code}")
    public ResponseEntity<ResponseWrapper<UserResponseDTO>> updateUserEmail(
        @PathVariable String code,
        @RequestBody String email
    ) {
        UserResponseDTO patchedUser = userService.updateUserEmail(code, email);
        ResponseWrapper<UserResponseDTO> responseWrapper =
            new ResponseWrapper<>(
                "User updated successfully",
                200,
                patchedUser
            );
        return ResponseEntity.ok(responseWrapper);
    }

    @PatchMapping("/password/{id}")
    public ResponseEntity<ResponseWrapper<UserResponseDTO>> updateUserPassword(
        @PathVariable String id,
        @RequestBody String password,
        @RequestBody String confirmPassword
    ) {
        UserResponseDTO patchedUser = userService.updateUserPassword(
            id,
            password,
            confirmPassword
        );
        ResponseWrapper<UserResponseDTO> responseWrapper =
            new ResponseWrapper<>(
                "User updated successfully",
                200,
                patchedUser
            );
        return ResponseEntity.ok(responseWrapper);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseWrapper<Void>> deleteUser(
        @PathVariable String id
    ) {
        userService.deleteUser(id);
        ResponseWrapper<Void> responseWrapper = new ResponseWrapper<>(
            "User deleted successfully",
            200
        );
        return ResponseEntity.ok(responseWrapper);
    }
}
