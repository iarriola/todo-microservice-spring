package com.incode.todo.utils;

import com.incode.todo.models.AppException;
import com.incode.todo.models.AppResponseBody;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

public class HttpUtils {

  private HttpUtils() {}

  /**
   * Generic OK response.
   * 
   * @param obj Returning payload.
   * @return
   */
  public static Mono<ServerResponse> okServerResponse(Object obj) {
    return ServerResponse.ok().contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(obj));
  }

  /**
   * Generic NO CONTENT response.
   * 
   * @param obj Returning payload.
   * @return
   */
  public static Mono<ServerResponse> noContentServerResponse(Object obj) {
    if (ObjectsUtils.hasContent(obj)) {
      return HttpUtils.okServerResponse(obj);
    } else {
      return ServerResponse.noContent().build();
    }
  }

  /**
   * Generic INTERNAL SERVER ERROR response.
   * 
   * @param throwable The actuall error.
   * @return
   */
  public static Mono<ServerResponse> errorServerResponse(Throwable throwable) {
    return ErrorsUtil.normalize(throwable).flatMap(HttpUtils::log)
        .flatMap(HttpUtils::serverResponseException);
  }

  private static Mono<ServerResponse> serverResponseException(AppException exception) {
    return ServerResponse.status(exception.status()).contentType(MediaType.APPLICATION_JSON)
        .body(BodyInserters.fromValue(AppResponseBody.builder()
            .status(exception.status().value() + " " + exception.status().getReasonPhrase())
            .message(exception.getMessage()).build()));
  }

  private static boolean is4xx(HttpStatus status) {
    return ObjectsUtils.between(400, 499, status.value());
  }

  private static Mono<AppException> log(AppException exception) {
    if (is4xx(exception.status())) {
      LoggerUtils.logger(HttpUtils.class).debug("message={}", exception.getMessage());
    } else {
      LoggerUtils.logger(HttpUtils.class).error("message={}", exception.getMessage(), exception);
    }
    return Mono.just(exception);
  }

}
