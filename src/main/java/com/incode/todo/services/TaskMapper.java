package com.incode.todo.services;

import com.incode.todo.models.TaskRequest;
import com.incode.todo.models.TaskResponse;
import com.incode.todo.repositories.entities.TaskEntity;
import com.incode.todo.utils.ObjectsUtils;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import reactor.core.publisher.Mono;

public class TaskMapper {

  private TaskMapper() {}

  /**
   * Maps a model to an record entity.
   * @param entity Model record.
   * @return
   */
  public static TaskResponse toModel(TaskEntity entity) {
    return TaskResponse.builder().id(entity.getUuid()).title(entity.getTitle())
        .description(entity.getDescription()).createdAt(entity.getCreatedAt())
        .completedAt(entity.getCompletedAt()).deletedAt(entity.getDeletedAt()).build();
  }

  /**
   * Maps an entity to a model.
   * @param entity Data record.
   * @return
   */
  public static List<TaskResponse> toListModel(TaskEntity entity) {
    return List.of(TaskResponse.builder().id(entity.getUuid()).title(entity.getTitle())
        .description(entity.getDescription()).createdAt(entity.getCreatedAt())
        .completedAt(entity.getCompletedAt()).deletedAt(entity.getDeletedAt()).build());
  }

  public static Mono<List<TaskResponse>> toListModel() {
    return Mono.just(List.of());
  }

  public static TaskEntity toEntity(TaskRequest model) {
    return TaskEntity.builder().title(model.title()).description(model.description()).build();
  }

  public static TaskEntity toEntity(String id) {
    return TaskEntity.builder().uuid(UUID.fromString(id)).build();
  }

  /**
   * Applies basic patch operation to a giving model.
   * @param entity Data record.
   * @param model Mode record.
   * @param completed Optional status of a Tasks.
   * @return a {@link TaskEntity}
   */
  public static TaskEntity applyEntityChanges(TaskEntity entity, TaskRequest model,
      Optional<String> completed) {

    ObjectsUtils.applyObjectChange(model.title(), entity.getTitle())
        .ifPresent(title -> entity.setTitle(title.trim()));

    ObjectsUtils.applyObjectChange(model.description(), entity.getDescription())
        .ifPresent(description -> entity.setDescription(description.trim()));

    patchCompletedEntity(entity, completed.map(Boolean::parseBoolean).orElse(false));

    return entity;
  }

  private static TaskEntity patchCompletedEntity(TaskEntity entity, boolean completed) {
    if (completed) {
      entity.setCompletedAt(ZonedDateTime.now());
    } else {
      entity.setCompletedAt(null);
    }
    return entity;
  }

  public static boolean isSoftDelete(Optional<String> soft) {
    return soft.map(Boolean::parseBoolean).orElse(false);
  }

  public static TaskEntity patchSoftDeleteEntity(TaskEntity entity) {
    entity.setDeletedAt(ZonedDateTime.now());
    return entity;
  }

  public static Boolean filterSoftDeleted(TaskEntity model) {
    return model.getDeletedAt() == null;
  }

}
