package com.fernando.todos.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;

import com.fernando.todos.entity.Role;

public interface RoleRepository extends CrudRepository<Role, Long> {
    
    Optional<Role> findByName(String name);
}
