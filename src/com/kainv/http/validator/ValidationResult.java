package com.kainv.http.validator;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

/**
 * <h1>HTTP. Servlets. 46. Практика. Часть 2</h1>
 * <h2>
 * Объект, в котором храним список ошибок, если они есть.
 * </h2>
 * <p>Получаем список ошибок и создаём метод получения их:</p>
 * <pre>{@code
 *     @Getter
 *     private final List<Error> errors = new ArrayList<>();
 * }</pre>
 * <p>Метод по добавлению соответствующей ошибки в список, если её нашли:</p>
 * <pre>{@code
 * public void add(Error error) {
 *         this.errors.add(error);
 *     }
 * }</pre>
 * <p>Метод, определяющий валидность результата (если список ошибок пустой, то результат валидный):</p>
 * <pre>{@code
 *     public boolean isValid() {
 *         return errors.isEmpty();
 *     }
 * }</pre>
 */
public class ValidationResult {

    @Getter
    private final List<Error> errors = new ArrayList<>();

    public void add(Error error) {
        this.errors.add(error);
    }

    public boolean isValid() {
        return errors.isEmpty();
    }
}
