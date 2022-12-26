package com.kainv.http.servlet;

import com.kainv.http.dto.CreateUserDto;
import com.kainv.http.entity.Gender;
import com.kainv.http.entity.Role;
import com.kainv.http.exception.ValidationException;
import com.kainv.http.service.UserService;
import com.kainv.http.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


/**
 * <h1>HTTP. Servlets. 46. Практика. Часть 2</h1>
 * <h2>Реализуем механизм сохранения пользователя, которого передадим в качестве параметров из jsp страницы registration.</h2>
 * <p>
 * Другими словами говоря, мы те данные, которые передали из jsp страницы и приняли на сервлете - должны красиво
 * преобразовать в соответствующее dto, передать его на уровень сервисов и из уровня сервисов должны сделать
 * что-то вроде валидации dto, преобразовать все в сущность entity и передать на слой DAO, который просто сохранит
 * в базу данных. Вернуть response, что успешно сохранили или нет назад на сервис, сервис на сервлет и сервлет уже
 * при успешном сохранении, например сделает {@code .sendRedirect} на страницу login для того чтобы пользоватлеь
 * мог провести логин в систему по только что зарегистрированому пользователю.
 * </p>
 */
@WebServlet("/registration")
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
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        CreateUserDto userDto = CreateUserDto.builder()
                .name(req.getParameter("name"))
                .birthday(req.getParameter("birthday"))
                .email(req.getParameter("email"))
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
