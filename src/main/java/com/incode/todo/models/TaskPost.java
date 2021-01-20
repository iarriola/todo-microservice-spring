package com.incode.todo.models;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskPost {
  @NotBlank(message = "Please provide a valid title")
  private String title;

  @NotBlank(message = "Please provide a valid description")
  private String description;
}
