<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: db400tea
  Date: 2024-05-23
  Time: 오후 4:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/admin/admin.css">
    <script src="/resources/js/admin/admin.js"></script>
</head>
<body>
<div id="container">
    <ul id="upper_tab">
        <li>
            <button class="tablink">승인 대기 리스트</button>
        </li>
        <li>
            <button class="tablink">강의 리스트</button>
        </li>
        <li>
            <button class="tablink">회원 리스트</button>
        </li>
    </ul>

    <table id="tab1" class="content">
        <thead>
        <tr>
            <th>과목</th>
            <th>강의명</th>
            <th>가격</th>
            <th>개강일</th>
            <th>강의세션</th>
            <th>정원</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${cList}">
            <tr>
                <td>${i.subject}</td>
                <td><a href="/updatelec/${i.lid}">${i.lname}</a></td>
                <td>${i.price}</td>
                <td>${i.startdate}</td>
                <td>${i.timesession}</td>
                <td>${i.entry}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>

    <table id="tab2" class="content">
        <thead>
        <tr>
            <th>과목</th>
            <th>강의명</th>
            <th>가격</th>
            <th>개강일</th>
            <th>강의세션</th>
            <th>정원</th>
            <th>승인여부</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${leclist}">
            <tr>
                <td>${i.subject}</td>
                    <%--승인된 강의는 강의 수강페이지로 연결--%>
                <c:choose>
                    <c:when test="${i.confirm == false}">
                        <td>${i.lname}</td>
                    </c:when>
                    <c:when test="${i.confirm == true}">
                        <td><a href="/room/${i.lid}">${i.lname}</a></td>
                    </c:when>
                </c:choose>
                <td>${i.price}</td>
                <td>${i.startdate}</td>
                <td>${i.timesession}</td>
                <td>${i.entry}</td>
                    <%--미승인 클릭시 승인 페이지로 연결--%>
                <c:choose>
                    <c:when test="${i.confirm == false}">
                        <td><a href="/updatelec/${i.lid}">미승인</a></td>
                    </c:when>
                    <c:when test="${i.confirm == true}">
                        <td>승인</td>
                    </c:when>
                </c:choose>
            </tr>
        </c:forEach>

        </tbody>
    </table>

    <table id="tab3" class="content">
        <thead>
        <tr>
            <th>아이디</th>
            <th>이메일</th>
            <th>이름</th>
            <th>탈퇴여부</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="i" items="${uList}">
            <tr>
                <td>${i.uid}</td>
                <td><a href="/updateuser/${i.uid}">${i.email}</a></td>
                <td><a href="/updateuser/${i.uid}">${i.uname}</a></td>
                <td>${i.disable}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
