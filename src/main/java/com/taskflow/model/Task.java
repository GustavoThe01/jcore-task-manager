package com.taskflow.model;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Representa uma tarefa no sistema.
 */
public class Task {
    private String id;
    private String title;
    private String description;
    private Priority priority;
    private boolean completed;
    private LocalDateTime creationDate;

    // Construtor padrão necessário para o Gson
    public Task() {}

    public Task(String title, String description, Priority priority) {
        this.id = UUID.randomUUID().toString();
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.completed = false;
        this.creationDate = LocalDateTime.now();
    }

    // Getters e Setters

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

    @Override
    public String toString() {
        return String.format(
            "ID: %s | [%s] %s - Prioridade: %s | Status: %s | Criado em: %s",
            id.substring(0, 8), // Exibe apenas os primeiros 8 caracteres do UUID para brevidade
            completed ? "X" : " ",
            title,
            priority,
            completed ? "Concluída" : "Pendente",
            creationDate
        );
    }
}