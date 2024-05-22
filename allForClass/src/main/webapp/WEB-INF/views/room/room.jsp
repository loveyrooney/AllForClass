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
    <link rel="stylesheet" href="/resources/css/room/classroom.css">
    <script src="/resources/js/room/classroom.js"></script>
</head>
<body>
role: ${role}/
sessionId(uid): ${sessionId}
<%-- 강의 영상 부분 --%>
<div id="container">
    <div id="video">
        <img src="/resources/images/default.png" alt="default img">
        <ul>
            <li>영상제목: ${vdto.title}</li>
            <li>영상경로: ${vdto.videopath}</li>
        </ul>
    </div>
    <div id="lec_detail">
        <%-- 강의 자료 설명 부분--%>
        <ul id=>
            <li><h3>과목 > ${ldto.subject}</h3></li>
            <li><h2>${ldto.lname}</h2></li>
            <li><h4>강사 : ${ldto.tname} [${ldto.temail}]</h4></li>
        </ul>

        <%-- 자료 등록 부분 / 강사에게만 보이게 --%>
        <ul>
            <c:forEach var="i" items="${reflist}" varStatus="status">
                <c:set var="decodedRef" value="${decodeRef[status.index]}"/>
                <c:forTokens var="path" items="${i.refpath}" delims=",">
                    <li><a href="/download/${path}">${decodedRef}</a></li>
                </c:forTokens>
            </c:forEach>
        </ul>

        <form method="post" action="/insertref" enctype="multipart/form-data">
            <input type="hidden" name="lid" id="reflid" value="${ldto.lid}">
            <input type="hidden" name="sessionId" id="sessionId" value="${sessionId}">
            <%--    <input type="hidden" name="role" id="role" value="${role}">--%>
            <div id="file_upload">
                <input type="file" name="files" id="files" multiple>
                <label class="label_file" for="files">강의 자료 선택</label>
                <span class="span_file">선택된 파일이 없습니다.</span>
            </div>
            <ul>
                <li>
                    <button type="button">수강하기</button>
                    <button type="submit">강의 자료 추가</button>
                </li>
            </ul>
        </form>
    </div>

    <%-- 강의 설명 부분--%>
    <div id="lec_description">
        ${ldto.description}
    </div>


    <%-- 댓글 부분--%>
    <div id="reply">
        <form>
            <input type="text" name="content" id="content">
            <input type="hidden" name="rlid" id="rlid" value="${ldto.lid}">
            <button type="button" id="append">쓰기</button>
        </form>
        <ul id="replyList"></ul>
    </div>
</div>
</body>
</html>
