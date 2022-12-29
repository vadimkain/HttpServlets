package com.kainv.http.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.util.Arrays;

/**
 * <h1>HTTP. Servlets. 50. Servlet Filters. Часть 2</h1>
 * <h2>Фильтр, который логгирует параметры и выводить их на консоль</h2>
 */
@WebFilter("/*")
public class LoggingFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Берём из запроса все параметры и выводим их на консоль
        servletRequest.getParameterMap().forEach((k, v) -> System.out.println(k + " " + Arrays.toString(v)));

        // Не разрываем цепочку
        filterChain.doFilter(servletRequest, servletResponse);
    }
}
