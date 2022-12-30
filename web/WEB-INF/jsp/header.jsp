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
        width: 100%;
        background: gainsboro;
    }
</style>
<header>
    <div style="justify-items: left; width: 50%;">
        <h1>It is header</h1>
    </div>
    <!-- Если пользователь авторизован -->
    <c:if test="${not empty sessionScope.user}">
        <div style="justify-items: right; width: 50%;">
            <form action="${pageContext.request.contextPath}/logout" method="post">
                <button type="submit">Выйти</button>
            </form>
        </div>
    </c:if>
</header>
