package com.project.simpletaskmanager.service;

import com.project.simpletaskmanager.entity.Task;

import java.util.List;

public interface TaskService {
    Task saveTask(Task task, String email);

    List<Task> getTaskByUserId(Long userId);

    Task updateTask(Long id, Task task, String email);

    void deleteTask(Long id, String email);

    List<Task> getTaskByUsername(String email);
}
