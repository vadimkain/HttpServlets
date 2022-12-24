<%@ page import="com.kainv.http.service.TicketService" %>
<%@ page import="com.kainv.http.dto.TicketDto" %>
<%@ page import="java.util.List" %><%--
  Created by IntelliJ IDEA.
  User: kainv
  Date: 24.12.2022
  Time: 19:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>Купленные билеты:</h1>
<ul>
    <%-- Используем скреплеты (это вкрепление java в .jsp страничку). Так нельзя не делать --%>
    <%
        Long flightId = Long.valueOf(request.getParameter("flightId"));
        List<TicketDto> tickets = TicketService.getInstance().findAllByFlightId(flightId);
        for (TicketDto ticket : tickets) {
            out.write(String.format("<li>%s</li>", ticket.getSeatNo()));
        }
    %>
</ul>
</body>
</html>
<%-- Используем declaration'ы для переопределения методов --%>
<%!
    public void jspInit() {
        System.out.println("Hello world!");
    }
%>