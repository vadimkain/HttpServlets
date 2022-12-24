package com.kainv.http.util;

import lombok.experimental.UtilityClass;

/**
 * <h1>Класс-шаблон для передачи пути к jsp страницам</h1>
 * <p>
 * Каждый раз, когда будем перенаправлять страницу на .jsp, нам приходится повторять из кода в код
 * {@code WEB-INF/jsp/name.jsp}, следовательно, проще создать утилитный класс.
 * </p>
 * <p>
 * В этом классе будет один метод, который добавляет префикс и суфикс к названии нашей страницы.
 * </p>
 * <pre>{@code public static String getPath(String jspName)}</pre>
 */
@UtilityClass
public class JspHelper {
    public static final String JSP_FORMAT = "WEB-INF/jsp/%s.jsp";

    public static String getPath(String jspName) {
        return String.format(JSP_FORMAT, jspName);
    }
}
