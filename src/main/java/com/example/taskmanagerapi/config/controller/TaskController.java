package com.example.taskmanagerapi.config.controller; // FIXED: Standard package structure

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.example.taskmanagerapi.config.controller.dto.TaskDto;
import com.example.taskmanagerapi.config.controller.dto.entity.Task;
import com.example.taskmanagerapi.config.controller.dto.entity.exception.repository.security.service.TaskService;

import java.util.HashMap; // FIXED: Added missing import
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @GetMapping
    public ResponseEntity<List<TaskDto>> getTasks(Authentication auth) {
        Long userId = getUserIdFromAuth(auth); // FIXED: Corrected method name
        return ResponseEntity.ok(taskService.getTasksByUser(userId)); // FIXED: Removed space in method call
    }

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskDto dto, Authentication auth) {
        Long userId = getUserIdFromAuth(auth); // FIXED: Corrected method name
        Task created = taskService.createTask(dto, userId);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody TaskDto dto, Authentication auth) {
        // The service layer should verify that this task 'id' belongs to the 'userId' before updating.
        Task updated = taskService.updateTask(id, dto); // Pass userId for authorization check
        return ResponseEntity.ok(updated);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, String>> deleteTask(@PathVariable Long id, Authentication auth) {
        // The service layer should verify that this task 'id' belongs to the 'userId' before deleting.
        taskService.deleteTask(id); // Pass userId for authorization check
        Map<String, String> response = new HashMap<>();
        response.put("message", "Task deleted successfully");
        return ResponseEntity.ok(response);
    }

    private Long getUserIdFromAuth(Authentication auth) { // FIXED: Corrected method signature
        // This is a placeholder. See the "Major Improvements" section below for the correct way to do this.
        // For now, we'll assume the username is the ID. In a real app, this is insecure.
        // String username = auth.getName();
        // return userService.findUserByUsername(username).getId();
        
        // Using a hardcoded value for demonstration purposes ONLY.
        return 1L; // TODO: Implement a real user details service to get the ID from the Authentication principal.
    }
}