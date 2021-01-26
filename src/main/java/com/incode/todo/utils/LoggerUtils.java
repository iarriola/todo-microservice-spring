package com.incode.todo.utils;

import java.util.function.Function;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.reactive.function.server.ServerRequest;

public class LoggerUtils {
  public static final Function<ServerRequest, ServerRequest> stdout =
    request -> {
      logger(HttpUtils.class).info("Executing {}: {}", request.method(), request.path());
      return request;
    };

    public static final Logger logger(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }
}
