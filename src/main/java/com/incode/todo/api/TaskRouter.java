package com.incode.todo.api;

import com.incode.todo.utils.LoggerUtils;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class TaskRouter implements ApiRoutes {

  @Bean
  public RouterFunction<ServerResponse> route(TaskHandler handler) {
    return RouterFunctions
      .route()
      .before(LoggerUtils.stdoutBefore)
      .GET(TASKS, handler::getAll)
      .GET(TASK, handler::get)
      .POST(TASKS, handler::post)
      .DELETE(TASK, RequestPredicates.accept(MediaType.TEXT_PLAIN), handler::delete)
      .PATCH(TASK, handler::patch)
      .after(LoggerUtils.stdoutAfter)
      .build();
  }
}
