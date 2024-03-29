package com.incode.todo.models;

import org.springframework.http.HttpStatus;

public class AppException extends RuntimeException {

  private static final long serialVersionUID = 7253471705112537592L;
  private final HttpStatus status;
  private final String code;

  /**
   * General Ctor.
   * @param error a given {@link AppErrorType}
   */
  public AppException(final AppErrorType error) {
    super(error.getMessage());
    this.code = error.name();
    this.status = error.getStatus();
  }

  
  /**
   * General Ctor.
   * @param code Http Status code
   * @param status Http status
   * @param pairs Array of string messages
   */
  public AppException(final String code, final HttpStatus status, String... pairs) {
    super(pairs.length == 0 ? status.getReasonPhrase() : flatten(pairs));
    this.code = code;
    this.status = status;
  }

  /**
   * General Ctor.
   * @param error a given {@link AppErrorType}
   * @param pairs Array of string messages
   */
  public AppException(final AppErrorType error, String... pairs) {
    super(pairs.length == 0 ? error.getStatus().getReasonPhrase() : flatten(pairs));
    this.code = error.name();
    this.status = error.getStatus();
  }

  /**
   * General Ctor.
   * @param status Http status
   * @param pairs Array of string messages
   */
  public AppException(final HttpStatus status, String... pairs) {
    super(pairs.length == 0 ? status.getReasonPhrase() : flatten(pairs));
    this.code = status.name();
    this.status = status;
  }

  public String code() {
    return code;
  }

  public HttpStatus status() {
    return status;
  }

  private static String flatten(final String... pairs) {
    final StringBuilder builder = new StringBuilder();
    int i = 0;
    for (final String item : pairs) {
      if (i % 2 == 0) {
        builder.append(" ").append(item);
      } else {
        builder.append("=").append(item);
      }
      i++;
    }
    return builder.toString().trim();
  }

}
