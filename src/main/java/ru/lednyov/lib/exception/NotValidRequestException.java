package ru.lednyov.lib.exception;

public class NotValidRequestException extends IllegalArgumentException{
    public NotValidRequestException(String s) {
        super(s);
    }
}
