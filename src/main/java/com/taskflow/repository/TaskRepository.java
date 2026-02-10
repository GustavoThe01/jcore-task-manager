package com.taskflow.repository;

import com.taskflow.model.Task;
import java.util.List;
import java.util.Optional;

/**
 * Interface que define as operações de persistência.
 */
public interface TaskRepository {
    void save(Task task);
    void update(Task task);
    void delete(String id);
    Optional<Task> findById(String id);
    List<Task> findAll();
}