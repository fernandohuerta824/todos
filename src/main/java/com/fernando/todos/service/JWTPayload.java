package com.fernando.todos.service;

import java.util.Date;
import java.util.Set;

public record JWTPayload(
    Long userId,
    Set<String> authorities,
    String issuer,
    String subject,
    Date issuedAt,
    Date expiration
) {
    
}
