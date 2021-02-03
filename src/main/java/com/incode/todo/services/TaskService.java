package com.incode.todo.services;

import com.incode.todo.models.TaskRequest;
import com.incode.todo.models.TaskResponse;
import com.incode.todo.repositories.TaskRepository;
import com.incode.todo.utils.MapperUtils;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository repository;

  public Mono<List<TaskResponse>> getAllTasks() {
    return repository.findAll().map(MapperUtils::toModel).collectList();
  }

  public Mono<List<TaskResponse>> getTask(String id) {
    return repository.findById(UUID.fromString(id)).map(MapperUtils::toListModel);
  }

  public Mono<List<TaskResponse>> createTask(TaskRequest model) {
    return repository
      .save(MapperUtils.toEntity(model))
      .map(MapperUtils::toListModel);
  }

  public Mono<List<TaskResponse>> updateTask(String id, TaskRequest model, Optional<String> completed) {
    return repository
      .findById(UUID.fromString(id))
      .map(entity -> MapperUtils.patchEntity(entity, model, completed))
      .switchIfEmpty(Mono.empty())
      .flatMap(entity -> repository.save(entity).map(MapperUtils::toListModel));
  }

  public Mono<Void> removeTask(String id,  Optional<String> soft) {

    if (MapperUtils.isSoftDelete(soft)) {
      repository
        .findById(UUID.fromString(id))
        .map(entity -> MapperUtils.patchSoftDeleteEntity(entity))
        .switchIfEmpty(Mono.empty())
        .flatMap(entity -> repository.save(entity));
    return Mono.empty();
    } else {
      return repository.delete(MapperUtils.toEntity(UUID.fromString(id)));
    }
    
  }
}
