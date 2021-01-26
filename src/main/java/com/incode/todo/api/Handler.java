package com.incode.todo.api;

import com.incode.todo.models.TaskPost;
import com.incode.todo.services.TaskService;
import com.incode.todo.utils.HttpUtils;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Component
public class Handler {
  private final TaskService taskService;

  public Mono<ServerResponse> getAll(ServerRequest request) {
    return HttpUtils.okResponse(taskService.getAllTasks()).onErrorResume(HttpUtils::handleError);
  }

  public Mono<ServerResponse> get(ServerRequest request) {
    return HttpUtils.okResponse(
      taskService.getTask(request.pathVariable("id"))
    ).onErrorResume(HttpUtils::handleError);
  }

  public Mono<ServerResponse> create(ServerRequest request) {
    return HttpUtils.okResponse(
      taskService.createTask(TaskPost.builder().build())
    ).onErrorResume(HttpUtils::handleError);
  }
}
