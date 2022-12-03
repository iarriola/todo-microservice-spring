package com.incode.todo.models;

import lombok.Builder;

@Builder
public record TaskRequest (
  String title,
  String description
) {}
