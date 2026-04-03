package com.fernando.todos.service;

import java.util.Set;

import com.fernando.todos.dto.TodoRequest;
import com.fernando.todos.dto.TodoResponse;

public interface TodoService {
    TodoResponse createTodo(TodoRequest request, Long userId);
    Set<TodoResponse> getUserTodos(Long userId);
    TodoResponse toggleTodoCompletion(Long todoId, Long userId);
    void deleteTodo(Long todoId, Long userId);
}
