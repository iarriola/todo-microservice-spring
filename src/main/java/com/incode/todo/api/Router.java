package com.incode.todo.api;

import com.incode.todo.models.Task;
import com.incode.todo.services.TaskService;
import com.incode.todo.utils.HttpUtils;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@RequiredArgsConstructor
@Configuration
public class Router implements Routes {

  private final TaskService taskService;
}
