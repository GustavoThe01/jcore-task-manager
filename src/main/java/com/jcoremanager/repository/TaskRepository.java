package com.jcoremanager.repository;

import com.jcoremanager.model.Task;
import java.util.List;
import java.util.Optional;

/**
 * Interface Repository.
 * Segue o princípio de abstração: O restante da aplicação não precisa saber
 * SE os dados estão salvos em um banco SQL, Mongo ou Arquivo de Texto.
 * Eles apenas chamam estes métodos.
 */
public interface TaskRepository {
    void save(Task task);
    void update(Task task);
    void delete(String id);
    
    // Optional é usado para evitar NullPointerException caso o ID não exista.
    Optional<Task> findById(String id);
    
    List<Task> findAll();
}