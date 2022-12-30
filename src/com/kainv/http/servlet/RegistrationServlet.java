package com.kainv.http.servlet;

import com.kainv.http.dto.CreateUserDto;
import com.kainv.http.entity.Gender;
import com.kainv.http.entity.Role;
import com.kainv.http.exception.ValidationException;
import com.kainv.http.service.UserService;
import com.kainv.http.util.JspHelper;
import com.kainv.http.util.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;

import java.io.IOException;


/**
 * <h2>Реализуем механизм сохранения пользователя, которого передадим в качестве параметров из jsp страницы registration.</h2>
 * <p>
 * Другими словами говоря, мы те данные, которые передали из jsp страницы и приняли на сервлете - должны красиво
 * преобразовать в соответствующее dto, передать его на уровень сервисов и из уровня сервисов должны сделать
 * что-то вроде валидации dto, преобразовать все в сущность entity и передать на слой DAO, который просто сохранит
 * в базу данных. Вернуть response, что успешно сохранили или нет назад на сервис, сервис на сервлет и сервлет уже
 * при успешном сохранении, например сделает {@code .sendRedirect} на страницу login для того чтобы пользоватлеь
 * мог провести логин в систему по только что зарегистрированому пользователю.
 * </p>
 * <h1>HTTP. Servlets. 49. Servlet Filters. Часть 1</h1>
 * <p>
 * Переопределяем название сервлета (по умолчанию: пакет.название) чтобы сократить название для наших фильтров.
 * </p>
 * <pre>{@code @WebServlet(value = "/registration", name = "RegistrationServlet")}</pre>
 */
@WebServlet(value = UrlPath.REGISTRATION, name = "RegistrationServlet")
@MultipartConfig(fileSizeThreshold = 1024 * 1024)
public class RegistrationServlet extends HttpServlet {

    private final UserService userService = UserService.getInstance();

    /**
     * <p>Устанавливаем атрибуты для динамического отображения полей в фронт енд</p>
     * <pre>{@code
     *         req.setAttribute("roles", Role.values());
     *         req.setAttribute("genders", Gender.values());
     * }</pre>
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("roles", Role.values());
        req.setAttribute("genders", Gender.values());

        req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
    }

    /**
     * <p>Извлекаем все необходимые данные:</p>
     * <pre>{@code
     *         CreateUserDto userDto = CreateUserDto.builder()
     *                 .name(req.getParameter("name"))
     *                 .birthday(req.getParameter("birthday"))
     *                 .email(req.getParameter("email"))
     *                 .password(req.getParameter("password"))
     *                 .role(req.getParameter("role"))
     *                 .gender(req.getParameter("gender"))
     *                 .build();
     * }</pre>
     * <p>Передаём на уровень сервиса, где создаём пользователя</p>
     * <pre>{@code userService.create(userDto);}</pre>
     * <p>После создания пользователя перенаправляем запрос на login страницу</p>
     * <pre>{@code resp.sendRedirect("/login")}</pre>
     * <p>Обрабатываем исключение, если что-то пошло не так:</p>
     * <pre>{@code
     *         try {
     *             userService.create(userDto);
     *             resp.sendRedirect("/login");
     *         } catch (ValidationException e) {
     *             // Если ловим ошибку, то заново перенаправляем на страничку и информируем его, передавая ошибки
     *             req.setAttribute("errors", e.getErrors());
     *             doGet(req, resp);
     *         }
     * }</pre>
     * <h1>HTTP. Servlets. 47. File upload. Multipart form-data</h1>
     * <p>
     * Здесь нам надо получить значение файла, который передаёт пользователь. Но опять же. Получение параметров
     * происходит через метод {@code .getParameter()} и получаем строку. Т.е. и ключ используем в качестве строки,
     * и значение в качестве строки, но в данном случае нас это не устраивает, потому то передаём какой-то бинарный
     * файл. Т.е. массив байт. Следовательно, для этого мы должны использовать другой вариант - это {@code .getPart()}.
     * Т.е. именно часть (part). И здесь задаём параметр <i>image</i> как в input. И таким образом мы получим уже
     * другой объект типа {@code Part} и в этом объекте уже можем получить название файла, которое отправил клиент,
     * или целый {@code InputStream} (тот поток байт, который ожидаем получить) и уже с этим потоком можем получить
     * всё что угодно. Можем получить {@code ContentType} этого файла и делать всё, что захотим.
     * </p>
     * <pre>{@code Part image = req.getPart("image");}</pre>
     * <p>
     * Этого тоже недостаточно потому что по умолчанию сервлеты не могут работать с {@code Part}'ами. Для этого
     * нужно использовать ещё одну аннотацию над классом:
     * </p>
     * <ul>
     *     <li>
     *         Аргумент {@code String location()} говорит путь на сервере, где будут сохранятся эти Multipart файлы,
     *         которые передаём из нашего клиента.
     *     </li>
     *     <li>
     *         {@code long maxFileSize()} - максимальный размер нашего файла.
     *     </li>
     *     <li>
     *         {@code long maxRequestSize()} - максимальный размер всего HTTP запроса.
     *     </li>
     *     <li>
     *         {@code int fileSizeThreshold()} - позволяет не сохранять на жесткий диск в папку location, а держать его
     *         в in-memory. Задаём значения в байтах. Только файлы больше этого Threshold будут сохранятся на диск в
     *         location.
     *     </li>
     * </ul>
     * <pre>{@code @MultipartConfig(fileSizeThreshold = 1024 * 1024)}</pre>
     * <p>
     *     Мы хотим как-то сохранять картинку, поэтому необходимо добавить dto и ещё какой-то сервис, который отвечает
     *     за сохранение этой картинки. Начнём с добавления в базы данных поле image.
     * </p>
     * <p>
     *     После редактирования БД, dto и создания {@code ImageService} передаём в {@code CreateUserDto} данные и
     *     переходим на уровень сервисов:
     * </p>
     * <pre>{@code
     *         CreateUserDto userDto = CreateUserDto.builder()
     *                 .name(req.getParameter("name"))
     *                 .birthday(req.getParameter("birthday"))
     *                 .email(req.getParameter("email"))
     *                 .image(image)
     *                 .password(req.getParameter("password"))
     *                 .role(req.getParameter("role"))
     *                 .gender(req.getParameter("gender"))
     *                 .build();
     * }</pre>
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part image = req.getPart("image");

        CreateUserDto userDto = CreateUserDto.builder()
                .name(req.getParameter("name"))
                .birthday(req.getParameter("birthday"))
                .email(req.getParameter("email"))
                .image(image)
                .password(req.getParameter("password"))
                .role(req.getParameter("role"))
                .gender(req.getParameter("gender"))
                .build();

        try {
            userService.create(userDto);
            resp.sendRedirect("/login");
        } catch (ValidationException e) {
            // Если ловим ошибку, то заново перенаправляем на страничку и информируем его, передавая ошибки
            req.setAttribute("errors", e.getErrors());
            doGet(req, resp);
        }
    }
}
