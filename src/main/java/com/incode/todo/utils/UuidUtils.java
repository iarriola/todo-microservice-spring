package com.incode.todo.utils;

import java.util.Optional;
import java.util.UUID;

public class UuidUtils {
    public static Optional<UUID> getUuid(String uuid) {
        try {
            return Optional.ofNullable(UUID.fromString(uuid));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
}
