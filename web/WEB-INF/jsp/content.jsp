<%--
  Created by IntelliJ IDEA.
  User: kainv
  Date: 24.12.2022
  Time: 20:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<h1>HTTP. Servlets. 42. Expression Language. EL</h1>
<img src="content-1.png" alt="" width="30%">
<img src="content-2.png" alt="" width="30%">
<ul>
    <li><b>pageScope</b> - можно использовать, когда определяем какие-то данные на уровне страницы.</li>
    <li><b>requestScope</b> - атрибуты, которые вставили в <code>HttpRequest</code> объект.</li>
    <li><b>sessionScope</b> - атрибуты, но устанавливаются в объект <code>HttpSession</code>.</li>
    <li><b>applicationScope</b> - атрибуты, которые устанавливаются в <code>ServletContext</code>.</li>
    <li><b>initParam</b> - параметры сервлета, которые можем задавать.</li>
    <li><b>cookie</b> - куки у сессии.</li>
    <li><b>header</b> - все заголовки, которые передали в request.</li>
    <li><b>param</b> - параметры запроса, т.е. то что передали после знака вопроса, либо с помощью body, передав header.
    </li>
</ul>
<img src="content-3.png" alt="" width="30%">

<%@include file="header.jsp" %>
<div>
    <span>Content. Русский.</span>
    <p><code>requestScope.flights.size()</code> Size: ${requestScope.flights.size()}</p>
    <p><code>requestScope.flights[1].id</code> Id: ${requestScope.flights[1].id}</p>
    <p><code>sessionScope.flightMap[1]</code> Id: ${sessionScope.flightMap[1]}</p>
    <p><code>cookie["JSESSIONID"]</code> JSESSION id: ${cookie["JSESSIONID"]}, unique identifier</p>
    <p><code>cookie["JSESSIONID"].value</code> JSESSION id: ${cookie["JSESSIONID"].value}, unique identifier</p>
    <p><code>header["Cookie"]</code> Header: ${header["Cookie"]}</p>
    <p><code>param.id</code> Param id: ${param.id}</p>
    <p><code>param.test</code> Param test: ${param.test}</p>
    <p><code>not empty requestScope.flights</code> Not empty list: ${not empty requestScope.flights}</p>
</div>
<%@include file="footer.jsp" %>
</body>
</html>
