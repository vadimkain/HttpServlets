package com.kainv.http.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * <h1>HTTP. Servlets. 52. Logout. Выход из приложения</h1>
 * <h2>Сервлет, который отвечает за закрытие сессии. Т.е., выход пользователя из учетной записи.</h2>
 * <p>
 *     Метод {@code .invalidate()} удаляет сессию из ассоциативного массива, который хранится на сервере, делает её
 *     невалидной и следовательно. После этого уже не сможем обратиться к этой сессии и придется создавать новую (по сути
 *     осуществлять новый логин в системе).
 * </p>
 * <pre>{@code req.getSession().invalidate();}</pre>
 * <p>Далее перенаправляем на какую-нибудь безопасную страницу:</p>
 * <pre>{@code resp.sendRedirect("/login");}</pre>
 */
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Уничтожаем сессию
        req.getSession().invalidate();

        // Перенаправляем на безопасную страницу
        resp.sendRedirect("/login");
    }
}
