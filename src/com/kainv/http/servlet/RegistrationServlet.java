package com.kainv.http.servlet;

import com.kainv.http.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

/**
 * <h1>HTTP. Servlets. 45. HTML Forms</h1>
 */
@WebServlet("/registration")
public class RegistrationServlet extends HttpServlet {
    /**
     * <p>Устанавливаем атрибуты для динамического отображения полей в фронт енд</p>
     * <pre>{@code
     *         // Подобное лучше реализовывать не через List.of(), а через Enum
     *         req.setAttribute("roles", List.of("USER", "ADMIN"));
     *         req.setAttribute("gender", List.of("MALE", "FEMALE"));
     * }</pre>
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Подобное лучше реализовывать не через List.of(), а через Enum
        req.setAttribute("roles", List.of("USER", "ADMIN"));
        req.setAttribute("genders", List.of("MALE", "FEMALE"));

        req.getRequestDispatcher(JspHelper.getPath("registration")).forward(req, resp);
    }

    /**
     * <p>Получаем параметры по ключу:</p>
     * <pre>{@code String name = req.getParameter("name");}</pre>
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String name = req.getParameter("name");
    }
}
