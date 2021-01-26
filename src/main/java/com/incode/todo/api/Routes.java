package com.incode.todo.api;

public interface Routes {
  String TASKS = "/todo/v1/tasks";

  String ADMIN = "/admin";

  default String[] allowedRoutes() {
    return new String[] {"/", ADMIN + "/**", TASKS};
  }
}
