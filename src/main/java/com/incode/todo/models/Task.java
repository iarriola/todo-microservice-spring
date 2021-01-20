package com.incode.todo.models;

import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
  private UUID id;
  private String title;
  private String description;
  private ZonedDateTime createdAt;
  private ZonedDateTime completedAt;
  private ZonedDateTime deletedAt;
}
