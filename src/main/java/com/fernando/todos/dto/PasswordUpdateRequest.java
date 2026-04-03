package com.fernando.todos.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PasswordUpdateRequest {
    @NotBlank(message = "The old password is required")
    @Size(min = 5, max = 60, message = "Old password must be between {min} and {max} characters long")
    private String oldPassword;

    @NotBlank(message = "The new password is required")
    @Size(min = 5, max = 60, message = "New password must be between {min} and {max} characters long")
    private String newPassword;

    @NotBlank(message = "The confirm password is required")
    @Size(min = 5, max = 60, message = "Confirm password must be between {min} and {max} characters long")
    private String confirmPassword;
}
