package com.incode.todo.utils;

import com.incode.todo.models.Task;
import com.incode.todo.models.TaskPatch;
import com.incode.todo.models.TaskPost;
import com.incode.todo.repositories.entities.TaskEntity;
import java.util.UUID;

public class TaskMapper {
  public static Task toModel(TaskEntity entity) {
    return Task.builder()
        .id(entity.getUuid())
        .title(entity.getTitle())
        .createdAt(entity.getCreatedAt())
        .completedAt(entity.getCompletedAt())
        .deletedAt(entity.getDeletedAt())
        .build();
  }

  public static TaskEntity toEntity(TaskPost model) {
    return TaskEntity.builder().title(model.getTitle()).description(model.getDescription()).build();
  }

  public static TaskEntity toEntity(UUID id) {
    return TaskEntity.builder().uuid(id).build();
  }

  public static TaskEntity patchEntity(TaskEntity entity, TaskPatch model) {
    ObjectsCustom.applyObjectChange(model.getTitle(), entity.getTitle())
        .ifPresent(entity::setTitle);

    ObjectsCustom.applyObjectChange(model.getDescription(), entity.getDescription())
        .ifPresent(entity::setTitle);

    return entity;
  }
}