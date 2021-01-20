package com.incode.todo.services;

import com.incode.todo.models.Task;
import com.incode.todo.models.TaskPatch;
import com.incode.todo.models.TaskPost;
import com.incode.todo.repositories.TaskRepository;
import com.incode.todo.utils.TaskMapper;
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
    return repository.findAll().map(TaskMapper::toModel);
  }

  public Mono<Task> getTask(UUID id) {
    return repository.findById(id).map(TaskMapper::toModel);
  }

  public Mono<Task> createTask(TaskPost model) {
    return repository.save(TaskMapper.toEntity(model)).map(TaskMapper::toModel);
  }

  public Mono<Task> updateTask(UUID id, TaskPatch model) {
    return repository
        .findById(id)
        .filter(Objects::nonNull)
        .map(entity -> TaskMapper.patchEntity(entity, model))
        .flatMap(entity -> repository.save(entity).map(TaskMapper::toModel));
  }

  public Mono<Void> removeTask(UUID id) {
    return repository.delete(TaskMapper.toEntity(id));
  }
}
