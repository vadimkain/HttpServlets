<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>

<%@ include file="../../index.html" %>

<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>HTTP. Servlets. 41. JSP Директивы</h1>
<p>Директив три. По сути, они представляют собой jsp теги.</p>
<ul>
    <li>
        <b style="color: orange"><@ page</b> директива используется для конфигурации jsp странички. В основном
        используется один раз для того, чтобы указать <code>contentType</code>.
        <pre>< %@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" % ></pre>
    </li>
    <li>
        <b style="color: orange"><@ taglib</b> в основном используется для того, чтобы указать другие библиотеки,
        которые хотим использовать в jsp странице. С помощью этой директивы будем указывать jstl, т.е. вспомогательную
        библиотеку для того, чтобы использовать циклы, форматирования и прочее. Должны указать <code>prefix</code>,
        элементы библиотеки которой будем обращаться (в основном используется <code>c</code>). Далее указываем
        <code>uri</code>, пусть у нашей библиотеке из которой будем получать необходимые новые теги. Обычно используется
        просто сайт.
        <pre>< %@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" % ></pre>
    </li>
    <li>
        <b style="color: orange"><@ include</b> позволяет включать в нашу jsp страницу содержимое другой jsp страницы
        или html. Т.е. это чем-то похож на <code>requestDispatcher.include(req, resp)</code> (передаём управление на
        другую jsp страницу, чтобы подключить контент из неё в нашу, где мы указываем). Здесь всего лишь есть один
        атрибут - это <code>file</code>, где указываем путь к нашей jsp или html страничке.
        <pre>< %@ include file="index.html" % ></pre>
    </li>
</ul>
<h1>HTTP. Servlets. 43. JSTL. Теория</h1>
<img src="content-4.png" alt="" width="40%">
<img src="content-5.png" alt="" width="40%">
<img src="content-6.png" alt="" width="40%">
<img src="content-7.png" alt="" width="40%">
<img src="content-8.png" alt="" width="40%">
<img src="content-9.png" alt="" width="40%">
<img src="content-10.png" alt="" width="40%">
<img src="content-11.png" alt="" width="40%">
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:if test="${not empty requestScope.tickets}">
    <h1>Купленные билеты:</h1>
    <ul>
        <c:forEach var="ticket" items="${requestScope.tickets}">
            <li>${fn:toLowerCase(ticket.seatNo)}</li>
        </c:forEach>
    </ul>
</c:if>
</body>
</html>