package com.incode.todo.services;

import com.incode.todo.models.TaskRequest;
import com.incode.todo.models.TaskResponse;
import com.incode.todo.repositories.TaskRepository;
import com.incode.todo.utils.ErrorsUtil;

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

  /**
   * Fetch data from repository.
   * @return a {@link Flux} of {@link TaskResponse}
   */
  public Flux<TaskResponse> read() {
    return repository
      .findAll()
      .map(TaskMapper::toModel);
  }

  /**
   * Fetch data from repository with specific identifier.
   * @param id Resource identifier.
   * @return a {@link Mono} {@link List} of {@link TaskResponse}
   */
  public Mono<List<TaskResponse>> read(String id) {
    return validator
      .getUuid(id)
      .map(uuid -> 
        repository
          .findById(uuid)
          .switchIfEmpty(ErrorsUtil.emptyObject())
          .map(TaskMapper::toListModel)
      )
      .orElse(ErrorsUtil.emptyObject());
  }

  /**
   * Creates a new record via the repository.
   * @param task a {@link TaskRequest}
   * @return a {@link Mono} {@link List} of {@link TaskResponse}
   */
  public Mono<List<TaskResponse>> create(TaskRequest task) {
    return validator
      .validate(task)
      .flatMap(model ->
        repository
          .save(TaskMapper.toEntity(model))
          .map(TaskMapper::toListModel)
      );
  }

  /**
   * Updates a resource with a given identifier via the repository.
   * @param task a {@link TaskRequest}
   * @param id Resource identifier.
   * @param completed Resource boolean status.
   * @return a {@link Mono} {@link List} of {@link TaskResponse}
   */
  public Mono<List<TaskResponse>> update(TaskRequest task, String id, Optional<String> completed) {
    return validator
      .validate(task, id)
      .flatMap(model ->
        repository
          .findById(validator.getUuid(id).get())
          .filter(TaskMapper::filterSoftDeleted)
          .map(entity -> TaskMapper.applyEntityChanges(entity, model, completed))
          .switchIfEmpty(ErrorsUtil.emptyObject())
          .flatMap(entity -> repository.save(entity).map(TaskMapper::toListModel))
      );
  }

  /**
   * Removes a recroces with agiven identifier via the repository.
   * @param id Resource identifier.
   * @param soft Operation boolean flag, if true soft delete; if false removes the record from db.
   * @return a {@link Mono} {@link List} of {@link TaskResponse}
   */
  public Mono<List<TaskResponse>> remove(String id, Optional<String> soft) {
    return validator
      .getUuid(id)
      .map(uuid ->
        repository
          .findById(uuid)
          .filter(TaskMapper::filterSoftDeleted)
          .switchIfEmpty(ErrorsUtil.emptyObject())
          .flatMap(entity -> {
            if (soft.isPresent() && TaskMapper.isSoftDelete(soft)) {
              return repository
                .save(TaskMapper.patchSoftDeleteEntity(entity))
                .map(TaskMapper::toListModel);
            } else {
              return repository
                .deleteById(entity.getUuid())
                .then(TaskMapper.toListModel());
            }
          })
      ).orElse(ErrorsUtil.emptyObject());
  }
}
