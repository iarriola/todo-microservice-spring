package com.incode.todo.api;

import com.incode.todo.utils.LoggerUtils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class TaskRouter {

  private static final String TASKS = "/api/v1/tasks";

  private static final String TASK = "/api/v1/tasks/{id}";

  @Bean
  RouterFunction<ServerResponse> route(TaskHandler handler) {
    return RouterFunctions
      .route()
      .before(LoggerUtils.stdoutBefore)
      .POST(TASKS, handler::post)
      .GET(TASKS, handler::getAll)
      .GET(TASK, handler::get)
      .PATCH(TASK, handler::patch)
      .DELETE(TASK, handler::delete)
      .after(LoggerUtils.stdoutAfter)
      .build();
  }
}
