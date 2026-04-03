package com.fernando.todos.serviceImpl;

import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.fernando.todos.dto.PasswordUpdateRequest;
import com.fernando.todos.dto.UserResponse;
import com.fernando.todos.entity.User;
import com.fernando.todos.entity.security.AuthorityImpl;
import com.fernando.todos.repository.UserRepository;
import com.fernando.todos.service.UserService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserResponse getUserInfo(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow();

        return new UserResponse(
            user.getId(), 
            user.getFirstName() + " "+ user.getLastName(), 
            user.getEmail(), 
            user.getRoles().stream().map(r -> new AuthorityImpl(r.getName())).collect(Collectors.toSet()));
    }

    @Override
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
            .orElseThrow();

        if(isLastAdmin(user)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Cannot delete the last admin");
        }

        userRepository.delete(user);
    }

    private boolean isLastAdmin(User user) {
        boolean isAdmin = user.getRoles().stream().anyMatch(a -> a.getName().equalsIgnoreCase("role_admin"));

        if(isAdmin) {
            long count = userRepository.countAdminUsers();

            return count <= 1;
        }

        return false;
    }

    @Override
    @Transactional
    public void updatePassword(PasswordUpdateRequest request, Long id) {
        User user = userRepository.findById(id)
            .orElseThrow();

        if(!isOldPasswordCorrect(request.getOldPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The current password is incorrect");
        }

        if(!isNewPasswordConfirmed(request.getNewPassword(), request.getConfirmPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Passwords do not match");
        }

        if(!isNewPasswordDifferent(request.getOldPassword(), request.getNewPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "The new password the sama as the old");
        }

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
    }
    
    private boolean isOldPasswordCorrect(String oldPassword, String currentPassword) {
        return passwordEncoder.matches(oldPassword, currentPassword);
    }

    private boolean isNewPasswordConfirmed(String newPassword, String confirmPassword) {
        return newPassword.equals(confirmPassword);
    }

    private boolean isNewPasswordDifferent(String oldPassword, String newPassword) {
        return !oldPassword.equals(newPassword);
    }
}
