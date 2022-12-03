package com.incode.todo.services;

import com.incode.todo.services.validations.TaskRequestValidator;
import com.incode.todo.utils.ErrorsUtil;
import com.incode.todo.utils.UuidUtils;

import java.util.Optional;
import java.util.UUID;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TaskValidator {

  private final TaskRequestValidator validator;

  private static final Integer FIRST_INDEX = 0;

  <T> Mono<T> validate(T model) {
    return validateInput(model);
  }

  <T> Mono<T> validate(T model, String id) {

    if (!this.getUuid(id).isPresent()) {
      return ErrorsUtil.emptyObject();
    }

    return validateInput(model);
  }

  Optional<UUID> getUuid(String id) {
    return UuidUtils.getUuid(id);
  }

  <T> Mono<T> validateInput(T model) {

    Errors errors = new BeanPropertyBindingResult(
        model,
        model.getClass().getName()
    );

    this.validator.validate(model, errors);

    if (errors.getAllErrors().isEmpty()) {
      return Mono.just(model);
    } else {
      return ErrorsUtil.invalidObject(errors.getAllErrors().get(FIRST_INDEX).getDefaultMessage());
    }

  }

}
