package com.jcoremanager;

import com.jcoremanager.controller.TaskController;

/**
 * Classe principal (Entry Point) da aplicação JCore Manager.
 * Responsável apenas por inicializar o sistema.
 */
public class Main {
    public static void main(String[] args) {
        // Instancia o controlador principal que gerencia o fluxo da aplicação.
        // Isso mantém o método main limpo e delega a responsabilidade para a camada correta.
        TaskController controller = new TaskController();
        
        // Inicia o loop de interação com o usuário (Menu Principal).
        controller.start();
    }
}