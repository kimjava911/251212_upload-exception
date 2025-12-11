<%--
  Created by IntelliJ IDEA.
  User: morgan
  Date: 25. 12. 12.
  Time: 오전 12:02
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<head>
    <title>${param.pageName} - 리뷰 시스템</title>
    <%-- 부트스트랩 --%>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
    <%-- 글씨체 & 기본 마진 --%>
    <style>
        @font-face {
            font-family: 'ChangwonDangamRounded';
            src: url('https://cdn.jsdelivr.net/gh/projectnoonnu/2511-1@1.0/ChangwonDangamRound-Regular.woff2') format('woff2');
            font-weight: normal;
            font-display: swap;
        }

        body {
            font-family: ChangwonDangamRounded, sans-serif;
            padding: 0.5rem;
        }
    </style>
</head>