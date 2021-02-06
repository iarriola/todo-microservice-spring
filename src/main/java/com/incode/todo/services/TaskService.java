package com.incode.todo.services;

import com.incode.todo.models.TaskRequest;
import com.incode.todo.models.TaskResponse;
import com.incode.todo.repositories.TaskRepository;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class TaskService {

  private final TaskRepository repository;

  private final TaskValidator validator;

  public Flux<TaskResponse> read() {
    return repository
      .findAll()
      .map(TaskMapper::toModel);
  }

  public Mono<List<TaskResponse>> read(String id) {
    return validator
      .getUuid(id)
      .map(uuid -> {
        return repository
        .findById(uuid)
        .switchIfEmpty(TaskErrorHandler.emptyObject())
        .map(TaskMapper::toListModel);
      })
      .orElse(TaskErrorHandler.emptyObject());
  }

  public Mono<List<TaskResponse>> create(TaskRequest task) {
    return validator
      .validate(task)
      .flatMap(model -> {
        return repository
          .save(TaskMapper.toEntity(model))
          .map(TaskMapper::toListModel);
      });
  }

  public Mono<List<TaskResponse>> update(TaskRequest task, String id, Optional<String> completed) {
    return validator
      .validate(task, id)
      .flatMap(model -> {
        return repository
          .findById(validator.getUuid(id).get())
          .filter(TaskMapper::filterSoftDeleted)
          .map(entity -> TaskMapper.applyEntityChanges(entity, model, completed))
          .switchIfEmpty(TaskErrorHandler.emptyObject())
          .flatMap(entity -> repository.save(entity).map(TaskMapper::toListModel));
      });
  }

  public Mono<List<TaskResponse>> remove(String id, Optional<String> soft) {
    return validator
      .getUuid(id)
      .map(uuid -> {
        return repository
          .findById(uuid)
          .filter(TaskMapper::filterSoftDeleted)
          .switchIfEmpty(TaskErrorHandler.emptyObject())
          .flatMap(entity -> {
            if(soft.isPresent() && TaskMapper.isSoftDelete(soft)) {
              return repository
                .save(TaskMapper.patchSoftDeleteEntity(entity))
                .map(TaskMapper::toListModel);
            } else {
              return repository
                .deleteById(entity.getUuid())
                .then(TaskMapper.toListModel());
            }
          });
      }).orElse(TaskErrorHandler.emptyObject());
  }
}
