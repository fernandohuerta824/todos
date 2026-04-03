package com.fernando.todos.serviceImpl;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fernando.todos.dto.TodoRequest;
import com.fernando.todos.dto.TodoResponse;
import com.fernando.todos.entity.Todo;
import com.fernando.todos.entity.User;
import com.fernando.todos.repository.TodoRepository;
import com.fernando.todos.repository.UserRepository;
import com.fernando.todos.service.TodoService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public TodoResponse createTodo(TodoRequest request, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        Todo todo = new Todo(
            null,
            request.getTitle(),
            request.getDescription(),
            request.getPriority(),
            false,
            user
        );

        Todo savedTodo = todoRepository.save(todo);

        return new TodoResponse(
            savedTodo.getId(), 
            request.getTitle(),
            request.getDescription(),
            request.getPriority(),
            todo.getComplete()
        );
    }

	@Override
    @Transactional
	public Set<TodoResponse> getUserTodos(Long userId) {
        User user = userRepository.findById(userId).orElseThrow();
		

        return user.getTodos().stream()
            .map(t -> new TodoResponse(
                t.getId(), 
                t.getTitle(), 
                t.getDescription(), 
                t.getPriority(), 
                t.getComplete()))
            .collect(Collectors.toSet());
	}

    @Override
    @Transactional
    public TodoResponse toggleTodoCompletion(Long todoId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        Todo todo = todoRepository.findByIdAndUser(todoId, user).orElseThrow();

        todo.setComplete(!todo.getComplete());

        todoRepository.save(todo);

        return new TodoResponse(
            todo.getId(), 
            todo.getTitle(),
            todo.getDescription(),
            todo.getPriority(),
            todo.getComplete()
        );
    }

    @Override
    @Transactional
    public void deleteTodo(Long todoId, Long userId) {
        User user = userRepository.findById(userId).orElseThrow();

        Todo todo = todoRepository.findByIdAndUser(todoId, user).orElseThrow();

        todoRepository.delete(todo);
    }
    
}
