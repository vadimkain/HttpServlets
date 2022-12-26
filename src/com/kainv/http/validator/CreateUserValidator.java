package com.kainv.http.validator;

import com.kainv.http.dto.CreateUserDto;
import com.kainv.http.entity.Gender;
import com.kainv.http.util.LocalDateFormatter;

/**
 * <h1>HTTP. Servlets. 46. Практика. Часть 2</h1>
 * <h2>Класс, в котором валидируем введённые пользователем данные во время регистрации.</h2>
 */
public class CreateUserValidator implements Validator<CreateUserDto> {

    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    private CreateUserValidator() {
    }

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }

    /**
     * <p>
     * Валидируем Gender. Enums valueOf() бросает исключение, если нет соответствия, поэтому лучше реализовать
     * свой null-safe метод find и использовать его (21я минута).
     * </p>
     * <pre>{@code
     *         if (Gender.valueOf(object.getGender()) == null) {
     *             validationResult.add(Error.of("invalid.gender", "Gender is invalid"));
     *         }
     * }</pre>
     *
     * @param object
     * @return
     */
    @Override
    public ValidationResult isValid(CreateUserDto object) {
        ValidationResult validationResult = new ValidationResult();

        // Если значение ДР невалижное, то тогда добавляем ошибку. Тут тоже есть говнокод (смотреть класс)
        if (!LocalDateFormatter.isValid(object.getBirthday())) {
            validationResult.add(Error.of("invalid.birthday", "Birthday is invalid"));
        }
        // Это говнокод. Смотреть описание над методом!!!
        if (object.getGender() == null || Gender.valueOf(object.getGender()) == null) {
            validationResult.add(Error.of("invalid.gender", "Gender is invalid"));
        }

        return validationResult;
    }
}
