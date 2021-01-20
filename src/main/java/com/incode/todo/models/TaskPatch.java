package com.incode.todo.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskPatch {
  private String title;
  private String description;
  private Boolean completed;
  private Boolean deleted;
}
