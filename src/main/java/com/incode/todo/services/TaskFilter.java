package com.incode.todo.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.incode.todo.models.TaskResponse;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TaskFilter {

  private final TaskService service;

  public Mono<List<TaskResponse>> readAll(Optional<String> include) {
    if(includeDeleted(include)) {
      return service.read().collectList();
    } else {
      return service.read().filter(this::filterSoftDeleted).collectList();
    }
  }

  public Mono<List<TaskResponse>> read(String id, Optional<String> include) {
    if(includeDeleted(include)) {
      return service.read(id);
    } else {
      return service
        .read(id)
        .map(list -> {
            return list.stream().filter(this::filterSoftDeleted).collect(Collectors.toList());
        });
    }
  }

  private Boolean includeDeleted(Optional<String> include) {
    return include.map(value -> "deleted".equalsIgnoreCase(value)).orElse(false);
  }

  private Boolean filterSoftDeleted(TaskResponse model) {
    return model.deletedAt() == null;
  }
}
