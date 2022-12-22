package com.kainv.http.dto;

import lombok.*;

/**
 * <h2>Применяем lombok аннотации</h2>
 * <p>Все аннотации видны только в исходном коде, после компиляции они нам не нужны будут, потому что
 * ломбок все нам сгенерирует.</p>
 * <p>
 * Генерируем конструктор:
 * <pre>{@code @AllArgsConstructor}</pre>
 * </p>
 * <p>
 * Генерируем геттеры:
 * <pre>{@code @Getter}</pre>
 * </p>
 * <p>
 * Генерируем equals & hashcode:
 * <pre>{@code @EqualsAndHashCode}</pre>
 * </p>
 * <p>
 * Генерируем toString():
 * <pre>{@code @ToString}</pre>
 * </p>
 */
@Value
@Builder
public class FlightDto {
    Long id;
    String description;
}
