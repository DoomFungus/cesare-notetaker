package edu.kpi.notetaker.exceptionhandling.exceptions;

public class EntityAlreadyExists extends RuntimeException{
    public EntityAlreadyExists(String message) {
        super(message);
    }
}