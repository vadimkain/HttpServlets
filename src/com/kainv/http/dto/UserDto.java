package com.kainv.http.dto;

import com.kainv.http.entity.Gender;
import com.kainv.http.entity.Role;
import lombok.Builder;
import lombok.Value;

import java.time.LocalDate;

@Value
@Builder
public class UserDto {
    Integer id;
    String name;
    LocalDate birthday;
    String email;
    String image;
    // Пароль не будем добавлять чтобы не гулял по приложению
    Role role;
    Gender gender;
}
