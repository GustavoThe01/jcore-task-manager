package com.taskflow.service;

import com.taskflow.model.Priority;
import com.taskflow.model.Task;
import com.taskflow.repository.JsonTaskRepository;
import com.taskflow.repository.TaskRepository;

import java.util.List;
import java.util.Optional;

/**
 * Camada de serviço responsável pelas regras de negócio.
 */
public class TaskService {

    private final TaskRepository repository;

    public TaskService() {
        // Injeção de dependência manual (em frameworks como Spring seria automático)
        this.repository = new JsonTaskRepository();
    }

    public void createTask(String title, String description, Priority priority) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("O título da tarefa não pode estar vazio.");
        }
        Task task = new Task(title, description, priority);
        repository.save(task);
    }

    public List<Task> listAllTasks() {
        return repository.findAll();
    }

    public void updateTask(String id, String newTitle, String newDescription, Priority newPriority) {
        Task task = getTaskOrThrow(id);
        
        if (newTitle != null && !newTitle.trim().isEmpty()) task.setTitle(newTitle);
        if (newDescription != null) task.setDescription(newDescription);
        if (newPriority != null) task.setPriority(newPriority);

        repository.update(task);
    }

    public void completeTask(String id) {
        Task task = getTaskOrThrow(id);
        task.setCompleted(true);
        repository.update(task);
    }

    public void removeTask(String id) {
        // Verifica se existe antes de tentar remover
        getTaskOrThrow(id);
        repository.delete(id);
    }

    private Task getTaskOrThrow(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada com ID: " + id));
    }
}