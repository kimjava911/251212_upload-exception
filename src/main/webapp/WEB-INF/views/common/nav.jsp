<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<nav>
    <h1>${param.pageName}</h1>
    <ul>
        <li><a href="${requestScope.contextPath}/">홈</a></li>
        <li><a href="${requestScope.contextPath}/new">생성</a></li>
    </ul>
    <hr>
</nav>