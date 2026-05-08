package com.todo.exception;

public class TacheNotFoundException extends RuntimeException {
    public TacheNotFoundException(Long id) {
        super("Tâche introuvable avec l'id : " + id);
    }
}
