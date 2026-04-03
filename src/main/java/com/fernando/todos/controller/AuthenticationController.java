package com.fernando.todos.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fernando.todos.dto.AuthenticationRequest;
import com.fernando.todos.dto.AuthenticationResponse;
import com.fernando.todos.dto.RegisterRequest;
import com.fernando.todos.service.AuthenticationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@Tag(name = "Authentication REST API Endpoints", description = "Operations related to register and login")
@AllArgsConstructor
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    @ResponseStatus(code = HttpStatus.CREATED)
    @Operation(summary = "Register a user", description = "Create new user in database")
    public void register(@RequestBody @Valid RegisterRequest input) throws Exception {
        authenticationService.register(input);
    }

    @PostMapping("/login")
    @Operation(summary = "Login a user", description = "Submit a email and password to authenticate a user")
    public AuthenticationResponse login(@RequestBody @Valid AuthenticationRequest request) {
        return authenticationService.login(request);
    }
}
