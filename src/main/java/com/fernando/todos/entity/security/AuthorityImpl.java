package com.fernando.todos.entity.security;



import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthorityImpl implements GrantedAuthority{

    private final String authority;

    @Override
    public @Nullable String getAuthority() {
        return authority;
    }

 
    
}
