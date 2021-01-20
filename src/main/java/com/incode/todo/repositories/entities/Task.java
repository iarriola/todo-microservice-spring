package com.incode.todo.repositories.entities;

import java.time.ZonedDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Task {
  private Integer id;
  @Id private UUID uuid;
  private String title;
  private String description;
  private ZonedDateTime createdAt;
  private ZonedDateTime completedAt;
  private ZonedDateTime deletedAt;
}
