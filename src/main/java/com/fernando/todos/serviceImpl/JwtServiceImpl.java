package com.fernando.todos.serviceImpl;

import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import com.fernando.todos.entity.security.UserDetailsImpl;
import com.fernando.todos.service.JWTPayload;
import com.fernando.todos.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService {

    @Value("${spring.jwt.secret}")
    private String SECRET_KEY;

    @Value("${spring.jwt.expiration}")
    private long EXPIRATION;

    private final String USER_ID_KEY = "userId";
    private final String AUTHORITIES_KEY = "authorities";

    private Claims extractAllClaims (String token) {
        return Jwts.parserBuilder()
            .setSigningKey(getSigningKey())
            .build()
            .parseClaimsJws(token)
            .getBody();
    }

    @SuppressWarnings("unchecked")
	@Override
    public JWTPayload getJwtPayload(String token) {
        Claims claims = extractAllClaims(token);

        return new JWTPayload(
            claims.get(USER_ID_KEY, Long.class),
            claims.get(AUTHORITIES_KEY, Set.class),
            claims.getIssuer(),
            claims.getSubject(),
            claims.getIssuedAt(),
            claims.getExpiration()
        );
    }

    @Override
    public String generateToken(UserDetailsImpl userDetails) {
       return Jwts.builder()
            .claim(USER_ID_KEY, userDetails.getId())
            .claim(AUTHORITIES_KEY, userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority).collect(Collectors.toSet()))
            .setIssuer("Fernando")
            .setSubject(userDetails.getUsername())
            .setIssuedAt(new Date(System.currentTimeMillis()))
            .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION))
            .signWith(getSigningKey(), SignatureAlgorithm.HS256)
            .compact();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(SECRET_KEY);

        return Keys.hmacShaKeyFor(keyBytes);
    }

}
