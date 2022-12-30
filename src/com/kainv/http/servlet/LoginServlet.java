package com.kainv.http.servlet;

import com.kainv.http.dto.UserDto;
import com.kainv.http.service.UserService;
import com.kainv.http.util.JspHelper;
import com.kainv.http.util.UrlPath;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * <h1>HTTP. Servlets. 51. Authentication. Аутентификация</h1>
 */
@WebServlet(UrlPath.LOGIN)
public class LoginServlet extends HttpServlet {
    private final UserService userService = UserService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("login")).forward(req, resp);
    }

    /**
     * <p>
     * Если пользователь залогинен, т.е. у нас будет два действия: на успешный логин добавляем в сессию т.е. наш
     * пользователь залогинен и должен быть в {@code scopeSessionRequest} чтобы в последующем могли получать из
     * любого сервлета. В противном случае, перенаправляем назад на логин страничку и говорим, что ввели некорректные
     * данные.
     * </p>
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Получаем email и пароль
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        // Получаем пользователя из базы данных
        userService.login(email, password)
                // Если такой пользователь есть
                .ifPresentOrElse(
                        userDto -> onLoginSucces(userDto, req, resp),
                        // В противном случае
                        () -> onLoginFail(req, resp)
                );

    }

    private void onLoginFail(HttpServletRequest req, HttpServletResponse resp) {
        try {
            // Устанавливаем ещё параметр email чтобы юзеру не пришлось повторно вводить данные
            resp.sendRedirect("login?error&email=" + req.getParameter("email"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void onLoginSucces(UserDto userDto, HttpServletRequest req, HttpServletResponse resp) {
        // Устанавливаем пользователя в сессию
        req.getSession().setAttribute("user", userDto);
        // Перенаправляем запрос на страницу с перелётами
        try {
            resp.sendRedirect("/flights");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
