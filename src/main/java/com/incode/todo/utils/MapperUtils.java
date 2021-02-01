package com.incode.todo.utils;

import com.incode.todo.models.TaskRequest;
import com.incode.todo.models.TaskResponse;
import com.incode.todo.repositories.entities.TaskEntity;

import org.springframework.util.MultiValueMap;

import java.time.ZonedDateTime;
import java.util.List;
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

  public static TaskEntity toEntity(UUID id) {
    return TaskEntity.builder().uuid(id).build();
  }

  public static TaskEntity patchEntity(TaskEntity entity, TaskRequest model, MultiValueMap<String, String> params) {

    ObjectsUtils.applyObjectChange(
      model.getTitle(),
      entity.getTitle()
    ).ifPresent(entity::setTitle);

    ObjectsUtils.applyObjectChange(
      model.getDescription(),
      entity.getDescription()
    ).ifPresent(entity::setDescription);

    if(Boolean.parseBoolean(params.get("completed").get(0))) {
      entity.setCompletedAt(ZonedDateTime.now());
    }

    return entity;
  }

  public static Boolean isSoftDelete(MultiValueMap<String, String> params) {
    return Boolean.parseBoolean(params.get("soft").get(0));
  }

  public static TaskEntity patchSoftDeleteEntity(TaskEntity entity) {
    entity.setDeletedAt(ZonedDateTime.now());
    return entity;
  }

}
