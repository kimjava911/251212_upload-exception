<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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

<%-- 에러 메시지 표시 (예외 발생 시) --%>
<c:if test="${not empty errorMessage}">
    <p style="color: red; font-weight: bold;">${errorMessage}</p>
</c:if>

<%--
    enctype="multipart/form-data": 파일 업로드 시 필수 설정
    일반 폼은 텍스트만 전송하지만, 이 설정으로 바이너리 파일도 전송 가능
--%>
<form action="<c:url value='/reviews'/>" method="post" enctype="multipart/form-data">

    <p>
        <label for="title">제목</label><br>
        <input type="text" id="title" name="title" required
               maxlength="100" style="width: 300px;"
               value="${review.title}">

        <c:if test="${bindingResult.hasFieldErrors('title')}">
            <p>${bindingResult.getFieldError('title').defaultMessage}</p>
        </c:if>
    </p>

    <p>
        <label for="content">내용</label><br>
        <textarea id="content" name="content" rows="5"
                  style="width: 300px;">${review.content}</textarea>
        <c:if test="${bindingResult.hasFieldErrors('content')}">
            <p>${bindingResult.getFieldError('content').defaultMessage}</p>
        </c:if>
    </p>

    <p>
        <label for="rating">평점</label><br>
        <select id="rating" name="rating" required>
            <option value="">선택하세요</option>
            <%-- c:forEach로 1~5 옵션 생성 --%>
            <c:forEach begin="1" end="5" var="i">
                <option value="${i}"
                        <c:if test="${review.rating == i}">selected</c:if>
                >${i}점</option>
            </c:forEach>
        </select>
        <c:if test="${bindingResult.hasFieldErrors('rating')}">
            <p>${bindingResult.getFieldError('rating').defaultMessage}</p>
        </c:if>
    </p>

    <p>
        <label for="imageFile">이미지 (선택)</label><br>
        <%--
            accept: 파일 선택 창에서 특정 타입만 보이도록 필터링
            서버에서도 검증하지만, 클라이언트에서 먼저 걸러주면 사용성 향상
        --%>
        <input type="file" id="imageFile" name="imageFile"
               accept="image/jpeg,image/png,image/gif,image/webp">
        <br>
        <small style="color: #666;">허용 형식: JPG, PNG, GIF, WebP (최대 10MB)</small>
    </p>

    <p>
        <button>등록</button>
        <a href="<c:url value='/reviews'/>">취소</a>
    </p>
</form>
</body>
</html>