package com.incode.todo.utils;

import java.util.function.Function;

import com.incode.todo.api.validations.TaskRequestValidator;
import com.incode.todo.models.AppErrorType;
import com.incode.todo.models.AppException;
import com.incode.todo.models.TaskRequest;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Component
@RequiredArgsConstructor
public class ValidatorUtils {

    private final TaskRequestValidator postValidator;

    private final Integer FIRST_INDEX = 0;

    private <T> Validator getValidator(T model) {

      if(model instanceof TaskRequest) {
        return this.postValidator;
      }

      return null;
    }

    public <T> Mono<T> validate(T model) {

      Validator validator = getValidator(model);

      if(validator == null) {
          throw new AppException(
              AppErrorType.BAD_REQUEST
          );
      }

      Errors errors = new BeanPropertyBindingResult(
          model,
          model.getClass().getName()
      );

      validator.validate(model, errors);

      if(errors == null || errors.getAllErrors().isEmpty()) {
          return Mono.just(model);
      } else {
        throw new AppException(
            HttpStatus.BAD_REQUEST,
            defaultMessage.apply(errors)
        );
      }

  }

  private Function<Errors, String> defaultMessage = errors -> {
    return errors.getAllErrors().get(FIRST_INDEX).getDefaultMessage();
  };

}
