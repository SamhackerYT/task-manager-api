package com.example.taskmanagerapi.config.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;

@Data
public class TaskDto {
    @NotBlank(message = "Title is required")
    private String title;

    private String description;

    @NotNull(message = "Completed status is required")
    private Boolean completed = false;

    private LocalDate dueDate;

    public String getTitle() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}