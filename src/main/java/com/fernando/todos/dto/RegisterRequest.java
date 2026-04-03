package com.fernando.todos.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RegisterRequest {
    
    @NotBlank(message = "First name is required")
    @Size(min = 3, max = 30, message = "First name must be between {min} and {max} characters long")
    private String fisrtName;

    @NotBlank(message = "Last name is required")
    @Size(min = 3, max = 30, message = "Last name must be between {min} and {max} characters long")
    private String lastName;
    
    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Passoword is required")
    @Size(min = 5, max = 60, message = "Password must be between {min} and {max} characters long")
    private String password;
}
