package com.fernando.todos.service;

import com.fernando.todos.dto.PasswordUpdateRequest;
import com.fernando.todos.dto.UserResponse;

public interface UserService {
    UserResponse getUserInfo(Long id);
    void deleteUser(Long id);
    void updatePassword(PasswordUpdateRequest request, Long id);
}
