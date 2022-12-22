package com.kainv.http.dto;

import lombok.Builder;
import lombok.Value;

/**
 * <h2>Устанавливаем lombok аннотации</h2>
 * <p>
 *     Для часто встречаемых аннотаций как в FlightDto есть аннотация {@code @Data}, Суть её в том, что она
 *     генерирует сразу же и геттеры, и сеттеры, и т.д. Так же можем добавить аннотацию {@code @Builder}, который
 *     генерирует паттерн проектирования билдер для сущности.
 * </p>
 * <p>
 *     Так же можем добавить аннотацию {@code @Value}, если нам нужны private final поля. (т.е. в коде можем их
 *     не писать)
 * </p>
 */
@Value
@Builder
public class TicketDto {
    Long id;
    Long flightId;
    String seatNo;
}
