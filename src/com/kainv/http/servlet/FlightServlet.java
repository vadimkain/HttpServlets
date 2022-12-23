package com.kainv.http.servlet;


import com.kainv.http.service.FlightService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

@WebServlet("/flights")
public class FlightServlet extends HttpServlet {
    private final FlightService flightService = FlightService.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Устанавливаем заголовки
        resp.setContentType("text/html");
        resp.setCharacterEncoding(StandardCharsets.UTF_8.name());

        // Отправляем пользователю список перелётов
        PrintWriter printWriter = resp.getWriter();
        printWriter.write("<h1>Список перелётов:</h1>");
        printWriter.write("<ul>");
        flightService.findAll().stream().forEach(flightDto -> {
            printWriter.write("""
                    <li>
                    <a href="/tickets?flightId=%d">%s</a>
                    </li>
                    """.formatted(flightDto.getId(), flightDto.getDescription()));
        });
        printWriter.write("</ul>");

    }
}
