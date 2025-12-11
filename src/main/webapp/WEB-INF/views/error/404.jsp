<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<jsp:include page="../common/head.jsp">
    <jsp:param name="pageName" value="찾을 수 없는 페이지"/>
</jsp:include>
<body>
<jsp:include page="../common/nav.jsp">
    <jsp:param name="pageName" value="찾을 수 없는 페이지"/>
</jsp:include>

<div style="text-align: center; padding: 50px;">
    <h1 style="color: #6c757d;">🔍 404</h1>
    <h2>페이지를 찾을 수 없습니다</h2>

    <c:choose>
        <c:when test="${not empty errorMessage}">
            <p>${errorMessage}</p>
        </c:when>
        <c:otherwise>
            <p>요청하신 페이지가 존재하지 않거나 삭제되었습니다.</p>
        </c:otherwise>
    </c:choose>

    <p>
        <a href="<c:url value='/reviews'/>">리뷰 목록으로 이동</a>
    </p>
</div>
</body>
</html>