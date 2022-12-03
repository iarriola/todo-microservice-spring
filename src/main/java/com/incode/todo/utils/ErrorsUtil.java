package com.incode.todo.utils;

import com.incode.todo.models.AppErrorType;
import com.incode.todo.models.AppException;

import org.springframework.core.codec.CodecException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class ErrorsUtil {

  private ErrorsUtil() {}

  public static <T> Mono<T> emptyObject() {
    return Mono.error(new AppException(AppErrorType.NOT_FOUND));
  }

  public static <T> Flux<T> emptyObjects() {
    return Flux.error(new AppException(AppErrorType.NOT_FOUND));
  }

  public static <T> Mono<T> invalidObject(String message) {
    return Mono.error(new AppException(AppErrorType.BAD_REQUEST, message));
  }

  public static Mono<AppException> normalize(Throwable throwable) {

    AppException exception = null;

    if (throwable instanceof AppException e) {
      exception = e;
    }

    if (throwable instanceof CodecException e) {
      exception = new AppException(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    if (throwable instanceof ResponseStatusException e) {
      exception = new AppException((HttpStatus) e.getStatusCode(), e.getMessage());
    }

    if (throwable instanceof WebClientResponseException e) {
      exception = new AppException((HttpStatus) e.getStatusCode(), e.getMessage());
    }

    if (throwable instanceof ServerWebInputException e) {
      exception = new AppException(HttpStatus.BAD_REQUEST, e.getCause().getMessage());
    }

    if (throwable instanceof DataAccessException) {
      exception = new AppException(AppErrorType.SERVICE_UNAVAILABLE, "Storage error");
    }

    if (exception == null) {
      exception = new AppException(AppErrorType.INTERNAL_ERROR, throwable.getMessage());
    }

    return Mono.just(exception);
  }
}
