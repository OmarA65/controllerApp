package com.example.task.exception;

public class ControllerNotFoundException extends RuntimeException{
    public ControllerNotFoundException(String message) {
        super(message);
    }
}
