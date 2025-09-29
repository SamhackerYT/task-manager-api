package com.example.taskmanagerapi.config.controller.dto.entity;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    private String description;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TaskStatus status = TaskStatus.TODO; // Enum: TODO, IN_PROGRESS, DONE

    private int priority; // 1-5, higher is more urgent

    private LocalDate dueDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Constructors
    public Task() {}

    public Task(String title, String description, int priority, LocalDate dueDate, User user) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.dueDate = dueDate;
        this.user = user;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
    public int getPriority() { return priority; }
    public void setPriority(int priority) { this.priority = priority; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }
    public User getUser () { return user; }
    public void setUser (User user) { this.user = user; }

    public void setCompleted(Boolean completed) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setCompleted'");
    }

    public Boolean getCompleted() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getCompleted'");
    }
}

// Enum for status
enum TaskStatus {
    TODO, IN_PROGRESS, DONE
}
