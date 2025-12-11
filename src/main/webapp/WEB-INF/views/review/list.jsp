<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- JSTL core 라이브러리: c:if, c:forEach 등 조건문/반복문 태그 제공 --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<jsp:include page="../common/head.jsp">
    <jsp:param name="pageName" value="${pageName}"/>
</jsp:include>
<body>
<jsp:include page="../common/nav.jsp">
    <jsp:param name="pageName" value="${pageName}"/>
</jsp:include>

<%-- Flash 메시지 표시 (등록/수정/삭제 후 한 번만 보여줌) --%>
<c:if test="${not empty message}">
    <p style="color: green; font-weight: bold;">${message}</p>
</c:if>

<p>
    <%-- c:url: 컨텍스트 경로를 자동으로 붙여주는 태그 --%>
    <a href="<c:url value='/reviews/new'/>">새 리뷰 작성</a>
</p>

<%-- 리뷰가 없는 경우 --%>
<c:if test="${empty reviews}">
    <p>등록된 리뷰가 없습니다.</p>
</c:if>

<%-- 리뷰 목록 반복 출력 --%>
<%-- c:forEach: Java의 for-each와 동일한 역할 --%>
<c:forEach var="review" items="${reviews}">
    <div style="border: 1px solid #ccc; padding: 15px; margin: 10px 0;">
            <%-- 이미지가 있으면 썸네일 표시 --%>
        <c:if test="${not empty review.imageUrl}">
            <img src="${review.imageUrl}" alt="리뷰 이미지"
                 style="max-width: 150px; max-height: 150px;">
        </c:if>

        <h3>
            <a href="<c:url value='/reviews/${review.id}'/>">${review.title}</a>
        </h3>

            <%-- 평점을 별(★)로 표시 --%>
        <p>
            평점:
            <c:forEach begin="1" end="${review.rating}">★</c:forEach>
            <c:forEach begin="${review.rating + 1}" end="5">☆</c:forEach>
            (${review.rating}/5)
        </p>

        <p style="color: #666; font-size: 0.9em;">
                <%-- 작성일시 출력 --%>
                ${review.createdAt}
        </p>
    </div>
</c:forEach>
</body>
</html>