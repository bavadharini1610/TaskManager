package com.project.simpletaskmanager.repository;

import com.project.simpletaskmanager.entity.Task;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class TaskRepositoryImpl implements TaskRepository{

    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Task save(Task task) {
        entityManager.persist(task);
        return task;
    }

    @Override
    public List<Task> findTaskByUserId(Long userId) {

        TypedQuery query = entityManager.createQuery("from Task t where t.user.id=:userId",Task.class);

        query.setParameter("userId",userId);
        return query.getResultList();
    }

    @Override
    public Task getById(Long id) {
        return entityManager.find(Task.class,id);
    }

    @Override
    @Transactional
    public Task updateTask(Task task) {
        return entityManager.merge(task);
    }

//    @Override
//    @Transactional
//    public void delete(Task existing) {
//        entityManager.remove(existing);
//       // entityManager.remove(entityManager.contains(existing) ? existing : entityManager.merge(existing));
//    }

    @Override
    public List<Task> getTaskByUsername(String email) {
        TypedQuery query = entityManager.createQuery("SELECT t FROM Task t WHERE t.user.email = :email AND t.deleted = false", Task.class);
        query.setParameter("email", email);
        return query.getResultList();
    }
}
