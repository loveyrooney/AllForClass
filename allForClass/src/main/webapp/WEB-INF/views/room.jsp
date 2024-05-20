<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: db400tea
  Date: 2024-05-20
  Time: 오후 7:14
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/room/room.css">
</head>
<body>

<%-- 강의 영상 부분 --%>


<hr>
<%-- 강의 자료 설명 부분--%>
과목 > ${subject}
${lname}
강사 : ${uname} [${email}]
<hr>
<%-- 자료 다운로드 / 등록 부분--%>
${refpath}
<button type="button">수강하기</button>
<button type="button">자료 다운로드</button>
<button type="button">자료 등록 하기</button>
<hr>
<%-- 강의 설명 부분--%>
${discription}
<hr>
<%-- 댓글 부분--%>
<textarea></textarea>
<button type="button">쓰기</button>
<%--<c:forEach>--%>
<%--    --%>
<%--</c:forEach>--%>
<hr>
</body>
</html>
