package com.fernando.todos.entity;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.boot.webmvc.autoconfigure.WebMvcProperties.Apiversion.Use;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "users")
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
    
    @EqualsAndHashCode.Include
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;

    @CreationTimestamp
    @Column(nullable = false, updatable = false, name = "created_at")
    @Setter(value = AccessLevel.NONE)
    private Date createdAt;

    @UpdateTimestamp
    @Column(nullable = false, name = "updated_at")
    @Setter(value = AccessLevel.NONE)
    private Date updatedAt;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "users_roles",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roles = new HashSet<>();

    @Setter(value = AccessLevel.NONE)
    @Getter(value = AccessLevel.NONE)
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Todo> todos = new HashSet<>();

    public boolean addTodo(Todo todo) {
        boolean wasAdded = todos.add(todo);
        if(wasAdded) {
            todo.setUser(this);
        }

        return wasAdded;
    }

    public boolean removeTodo(Todo todo) {
        boolean wasRemoved = todos.remove(todo);
        if(wasRemoved) {
            todo.setUser(null);
        }

        return wasRemoved;
    }


    public boolean addRole(Role role) {
        boolean wasAdded = roles.add(role);
    
        return wasAdded;
    }

    public boolean removeRole(Role role) {
        boolean wasRemoved = roles.remove(role);
    
        return wasRemoved;
    }

    public Set<Todo> getTodos() {
        return Collections.unmodifiableSet(todos);
    }

    public Set<Role> getRoles() {
        return Collections.unmodifiableSet(roles);
    }



}
