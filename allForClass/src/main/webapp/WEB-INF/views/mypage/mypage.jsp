<%--
  Created by IntelliJ IDEA.
  User: FULL8-006
  Date: 2024-05-21
  Time: 오전 9:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/mypage/mypage.css">
    <script src='https://cdn.jsdelivr.net/npm/fullcalendar@6.1.11/index.global.min.js'></script>
    <script src="/resources/js/mypage/mypage.js"></script>
</head>
<body>
<%--환영 문구--%>
<div>
    ${dto.uname}님 환영합니다.
</div>

<%--회원정보 조회--%>
<div>
    <ul>
        <li>${dto.uname}</li>
        <li>${dto.email}</li>
    </ul>
</div>

<%--캘린더--%>
<div id='calendar'></div>


<script>
    // 스크립트로 계정 아이디 값 넘겨주기
    init(${dto.uid});
</script>

</body>
</html>
