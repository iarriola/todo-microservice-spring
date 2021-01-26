package com.incode.todo.models;

import org.springframework.http.HttpStatus;

public enum AppErrorType {
    BAD_REQUEST("Mal formed request"),
    NOT_FOUND("Cannot find resource", HttpStatus.NOT_FOUND),
    INTERNAL_ERROR("An error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String message;
    private final HttpStatus status;

    AppErrorType(final String message) {
        this.message = message;
        this.status = HttpStatus.BAD_REQUEST;
    }

    AppErrorType(final String message, final HttpStatus status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
