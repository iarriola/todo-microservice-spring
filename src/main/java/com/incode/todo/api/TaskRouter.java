package com.incode.todo.api;

import com.incode.todo.utils.LoggerUtils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class TaskRouter {

  private final String tasks = "/api/v1/tasks";

  private final String task = "/api/v1/tasks/{id}";

  private final String admin = "/admin";

  public String[] allowedRoutes() {
    return new String[] {"/", admin + "/**", task};
  }

  @Bean
  public RouterFunction<ServerResponse> route(TaskHandler handler) {
    return RouterFunctions
      .route()
      .before(LoggerUtils.stdoutBefore)
      .POST(tasks, handler::post)
      .GET(tasks, handler::getAll)
      .GET(task, handler::get)
      .PATCH(task, handler::patch)
      .DELETE(task, handler::delete)
      .after(LoggerUtils.stdoutAfter)
      .build();
  }
}
