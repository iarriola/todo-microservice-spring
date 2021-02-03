package com.incode.todo.utils;

import com.incode.todo.models.TaskRequest;
import com.incode.todo.models.TaskResponse;
import com.incode.todo.repositories.entities.TaskEntity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class MapperUtils {
  public static TaskResponse toModel(TaskEntity entity) {
    return TaskResponse.builder()
        .id(entity.getUuid())
        .title(entity.getTitle())
        .description(entity.getDescription())
        .createdAt(entity.getCreatedAt())
        .completedAt(entity.getCompletedAt())
        .deletedAt(entity.getDeletedAt())
        .build();
  }

  public static List<TaskResponse> toListModel(TaskEntity entity) {
    return List.of(
      TaskResponse.builder()
        .id(entity.getUuid())
        .title(entity.getTitle())
        .description(entity.getDescription())
        .createdAt(entity.getCreatedAt())
        .completedAt(entity.getCompletedAt())
        .deletedAt(entity.getDeletedAt())
        .build()
    );
  }

  public static TaskEntity toEntity(TaskRequest model) {
    return TaskEntity
      .builder()
      .title(model.getTitle())
      .description(model.getDescription())
      .build();
  }

  public static TaskEntity toEntity(String id) {
    return TaskEntity.builder().uuid(UUID.fromString(id)).build();
  }

  public static TaskEntity patchEntity(TaskEntity entity, TaskRequest model, Optional<String> completed) {

    ObjectsUtils.applyObjectChange(
      model.getTitle(),
      entity.getTitle()
    ).ifPresent(title -> entity.setTitle(title.trim()));

    ObjectsUtils.applyObjectChange(
      model.getDescription(),
      entity.getDescription()
    ).ifPresent(description -> entity.setDescription(description.trim()));

    completed
    .map(Boolean::parseBoolean)
    .map(param -> patchCompletedEntity(entity, param));

    return entity;
  }

  private static TaskEntity patchCompletedEntity(TaskEntity entity, Boolean completed) {
    if(completed) {
      entity.setCompletedAt(ZonedDateTime.now());
    } else {
      entity.setCompletedAt(null);
    }
    return entity;
  }

  public static Boolean isSoftDelete(Optional<String> soft) {
    return soft.map(Boolean::parseBoolean).get();
  }

  public static TaskEntity patchSoftDeleteEntity(TaskEntity entity) {
    entity.setDeletedAt(ZonedDateTime.now());
    return entity;
  }

}
