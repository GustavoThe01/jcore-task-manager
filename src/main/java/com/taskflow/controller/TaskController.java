package com.taskflow.controller;

import com.taskflow.model.Priority;
import com.taskflow.model.Task;
import com.taskflow.service.TaskService;

import java.util.List;
import java.util.Scanner;

/**
 * Controlador responsável pela interação com o usuário via Console.
 */
public class TaskController {

    private final TaskService service;
    private final Scanner scanner;

    public TaskController() {
        this.service = new TaskService();
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        boolean running = true;
        System.out.println("=== Bem-vindo ao TaskFlow ===");

        while (running) {
            showMenu();
            String option = scanner.nextLine();

            try {
                switch (option) {
                    case "1" -> handleCreateTask();
                    case "2" -> handleListTasks();
                    case "3" -> handleUpdateTask();
                    case "4" -> handleRemoveTask();
                    case "5" -> handleCompleteTask();
                    case "6" -> {
                        running = false;
                        System.out.println("Saindo... Até logo!");
                    }
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
            System.out.println(); // Linha em branco para organização visual
        }
    }

    private void showMenu() {
        System.out.println("-------------------------");
        System.out.println("1. Nova Tarefa");
        System.out.println("2. Listar Tarefas");
        System.out.println("3. Atualizar Tarefa");
        System.out.println("4. Remover Tarefa");
        System.out.println("5. Concluir Tarefa");
        System.out.println("6. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private void handleCreateTask() {
        System.out.println("\n--- Nova Tarefa ---");
        System.out.print("Título: ");
        String title = scanner.nextLine();
        
        System.out.print("Descrição: ");
        String description = scanner.nextLine();
        
        Priority priority = readPriority();
        
        service.createTask(title, description, priority);
        System.out.println("Tarefa criada com sucesso!");
    }

    private void handleListTasks() {
        System.out.println("\n--- Lista de Tarefas ---");
        List<Task> tasks = service.listAllTasks();
        if (tasks.isEmpty()) {
            System.out.println("Nenhuma tarefa cadastrada.");
        } else {
            tasks.forEach(System.out::println);
        }
    }

    private void handleUpdateTask() {
        handleListTasks();
        System.out.print("\nID da tarefa para atualizar (pode ser parcial): ");
        String id = scanner.nextLine();

        System.out.print("Novo Título (Enter para manter atual): ");
        String title = scanner.nextLine();

        System.out.print("Nova Descrição (Enter para manter atual): ");
        String description = scanner.nextLine();

        System.out.println("Nova Prioridade (Enter para manter atual):");
        System.out.println("1-BAIXA, 2-MEDIA, 3-ALTA");
        String priorityInput = scanner.nextLine();
        
        Priority priority = null;
        if (!priorityInput.isEmpty()) {
            priority = parsePriority(priorityInput);
        }

        service.updateTask(id, title, description, priority);
        System.out.println("Tarefa atualizada com sucesso!");
    }

    private void handleRemoveTask() {
        handleListTasks();
        System.out.print("\nID da tarefa para remover: ");
        String id = scanner.nextLine();
        
        service.removeTask(id);
        System.out.println("Tarefa removida com sucesso!");
    }

    private void handleCompleteTask() {
        handleListTasks();
        System.out.print("\nID da tarefa para concluir: ");
        String id = scanner.nextLine();
        
        service.completeTask(id);
        System.out.println("Tarefa marcada como concluída!");
    }

    private Priority readPriority() {
        while (true) {
            System.out.print("Prioridade (1-BAIXA, 2-MEDIA, 3-ALTA): ");
            String input = scanner.nextLine();
            try {
                return parsePriority(input);
            } catch (IllegalArgumentException e) {
                System.out.println("Opção inválida. Use 1, 2 ou 3.");
            }
        }
    }
    
    private Priority parsePriority(String input) {
        return switch (input) {
            case "1" -> Priority.BAIXA;
            case "2" -> Priority.MEDIA;
            case "3" -> Priority.ALTA;
            default -> throw new IllegalArgumentException("Prioridade inválida");
        };
    }
}