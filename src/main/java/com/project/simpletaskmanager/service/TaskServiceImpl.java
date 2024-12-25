package com.project.simpletaskmanager.service;

import com.project.simpletaskmanager.entity.Task;
import com.project.simpletaskmanager.entity.User;
import com.project.simpletaskmanager.exception.TaskManagerException;
import com.project.simpletaskmanager.exception.UserNotFoundException;
import com.project.simpletaskmanager.repository.TaskRepository;
import com.project.simpletaskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    public Task saveTask(Task task, String username) {

        Optional<User> user = userRepository.findByUsername(username);

        if(user.isEmpty()){
            throw new RuntimeException("user id not found- "+username);
        }

        task.setUser(user.orElse(null));
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getTaskByUserId(Long userId) {
        User user = userRepository.getById(userId);
        List<Task> taskList;
        if (user == null) {
            throw new UserNotFoundException("User not found with the given id");
        } else {
            taskList = taskRepository.findTaskByUserId(userId);
            if (taskList.isEmpty()) {
                throw new TaskManagerException("The task for the user is not available");
            }
        }
        return taskList;
    }

    @Override
    public Task updateTask(Long id, Task task, String username) {

        Task existingTask = taskRepository.getById(id);


        // Check if the task exists
        if (existingTask == null) {
            throw new TaskManagerException("Task not found with id: " + id);
        }

        // Check if the user is authorized to update the task
        if (!existingTask.getUser().getUsername().equals(username)) {
            throw new TaskManagerException("User is not authorized to update this task.");
        }

        // Update the existing task with the new values
        existingTask.setTitle(task.getTitle());
        existingTask.setDescription(task.getDescription());
        existingTask.setStatus(task.getStatus());
        existingTask.setPriority(task.getPriority());
        existingTask.setDueDate(task.getDueDate());


        Optional<User> user = userRepository.findByUsername(username);
        existingTask.setUser(user.orElse(null));

        return taskRepository.updateTask(existingTask);
    }

    @Override
    public void deleteTask(Long id, String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UserNotFoundException("User not found");
        } else {
            Task existing = taskRepository.getById(id);
            if (existing == null) {
                throw new TaskManagerException("task does not exist");
            }
            if (!existing.getUser().getUsername().equals(username)) {
                throw new TaskManagerException("The user does not have authority to delete");
            }

            existing.setDeleted(true);
            taskRepository.save(existing);

        }
    }

    @Override
    public List<Task> getTaskByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        List<Task> taskList;
        if (user == null) {
            throw new UserNotFoundException("User not found with the given id");
        } else {
            taskList = taskRepository.getTaskByUsername(username);
            if (taskList.isEmpty()) {
                throw new TaskManagerException("The task for the user is not available");
            }
        }
        return taskList;
    }
}
