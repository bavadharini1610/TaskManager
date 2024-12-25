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
        String username = userPrincipal.getUsername();
        Task newTask = taskService.saveTask(task, username);
        return newTask;

    }

//    @GetMapping("/user/{userId}")
//    public List<Task> getTaskByUserId(@PathVariable Long userId){
//        List<Task> taskList = new ArrayList<>();
//        taskList=taskService.getTaskByUserId(userId);
//        return taskList;
//    }

    @GetMapping
    public List<Task> getTasksForCurrentUser(@AuthenticationPrincipal UserPrincipal userPrincipal) {
        // Extract the username from the SecurityContext
        String username = userPrincipal.getUsername();
        System.out.println(username);
        return taskService.getTaskByUsername(username);  // Fetch tasks for the current user
    }

//    @PutMapping("/{id}")
//    public Task updateTask(@PathVariable Long id, @RequestBody Task task, @RequestParam Long userId){
//        return taskService.updateTask(id,task,userId);
//    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody Task task, @AuthenticationPrincipal UserPrincipal userPrincipal){
        String username = userPrincipal.getUsername();
        return taskService.updateTask(id,task,username);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable Long id, @AuthenticationPrincipal UserPrincipal userPrincipal){
        String username = userPrincipal.getUsername();
        taskService.deleteTask(id,username);
        return ResponseEntity.ok("task deleted");
    }


}
