package com.fernando.todos.service;

import com.fernando.todos.entity.security.UserDetailsImpl;

public interface JwtService {

    String generateToken(UserDetailsImpl userDetails);

    JWTPayload getJwtPayload(String token);
}
