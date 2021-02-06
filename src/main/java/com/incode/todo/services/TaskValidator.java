package com.incode.todo.services;

import java.util.Optional;
import java.util.UUID;

import com.incode.todo.services.validations.TaskRequestValidator;
import com.incode.todo.utils.UuidUtils;

import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class TaskValidator {

    private final TaskRequestValidator validator;

    private final Integer FIRST_INDEX = 0;

    public <T> Mono<T> validate(T model) {
      return validateInput(model);
    }

    public Optional<UUID> getUuid(String id) {
      return UuidUtils.getUuid(id);
    }

    public <T> Mono<T> validate(T model, String id) {

      if(!this.getUuid(id).isPresent()) {
        return TaskErrorHandler.emptyObject();
      }

      return validateInput(model);
    }

    public <T> Mono<T> validateInput(T model) {

      Errors errors = new BeanPropertyBindingResult(
          model,
          model.getClass().getName()
      );

      this.validator.validate(model, errors);

      if(errors == null || errors.getAllErrors().isEmpty()) {
          return Mono.just(model);
      } else {
        return TaskErrorHandler.invalidObject(errors.getAllErrors().get(FIRST_INDEX).getDefaultMessage());
      }

  }

}
