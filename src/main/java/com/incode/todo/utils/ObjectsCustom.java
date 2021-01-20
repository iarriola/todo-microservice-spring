package com.incode.todo.utils;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.util.ObjectUtils;

public class ObjectsCustom {
  public static <T> Optional<T> applyObjectChange(T value, T defaultValue) {
    return Stream.of(value, defaultValue)
        .filter(Objects::nonNull)
        .filter(ObjectsCustom::nonEmpty)
        .findFirst();
  }

  private static boolean nonEmpty(Object object) {
    if (object instanceof String) {
      return !ObjectUtils.isEmpty(((String) object).trim());
    } else {
      return true;
    }
  }
}
