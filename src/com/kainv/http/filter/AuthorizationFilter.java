package com.kainv.http.filter;

import com.kainv.http.dto.UserDto;
import com.kainv.http.util.UrlPath;
import com.oracle.wls.shaded.org.apache.regexp.RE;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

import static com.kainv.http.util.UrlPath.*;

/**
 * <h1>HTTP. Servlets. 53. Authorization. Авторизация</h1>
 * <h2>
 * Данный фильтр будет срабатывать на все наши URL. К одним ресурсами будем давать доступ, к другим - нет.
 * </h2>
 * <p>
 * Например, если пользователь не залогинен в системе, то должны по-любому давать доступ к таким страницам как
 * /registration, /login. Следовательно, есть какие-то public ресурсы и есть private ресурсы. Для того, чтобы
 * понять к какому ресурсу идёт пользователь - мы берём его URI.
 * </p>
 * <p>
 * Проводим через фильтр все запросы: /*
 * </p>
 * <pre>{@code @WebFilter("/*")}</pre>
 * <p>Берём URI:</p>
 * <pre>{@code String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();}</pre>
 * <p>
 * Теперь на основании пути можно определить, что пользователю разрешено, а что нет. Следовательно, у нас будет
 * два основных момента:
 * </p>
 * <ul>
 *     <li>
 *         если это public URI или пользователь залогинен, тогда разрешаем доступ к ресурсу.
 *     </li>
 *     <li>
 *         если это private URI, тогда должны разрешать к ней только тогда, когда есть пользователь в системе.
 *         Более того, можем проверять ещё и роль нашего пользователя.
 *     </li>
 * </ul>
 */
@WebFilter("/*")
public class AuthorizationFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        String requestURI = ((HttpServletRequest) servletRequest).getRequestURI();

        // Если URI публичный или пользователь залогинен, то не разрываем цепочку
        if (isPublicPath(requestURI) || isUserLoggedIn(servletRequest)) {
            filterChain.doFilter(servletRequest, servletResponse);

        }
        // В противном случае перенаправляем пользователя назад, на страницу из которой пришёл
        else {
            // Этот заголовок говорит о том, с какой страницы пользователь пришёл на страницу, где сработал фильтр
            String previousPage = ((HttpServletRequest) servletRequest).getHeader("referer");
            // Если предыдущая страница не равна null, то отправляем назад. В противном случае отправляем на "/login"
            ((HttpServletResponse) servletResponse).sendRedirect(previousPage != null ? previousPage : LOGIN);
        }
    }

    // КОНСТАНТА - ПУБЛИЧНЫЕ РЕСУРСЫ
    private static final Set<String> PUBLIC_PATH = Set.of(LOGIN, REGISTRATION, IMAGES);

    // ПРОВЕРЯЕМ, ЯВЛЯЕТСЯ ЛИ ВВЕДЕННЫЙ URI ПОЛЬЗОВАТЕЛЕМ ПУБЛИЧНЫМ
    private boolean isPublicPath(String uri) {
        // про
        return PUBLIC_PATH.stream()
                // uri по которому переходим должен совпадать с PUBLIC_PATH элементом
                .anyMatch(uri::startsWith);
//                .anyMatch(path -> uri.startsWith(path));
    }

    // ПРОВЕРЯЕМ, ЗАЛОГИНЕН ЛИ ПОЛЬЗОВАТЕЛЬ В СИСТЕМЕ
    private boolean isUserLoggedIn(ServletRequest servletRequest) {
        UserDto user = (UserDto) ((HttpServletRequest) servletRequest).getSession().getAttribute("user");
        return user != null;
    }
}
