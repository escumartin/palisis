package com.example.palisis.infrastructure.rest;

import com.example.palisis.application.dto.UserCreateDTO;
import com.example.palisis.application.dto.UserDTO;
import com.example.palisis.application.dto.UserUpdateDTO;
import com.example.palisis.application.service.UserServiceImpl;
import com.example.palisis.infrastructure.exception.CustomIllegalArgumentException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    private final UserServiceImpl userService;

    @Operation(summary = "Create a new user",
            description = "Creates a new user and optionally associates operation lines with it. Fields `id` and `created` are auto-generated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input, check the request body"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody @Valid UserCreateDTO userCreateDTO) {
        UserDTO createdUser = userService.createUser(userCreateDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser);
    }

    @Operation(summary = "Update an existing user",
            description = "Updates the specified user with new details. Fields `id` and `created` cannot be updated.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or user ID format"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PutMapping("/{userId}")
    public ResponseEntity<UserDTO> updateUser(
            @PathVariable @Parameter(description = "ID of the user to update", example = "1") Long userId,
            @RequestBody @Valid UserUpdateDTO userUpdateDTO) {
        UserDTO updatedUser = userService.updateUser(userId, userUpdateDTO);
        return ResponseEntity.ok(updatedUser);
    }

    @Operation(summary = "Get user by ID",
            description = "Fetches a user by their ID, including their associated operation lines.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User found"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID format"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserDTO> getUserById(
            @PathVariable @Parameter(description = "ID of the user to retrieve", example = "1") Long userId) {
        if (userId <= 0) {
            throw new CustomIllegalArgumentException("User ID must be greater than zero.");
        }
        UserDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }

    @Operation(summary = "Get all users",
            description = "Returns a list of all users with their associated operation lines.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "List of users retrieved successfully"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Delete a user by ID",
            description = "Deletes a user by their ID. This operation is irreversible.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "User deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid user ID format"),
            @ApiResponse(responseCode = "404", description = "User not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(
            @PathVariable @Parameter(description = "ID of the user to delete", example = "1") Long userId) {
        if (userId <= 0) {
            throw new CustomIllegalArgumentException("User ID must be greater than zero.");
        }
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }
}
