package com.fernando.todos.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.fernando.todos.entity.Todo;
import com.fernando.todos.entity.User;

public interface TodoRepository extends CrudRepository<Todo, Long>{
    
    Optional<Todo> findByIdAndUser(Long id, User user);
}
