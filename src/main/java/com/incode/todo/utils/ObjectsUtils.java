package com.incode.todo.utils;

import java.util.Collection;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;
import org.springframework.util.ObjectUtils;

public class ObjectsUtils {

  private ObjectsUtils() {}

  /**
   * Applies generic patch operation.
   * @param <T> Any type
   * @param value The actual vale to apply.
   * @param defaultValue Default value.
   * @return Any type.
   */
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

  static boolean hasContent(Object obj) {
    return obj == null || obj instanceof Collection && ((Collection<?>)obj).isEmpty();
  }

}
