package com.fernando.todos.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fernando.todos.dto.UserResponse;
import com.fernando.todos.service.AdminService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/admin")
@Tag(name = "Admin REST API Endpoints", description = "Operations related to an admin")
@AllArgsConstructor
public class AdminController {
    
    private final AdminService adminService;

    @GetMapping("/users")
    @Operation(summary = "Get all users", description = "Retrieve a list of all users in the system")
    public List<UserResponse> getAllUsers() {
        return adminService.getAllUsers();
    }

    @PutMapping("/{userId}/role")
    @Operation(summary = "Promote user to admin", description = "Promote user to admin role")
    public UserResponse promoteToAdmin(
        @PathVariable @Min(1) Long userId
    ) {
        return adminService.promoteToAdmin(userId);
    }

    @DeleteMapping("/{userId}")
    @Operation(summary = "Delete non admin", description = "Delete a non admin user")
    public void deleteNonAdmin(
        @PathVariable @Min(1) Long userId
    ) {
        adminService.deleteNonAdminUser(userId);
    }
}
