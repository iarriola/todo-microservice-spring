package com.incode.todo.models;

import org.springframework.http.HttpStatus;

public enum AppErrorType {
  BAD_REQUEST("Mal formed request"),
  INVALID_IDENTIFIER("Invalid resource identifier"),
  NOT_FOUND("Unable to find specified resource", HttpStatus.NOT_FOUND),
  SERVICE_UNAVAILABLE("Request cannot be processed", HttpStatus.SERVICE_UNAVAILABLE),
  INTERNAL_ERROR("An error has occurred", HttpStatus.INTERNAL_SERVER_ERROR);

  private final String message;
  private final HttpStatus status;

  AppErrorType(final String message) {
    this.message = message;
    this.status = HttpStatus.BAD_REQUEST;
  }

  AppErrorType(final String message, final HttpStatus status) {
    this.message = message;
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public HttpStatus getStatus() {
    return status;
  }
}
