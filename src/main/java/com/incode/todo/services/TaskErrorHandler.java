package com.incode.todo.services;

import com.incode.todo.models.AppErrorType;
import com.incode.todo.models.AppException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class TaskErrorHandler {
    public static <T> Mono<T> emptyObject() {
        return Mono.error(new AppException(AppErrorType.NOT_FOUND));
    }

    public static <T> Flux<T> emptyObjects() {
        return Flux.error(new AppException(AppErrorType.NOT_FOUND));
    }

    public static <T> Mono<T> invalidObject(String message) {
        return Mono.error(new AppException(AppErrorType.BAD_REQUEST, message));
    }
}
