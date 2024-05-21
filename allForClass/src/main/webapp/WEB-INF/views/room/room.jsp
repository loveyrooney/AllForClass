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
    <script src="/resources/js/room/reply.js"></script>
</head>
<body>

<%-- 강의 영상 부분 --%>
<hr>




<%-- 강의 자료 설명 부분--%>
과목 > ${ldto.subject}<br>
${ldto.lname}<br>
강사 : ${ldto.tname} [${ldto.temail}]<br>
<hr>



<%-- 자료 다운로드 / 등록 부분--%>
<button type="button">수강하기</button>
<button type="button">자료 다운로드</button>

수업자료: ${refdto.refpath}

<form method="post" action="insertref" enctype="multipart/form-data">
    <input type="file" id="files" name="files" multiple><br>
    <input type="submit" value="업로드"/><br>
</form>
<hr>




<%-- 강의 설명 부분--%>
${discription}
<hr>




<%-- 댓글 부분--%>
<form>
    <textarea name="content" id="content"></textarea>
    <input type="hidden" name="lid" id="lid" value="${lid}">
    <button type="button" id="append">쓰기</button>
</form>
<ul id="replyList">
    <li></li>
    <%--<c:forEach>--%>
    <%--    --%>
    <%--</c:forEach>--%>
</ul>
<hr>




</body>
</html>
