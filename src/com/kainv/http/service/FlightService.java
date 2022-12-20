package com.kainv.http.service;

import com.kainv.http.dao.FlightDao;
import com.kainv.http.dto.FlightDto;

import java.util.List;

import static java.util.stream.Collectors.*;

/**
 * <h1>Класс, в котором взаимодействуем с таблицей Flight из DAO</h1>
 * <p>
 * По правилу хорошего тона из сервисов мы должны возвращать <b>dto</b>
 * </p>
 * <p>
 * Поэтому создадим {@code FlightDto}, где отобразим только необходимую нам информацию.
 * </p>
 * <p>
 * Создаём метод
 * <pre>{@code public List<FlightDto> findAll()}</pre>
 * , который без параметров возвращает все наши перелёты.
 * Хотя конечно же в реальных проектах так как перелётов может быть много, нам понадобится объект типа фильтр.
 * </p>
 * <p>
 * Всё, что нам осталось сделать - попросить у {@code FlightDao} так же все перелёты:
 * <pre>{@code private final FlightDao flightDao = FlightDao.getInstance();}</pre>
 * Правило хорошего тона говорит - классы, относящиеся к слоям сервисов, дао, сервлетов, они должны быть
 * синглтонами. Они не содержат изменяющихся полей, поэтому потокобезопасны.
 * </p>
 */
public class FlightService {
    private static final FlightService INSTANCE = new FlightService();

    private FlightService() {
    }

    public static FlightService getInstance() {
        return INSTANCE;
    }

    private final FlightDao flightDao = FlightDao.getInstance();

    public List<FlightDto> findAll() {
        // Преобразовываем все сущности в dto
        return flightDao.findAll().stream().map(flight -> new FlightDto(
                                // Передаём ид
                                flight.getId(),
                                // Передаём описание
                                "%s - %s - %s".formatted(
                                        flight.getDeparture_airport_code(),
                                        flight.getArrival_airport_code(),
                                        flight.getStatus())
                        )
                )
                .collect(toList());
    }
}
