package com.project.simpletaskmanager.controller;

import com.project.simpletaskmanager.entity.Task;
import com.project.simpletaskmanager.entity.UserPrincipal;
import com.project.simpletaskmanager.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

//    @Operation(
//            summary = "Create a new task",
//            description = "Allows the authenticated user to create a new task. The user must provide task details including title, description, status, priority, and due date.",
//            security = @SecurityRequirement(name = "BearerAuth"),
//            responses = {
//                    @ApiResponse(
//                            responseCode = "200",
//                            description = "Task created successfully",
//                            content = @Content(
//                                    mediaType = "application/json",
//                                    schema = @Schema(implementation = Task.class),
//                                    examples = @ExampleObject(
//                                            name = "Task Example",
//                                            value = "{\n" +
//                                                    "  \"title\": \"Complete Spring Boot training\",\n" +
//                                                    "  \"description\": \"Spring Boot\",\n" +
//                                                    "  \"status\": \"PENDING\",\n" +
//                                                    "  \"priority\": \"MEDIUM\",\n" +
//                                                    "  \"dueDate\": \"2025-04-10\"\n" +
//                                                    "}"
//                                    )
//                            )
//                    ),
//                    @ApiResponse(
//                            responseCode = "401",
//                            description = "Unauthorized - Invalid or missing JWT token"
//                    )
//            }
//    )
//    @PostMapping
//    public Task createTask(
//            @RequestBody(
//                    description = "Details of the task to be created",
//                    required = true,
//                    content = @Content(
//                            mediaType = "application/json",
//                            schema = @Schema(implementation = Task.class),
//                            examples = @ExampleObject(
//                                    name = "Task Example",
//                                    value = "{\n" +
//                                            "  \"title\": \"Complete Spring Boot training\",\n" +
//                                            "  \"description\": \"Spring Boot\",\n" +
//                                            "  \"status\": \"PENDING\",\n" +
//                                            "  \"priority\": \"MEDIUM\",\n" +
//                                            "  \"dueDate\": \"2025-04-10\"\n" +
//                                            "}"
//                            )
//                    )
//            ) Task task,
//            @AuthenticationPrincipal UserPrincipal userPrincipal
//    ) {
//        String email = userPrincipal.getUsername();
//        Task newTask = taskService.saveTask(task, email);
//        return newTask;
//    }




    @Operation(summary = "Get all tasks", security = @SecurityRequirement(name = "BearerAuth"))
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
