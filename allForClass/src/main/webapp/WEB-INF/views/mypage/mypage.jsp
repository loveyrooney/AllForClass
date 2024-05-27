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
<div id="mypage_top">
    <h1>
        <c:choose>
            <c:when test="${dto.role == 1}">
                ${dto.uname}님 환영합니다!
            </c:when>
            <c:when test="${dto.role == 2}">
                ${dto.uname} 강사님 환영합니다!
            </c:when>
        </c:choose>
    </h1>
</div>


<%--회원정보 조회--%>
<div id="mypage_myinfo">

    <%--회원 아이콘 이미지--%>
    <c:choose>
        <c:when test="${dto.role == 1}">
            <img src="/resources/images/icon_student.png" alt="icon">
        </c:when>
        <c:when test="${dto.role == 2}">
            <img src="/resources/images/icon_teacher.png" alt="icon">
        </c:when>
    </c:choose>

    <%--회원 정보--%>
    <ul>
        <li>${dto.uname}</li>
        <li>${dto.email}</li>
    </ul>

    <%--회원정보 수정 버튼--%>
    <a href="/updateuser/${dto.uid}" class="mypage_btn">회원정보 수정</a>

</div>


<%--본문--%>
<c:choose>
<c:when test="${dto.role == 1}">
    <%--수강생이면 캘린더 보여주기--%>
    <div id='calendar'></div>
</c:when>
<c:when test="${dto.role == 2}">
    <%--강사면 강의 리스트 보여주기--%>
<section id="mypage_content">
    <ul>
        <li id="pastlec" class="lec_list">지난 강의</li>
        <li id="confirmlec" class="lec_list active">예정된 강의</li>
        <li id="waitlec" class="lec_list">승인 대기 중</li>
        <li><a href="/insertlec" id="insert_btn">강의 등록 신청</a></li>
    </ul>

    <div id="mypage_line"></div>

    <table>
        <thead>
        <tr>
            <th>과목</th>
            <th>강의명</th>
            <th>가격</th>
            <th>개강일</th>
            <th>강의 세션</th>
        </tr>
        </thead>
        <tbody id="myleclist">
        </tbody>
    </table>


    </c:when>
    </c:choose>
</section>

<script>
    // 스크립트로 회원계정 아이디 값 넘겨주기
    init(${dto.uid});
</script>

</body>
</html>
