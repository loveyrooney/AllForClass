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
role: ${role} / sessionId(uid): ${sessionId} / tid: ${ldto.tid} /
영상제목: ${vdto.title} / 영상경로: ${vdto.videopath}
<div id="container">
    <%-- 강의 영상  --%>
    <div id="video">
        <%--         <img src="/resources/images/default.png" alt="default_img"> --%>
        <%--        <iframe src=""></iframe>--%>
        <video src="/getVideo/${vdto.videopath}" controls>대체텍스트</video>

        <form method="post" action="/insertvid" enctype="multipart/form-data">
            <input type="hidden" name="lid" id="vlid" value="${ldto.lid}">

            <input type="text" name="title" id="title">
            <div id="video_upload">
                <input type="file" name="vidfile" id="vidfile">
                <span class="span_file">선택된 파일이 없습니다.</span>
                <label class="label_file_btn" for="vidfile">영상 선택</label>
                <button type="submit">영상 추가</button>
            </div>
        </form>
    </div>

    <%-- 강의 자료 설명 --%>
    <div id="lec_detail">
        <ul id=lec_info>
            <li><h3>과목 > ${ldto.subject}</h3></li>
            <li><h1>${ldto.lname}</h1></li>
            <li><h3>강사 : ${ldto.tname} [${ldto.temail}]</h3></li>
        </ul>

        <%-- 등록된 자료 리스트 / 다운로드 --%>
        강의자료
        <ul id="reflist">
            <c:forEach var="i" items="${reflist}" varStatus="status">
                <c:set var="decodedRef" value="${decodeRef[status.index]}"/>
                <c:forTokens var="path" items="${i.refpath}" delims=",">
                    <li><a href="/download/${path}">${decodedRef}</a></li>
                </c:forTokens>
            </c:forEach>
        </ul>

        <%-- 자료 등록  --%>
        <form method="post" action="/insertref" enctype="multipart/form-data">
            <input type="hidden" name="lid" id="reflid" value="${ldto.lid}">
            <input type="hidden" name="sessionId" id="sessionId" value="${sessionId}">
            <input type="hidden" name="role" id="role" value="${role}">
            <input type="hidden" name="tid" id="tid" value="${ldto.tid}">

            <div id="file_upload">
                <input type="file" name="files" id="files" multiple>
                <span class="span_file">선택된 파일이 없습니다.</span>
                <label class="label_file_btn" for="files">강의 자료 선택</label>
                <button type="submit">강의 자료 추가</button>
            </div>
        </form>
    </div>

    <%-- 강의 설명 --%>
    <div id="lec_description">
        ${ldto.description}
    </div>

    <%-- 댓글 --%>
    <div id="reply">
        <form>
            <input type="hidden" name="rlid" id="rlid" value="${ldto.lid}">

            <input type="text" name="content" id="content">
            <button type="button" id="append_btn">쓰기</button>
        </form>
        <%-- 댓글 리스트 --%>
        <ul id="replyList"></ul>
    </div>
</div>
</body>
</html>
