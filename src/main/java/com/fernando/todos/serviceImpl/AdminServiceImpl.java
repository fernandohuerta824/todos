package com.fernando.todos.serviceImpl;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.fernando.todos.dto.UserResponse;
import com.fernando.todos.entity.Role;
import com.fernando.todos.entity.User;
import com.fernando.todos.entity.security.AuthorityImpl;
import com.fernando.todos.repository.RoleRepository;
import com.fernando.todos.repository.UserRepository;
import com.fernando.todos.service.AdminService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService{

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    @Transactional(readOnly = true)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserResponse> getAllUsers() {
        
        return StreamSupport.stream(userRepository.findAll().spliterator(), false)
            .map(u -> 
                new UserResponse(
                    u.getId(), 
                    u.getFirstName() + " " + u.getLastName(), 
                    u.getEmail(), 
                    u.getRoles().stream().map(r -> new AuthorityImpl(r.getName())).collect(Collectors.toSet())
                ))
            .toList();
    }

	@Override
    @Transactional
	public UserResponse promoteToAdmin(Long userId) {
		User user = userRepository.findById(userId)
            .orElseThrow();

        if(user.getRoles().stream().anyMatch(r -> r.getName().equalsIgnoreCase("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user is already an admin");
        }

        Role role = roleRepository.findByName("ROLE_ADMIN")
            .orElseThrow();
        user.addRole(role);

        return new UserResponse(
            user.getId(), 
            user.getFirstName() + " " + user.getLastName(), 
            user.getEmail(), 
            user.getRoles().stream().map(r -> new AuthorityImpl(r.getName())).collect(Collectors.toSet())
        );
	}

    @Override
    @Transactional
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void deleteNonAdminUser(Long userId) {
        User user = userRepository.findById(userId)
            .orElseThrow();

        if(user.getRoles().stream().anyMatch(r -> r.getName().equalsIgnoreCase("ROLE_ADMIN"))) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The user is an admin");
        }

        userRepository.delete(user);
    }
    
}
