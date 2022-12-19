package com.kainv.http.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;

/**
 * <h1>HTTP. Servlets. 29. Параметры запроса. Postman</h1>
 * Специальный заголовок, header, который мы дополнительно передаём на сервер - это MIME тип:
 * <i>x-www-form-urlencoded</i> и как раз таки этот заголовок позволяет передавать дополнительно параметры в теле
 * запроса.
 */
@WebServlet("/first")
public class FirstServlet extends HttpServlet {
    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init();
    }

    /**
     * <h2>Возвращаем браузеру HTML страничку с текстом</h2>
     * <p>
     * Т.к. это HTML текст, мы знаем, что в response нам нужно установить этот текст. Более того, так как мы хотим
     * вернуть HTML страницу, следовательно мы должны установить несколько header'ов:
     * <pre>{@code resp.setContentType("text/html");}</pre>
     * Далее, записываем выходной поток нашего HTML:
     * <pre>{@code
     * try (PrintWriter writer = resp.getWriter()) {
     *      writer.write("<h1>Hello from first servlet</h1>");
     * }
     * }</pre>
     * </p>
     * <p>
     * По мимо этого так же можем работать с заголовками, например, получим user-agent (header, который позволяет
     * получать информацию о том, откуда пришёл запрос, т.е., с какого устройства):
     * <pre>{@code req.getHeader("user-agent")}</pre>
     * Так же можем получить все заголовки, который получает {@code Enumeration<String>} (что-то вроде итератора),
     * по которому можем итерироваться через цикл:
     * <pre>{@code
     *         // Получаем все заголовки
     *         Enumeration<String> headerNames = req.getHeaderNames();
     *         while (headerNames.hasMoreElements()) {
     *             String header = headerNames.nextElement();
     *             System.out.println(req.getHeader(header));
     *         }
     * }</pre>
     * </p>
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Получаем header по ключу
        req.getHeader("user-agent");
        // Получаем все заголовки
        Enumeration<String> headerNames = req.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String header = headerNames.nextElement();
            System.out.println(req.getHeader(header));
        }

        resp.setContentType("text/html");
        resp.setHeader("token", "12345");
        try (PrintWriter writer = resp.getWriter()) {
            writer.write("<h1>Привет из first сервлета!</h1>");
        }
        super.service(req, resp);
    }

    /**
     * <h2>Метод, в котором получаем параметры из тела запроса от пользователя</h2>
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Получаем ключи и значения параметров от запроса
        Map<String, String[]> parameterMap = req.getParameterMap();
        System.out.println(parameterMap);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
