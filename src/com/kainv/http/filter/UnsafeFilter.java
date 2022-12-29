package com.kainv.http.filter;

import com.kainv.http.dto.UserDto;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

/**
 * <h1>HTTP. Servlets. 50. Servlet Filters. Часть 2</h1>
 * <h2>
 * Фильтр, который проверяет определенный URL PATH и если пользователя нет в системе (не залогинен), нет объекта
 * USER в сессии, то мы его перенаправляем на безопасную страницу.
 * </h2>
 * <p>Попрактикуемся перенаправлением запросов в фильтрах.</p>
 * <p>
 * Так как будем проверять path на unsafe, т.е. проверяем тот URI, который небезопасен для пользователя. Таким
 * образом мы можем проверять админ страницы. Т.е. есть какой-то функционал только для администраторов пользователя.
 * Следовательно для этой страницы проверить у пользователя его роль.
 * </p>
 * <pre>{@code @WebFilter("/admin")}</pre>
 * <p>
 * Для проверки на роль из request возьмём сессию. Так как {@code ServletRequest} это базовый класс над
 * {@code HttpServletRequest} - делаем нисходящее преобразование и уже у него берём сессию, у объекта сессии получаем
 * атрибут <i>user</i>. Полученный {@code Object} из {@code .getAttribute("user")} приводим к {@code UserDto}.
 * </p>
 * <pre>{@code UserDto user = (UserDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");}</pre>
 */
@WebFilter("/admin")
public class UnsafeFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        UserDto user = (UserDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");

        // Если пользователь существует, тогда продолжаем выполнение запроса (не разрываем цепочку)
        if (user != null) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            // В противном случае делаем переадресацию на страницу аутентификации
            ((HttpServletResponse) servletResponse).sendRedirect("/registration");
        }
    }
}
