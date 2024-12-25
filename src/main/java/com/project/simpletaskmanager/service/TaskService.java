package com.project.simpletaskmanager.service;

import com.project.simpletaskmanager.entity.Task;

import java.util.List;

public interface TaskService {
    Task saveTask(Task task, String username);

    List<Task> getTaskByUserId(Long userId);

    Task updateTask(Long id, Task task, String username);

    void deleteTask(Long id, String username);

    List<Task> getTaskByUsername(String username);
}
