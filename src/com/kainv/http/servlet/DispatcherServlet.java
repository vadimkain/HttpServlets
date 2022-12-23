package com.kainv.http.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * <h1>HTTP. Servlets. 38. Перенаправление запросов</h1>
 * <p>
 * Получаем объект {@code RequestDispatcher} и сразу передаём URL, куда будем перенаправлять запрос (/flights):
 * </p>
 * <pre>{@code RequestDispatcher requestDispatcher = req.getRequestDispatcher("/flights");}</pre>
 * <p>
 * Далее можем вызвать метод перенаправления, которым нам нужен (forward, include), а так же поставить атрибуты, которые
 * можем использовать в {@code FlightServlet}. Такой функционал часто используется при работе с jsp (это обычные сервлеты).
 * <br>
 * Перенаправим по методу {@code .forward()}:
 * </p>
 * <pre>{@code requestDispatcher.forward(req, resp);}</pre>
 * <p>
 * Так как очень часто повторяется получение этих {@code requestDispatcher}'ов и все что после этого делаем - это
 * вызываем либо {@code .forward()}, либо {@code .include()}, поэтому можно не создавать отдельный объект типа
 * {@code RequestDispatcher}, а сразу после вызова {@code .getRequestDispatcher()} вызывать {@code .forward()}:
 * </p>
 * <pre>{@code req.getRequestDispatcher("/flights").forward(req, resp);}</pre>
 * <h2>Важный момент c forward & include: смотреть precis/commit-38.html</h2>
 */
@WebServlet("/dispatcher")
public class DispatcherServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        // req.getRequestDispatcher("/flights").forward(req, resp);
        req.getRequestDispatcher("/flights").include(req, resp);

        PrintWriter writer = resp.getWriter();
        writer.write("Hello 2");
        System.out.println();*/

        // Редиректим на другой сервлет
        resp.sendRedirect("/flights");
    }
}
