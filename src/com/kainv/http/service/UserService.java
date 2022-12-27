package com.kainv.http.service;

import com.kainv.http.dao.UserDao;
import com.kainv.http.dto.CreateUserDto;
import com.kainv.http.entity.User;
import com.kainv.http.exception.ValidationException;
import com.kainv.http.mapper.CreateUserMapper;
import com.kainv.http.validator.CreateUserValidator;
import com.kainv.http.validator.ValidationResult;

import java.io.IOException;

/**
 * <h2>На уровне сервиса создаём соответствующий метод и передаём {@code userDto}</h2>
 */
public class UserService {

    /**
     * <h2>Метод, в котором возвращаем ID сущности, которую создали</h2>
     * <p>
     * <b>step 1: Validation (/validator/*.java)</b><br><br>
     * <b>Валидируем постувшие данные от пользователя</b>. Т.е., первый этап это валидация. Валидацию можно проводить
     * на уровне сервисов, но так же можно на уровне сервлетов, потому что на уровне сервисов можем к примеру
     * сходить в базу данных проверить, существует ли email или нет, уникальный ли он, можно ли создавать
     * пользовтеля с таким email и прочее. На уровне клиента используется клиентская валидация, но она необязательна.
     * В случае серверной валидации - она обязательно должна быть.
     * </p>
     * <pre>{@code
     *         ValidationResult validationResult = createUserValidator.isValid(userDto);
     *         if (!validationResult.isValid()) {
     *             throw new ValidationException(validationResult.getErrors());
     *         }
     * }</pre>
     * <p>
     * <b>step 2: Map (/mapper/*.java)</b><br><br>
     * После валидации нам <b>необходимо преобразовать {@code userDto} в сущность {@code Entity}</b> потому что
     * в DAO работаем с сущностями.
     * </p>
     * <pre>{@code User userEntity = createUserMapper.mapFrom(userDto);}</pre>
     * <p>
     * <b>step 3: Save</b><br><br>
     * После преобразования можем воспользоваться DAO для того чтобы сохранить преобразованную сущность из
     * предыдущего шага.
     * </p>
     * <pre>{@code userDao.save(userEntity);}</pre>
     * <p>
     * <b>step 4: Return</b><br><br>
     * </p>
     * <pre>{@code return userEntity.getId();}</pre>
     * <p>
     * Важно помнить, что в случае валидации мы можем не дойди до шага Map.
     * </p>
     * <p>
     * Так как нам нужно получить {@code validationResult} на уровне сервлетов, чтобы узнать, что там за ошибки, то
     * по хорошему в реальных приложениях использовали бы что-то вроде объекта Azer(?), где был бы результат, либо
     * второй вариант - ошибки, но для этого нужно создавать объект с результатом закончилось успешно или провален.
     * В этом примере не будем реализовывать эти варианты, а просто пробросим исключения на основа
     * {@code validationResult}.
     * </p>
     * <h1>HTTP. Servlets. 47. File upload. Multipart form-data</h1>
     * <p>
     * Сразу после сохранения {@code userDao.save(userEntity);} по-хорошему необходимо и сохранить сущность
     * пользователя и картинку успешно. В противном случае нужно откатить транзакцию, если что-то из одного не
     * получилсоь сделать. Следовательно, в фреймворках удобно открывать транзакцию и руководить ею на уровне
     * сервисов, но это усложняет логику, поэтому сделаем по-простому. Сначала сохраним картинку. Ничего страшного,
     * если будет висеть без сущности, просто её никто не будет использовать. Гораздо хуже, если будем сохранять
     * картинку после сохранения сущности и у нас это не получится.
     * </p>
     * <pre>{@code
     *         User userEntity = createUserMapper.mapFrom(userDto);
     *
     *         try {
     *             imageService.upload(userEntity.getImage(), userDto.getImage().getInputStream());
     *         } catch (IOException e) {
     *             throw new RuntimeException(e);
     *         }
     *         userDao.save(userEntity);
     * }</pre>
     * <p>После этого нужно поправить {@code createUserMapper.mapFrom(userDto);}</p>
     *
     * @param userDto
     * @return возвращаем id успешно созданной сущности.
     */
    public Integer create(CreateUserDto userDto) {
        ValidationResult validationResult = createUserValidator.isValid(userDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }

        User userEntity = createUserMapper.mapFrom(userDto);

        try {
            imageService.upload(userEntity.getImage(), userDto.getImage().getInputStream());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        userDao.save(userEntity);

        return userEntity.getId();
    }

    // ПОДКЛЮЧАЕМ ЗАВИСИМОСТИ
    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();
    private final CreateUserMapper createUserMapper = CreateUserMapper.getInstance();
    private final UserDao userDao = UserDao.getInstance();
    private final ImageService imageService = ImageService.getInstance();

    private static final UserService INSTANCE = new UserService();

    private UserService() {
    }

    public static UserService getInstance() {
        return INSTANCE;
    }
}
