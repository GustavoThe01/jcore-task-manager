package com.jcoremanager.controller;

import com.jcoremanager.model.Priority;
import com.jcoremanager.model.Task;
import com.jcoremanager.service.TaskService;

import java.util.List;
import java.util.Scanner;

/**
 * Controller Layer.
 * Responsável exclusivamente pela interação com o usuário (Interface Console).
 * Captura entradas (System.in), exibe saídas (System.out) e delega a lógica para o Service.
 */
public class TaskController {

    private final TaskService service;
    private final Scanner scanner;

    public TaskController() {
        this.service = new TaskService();
        this.scanner = new Scanner(System.in);
    }

    /**
     * Inicia o loop principal da aplicação (Game Loop / Event Loop pattern).
     */
    public void start() {
        boolean running = true;
        System.out.println("=== Bem-vindo ao JCore Manager ===");

        while (running) {
            showMenu();
            String option = scanner.nextLine();

            try {
                // Utiliza Switch Expressions (Java 12+) para um código mais limpo.
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
                // Tratamento genérico de erros para evitar que a aplicação quebre (crash) no console.
                System.out.println("Erro: " + e.getMessage());
            }
            System.out.println(); // Linha em branco para melhor visualização.
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
        
        // Utiliza métodos auxiliares para garantir que o input seja válido antes de passar ao Service.
        String title = readRequiredString("Título: ");
        String description = readRequiredString("Descrição: ");
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
            // Method Reference para impressão limpa.
            tasks.forEach(System.out::println);
        }
    }

    private void handleUpdateTask() {
        handleListTasks();
        System.out.print("\nID da tarefa para atualizar (pode ser parcial): ");
        String id = scanner.nextLine();

        System.out.print("Novo Título (Enter para manter atual): ");
        String title = scanner.nextLine();
        // Lógica de UI: Se estiver vazio, define como null para o Service ignorar a atualização.
        if (!title.isEmpty() && title.trim().isEmpty()) {
            System.out.println("Aviso: Título em branco ignorado.");
            title = null;
        }

        System.out.print("Nova Descrição (Enter para manter atual): ");
        String description = scanner.nextLine();
        if (!description.isEmpty() && description.trim().isEmpty()) {
            System.out.println("Aviso: Descrição em branco ignorada.");
            description = null;
        }

        System.out.println("Nova Prioridade (Enter para manter atual):");
        System.out.println("1-BAIXA, 2-MEDIA, 3-ALTA");
        String priorityInput = scanner.nextLine();
        
        Priority priority = null;
        if (!priorityInput.trim().isEmpty()) {
            try {
                priority = parsePriority(priorityInput.trim());
            } catch (IllegalArgumentException e) {
                System.out.println("Prioridade inválida. Mantendo a original.");
            }
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

    /**
     * Loop de validação de UI: Garante que o usuário não avance sem preencher campos obrigatórios.
     */
    private String readRequiredString(String prompt) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine();
            if (input != null && !input.trim().isEmpty()) {
                return input.trim();
            }
            System.out.println("Este campo é obrigatório e não pode ser vazio. Tente novamente.");
        }
    }

    private Priority readPriority() {
        while (true) {
            System.out.print("Prioridade (1-BAIXA, 2-MEDIA, 3-ALTA): ");
            String input = scanner.nextLine();
            try {
                return parsePriority(input.trim());
            } catch (IllegalArgumentException e) {
                System.out.println("Opção inválida. Digite 1, 2 ou 3.");
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