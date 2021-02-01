package com.incode.todo.utils;

import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.incode.todo.api.validations.TaskPatchValidator;
import com.incode.todo.api.validations.TaskPostValidator;
import com.incode.todo.models.AppErrorType;
import com.incode.todo.models.AppException;
import com.incode.todo.models.TaskPatch;
import com.incode.todo.models.TaskPost;

import org.apache.logging.log4j.util.Strings;
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

    private final TaskPostValidator postValidator;

    private final TaskPatchValidator patchValidator;

    private final String PATTERN_FIELD_NAME = "(\\.\\w+$)";
    private final String PATTERN_RULE = "(\\.\\w+\\.)";
    private final Integer FIRST_GROUP = 1;
    private final Integer SECOND_GROUP = 0;
    private final Integer FIRST_INDEX = 0;
    private final String REPLACE_CHAR = ".";

    private <T> Validator getValidator(T model) {

      if(model instanceof TaskPost) {
        return this.postValidator;
      }

      if(model instanceof TaskPatch) {
        return this.patchValidator;
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
            formatMessage(errors)
        );
      }

  }

  private String formatMessage(Errors errors) {
    String input = errors.getAllErrors().get(FIRST_INDEX).getCodes()[FIRST_INDEX];

    String output = String.format(
      "%s is %s",
      this.match.apply(FIRST_GROUP, PATTERN_FIELD_NAME, input),
      this.match.apply(SECOND_GROUP, PATTERN_RULE, input)
    );

    return output.substring(0, 1).toUpperCase() + output.substring(1);
  }

  private TriFunction<Integer, String, String, String> match = (group, pattern, input) -> {
    return this.extract.apply(group, Pattern.compile(pattern).matcher(input));
  };
  
  private BiFunction<Integer, Matcher, String> extract = (group, matcher) -> {
    return  matcher.find() ? matcher.group(group).replace(REPLACE_CHAR, Strings.EMPTY) : Strings.EMPTY;
  };

}
