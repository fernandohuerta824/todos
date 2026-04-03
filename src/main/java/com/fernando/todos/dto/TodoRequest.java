package com.fernando.todos.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class TodoRequest {

    @NotBlank(message = "The title is required")
    @Size(min = 5, max = 60, message = "Title must be between {min} and {max} characters long")
    private String title;

    @NotBlank(message = "The description is required")
    @Size(min = 3, max = 60, message = "Description must be between {min} and {max} characters long")
    private String description;

    @Min(1)
    @Max(5)
    private Integer priority;
}
