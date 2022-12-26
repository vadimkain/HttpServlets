package com.kainv.http.util;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * <h1>HTTP. Servlets. 46. Практика. Часть 2</h1>
 * <h2>Утилитный класс Formatter, в котором String преобразовываем в LocalDate</h2>
 */
@UtilityClass
public class LocalDateFormatter {
    public static final String PATTERN = "yyyy-MM-dd";
    public static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern(PATTERN);

    /**
     * <h2>Метод, который преобразовывает строку в дату</h2>
     */
    public LocalDate format(String date) {
        return LocalDate.parse(date, FORMATTER);
    }

    /**
     * <h2>Валидатор паттерна</h2>
     * <p>
     * Метод {@code .parse()} генерирует {@code Exception}, если текст несоответствует форматтору. Это плохо.
     * Поэтому делаем валидатор.
     * </p>
     * <p>
     *     Лучше добавить проверку на null. null-safe isValid method implementation (смотреть 27ю минуту)
     * </p>
     */
    public boolean isValid(String date) {
        try {
            format(date);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
