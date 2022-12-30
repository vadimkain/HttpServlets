package com.kainv.http.entity;

import java.util.Arrays;
import java.util.Optional;

public enum Role {
    USER, ADMIN;

    // Находим роль по её названию. Используем для необязательных полей
    public static Optional<Role> find(String role) {
        return Arrays.stream(values())
                .filter(it -> it.name().equals(role))
                .findFirst();
    }
}
