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
<%--    <script src="/resources/js/mypage/mypage.js"></script>--%>
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


<%--수강 강의 목록--%>
<c:forEach var="item" items="${list}">
    ${item.lname}
    ${item.tname}
    ${item.subject}
    ${item.startdate}
    ${item.timesession}<br>
</c:forEach>


<%--캘린더--%>
<div id='calendar'></div>


<script>

/*    init(${item.lname});*/
    var calendar;
    //캘린더에 이벤트 추가
    document.addEventListener('DOMContentLoaded', function() {
        var calendarEl = document.getElementById('calendar');
        calendar = new FullCalendar.Calendar(calendarEl, {
            initialView: 'dayGridMonth'
            , events : [
                {title:"09:00-12:00\n[국어] 고등1국어 홍길동ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇdddddddddㅇㅇ"
                    , start : "2024-05-21"
                    , color : 'pink'}
            ]
        });
        calendar.render();
    });
</script>
</body>
</html>
