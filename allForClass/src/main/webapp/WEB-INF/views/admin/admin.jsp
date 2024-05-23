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
</head>
<body>
<div id="container">
<ul id="upper_tab">
    <li>
        <button>승인 대기 리스트</button>
    </li>
    <li>
        <button>강의 리스트</button>
    </li>
    <li>
        <button>회원 리스트</button>
    </li>
</ul>
<table>
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
    <c:forEach var="i" items="${leclist}">
        <tr>
            <td>${i.subject}</td>
            <td>${i.lname}</td>
            <td>${i.price}</td>
            <td>${i.startdate}</td>
            <td>${i.timesession}</td>
            <td>${i.entry}</td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</div>
</body>
</html>
