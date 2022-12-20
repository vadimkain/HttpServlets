package com.kainv.http.dto;

import java.util.Objects;

/**
 * <h1>DTO класс</h1>
 * <p>
 *     В таком шаблоне проектирования, как dto есть просто объекты с набором полей и геттерами с сеттерами к нему.
 *     Больше ничего. Т.е. они служат только для передачи данных из одного слоя на другой.
 * </p>
 * <p>
 *     В нашем {@code FlightDto} нас будет интересовать только пару полей: <i>id</i> & <i>description</i> нашего
 *     перелёта и больше ничего. В нём будем указывать к примеру откуда и куда летит самолёт и например его статус.
 * </p>
 */
public class FlightDto {
    private final Long id;

    private final String description;

    public FlightDto(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "FlightDto{" +
               "id=" + id +
               ", description='" + description + '\'' +
               '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FlightDto flightDto = (FlightDto) o;
        return Objects.equals(id, flightDto.id) && Objects.equals(description, flightDto.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, description);
    }
}
