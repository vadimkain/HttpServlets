package com.kainv.http.validator;

import lombok.Value;

/**
 * <h1>HTTP. Servlets. 46. Практика. Часть 2</h1>
 * <h2>Класс-ошибка, который хранит два поля: ключ и описание ошибки. Необходим для интернационализации.</h2>
 * <p>Создаём при помощи статического метода с названием of:</p>
 * <pre>{@code @Value(staticConstructor = "of")}</pre>
 */
@Value(staticConstructor = "of")
public class Error {
    String code;
    String message;
}
