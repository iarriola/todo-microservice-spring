package com.incode.todo.services;

import com.incode.todo.models.AppErrorType;
import com.incode.todo.models.AppException;
import com.incode.todo.models.TaskRequest;
import com.incode.todo.models.TaskResponse;
import com.incode.todo.repositories.TaskRepository;
import com.incode.todo.utils.HttpUtils;
import com.incode.todo.utils.UuidUtils;

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
    return repository.findAll().map(TaskMapper::toModel).collectList();
  }

  public Mono<List<TaskResponse>> getTask(String id) {

    return UuidUtils
      .getUuid(id)
      .map(uuid -> {
        return repository
          .findById(uuid)
          .switchIfEmpty(HttpUtils.emptyObjectError())
          .map(TaskMapper::toListModel);
      })
      .orElse(Mono.error(new AppException(AppErrorType.NOT_FOUND)));
  }

  public Mono<List<TaskResponse>> createTask(TaskRequest model) {
    return repository
      .save(TaskMapper.toEntity(model))
      .map(TaskMapper::toListModel);
  }

  public Mono<List<TaskResponse>> updateTask(String id, TaskRequest model, Optional<String> completed) {
    return repository
      .findById(UUID.fromString(id))
      .map(entity -> TaskMapper.patchEntity(entity, model, completed))
      .switchIfEmpty(HttpUtils.emptyObjectError())
      .flatMap(entity -> repository.save(entity).map(TaskMapper::toListModel));
  }

  public Mono<List<TaskResponse>> removeTask(String id, Optional<String> soft) {
    return repository
      .findById(UUID.fromString(id))
      .switchIfEmpty(HttpUtils.emptyObjectError())
      .flatMap(entity -> {
        if(soft.isPresent() && TaskMapper.isSoftDelete(soft)) {
          return repository
            .save(TaskMapper.patchSoftDeleteEntity(entity))
            .map(TaskMapper::toListModel);
        } else {
          return repository.deleteById(entity.getUuid()).then(Mono.just(List.of()));
        }
      });
  }
}
