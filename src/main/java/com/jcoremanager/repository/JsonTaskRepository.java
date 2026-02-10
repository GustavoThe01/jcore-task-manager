package com.jcoremanager.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.jcoremanager.model.Task;
import com.jcoremanager.util.LocalDateTimeAdapter;

import java.io.*;
import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Implementação concreta do Repository.
 * Responsável pela persistência física dos dados em um arquivo JSON local.
 */
public class JsonTaskRepository implements TaskRepository {

    private static final String FILE_PATH = "tasks.json";
    private final Gson gson;
    private List<Task> tasks; // Cache em memória das tarefas para evitar ler o disco a todo momento.

    public JsonTaskRepository() {
        // Configura o Gson com indentação (PrettyPrinting) e o adaptador de Datas.
        this.gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter())
                .create();
        
        // Carrega os dados do arquivo ao iniciar a aplicação.
        this.tasks = loadFromFile();
    }

    @Override
    public void save(Task task) {
        tasks.add(task); // Adiciona na lista em memória.
        saveToFile();    // Persiste no arquivo físico.
    }

    @Override
    public void update(Task task) {
        // Como 'tasks' é uma lista de objetos em memória, e Java trabalha com referências,
        // as alterações no objeto já estão na lista. Só precisamos atualizar o arquivo.
        saveToFile();
    }

    @Override
    public void delete(String id) {
        // Remove da lista usando lambda expression se o ID coincidir.
        tasks.removeIf(t -> t.getId().equals(id));
        saveToFile();
    }

    @Override
    public Optional<Task> findById(String id) {
        // Utiliza Java Streams para filtrar a lista.
        // O startsWith permite que o usuário digite apenas o início do ID (mais amigável).
        return tasks.stream()
                .filter(t -> t.getId().startsWith(id))
                .findFirst();
    }

    @Override
    public List<Task> findAll() {
        // Retorna uma cópia da lista (ArrayList novo) para evitar que
        // classes externas modifiquem a lista original diretamente sem passar pelo Repository.
        return new ArrayList<>(tasks); 
    }

    /**
     * Método auxiliar para escrever a lista atual no arquivo JSON.
     * Utiliza Try-with-resources para garantir que o Writer seja fechado corretamente.
     */
    private void saveToFile() {
        try (Writer writer = new FileWriter(FILE_PATH)) {
            gson.toJson(tasks, writer);
        } catch (IOException e) {
            System.err.println("Erro crítico ao salvar tarefas: " + e.getMessage());
        }
    }

    /**
     * Método auxiliar para ler o arquivo JSON e converter em Objetos Java.
     */
    private List<Task> loadFromFile() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            return new ArrayList<>(); // Se arquivo não existe (primeira execução), retorna lista vazia.
        }

        try (Reader reader = new FileReader(file)) {
            // TypeToken é necessário porque Generics são apagados em tempo de execução (Type Erasure),
            // então precisamos dizer explicitamente ao Gson que queremos uma List<Task>.
            Type listType = new TypeToken<ArrayList<Task>>() {}.getType();
            List<Task> loadedTasks = gson.fromJson(reader, listType);
            return loadedTasks != null ? loadedTasks : new ArrayList<>();
        } catch (IOException e) {
            System.err.println("Erro ao carregar tarefas: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}