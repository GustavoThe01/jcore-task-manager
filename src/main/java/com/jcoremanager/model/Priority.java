package com.jcoremanager.model;

/**
 * Enum que define os níveis de prioridade de uma tarefa.
 * Utilizar Enum garante type-safety, impedindo que valores inválidos
 * sejam atribuídos à prioridade (ex: strings soltas como "Mais ou menos").
 */
public enum Priority {
    BAIXA,
    MEDIA,
    ALTA;
}