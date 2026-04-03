package com.fernando.todos.service;


import com.fernando.todos.dto.AuthenticationRequest;
import com.fernando.todos.dto.AuthenticationResponse;
import com.fernando.todos.dto.RegisterRequest;

public interface AuthenticationService {
    
    void register(RegisterRequest input) throws Exception;
    AuthenticationResponse login(AuthenticationRequest request);
}
