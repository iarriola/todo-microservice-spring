package com.incode.todo.utils;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.util.ObjectUtils;

public class ObjectsUtils {

  public static <T> Optional<T> applyObjectChange(T value, T defaultValue) {
    return Stream.of(value, defaultValue)
        .filter(Objects::nonNull)
        .filter(ObjectsUtils::nonEmpty)
        .findFirst();
  }

  private static boolean nonEmpty(Object object) {
    if (object instanceof String) {
      return !ObjectUtils.isEmpty(((String) object).trim());
    } else {
      return true;
    }
  }

  public static boolean between(int min, int max, int value) {
    return value >= min && value <= max;
  }

  public static boolean hasContent(Object obj) {

    if(obj == null) {
      return false;
    }

    if(obj instanceof Collection) {
      if(((Collection<?>)obj).isEmpty()) {
        return false;
      }
    }

    return true;

  }

}
