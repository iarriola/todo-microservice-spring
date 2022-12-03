package com.incode.todo.models;

import lombok.Builder;

@Builder
public record AppResponseBody(
    String status,
    String message
) {}
