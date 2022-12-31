<%--
  Created by IntelliJ IDEA.
  User: kainv
  Date: 24.12.2022
  Time: 20:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<style>
    header {
        display: flex;
        align-content: center;
        justify-content: center;

        width: 100%;
        background: gainsboro;
    }
</style>
<header>
    <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%--     Локаль берём из sessionScope--%>
    <fmt:setLocale value="${sessionScope.lang != null ? sessionScope.lang : (param.lang != null ? param.lang : 'en_US')}"/>
    <fmt:setBundle basename="translations"/>

    <div class="header-div">
        <h1>It is header</h1>
    </div>
    <!-- Изменение локали для пользователя -->
    <div class="header-div">
        <form action="${pageContext.request.contextPath}/locale" method="post">
            <button type="submit" name="lang" value="ru_RU">ru_RU</button>
            <button type="submit" name="lang" value="en_US">en_US</button>
        </form>
    </div>
    <!-- Если пользователь авторизован -->
    <c:if test="${not empty sessionScope.user}">
        <div class="header-div">
            <form action="${pageContext.request.contextPath}/logout" method="post">
                <button type="submit">Выйти</button>
            </form>
        </div>
    </c:if>
</header>
