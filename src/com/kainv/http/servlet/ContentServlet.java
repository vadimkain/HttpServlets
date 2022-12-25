package com.kainv.http.servlet;

import com.kainv.http.dto.FlightDto;
import com.kainv.http.service.FlightService;
import com.kainv.http.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

import static java.util.stream.Collectors.*;

/**
 * <h1>HTTP. Servlets. 41. JSP Директивы</h1>
 * <p>Сервлет, который проксирует запрос на страницу content в WEB-INF.</p>
 * <p>Т.к. jsp страница находится в WEB-INF, кроме как других сервлетов, к ней никто не может достучаться.</p>
 * <p>Так как jsp это тоже сервлет, проксируем на страницу content.jsp. Для этого должны указать полный путь к ней
 * (начиная от директории web):</p>
 * <pre>{@code req.getRequestDispatcher("WEB-INF/jsp/content.jsp").forward(req, resp);}</pre>
 * <h1>HTTP. Servlets. 42. Expression Language. EL</h1>
 * <p>В качестве интереса установим в качестве атрибутов в {@code requestScope} нашу информацию о полётах:</p>
 * <pre>{@code
 *         List<FlightDto> flightsDto = flightService.findAll();
 *
 *         req.setAttribute("flights", flightsDto);
 * }</pre>
 * <p>Установим атрибут в {@code sessionScope} ассоциативный массив о полётах:</p>
 * <pre>{@code
 *         req
 *                 .getSession()
 *                 .setAttribute(
 *                         "flightMap",
 *                         flightsDto.stream()
 *                                 .collect(toMap(FlightDto::getId, FlightDto::getDescription)));
 * }</pre>
 */
@WebServlet("/content")
public class ContentServlet extends HttpServlet {
    private final FlightService flightService = FlightService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<FlightDto> flightsDto = flightService.findAll();

        req.setAttribute("flights", flightsDto);

        req
                .getSession()
                .setAttribute(
                        "flightMap",
                        flightsDto.stream()
                                .collect(toMap(FlightDto::getId, FlightDto::getDescription)));

        req.getRequestDispatcher(JspHelper.getPath("content")).forward(req, resp);
    }
}
