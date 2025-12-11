<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>오류 발생</title>
</head>
<body>
<div style="text-align: center; padding: 50px;">
    <h1 style="color: #dc3545;">⚠️ 오류가 발생했습니다</h1>

    <p style="font-size: 1.2em; color: #333;">
        ${errorMessage}
    </p>

    <c:if test="${not empty errorDetail}">
        <p style="color: #666;">
                ${errorDetail}
        </p>
    </c:if>

    <hr style="margin: 30px 0;">

    <p>
        <%--
            javascript:history.back(): 브라우저의 뒤로가기 기능 호출
            이전 페이지로 돌아가서 다시 시도할 수 있게 함
        --%>
        <a href="javascript:history.back()">이전 페이지로</a>
        &nbsp;|&nbsp;
        <a href="<c:url value='/reviews'/>">리뷰 목록으로</a>
    </p>
</div>
</body>
</html>
