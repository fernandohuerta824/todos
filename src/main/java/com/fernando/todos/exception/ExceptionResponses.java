package com.fernando.todos.exception;

import java.time.Instant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponses {
    
    private int code;
    private String message;
    private Instant timestamp;
}
