package com.incode.todo.utils;

import com.incode.todo.models.Task;
import com.incode.todo.models.TaskPatch;
import com.incode.todo.models.TaskPost;
import com.incode.todo.repositories.entities.TaskEntity;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class MapperUtils {
  public static Task toModel(TaskEntity entity) {
    return Task.builder()
        .id(entity.getUuid())
        .title(entity.getTitle())
        .createdAt(entity.getCreatedAt())
        .completedAt(entity.getCompletedAt())
        .deletedAt(entity.getDeletedAt())
        .build();
  }

  public static List<Task> toListModel(TaskEntity entity) {
    return List.of(
      Task.builder()
        .id(entity.getUuid())
        .title(entity.getTitle())
        .createdAt(entity.getCreatedAt())
        .completedAt(entity.getCompletedAt())
        .deletedAt(entity.getDeletedAt())
        .build()
    );
  }

  public static TaskEntity toEntity(TaskPost model) {
    return TaskEntity
      .builder()
      .uuid(UUID.randomUUID())
      .title(model.getTitle())
      .description(model.getDescription())
      .createdAt(ZonedDateTime.now())
      .build();
  }

  public static TaskEntity toEntity(UUID id) {
    return TaskEntity.builder().uuid(id).build();
  }

  public static TaskEntity patchEntity(TaskEntity entity, TaskPatch model) {
    ObjectsUtils.applyObjectChange(model.getTitle(), entity.getTitle())
        .ifPresent(entity::setTitle);

    ObjectsUtils.applyObjectChange(model.getDescription(), entity.getDescription())
        .ifPresent(entity::setTitle);

    return entity;
  }
}
