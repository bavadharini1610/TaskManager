package com.project.simpletaskmanager.repository;

import com.project.simpletaskmanager.entity.User;

import java.util.Optional;

public interface UserRepository {

    User save(User user);
    User getById(Long id);

    User findByEmail(String email);

    Optional<User> findByUsername(String username);
}
