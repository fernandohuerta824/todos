package com.fernando.todos.dto;

import java.util.Set;

import com.fernando.todos.entity.security.AuthorityImpl;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@Data
@NoArgsConstructor
public class UserResponse {
    
    private long id;

    private String fullName;

    private String email;

    private Set<AuthorityImpl> authorities;
}
