package com.incode.todo.services;

import com.incode.todo.models.Task;
import com.incode.todo.models.TaskPatch;
import com.incode.todo.models.TaskPost;
import com.incode.todo.repositories.TaskRepository;
import com.incode.todo.utils.MapperUtils;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository repository;

  public Mono<List<Task>> getAllTasks() {
    return repository.findAll().map(MapperUtils::toModel).collectList();
  }

  public Mono<List<Task>> getTask(String id) {
    return repository.findById(UUID.fromString(id)).map(MapperUtils::toListModel);
  }

  public Mono<List<Task>> createTask(TaskPost model) {
    return repository
      .save(MapperUtils.toEntity(model))
      .map(MapperUtils::toListModel);
  }

  public Mono<List<Task>> updateTask(String id, TaskPatch model) {
    return repository
      .findById(UUID.fromString(id))
      .filter(Objects::nonNull)
      .map(entity -> MapperUtils.patchEntity(entity, model))
      .switchIfEmpty(Mono.empty())
      .flatMap(entity -> repository.save(entity).map(MapperUtils::toListModel));
  }

  public Mono<Void> removeTask(String id) {
    return repository.delete(MapperUtils.toEntity(UUID.fromString(id)));
  }
}
