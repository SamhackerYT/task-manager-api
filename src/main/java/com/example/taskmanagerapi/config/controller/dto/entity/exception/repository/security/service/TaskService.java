package com.example.taskmanagerapi.config.controller.dto.entity.exception.repository.security.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.taskmanagerapi.config.controller.dto.TaskDto;
import com.example.taskmanagerapi.config.controller.dto.entity.Task;
import com.example.taskmanagerapi.config.controller.dto.entity.exception.repository.TaskRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TaskService {
    @Autowired
    private TaskRepository taskRepository;

    public Task createTask(TaskDto dto, Long userId) {
        Task task = new Task();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.getCompleted());
        task.setDueDate(dto.getDueDate());
        task.setId(userId);
        return taskRepository.save(task);
    }

    public List<TaskDto> getTasksByUser (Long userId) {
        return taskRepository.findByUserId(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Task updateTask(Long id, TaskDto dto) {
        Task task = taskRepository.findById(id)
                .orElseThrow();
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompleted(dto.getCompleted());
        task.setDueDate(dto.getDueDate());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    private TaskDto mapToDto(Task task) {
        TaskDto dto = new TaskDto();
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCompleted(task.getCompleted());
        dto.setDueDate(task.getDueDate());
        return dto;
    }
}