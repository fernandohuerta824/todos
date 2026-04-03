package com.fernando.todos.controller;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fernando.todos.dto.TodoRequest;
import com.fernando.todos.dto.TodoResponse;
import com.fernando.todos.entity.security.UserDetailsImpl;
import com.fernando.todos.service.TodoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/api/todos")
@Tag(name = "Todo REST API Endpoints", description = "Operation for managing user todos")
@AllArgsConstructor
public class TodoController {
    
    private final TodoService todoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create todo for a user", description = "Create a todo for the signed user")
    public TodoResponse createResponse(
        @RequestBody @Valid TodoRequest request,
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return todoService.createTodo(request, userDetails.getId());
    }

    @GetMapping
    @Operation(summary = "Get all todos for a user", description = "Get all todos for the signed user")
    public Set<TodoResponse> getUserTodos(
        @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        return todoService.getUserTodos(userDetails.getId());
    }

    @PutMapping("/{id}")
    @Operation(summary = "Toggle todo completion", description = "Toggle the todo completion for the signed in user")
    public TodoResponse toggleTodoCompletion(
        @PathVariable @Min(1) Long id,
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        return todoService.toggleTodoCompletion(id, userDetailsImpl.getId());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "", description = "")
    public void deleteTodo(
        @PathVariable @Min(1) Long id,
        @AuthenticationPrincipal UserDetailsImpl userDetailsImpl
    ) {
        todoService.deleteTodo(id, userDetailsImpl.getId());
    }


}
