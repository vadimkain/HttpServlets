package com.kainv.http.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * <h1>HTTP. Servlets. 35. Cookies</h1>
 * <h2>Задача: считать уникальное посещение сайта с помощью cookie.</h2>
 * <p>Т.е. будем проверять, есть ли у нас в куках, которые пришли с клиента, какое-то определённое значение.
 * Если есть, то счётчик не увеличиваем, в противном случае увеличиваем.</p>
 */
@WebServlet("/cookies")
public class CookieServlet extends HttpServlet {
    public static final String UNIQUE_ID = "userId";

    /**
     * Используем потокобезопасный класс, т.к. это будет общее поле для всех запросов, которые приходят в
     * {@code .doGet()}, а куки сервлет это синглтон, следовательно, мы должны использовать только потокобезопасные
     * классы, коллекции и т.д..
     */
    private static final AtomicInteger counter = new AtomicInteger();

    /**
     * <p>
     * Для работы с куки из запроса у нас есть специальный метод {@code .getCookies}. Возвращает массив куки.
     * <pre>{@code Cookie[] cookies = req.getCookies();}</pre>
     * Он может быть null, если не пришло ни одно значение куки. Поэтому, надо написать код, который null-safe и
     * проверяет значение {@code UNIQUE_ID}, для того чтобы определить, был ли пользователь на сайте
     * или нет.
     * </p>
     * <p>
     * <i>Если массив куки пуст или находим куки по названию и проверяем, что такого куки нет</i>,
     * тогда этот пользователь у нас впервые, поэтому должны создать куку, инкриминировать счётчик уникальных
     * пользователей и отправить клиенту.
     * <pre>{@code
     * if (cookies == null || Arrays.stream(cookies)
     *      .filter(cookie -> UNIQUE_ID.equals(cookie.getName()))
     *      .findFirst()
     *      .isEmpty()
     * )
     * }</pre>
     * Создаём куки:
     * <pre>{@code Cookie cookie = new Cookie(UNIQUE_ID, "1");}</pre>
     * После создания куки можно вызвать куча сет-методов. Ставим куки: ставим, чтобы куки шли не только по домену
     * сервера, на котором отправили запрос, но и чтобы её видели только если идём на наш сервлет /cookies:
     * <pre>{@code cookie.setPath("/cookies");}</pre>
     * Устанавливаем время жизни куки. Дефолтное значение: <b>-1</b>, что означает, что куки будет жить, пока браузер
     * не закроется. Сделаем время жизни куки 15 минут:
     * <pre>{@code cookie.setMaxAge(15 * 60);}</pre>
     * Так же можем поставить {@code .setHttpOnly()} если не хотим иметь доступ к куки со стороны JS.
     * {@code .setSecure()}, если только по HTTPS отправлять куки (по HTTP не будут присоединятся).
     * </p>
     * <br>
     * <p>
     * Добавляем в response эту куки:
     * <pre>{@code resp.addCookie(cookie);}</pre>
     * Объект типа response преобразует куки в хеадер в соответствии с правилами отправки куки.
     * </p>
     * <p>
     * Инкриминируем потокобезопасным классом переменную:
     * <pre>{@code counter.incrementAndGet();}</pre>
     * </p>
     * <p>
     * Добавляем заголовок и выводим клиенту число:
     * <pre>{@code }</pre>
     * </p>
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cookie[] cookies = req.getCookies();

        if (cookies == null || Arrays.stream(cookies)
                .filter(cookie -> UNIQUE_ID.equals(cookie.getName()))
                .findFirst()
                .isEmpty()
        ) {
            Cookie cookie = new Cookie(UNIQUE_ID, "1");

            cookie.setPath("/cookies");
            cookie.setMaxAge(15 * 60);

            resp.addCookie(cookie);

            // Инкриминируем
            counter.incrementAndGet();

            resp.setContentType("text/html");
            try (PrintWriter writer = resp.getWriter()) {
                writer.write(counter.get());
            }
        }
    }
}