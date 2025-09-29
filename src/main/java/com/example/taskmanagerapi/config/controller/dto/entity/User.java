package com.example.taskmanagerapi.config.controller.dto.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Task> tasks = new HashSet<>();

    @Column(unique = true, nullable = false, length = 50)
    private String username;

    @Column(nullable = false, length = 500)
    private String password;

    @Column(unique = true, nullable = false, length = 100)
    private String email;

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}