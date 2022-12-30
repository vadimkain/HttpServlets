package com.kainv.http.servlet;

import com.kainv.http.dto.UserDto;
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
 * <h1>HTTP. Servlets. 37. Attributes</h1>
 * <p>
 *     Проверим, есть ли у сессии наш атрибут {@code public static final String USER = "user";}. Если он там есть, то
 *     мы уже установили в эту сессию пользователя, т.е. он уже пришел не в первые.
 * </p>
 * <pre>{@code UserDto user = (UserDto) session.getAttribute(USER);}</pre>
 * <p>
 *     Если {@code user == null}, значит это первый запрос от пользователя и только теперь можем создать этого
 *     пользователя и положить его в сессию:
 * </p>
 * <pre>{@code
 *         if (user == null) {
 *             // Создаём пользователя, обычно создаётся один раз на странице login
 *             user = UserDto.builder()
 *                     .id(25L)
 *                     .mail("kainv@gmail.com")
 *                     .build();
 *
 *             // Ложим созданного юзера в сессию
 *             session.setAttribute(USER, user);
 *         }
 * }</pre>
 */
@WebServlet("/sessions")
public class SessionServlet extends HttpServlet {
    public static final String USER = "user";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();

        UserDto user = (UserDto) session.getAttribute(USER);

        if (user == null) {
            // Создаём пользователя, обычно создаётся один раз на странице login
            user = UserDto.builder()
                    .id(25)
                    .email("kainv@gmail.com")
                    .build();

            // Ложим созданного юзера в сессию
            session.setAttribute(USER, user);
        }

        System.out.println(session.isNew());
    }
}
