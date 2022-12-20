package com.kainv.http.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.stream.Stream;

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
     * <h2>Обрабатываем RAW тип, который получили от пользователя</h2>
     * <p>
     * Для того, чтобы принять тело запроса, будем использовать объект типа request и метод {@code .getReader()}, если
     * необходимо считать всё в качестве символов строк. Если же есть какой-то бинарный тип, это это
     * {@code .getInputStream()}, т.е. мы считываем какой-то файл из нашего браузера.
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (
                BufferedReader reader = req.getReader();
                // Метод для считывания построчно, возвращает объект типа Stream<T>
                Stream<String> lines = reader.lines();
        ) {
            lines.forEach(System.out::println);
        }
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
