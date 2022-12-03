package com.incode.todo.utils;

import com.incode.todo.api.TaskRouter;
import java.util.function.BiFunction;
import java.util.function.UnaryOperator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

public class LoggerUtils {

  private LoggerUtils() {}

  public static final UnaryOperator<ServerRequest> stdoutBefore = request -> {
    logger(TaskRouter.class).debug("request={}", request);
    return request;
  };

  public static final BiFunction<ServerRequest, ServerResponse, ServerResponse> stdoutAfter =
      (request, response) -> {

        logger(TaskRouter.class).debug("response={} status={}", request,
            response.statusCode().value());

        return response;
      };

  public static Logger logger(Class<?> clazz) {
    return LoggerFactory.getLogger(clazz);
  }
}
