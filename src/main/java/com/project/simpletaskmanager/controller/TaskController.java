package com.project.simpletaskmanager.controller;

import com.project.simpletaskmanager.entity.Task;
import com.project.simpletaskmanager.entity.UserPrincipal;
import com.project.simpletaskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @PostMapping
    public Task createTask(@RequestBody Task task, @AuthenticationPrincipal UserPrincipal userPrincipal){
        String email = userPrincipal.getUsername();
        Task newTask = taskService.saveTask(task, email);
        return newTask;

    }

    @GetMapping
    public List<Task> getTasksForCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        // Extract the username from the SecurityContext
        String email = userPrincipal.getUsername();
        System.out.println("email "+email);
        System.out.println(email);
        return taskService.getTaskByUsername(email);  // Fetch tasks for the current user
    }


    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task, @AuthenticationPrincipal UserPrincipal userPrincipal){
        String email = userPrincipal.getUsername();
        return taskService.updateTask(id,task,email);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal userPrincipal){
        String email = userPrincipal.getUsername();
        taskService.deleteTask(id,email);
        return ResponseEntity.ok("task deleted");
    }


}
