package com.incode.todo.utils;

import com.incode.todo.models.AppErrorType;
import com.incode.todo.models.AppException;
import com.incode.todo.models.AppResponse;

import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ServerWebInputException;

import reactor.core.publisher.Mono;

public class HttpUtils {

  public static Mono<ServerResponse> okResponse(Object obj) {
    return ServerResponse.ok()
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(obj));
  }

  public static Mono<ServerResponse> noContentResponse(Object obj) {
    return ServerResponse.noContent().build();
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

  public static Mono<ServerResponse> handleError(Throwable t) {
    return normalize(t).flatMap(HttpUtils::httpResponse);
  }

  private static Mono<AppException> normalize(Throwable t) {

    LoggerUtils.logger(HttpUtils.class).error(t.getMessage(), t);

    if(t instanceof AppException) {
      return Mono.just((AppException) t);
    }

    if(t instanceof WebClientResponseException) {
      WebClientResponseException e = (WebClientResponseException) t;
      return Mono.just(new AppException(e.getStatusCode(), e.getMessage()));
    }

    if(t instanceof ServerWebInputException) {
      ServerWebInputException e = (ServerWebInputException) t;
      return Mono.just(new AppException(AppErrorType.BAD_REQUEST, e.getMessage()));
    }

    if(t instanceof DataAccessException) {
      return Mono.just(new AppException(AppErrorType.SERVICE_UNAVAILABLE, "Storage error"));
    }

    return Mono.just(new AppException(AppErrorType.INTERNAL_ERROR, t.getMessage()));
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

}
