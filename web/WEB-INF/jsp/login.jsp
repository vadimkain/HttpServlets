<%--
  Created by IntelliJ IDEA.
  User: kainv
  Date: 30.12.2022
  Time: 00:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!-- Внедряем зависимости для локализации -->
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Login</title>
</head>
<body>
<!--
    Устанавливаем локаль.

    В последующем будем брать локаль из сессии. В реальных приложениях можем получать из заголовка запроса, либо из
    настроек пользователя в базе данных.

    Т.е. алгоритм такой: сначала берём локаль из заголовка, если там не оказалось такой локали, то берём из настроек
    пользователя. Если там нет такой локали или настройки в принципе, то берём из дефолтного properties файла.
 -->
<%--<fmt:setLocale value="ru_RU"/>--%>
<!--
    Указываем название bundle.

    Т.е. сначала создаём Locale, когда делаем fmt:setLocale и после этого создаём ResourceBundle используя существующий
    Locale.
-->
<%--<fmt:setBundle basename="translations"/>--%>

<!-- Подключаем header -->
<%@include file="header.jsp" %>

<!--
    Теперь, там где статика (текст) - должны удалить и добавить fmt:message. В нём есть несколько атрибутов и главный из
    них - это key, где вводим ключ из properties файла.

    Стоит помнить, что в случае других атрибутов, например bundle, он нужен, для того чтобы брать значения по этому
    ключу из разных бандлов. Тогда через bundle="" должны будем вводить конкретный бандл, который хотим использовать
    для перевода этого ключа
 -->

<form action="${pageContext.request.contextPath}/login" method="post">
    <label for="email">
        <fmt:message key="page.login.email"/>
        <input type="text" name="email" id="email" value="${param.email}" required>
        <!-- Передаём параметр email чтобы в случае ошибки пользователю не надо было ещё раз вводить email -->
    </label>
    <br>
    <label for="password">
        <fmt:message key="page.login.password"/>
        <input type="password" name="password" id="password" required>
    </label>
    <br>
    <button type="submit">
        <fmt:message key="page.login.submit.button"/>
    </button>
    <br>
    <a href="${pageContext.request.contextPath}/registration">
        <button type="button">
            <fmt:message key="page.login.register.button"/>
        </button>
    </a>
    <!-- Выводим ошибки авторизации -->
    <c:if test="${param.error != null}">
        <br>
        <div>
                <%--            <span style="color: red; font-weight: bold">Email или пароль введены неверно</span>--%>
            <span style="color: red; font-weight: bold">
                <fmt:message key="page.login.error"/>
            </span>
        </div>
    </c:if>
</form>
</body>
</html>
