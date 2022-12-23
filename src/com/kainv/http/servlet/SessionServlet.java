package com.kainv.http.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

/**
 * <h1>HTTP. Servlets. 36. Session</h1>
 * <p>
 * Так как каждый запрос знает про сессию, к которой он относится, поэтому у request есть метод {@code .getSession()}
 * и здесь есть два параметра:
 * </p>
 * <ol>
 *     <li>
 *         Не принимает никакого параметра, но если зайти в исходники, то просто перенаправляет запрос на перегруженный
 *         метод с {@code boolean} параметром и устанавливает {@code true}. Этот параметр означает - создавать
 *         сессию если её не оказалось в нашем ассоциативном массиве, или нет. По умолчанию true, т.е. создаём.
 *     </li>
 *     <li>
 *         {@code boolean b} - тоже самое, что и первый аргумент.
 *     </li>
 * </ol>
 * <p>Получаем сессию и получаем объект типа {@code HttpSession}:</p>
 * <pre>{@code HttpSession session = req.getSession();}</pre>
 * <p>
 *     У сессий есть много методов (получение идентификатора, время когда создали, работа с атрибутами). В основном,
 *     работа с сессиями заключается в установке и обработке атрибутов (потом будем ложить объект типа User, чтобы
 *     ассоциировать её с юзером, который лежит в нашей базе данных). Можем получить время, когда не использовалась,
 *     получить сервлет контекст (глобальный объект на всём приложении, он один единственный и из любого места можем
 *     до него достучаться). Уничтожить сессию (убрать из нашего ассоциативного массива). Проверить, была ли вновь
 *     создана эта сессия. Удалить атрибуты, установить атрибуты и т.д. <b>Смотреть документацию и гугл</b>.
 * </p>
 * <p>Проверим, была ли вновь создана сессия:</p>
 * <pre>{@code System.out.println(session.isNew());}</pre>
 */
@WebServlet("/sessions")
public class SessionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        System.out.println(session.isNew());
    }
}
