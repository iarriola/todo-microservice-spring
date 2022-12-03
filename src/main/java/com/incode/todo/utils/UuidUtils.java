package com.incode.todo.utils;

import java.util.Optional;
import java.util.UUID;

public class UuidUtils {

  private UuidUtils() {}

  /**
   * convers string value to a {@link UUID}.
   * @param uuid String represetation of a {@link UUID} value
   * @return the actuall {@link UUID} value.
   */
  public static Optional<UUID> getUuid(String uuid) {
    try {
      return Optional.ofNullable(UUID.fromString(uuid));
    } catch (Exception e) {
      return Optional.empty();
    }
  }

}
