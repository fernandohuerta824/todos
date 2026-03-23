package com.fernando.todos.service;

import java.util.Map;

import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUser(String token);

    boolean isTokenValid(String token, UserDetails userDetails);

    String generateToken(Map<String, ?> claims, UserDetails userDetails);
}
