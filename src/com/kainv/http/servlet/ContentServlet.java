package com.kainv.http.servlet;

import com.kainv.http.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * <h1>HTTP. Servlets. 41. JSP Директивы</h1>
 * <p>Сервлет, который проксирует запрос на страницу content в WEB-INF.</p>
 * <p>Т.к. jsp страница находится в WEB-INF, кроме как других сервлетов, к ней никто не может достучаться.</p>
 * <p>Так как jsp это тоже сервлет, проксируем на страницу content.jsp. Для этого должны указать полный путь к ней
 * (начиная от директории web):</p>
 * <pre>{@code req.getRequestDispatcher("WEB-INF/jsp/content.jsp").forward(req, resp);}</pre>
 */
@WebServlet("/content")
public class ContentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(JspHelper.getPath("content")).forward(req, resp);
    }
}
