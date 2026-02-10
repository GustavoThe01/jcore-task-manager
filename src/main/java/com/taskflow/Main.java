package com.taskflow;

import com.taskflow.controller.TaskController;

/**
 * Classe principal que inicializa a aplicação.
 */
public class Main {
    public static void main(String[] args) {
        // Inicializa o controlador e inicia o loop da aplicação
        TaskController controller = new TaskController();
        controller.start();
    }
}