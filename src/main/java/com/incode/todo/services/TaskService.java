package com.incode.todo.services;

import com.incode.todo.models.Task;
import com.incode.todo.models.TaskPatch;
import com.incode.todo.models.TaskPost;
import com.incode.todo.repositories.TaskRepository;
import com.incode.todo.utils.MapperUtils;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Service
public class TaskService {

  private final TaskRepository repository;

  public Flux<Task> getAllTasks() {
    return repository.findAll().map(MapperUtils::toModel);
  }

  public Mono<Task> getTask(String id) {
    return repository.findById(UUID.fromString(id)).map(MapperUtils::toModel);
  }

  public Mono<Task> createTask(TaskPost model) {
    return repository.save(MapperUtils.toEntity(model)).map(MapperUtils::toModel);
  }

  public Mono<Task> updateTask(String id, TaskPatch model) {
    return repository
        .findById(UUID.fromString(id))
        .filter(Objects::nonNull)
        .map(entity -> MapperUtils.patchEntity(entity, model))
        .flatMap(entity -> repository.save(entity).map(MapperUtils::toModel));
  }

  public Mono<Void> removeTask(UUID id) {
    return repository.delete(MapperUtils.toEntity(id));
  }
}
