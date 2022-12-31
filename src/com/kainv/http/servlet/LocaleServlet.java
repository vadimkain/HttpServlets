package com.kainv.http.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static com.kainv.http.util.UrlPath.*;

/**
 * <h1>HTTP. Servlets. 55. Интернационализация и локализация. JSP</h1>
 * <h2>Сервлет, в котором устанавливаем локаль пользователю</h2>
 * <p>Получаем, какой язык выбрал пользователь:</p>
 * <pre>{@code String lang = req.getParameter("lang");}</pre>
 * <p>
 * Далее, устанавливаем в сессию. На публичных страницах для не авторизированных пользователей сессии не будет, тогда
 * в данном случае сработает возможность получения локали для нашего пользователя по header. Либо можем просто через
 * параметры передать наш язык, который установили, либо можем устанавливать и в сессию и в параметры в качестве
 * response и уже на клиенте определять, что из чего брать.
 * </p>
 * <pre>{@code req.getSession().setAttribute("lang", lang);}</pre>
 * <p>И перенаправляем пользователя туда, откуда пришёл. Если неизвестно, то на страницу логина:</p>
 * <pre>{@code
 *         String referer = req.getHeader("referer");
 *         String page = referer != null ? referer : LOGIN;
 *         resp.sendRedirect(page + "?lang=" + lang);
 * }</pre>
 */
@WebServlet("/locale")
public class LocaleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String lang = req.getParameter("lang");
        req.getSession().setAttribute("lang", lang);

        String referer = req.getHeader("referer");
        String page = referer != null ? referer : LOGIN;
        resp.sendRedirect(page + "?lang=" + lang);
    }
}
