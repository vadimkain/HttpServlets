package com.kainv.http.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * <h1>HTTP. Servlets. 49. Servlet Filters. Часть 1</h1>
 * <p>
 * Ставим URL паттерн, по которому этот фильтр должен срабатывать (/* - для всех запросов):
 * </p>
 * <pre>{@code @WebFilter(urlPatterns = "/*")}</pre>
 * <p>
 * Далее, после того как сделали весь функционал, который хотели на этом фильтре - мы должны продолжить выполнять
 * нашу цепочку. Т.е. можно несколько фильтров построить, но в конечном итоге должны добраться до сервлета, который
 * обрабатывает наш запрос. Следовательно, это цепочка вызовов один за другим (есть такой паттерн проектирования -
 * цепочка обязанностей) и если можем полностью обработать запрос на первом фильтре, то сразу можем вернуть
 * ответ и не продолжать цепочку вызовов. Если же знаем, что надо дальше продолжить выполнение, то просто передаём
 * процесс следующему в цепочке. Далее, следующий в цепочке определяет, нужно ли ему продолжать выполнение дальше
 * или тоже обрывать запрос и возвращать ответ до тех пор, пока не дойдём до сервлета, потому что сервлет может
 * быть только один.
 * </p>
 * <pre>{@code filterChain.doFilter(servletRequest, servletResponse);}</pre>
 * <p>
 * Следовательно, для этого нам и нужен {@code FilterChain} объект. Он знает про всю цепочку вызовов и следовательно,
 * у него есть один единственный метод {@code .doFilter()} которому нужно передать {@code servletRequest} и
 * {@code servletResponse}. Когда цепочка вызовов закончится и пойдёт обратно response от нашего сервлета или фильтра,
 * то мы вернёмся обратно в метод после {@code filterChain.doFilter(rq, rs);} и сможем выполнить какое-то действие.
 * Потому что запрос идёт через фильтры, обрабатывается в сервлете и возвращается так же через все эти же фильтры.
 * </p>
 * <br>
 * <p>
 * Так же фильтры можно привязывать не по {@code urlPatterns}, но ещё и по {@code servletNames}. Т.е. по названию
 * сервлета, который мы указываем непосредственно в самом сервлете. Нельзя оба одновременно использовать. Либо
 * {@code urlPatterns}, либо {@code servletNames}.
 * </p>
 * <p>
 * Указываем все сервлеты, которые хотим чтобы обрабатывались нашим фильтром:
 * </p>
 * <pre>{@code
 * @WebFilter(urlPatterns = "/*", servletNames = {
 *         "RegistrationServlet"
 * })
 * }</pre>
 * <br>
 * <p>
 * Аргумент {@code DispatcherType[] dispatcherTypes[]} по умолчанию возвращает REQUEST.
 * {@code dispatcherTypes = DispatcherType.REQUEST} означает, что REQUEST срабатывает для всех запросов на наш сервер.
 * Но так же здесь есть ещё аргументы:
 * </p>
 * <ul>
 *     <li><b>{@code .ASYNC}</b> - в случае с асинхронной работы наших фильтров и сервлетов.</li>
 *     <li><b>{@code .ERROR}</b> - срабатывает, если возвращаем какую-ту ERROR страницу. Это делается чтобы не видеть
 *     ошибки, которые обычно видим, если что-то пошло не так на сервере. Для этого можем в web.xml переопределить
 *     поведение и отправлять какую-нибудь страницу.</li>
 *     <li><b>{@code .FORWARD}</b> - срабатывает, если делаем отправку от одного сервлета к другому.</li>
 *     <li><b>{@code .INCLUDE}</b> - срабатывает, если делаем include запроса от одного сервлета к другому, то фильтр
 *     по умолчанию не срабатывает (т.е. в режиме REQUEST не срабатывает).</li>
 *     <li><b>{@code .REQUEST}</b> - установлен по умолчанию, срабатывает на обычные request запросы.</li>
 * </ul>
 */
@WebFilter(urlPatterns = "/*", servletNames = {
        "RegistrationServlet"
},
        dispatcherTypes = DispatcherType.REQUEST
)
public class CharsetFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // Устанавливаем кодировку для запросов
        servletRequest.setCharacterEncoding(StandardCharsets.UTF_8.name());
        // Устанавливаем кодировку для ответов
        servletResponse.setCharacterEncoding(StandardCharsets.UTF_8.name());

        filterChain.doFilter(servletRequest, servletResponse);

        System.out.println();
    }
}
