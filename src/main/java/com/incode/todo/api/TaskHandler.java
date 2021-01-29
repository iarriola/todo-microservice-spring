package com.incode.todo.api;

import com.incode.todo.models.TaskPatch;
import com.incode.todo.models.TaskPost;
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
      .onErrorResume(HttpUtils::handleError);
  }

  public Mono<ServerResponse> post(ServerRequest request) {

    Mono<TaskPost> taskPost = request.bodyToMono(TaskPost.class);
    return taskPost
      .map(post -> service.createTask(post))
      .flatMap(HttpUtils::okResponse)
      .switchIfEmpty(HttpUtils.badRequestResponse("Please provide a valid task"))
      .onErrorResume(HttpUtils::handleError);
  }

  public Mono<ServerResponse> delete(ServerRequest request) {
    return service
      .removeTask(request.pathVariable("id"))
      .flatMap(HttpUtils::noContentResponse)
      .onErrorResume(HttpUtils::handleError);
  }

  public Mono<ServerResponse> patch(ServerRequest request) {
    return request
      .bodyToMono(TaskPatch.class)
      .map(patch -> service.updateTask(request.pathVariable("id"), patch))
      .flatMap(HttpUtils::okResponse)
      .switchIfEmpty(HttpUtils.badRequestResponse("Please provide a valid task"))
      .onErrorResume(HttpUtils::handleError);
  }
}
