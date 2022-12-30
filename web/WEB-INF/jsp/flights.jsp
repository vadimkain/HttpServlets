<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<%@include file="header.jsp" %>
<h1>Список перелётов?</h1>
<c:if test="${not empty requestScope.flights}">
    <c:forEach var="flight" items="${requestScope.flights}">
        <li><a href="${pageContext.request.contextPath}/tickets?flightId=${flight.id}">${flight.description}</a></li>
    </c:forEach>
</c:if>
<p>
    <code>href="$ {pageContext.request.contextPath}/tickets?flightId=$ {flight.id}">$ {flight.description}</code> -
    указываем не абсолютный путь, а динамический. Т.е. на основании контекста патча. Т.е. из патч контекста обращаемся
    к request (<code>HttpServletRequest</code>) и через этот объект можем обратиться к любым геттерам, которые есть у
    (<code>HttpServletRequest</code>).
</p>
<p>
    <code>.contextPath</code> говорит о том, в каком Application context лежит обработка сервлетов.
</p>
</body>
</html>
