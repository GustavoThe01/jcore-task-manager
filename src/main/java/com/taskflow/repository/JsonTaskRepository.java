package com.taskflow.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.taskflow.model.Task;
import com.taskflow.util.LocalDateTimeAdapter;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementação do repositório que persiste dados em um arquivo JSON.
 */
public class JsonTaskRepository implements TaskRepository {

    private static final String FILE_PATH = "tasks.json";
    private final Gson gson;
    private List<Task> tasks;

    public JsonTaskRepository() {
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        this.tasks = loadFromFile();
    }

    @Override
    public void save(Task task) {
        tasks.add(task);
        saveToFile();
    }

    @Override
    public void update(Task task) {
        // Como estamos trabalhando com referência em memória (ArrayList), 
        // se o objeto foi modificado no service, basta salvar o arquivo.
        // Em um banco real, faríamos um update explícito.
        saveToFile();
    }

    @Override
    public void delete(String id) {
        tasks.removeIf(t -> t.getId().equals(id));
        saveToFile();
    }

    @Override
    public Optional<Task> findById(String id) {
        // Busca flexível: aceita ID completo ou início do UUID
        return tasks.stream()
                .filter(t -> t.getId().startsWith(id))
                .findFirst();
    }

    @Override
    public List<Task> findAll() {
        return new ArrayList<>(tasks); // Retorna uma cópia para proteger a lista interna
    }

    private void saveToFile() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            System.err.println("Erro ao salvar tarefas: " + e.getMessage());
        }
    }

    private List<Task> loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (Reader reader = new FileReader(file)) {
            Type listType = new TypeToken<ArrayList<Task>>() {}.getType();
            List<Task> loadedTasks = gson.fromJson(reader, listType);
            return loadedTasks != null ? loadedTasks : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Erro ao carregar tarefas: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}