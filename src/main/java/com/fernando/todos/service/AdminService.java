package com.fernando.todos.service;

import java.util.List;

import com.fernando.todos.dto.UserResponse;

public interface AdminService {
    List<UserResponse> getAllUsers();
    UserResponse promoteToAdmin(Long userId);
    void deleteNonAdminUser(Long id);
}
