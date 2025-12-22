package ru.lednyov.lib.exception;

public class EntryNotFoundException extends IllegalArgumentException{
    public EntryNotFoundException(String s) {
        super(s);
    }
}
