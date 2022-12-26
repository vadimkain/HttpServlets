package com.kainv.http.mapper;

import com.kainv.http.dto.CreateUserDto;
import com.kainv.http.entity.Gender;
import com.kainv.http.entity.Role;
import com.kainv.http.entity.User;
import com.kainv.http.util.LocalDateFormatter;

/**
 * <h1>HTTP. Servlets. 46. Практика. Часть 2</h1>
 * <h2>Реализовываем интерфейс {@code Mapper}, который будет преобразовывать данные при создании пользователя из dto
 * в сущность entity</h2>
 */
public class CreateUserMapper implements Mapper<CreateUserDto, User> {
    @Override
    public User mapFrom(CreateUserDto object) {
        return User.builder()
                .name(object.getName())
                .birthday(LocalDateFormatter.format(object.getBirthday()))
                .email(object.getEmail())
                .password(object.getPassword())
                .role(Role.valueOf(object.getRole()))
                .gender(Gender.valueOf(object.getGender()))
                .build();
    }

    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    private CreateUserMapper() {
    }

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }
}
