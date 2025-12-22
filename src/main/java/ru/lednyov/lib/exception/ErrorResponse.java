package ru.lednyov.lib.exception;

import lombok.Getter;

@Getter
public class ErrorResponse {
    private final String error;
    private final String message;
    private final int status;
    private final long timestamp;

    public ErrorResponse(String error, String message, int status) {
        this.error = error;
        this.message = message;
        this.status = status;
        this.timestamp = System.currentTimeMillis();
    }
}
