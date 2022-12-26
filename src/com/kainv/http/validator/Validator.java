package com.kainv.http.validator;

/**
 * <h1>HTTP. Servlets. 46. Практика. Часть 2</h1>
 * <h2>Интерфейс, который принимает сущность в зависимости от того, что он будет валидировать</h2>
 */
public interface Validator<T> {
    ValidationResult isValid(T object);
}
