<%--
  Created by IntelliJ IDEA.
  User: kainv
  Date: 30.12.2022
  Time: 00:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <title>Login</title>
</head>
<body>
<h1>HTTP. Servlets. 51. Authentication. Аутентификация</h1>
<form action="${pageContext.request.contextPath}/login" method="post">
    <label for="email">
        Email:
        <input type="text" name="email" id="email" value="${param.email}" required>
        <!-- Передаём параметр email чтобы в случае ошибки пользователю не надо было ещё раз вводить email -->
    </label>
    <br>
    <label for="password">
        Password:
        <input type="password" name="password" id="password" required>
    </label>
    <br>
    <button type="submit">Войти</button>
    <br>
    <a href="${pageContext.request.contextPath}/registration">
        <button type="button">Страница регистрации</button>
    </a>
    <!-- Выводим ошибки авторизации -->
    <c:if test="${param.error != null}">
        <br>
        <div>
            <span style="color: red; font-weight: bold">Email или пароль введены неверно</span>
        </div>
    </c:if>
</form>
</body>
</html>
