package com.incode.todo.utils;

import com.incode.todo.models.Task;
import com.incode.todo.models.TaskPatch;
import com.incode.todo.models.TaskPost;
import com.incode.todo.repositories.entities.TaskEntity;
import java.util.UUID;

public class TaskMapper {
  public static Task toModel(TaskEntity task) {
    return Task.builder()
        .id(task.getUuid())
        .title(task.getTitle())
        .createdAt(task.getCreatedAt())
        .completedAt(task.getCompletedAt())
        .deletedAt(task.getDeletedAt())
        .build();
  }

  public static TaskEntity toEntity(TaskPost task) {
    return TaskEntity.builder().title(task.getTitle()).description(task.getDescription()).build();
  }

  public static TaskEntity toEntity(UUID id) {
    return TaskEntity.builder().uuid(id).build();
  }

  public static TaskEntity patchEntity(TaskEntity entity, TaskPatch patch) {
    ObjectsCustom.applyObjectChange(patch.getTitle(), entity.getTitle())
        .ifPresent(entity::setTitle);

    ObjectsCustom.applyObjectChange(patch.getDescription(), entity.getDescription())
        .ifPresent(entity::setTitle);

    return entity;
  }
}
