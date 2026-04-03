package com.fernando.todos.controller;


import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fernando.todos.dto.PasswordUpdateRequest;
import com.fernando.todos.dto.UserResponse;
import com.fernando.todos.entity.security.UserDetailsImpl;
import com.fernando.todos.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RequestMapping("/api/users")
@RestController
@Tag(name = "User REST API Endpoints", description = "Operations related to info about current user")
@AllArgsConstructor
public class UserController {
    
    private final UserService userService;

    @Operation(summary = "User information", description = "Get current user info")
    @GetMapping("/info")
    public UserResponse getUserInfo(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {

        return userService.getUserInfo(userDetails.getId());
    }

    @Operation(summary = "Delete user", description = "Delete current use account")
    @DeleteMapping
    public void deleteUser(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        userService.deleteUser(userDetails.getId());
    }

    @Operation(summary = "Password update", description = "Change user password after verification")
    @PutMapping("/password")
    public void passwordUpdate(
        @RequestBody @Valid PasswordUpdateRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        userService.updatePassword(request, userDetails.getId());
    }
}
