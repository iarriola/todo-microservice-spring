package com.incode.todo.api;

import com.incode.todo.models.TaskRequest;
import com.incode.todo.services.TaskFilter;
import com.incode.todo.services.TaskService;
import com.incode.todo.utils.HttpUtils;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TaskHandler {

  private final TaskService service;

  private final TaskFilter filterService;

  /**
   * Path variable name for object identifier.
   */
  private final String ID = "id";

  /**
   * Query parameter name for toggle completed action.
   */
  private final String COMPLETED = "completed";

  /**
   * Query parameter name for soft deletion pivot.
   */
  private final String SOFT = "soft";

  /**
   * Query parameter name for including soft-deleted objects.
   */
  private final String INCLUDE = "include";

  public Mono<ServerResponse> getAll(ServerRequest request) {
    return filterService
      .readAll(request.queryParam(INCLUDE))
      .flatMap(HttpUtils::okServerResponse)
      .onErrorResume(HttpUtils::errorServerResponse);
  }

  public Mono<ServerResponse> get(ServerRequest request) {
    return filterService
      .read(request.pathVariable(ID), request.queryParam(INCLUDE))
      .flatMap(HttpUtils::okServerResponse)
      .onErrorResume(HttpUtils::errorServerResponse);
  }

  public Mono<ServerResponse> post(ServerRequest request) {
    return request
      .bodyToMono(TaskRequest.class)
      .flatMap(task -> service.create(task))
      .flatMap(HttpUtils::okServerResponse)
      .onErrorResume(HttpUtils::errorServerResponse);
  }

  public Mono<ServerResponse> patch(ServerRequest request) {
    return request
      .bodyToMono(TaskRequest.class)
      .flatMap(task -> service.update(task, request.pathVariable(ID), request.queryParam(COMPLETED)))
      .flatMap(HttpUtils::okServerResponse)
      .onErrorResume(HttpUtils::errorServerResponse);
  }

  public Mono<ServerResponse> delete(ServerRequest request) {
    return service
      .remove(request.pathVariable(ID), request.queryParam(SOFT))
      .flatMap(HttpUtils::noContentServerResponse)
      .onErrorResume(HttpUtils::errorServerResponse);
  }

}
