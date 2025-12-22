package ru.lednyov.lib.exception;

public class AlreadyExistsException extends IllegalArgumentException{
    public AlreadyExistsException(String s) {
        super(s);
    }
}
