package com.kainv.http.dto;

import jakarta.servlet.http.Part;
import lombok.Builder;
import lombok.Value;


/**
 * <h2>Dto для процесса создания пользователя.</h2>
 * <p>Преобразовываем dto в entity.</p>
 * <p>
 * Нам необходимы все поля, которые будем получать из jsp. Почти все они будут в виде {@code String} потому что все
 * параметры из запроса приходят в виде пар ключ-значение, где оба являются строками.
 * </p>
 */
@Value
@Builder
public class CreateUserDto {
    String name;
    String birthday;
    String email;
    /**
     * <p>{@code Part} потому что от пользователя получаем объект типа {@code Part}, а не {@code String}</p>
     */
    Part image;
    String password;
    String role;
    String gender;
}
