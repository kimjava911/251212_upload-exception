<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- JSTL core 라이브러리: c:if, c:forEach 등 조건문/반복문 태그 제공 --%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<jsp:include page="../common/head.jsp">
    <jsp:param name="pageName" value="${pageName} + ' - ' + ${review.title}"/>
</jsp:include>
<body>
<jsp:include page="../common/nav.jsp">
    <jsp:param name="pageName" value="${pageName}"/>
</jsp:include>

<h2>${review.title}</h2>

<%-- 평점 표시 --%>
<p>
    <div class="d-flex justify-content-start">
        <div class="pe-1">평점:</div>
        <div class="d-flex justify-content-start">
            <c:forEach begin="1" end="${review.rating}"><span>★</span></c:forEach>
            <c:forEach begin="${review.rating + 1}" end="5"><span>☆</span></c:forEach>
        </div>
        <div class="ps-1">(${review.rating}/5)</div>
    </div>
</p>

<%-- 이미지 표시 --%>
<c:if test="${not empty review.imageUrl}">
    <p>
        <img src="${review.imageUrl}" alt="리뷰 이미지"
             style="max-width: 500px; max-height: 500px;">
    </p>
</c:if>

<%-- 내용 (줄바꿈 보존) --%>
<div style="white-space: pre-wrap; background: #f5f5f5; padding: 15px; margin: 10px 0;">
    ${review.content}
</div>

<p style="color: #666;">
    작성일: ${review.createdAt}
</p>

<hr>

<p>
<%-- TODO : 수정 & 삭제 --%>
<a href="<c:url value='/reviews'/>">목록으로</a>
</p>
</body>
</html>