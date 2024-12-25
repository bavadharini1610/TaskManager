package com.project.simpletaskmanager.repository;

import com.project.simpletaskmanager.entity.Task;

import java.util.List;

public interface TaskRepository {
    Task save(Task task);

    List<Task> findTaskByUserId(Long userId);

    Task getById(Long id);

    Task updateTask(Task task);

//    void delete(Task existing);

    List<Task> getTaskByUsername(String email);
}
