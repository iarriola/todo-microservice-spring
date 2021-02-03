package com.incode.todo.services;

import com.incode.todo.models.TaskRequest;
import com.incode.todo.models.TaskResponse;
import com.incode.todo.repositories.TaskRepository;
import com.incode.todo.utils.HttpUtils;
import com.incode.todo.utils.LoggerUtils;
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
    return repository
      .findById(UUID.fromString(id))
      .switchIfEmpty(HttpUtils.emptyObjectError())
      .map(MapperUtils::toListModel);
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
      .switchIfEmpty(HttpUtils.emptyObjectError())
      .flatMap(entity -> repository.save(entity).map(MapperUtils::toListModel));
  }

  public Mono<List<TaskResponse>> removeTask(String id, Optional<String> soft) {
    return repository
      .findById(UUID.fromString(id))
      .switchIfEmpty(HttpUtils.emptyObjectError())
      .flatMap(entity -> {
        if(soft.isPresent() && !MapperUtils.isSoftDelete(soft)) {
          LoggerUtils.logger(this.getClass()).debug("Doing a hard delete");
          repository.deleteById(entity.getUuid());
          return Mono.just(List.of());
        } else {
          return repository
            .save(MapperUtils.patchSoftDeleteEntity(entity))
            .map(MapperUtils::toListModel);
        }
      });
  }
}
