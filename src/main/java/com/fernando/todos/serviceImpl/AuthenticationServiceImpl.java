package com.fernando.todos.serviceImpl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fernando.todos.dto.AuthenticationRequest;
import com.fernando.todos.dto.AuthenticationResponse;
import com.fernando.todos.dto.RegisterRequest;
import com.fernando.todos.entity.Role;
import com.fernando.todos.entity.User;
import com.fernando.todos.entity.security.UserDetailsImpl;
import com.fernando.todos.repository.UserRepository;
import com.fernando.todos.service.AuthenticationService;
import com.fernando.todos.service.JwtService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    @Lazy
    private final UserDetailsService userDetailsService;

    @Override
    @Transactional
    public void register(RegisterRequest input) throws Exception {
        if(isEmailTaken(input.getEmail())) {
            throw new Exception("Email already taken");
        }
        User user = buildNewUser(input);


        userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        UserDetailsImpl userDetails = (UserDetailsImpl) userDetailsService.loadUserByUsername(request.getEmail());

        String token = jwtService.generateToken(userDetails);

        return new AuthenticationResponse(token);
    }


    private boolean isEmailTaken(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    private Set<Role> initialAuthority() {
        boolean isFirstUser = userRepository.count() == 0;

        Set<Role> authorities = new HashSet<>();
        Role employeeRole = new Role();
        employeeRole.setId(1L);
        authorities.add(employeeRole);

        if(isFirstUser) {
            Role adminRole = new Role();
            adminRole.setId(2L);
            authorities.add(adminRole);
        }

        return authorities;
    }

    private User buildNewUser(RegisterRequest input) {
        
        User user = new User();
        user.setId(null);
        user.setFirstName(input.getFisrtName());
        user.setLastName(input.getLastName());
        user.setEmail(input.getEmail());
        user.setPassword(passwordEncoder.encode(input.getPassword()));
        user.setRoles(initialAuthority());
        return user;
    }
}
