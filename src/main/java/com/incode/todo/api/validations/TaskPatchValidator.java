package com.incode.todo.api.validations;

import com.incode.todo.models.TaskPatch;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

@Component
public class TaskPatchValidator implements Validator {
    private final String REQUIRED_VALIDATION = "field.required";

    @Override
    public boolean supports(Class<?> clazz) {
        return TaskPatch.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "title", REQUIRED_VALIDATION);
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", REQUIRED_VALIDATION);
    }
}
