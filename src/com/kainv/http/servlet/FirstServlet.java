package com.kainv.http.servlet;

import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * <h1>HTTP. Servlets. 27. Создание сервлетов</h1>
 * <p>Наш сервлет должен наследоваться от {@code HttpServlet}.</p>
 * <p>
 * Теперь, у него есть три основных шага:
 * <ol>
 *     <li>
 *         <pre>{@code
 *         public void init(ServletConfig config) throws ServletException {
 *              super.init();
 *         }
 *         }</pre>
 *         - где мы можем проинициализировать наш сервлет (мы не можем создавать
 *         никакие конструкторы в сервлете, потому что через Reflection API вызывается конструктор без параметров).
 *         {@code ServletConfig config} это всего лишь конфигурационный класс, в котором есть некоторые настройки
 *         сервлета:
 *         <ul>
 *             <li>
 *                {@code config.getServletName();} у каждого сервлета есть свое имя,
 *             </li>
 *             <li>
 *                 {@code config.getInitParameterNames();} какие-то параметры (набор строк, но на практике редко
 *                 встречаются),
 *             </li>
 *             <li>
 *                 {@code config.getServletContext();} суть в том, что это что-то вроде глобальной переменной на всё
 *                 веб-приложение. Т.е. у всех сервлетов будет один и тот же экземпляр класса сервлет контекст. Он
 *                 служит для того, чтобы мы могли установить какие-то глобальные переменные (атрибуты), фильтры,
 *                 и т.д.. Это мощный объект, который знает обо всём и обо всех.
 *             </li>
 *         </ul>
 *     </li>
 *     <li>
 *         <pre>{@code
 *         @Override
 *         public void destroy() {
 *              super.destroy();
 *         }
 *         }</pre>
 *         - вызывается для удаления каких-то ресурсов, для красивого завершения Tomcat Server'a, либо выполняем
 *         <i>undeploy</i> нашего приложения. Он никогда не вызовется сам, если не вызовем его аварийно.
 *     </li>
 *     <li>
 *         <pre>{@code
 *         @Override
 *         protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
 *              super.service(req, resp);
 *         }
 *         }</pre>
 *         - здесь у нас весь основной код. Не трудно предположить, что {@code HttpServletRequest req} это наш запрос
 *         от пользователя, а {@code HttpServletResponse resp} это то, что мы должны вернуть ему.
 *     </li>
 * </ol>
 * <b>Наша основная часть - это {@code service()}, где будет вся основная логика для каждого из сервлетов.</b>
 * </p>
 * <p>
 *     Если зайти во внутрь базового класса {@code Service()}, то увидим, что на самом деле метод {@code .service()}
 *     это метод, который имеет куча условных операторов на каждый из HTTP методов. Т.е. отдельный if на метод GET, POST,
 *     и т.д.. Следовательно, <b>правило хорошего тона говорит, что мы должны переопределить не метод {@code .service()},
 *     а соответствующий метод того HTTP метода, который хотим обработать нашим сервлетом.</b> Следовательно, мы
 *     переопределяем не {@code .service()}, а метод {@code .doGet()}, {@code doPost()} и т.д.:
 *     <pre>{@code
 *     @Override
 *     protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
 *          super.service(req, resp);
 *     }
 *     }</pre>
 * </p>
 * <p>
 *     При помощи аннотации устанавливаем маппинг, до Tomcat 7 делали это через XML:
 *     <pre>{@code @WebServlet("/first")}</pre>
 * </p>
 */
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
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        try (PrintWriter writer = resp.getWriter()) {
            writer.write("<h1>Hello from first servlet</h1>");
        }
        super.service(req, resp);
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}
