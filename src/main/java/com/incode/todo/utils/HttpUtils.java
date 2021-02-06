package com.incode.todo.utils;

import java.util.Collection;

import com.incode.todo.models.AppErrorType;
import com.incode.todo.models.AppException;
import com.incode.todo.models.AppResponse;

import org.springframework.core.codec.CodecException;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebInputException;

import reactor.core.publisher.Mono;

public class HttpUtils {

  public static Mono<ServerResponse> okResponse(Object obj) {
    return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(obj));
  }

  public static Mono<ServerResponse> noContentResponse(Object obj) {

    if(obj instanceof Collection && obj != null) {
      if(((Collection<?>)obj).isEmpty()) {
        return ServerResponse.noContent().build();
      } else {
        return ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(obj));
      }
    } else {
      return ServerResponse.noContent().build();
    }
  }

  public static Mono<ServerResponse> notFoundResponse(String message) {
    return httpResponse(
      AppErrorType.NOT_FOUND.getStatus(),
      StringUtils.hasText(message) ? message : AppErrorType.BAD_REQUEST.getMessage()
    );
  }

  public static Mono<ServerResponse> badRequestResponse(String message) {
    return httpResponse(
      AppErrorType.BAD_REQUEST.getStatus(),
      StringUtils.hasText(message) ? message : AppErrorType.BAD_REQUEST.getMessage()
    );
  }

  public static Mono<ServerResponse> handleError(Throwable throwable) {
    return normalize(throwable).flatMap(HttpUtils::httpResponse);
  }

  private static Mono<AppException> normalize(Throwable throwable) {

    AppException exception = null;

    if(throwable instanceof AppException) {
      exception = (AppException) throwable;
    }

    if(throwable instanceof CodecException) {
      CodecException e = (CodecException)throwable;
      exception = new AppException(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    if (throwable instanceof ResponseStatusException) {
      ResponseStatusException e = (ResponseStatusException) throwable;
      exception = new AppException(e.getStatus(), e.getMessage());
    }

    if(throwable instanceof WebClientResponseException) {
      WebClientResponseException e = (WebClientResponseException) throwable;
      exception = new AppException(e.getStatusCode(), e.getMessage());
    }

    if(throwable instanceof ServerWebInputException) {
      ServerWebInputException e = (ServerWebInputException) throwable;
      exception = new AppException(HttpStatus.BAD_REQUEST, e.getCause().getMessage());
    }

    if(throwable instanceof DataAccessException) {
      exception = new AppException(AppErrorType.SERVICE_UNAVAILABLE, "Storage error");
    }

    if(exception == null) {
      exception = new AppException(AppErrorType.INTERNAL_ERROR, throwable.getMessage());
    }

    if(is4xx(exception.status())) {
      LoggerUtils.logger(HttpUtils.class).debug("message={}", exception.getMessage());
    } else {
      LoggerUtils.logger(HttpUtils.class).error(exception.getMessage(), exception);
    }

    return Mono.just(exception);
  }

  private static Mono<ServerResponse> httpResponse(AppException exception) {
      return httpResponse(exception.status(), exception.getMessage());
  }

  private static Mono<ServerResponse> httpResponse(HttpStatus status, String message) {
    return ServerResponse.status(status)
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(
              responseBody(status, message)
            ));
}

  private static AppResponse responseBody(HttpStatus status, String message) {
    return AppResponse
      .builder()
      .status(status.value() + " " + status.getReasonPhrase())
      .message(message)
      .build();
  }

  private static boolean is4xx(HttpStatus status) {
    return ObjectsUtils.between(400, 499, status.value());
  }


}
