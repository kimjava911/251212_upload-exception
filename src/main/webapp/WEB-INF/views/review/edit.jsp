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

<c:if test="${not empty errorMessage}">
    <p style="color: red; font-weight: bold;">${errorMessage}</p>
</c:if>

<form action="<c:url value='/reviews/${review.id}/edit'/>"
      method="post" enctype="multipart/form-data">

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

    <%-- 현재 이미지 미리보기 --%>
    <c:if test="${not empty review.imageUrl}">
        <p>
            <label>현재 이미지</label><br>
            <img src="${review.imageUrl}" alt="현재 이미지"
                 style="max-width: 200px; max-height: 200px;">
        </p>
    </c:if>

    <p>
        <label for="imageFile">새 이미지 (선택)</label><br>
        <input type="file" id="imageFile" name="imageFile"
               accept="image/jpeg,image/png,image/gif,image/webp">
        <br>
        <small style="color: #666;">
            새 이미지를 선택하면 기존 이미지가 교체됩니다.
        </small>
    </p>

    <p>
        <button>수정 완료</button>
        <a href="<c:url value='/reviews'/>">취소</a>
    </p>
</form>
</body>
</html>