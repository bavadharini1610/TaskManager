package com.project.simpletaskmanager.repository;

import com.project.simpletaskmanager.entity.User;
import com.project.simpletaskmanager.exception.UserNotFoundException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public class UserRepositoryImpl implements UserRepository {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public User save(User user) {
        entityManager.persist(user);
        return user;
    }

    @Override
    public User getById(Long id) {
        User user = entityManager.find(User.class, id);
        return user;
    }

    @Override
    public User findByEmail(String email) {
        email = email.trim();  // Ensure no leading/trailing spaces
        System.out.println("Email to be searched (after trim): '" + email + "'");
        try {
            return entityManager.createQuery("SELECT u FROM User u WHERE u.email = :email", User.class)
                    .setParameter("email", email.trim())  // Trim to ensure no leading/trailing spaces
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;  // Return null if no user is found with the given email
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        try {
            User user = entityManager.createQuery("SELECT u FROM User u WHERE u.username = :username", User.class)
                    .setParameter("username", username.trim())  // Trim to ensure no leading/trailing spaces
                    .getSingleResult();
            return Optional.of(user);
        } catch (NoResultException e) {
            return Optional.empty();  // Return Optional.empty() if no user is found
        }
    }
}



