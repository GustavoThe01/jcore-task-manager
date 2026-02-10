package com.jcoremanager.service;

import com.jcoremanager.model.Priority;
import com.jcoremanager.model.Task;
import com.jcoremanager.repository.JsonTaskRepository;
import com.jcoremanager.repository.TaskRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Camada de Serviço (Service Layer).
 * Aqui residem as Regras de Negócio. O Service atua como um "porteiro" entre o Controller e o Repository,
 * garantindo que nenhum dado inválido seja persistido.
 */
public class TaskService {

    private final TaskRepository repository;

    public TaskService() {
        // Injeção de dependência manual.
        // Em um ambiente Enterprise (Spring/Jakarta EE), isso seria @Autowired ou @Inject.
        this.repository = new JsonTaskRepository();
    }

    public void createTask(String title, String description, Priority priority) {
        // Validação: Não permite criar tarefas sem título ou descrição.
        validateStringInput(title, "O título da tarefa é obrigatório.");
        validateStringInput(description, "A descrição da tarefa é obrigatória.");

        Task task = new Task(title, description, priority);
        repository.save(task);
    }

    public List<Task> listAllTasks() {
        return repository.findAll();
    }

    public void updateTask(String id, String newTitle, String newDescription, Priority newPriority) {
        Task task = getTaskOrThrow(id);
        
        // Atualização parcial: Só altera os campos que foram preenchidos pelo usuário.
        if (newTitle != null && !newTitle.trim().isEmpty()) {
            task.setTitle(newTitle);
        }
        
        if (newDescription != null && !newDescription.trim().isEmpty()) {
            task.setDescription(newDescription);
        }
        
        if (newPriority != null) {
            task.setPriority(newPriority);
        }

        repository.update(task);
    }

    public void completeTask(String id) {
        Task task = getTaskOrThrow(id);
        
        // Regra de negócio: Só atualiza se ainda não estiver concluída.
        if (!task.isCompleted()) {
            task.setCompleted(true);
            task.setCompletionDate(LocalDateTime.now()); // Regra: Registrar data exata da conclusão.
            repository.update(task);
        }
    }

    public void removeTask(String id) {
        getTaskOrThrow(id); // Garante que existe antes de tentar apagar.
        repository.delete(id);
    }

    // Método auxiliar para buscar tarefa ou falhar rapidamente (Fail-fast) se não existir.
    private Task getTaskOrThrow(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tarefa não encontrada com ID: " + id));
    }

    private void validateStringInput(String input, String errorMessage) {
        if (input == null || input.trim().isEmpty()) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}