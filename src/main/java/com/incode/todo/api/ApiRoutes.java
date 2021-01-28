package com.incode.todo.api;

public interface ApiRoutes {
  String TASKS = "/api/v1/tasks";

  String TASK = "/api/v1/tasks/{id}";

  String ADMIN = "/admin";

  default String[] allowedRoutes() {
    return new String[] {"/", ADMIN + "/**", TASKS};
  }
}
