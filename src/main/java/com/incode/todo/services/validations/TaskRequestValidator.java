package com.incode.todo.services.validations;

import com.incode.todo.models.TaskRequest;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class TaskRequestValidator implements Validator {

  private static final String REQUIRED_VALIDATION = "field.required";
  private static final String DEFAULT_MESSAGE = "Please provide a valid %s";

  @Override
  public boolean supports(Class<?> clazz) {
    return TaskRequest.class.isAssignableFrom(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {

    if (((TaskRequest) target).title() != null) {
      this.rejectIfEmptyOrWhitespace(errors, DEFAULT_MESSAGE, "title");
    }

    if (((TaskRequest) target).description() != null) {
      this.rejectIfEmptyOrWhitespace(errors, DEFAULT_MESSAGE, "description");
    }

  }

  private void rejectIfEmptyOrWhitespace(Errors errors, final String message, final String field) {
    ValidationUtils.rejectIfEmptyOrWhitespace(errors, field, REQUIRED_VALIDATION,
        String.format(message, field));
  }

}
