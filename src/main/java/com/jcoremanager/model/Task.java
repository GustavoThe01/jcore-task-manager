package com.jcoremanager.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * Classe de Modelo (Domain) que representa uma tarefa no sistema.
 * É um POJO (Plain Old Java Object) que encapsula os dados.
 */
public class Task {
    // Identificador único universal para evitar colisão de IDs, essencial em sistemas distribuídos ou persistidos.
    private String id;
    private String title;
    private String description;
    private Priority priority;
    private boolean completed;
    
    // Armazena datas para auditoria e histórico da tarefa.
    private LocalDateTime creationDate;
    private LocalDateTime completionDate; 

    // Construtor vazio é necessário para que o Gson consiga instanciar o objeto via Reflection ao ler o JSON.
    public Task() {}

    // Construtor principal para criação de novas tarefas.
    public Task(String title, String description, Priority priority) {
        this.id = UUID.randomUUID().toString(); // Gera ID automaticamente na criação.
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.completed = false; // Tarefa nasce pendente por padrão.
        this.creationDate = LocalDateTime.now(); // Registra o momento exato da criação.
        this.completionDate = null;
    }

    // --- Getters e Setters (Encapsulamento) ---

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public LocalDateTime getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(LocalDateTime completionDate) {
        this.completionDate = completionDate;
    }

    /**
     * Sobrescrita do método toString para fornecer uma representação textual formatada da tarefa.
     * Útil para exibir os dados no console de forma legível para o usuário.
     */
    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        
        String completionInfo = "";
        if (completed && completionDate != null) {
            completionInfo = " | Concluído em: " + completionDate.format(fmt);
        }

        // Exibe apenas os primeiros 8 caracteres do UUID para não poluir visualmente o console.
        return String.format(
            "ID: %s | [%s] %s - Prioridade: %s | Status: %s | Criado: %s%s",
            id.substring(0, 8),
            completed ? "X" : " ",
            title,
            priority,
            completed ? "Concluída" : "Pendente",
            creationDate.format(fmt),
            completionInfo
        );
    }
}