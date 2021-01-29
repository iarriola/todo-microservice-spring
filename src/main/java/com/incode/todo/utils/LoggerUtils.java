package com.incode.todo.utils;

import java.util.function.BiFunction;
import java.util.function.Function;

import com.incode.todo.api.TaskRouter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

public class LoggerUtils {
  public static Function<ServerRequest, ServerRequest> stdoutBefore =
    request -> {
      logger(TaskRouter.class).info("Requesting: method={} path={}", request.method(), request.path());
      return request;
    };

  public static BiFunction<ServerRequest, ServerResponse, ServerResponse> stdoutAfter = (request, response) -> {

    logger(TaskRouter.class).info(
      "Responded: method={} path={} status={} {}",
      request.method(),
      request.path(),
      response.statusCode().value(),
      response.statusCode().getReasonPhrase()
    );

    return response;
  };

  public static Logger logger(Class<?> clazz) {
    return LoggerFactory.getLogger(clazz);
  }
}
