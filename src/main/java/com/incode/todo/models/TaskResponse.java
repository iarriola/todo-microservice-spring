package com.incode.todo.models;

import java.time.ZonedDateTime;
import java.util.UUID;

import lombok.Builder;

@Builder
public record TaskResponse (
    UUID id,
    String title,
    String description,
    ZonedDateTime createdAt,
    ZonedDateTime completedAt,
    ZonedDateTime deletedAt
) {}
