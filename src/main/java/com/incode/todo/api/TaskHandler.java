package com.incode.todo.api;

import com.incode.todo.models.TaskRequest;
import com.incode.todo.services.TaskService;
import com.incode.todo.utils.HttpUtils;
import com.incode.todo.utils.ValidatorUtils;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TaskHandler {

  private final TaskService service;

  private final ValidatorUtils validator;

  public Mono<ServerResponse> getAll(ServerRequest request) {
    return service
      .getAllTasks()
      .flatMap(HttpUtils::okResponse)
      .onErrorResume(HttpUtils::handleError);
  }

  public Mono<ServerResponse> get(ServerRequest request) {
    return service
      .getTask(request.pathVariable("id"))
      .flatMap(HttpUtils::okResponse)
      .switchIfEmpty(HttpUtils.notFoundResponse("Unable to find task"))
      .onErrorResume(HttpUtils::handleError);
  }

  public Mono<ServerResponse> post(ServerRequest request) {
    return request
      .bodyToMono(TaskRequest.class)
      .flatMap(task -> HttpUtils.validate(task, validator))
      .flatMap(task -> service.createTask(task))
      .flatMap(HttpUtils::okResponse)
      .onErrorResume(HttpUtils::handleError);
  }

  public Mono<ServerResponse> patch(ServerRequest request) {
    return request
      .bodyToMono(TaskRequest.class)
      .flatMap(task -> HttpUtils.validate(task, validator))
      .flatMap(task -> service.updateTask(request.pathVariable("id"), task, request.queryParam("completed")))
      .flatMap(HttpUtils::okResponse)
      .switchIfEmpty(HttpUtils.notFoundResponse("Unable to find task"))
      .onErrorResume(HttpUtils::handleError);
  }

  public Mono<ServerResponse> delete(ServerRequest request) {
    return service
      .removeTask(request.pathVariable("id"), request.queryParam("soft"))
      .flatMap(HttpUtils::noContentResponse)
      .switchIfEmpty(HttpUtils.notFoundResponse("Unable to find task"))
      .onErrorResume(HttpUtils::handleError);
  }

}
