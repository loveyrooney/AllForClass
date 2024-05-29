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
<div id="container">
    <%-- 강의 영상  --%>
    <div id="video">
        <div id="vidPlayer"></div>

        <form id="insertVideoForm" method="post" action="/insertvid" enctype="multipart/form-data">
            <input type="hidden" name="lid" id="lid" value="${ldto.lid}">
            <div id="video_title"></div>
            <div id="video_upload"></div>
        </form>

        <form method="post" action="/deletevideo/${vdto.vid}">

        </form>
    </div>


    <%-- 강의 정보 --%>
    <div id="lec_detail">
        <ul id=lec_info>
            <li><h3>과목 > ${ldto.subject}</h3></li>
            <li><h1>${ldto.lname}</h1></li>
            <li><h3>강사 : ${ldto.tname} [${ldto.temail}]</h3></li>
        </ul>

        <%-- 등록된 자료 리스트 / 다운로드 --%>
        강의자료
        <div id="deleteBtn"></div>
        <div id="fileListContainer">
        </div>

        <%-- 자료 등록  --%>
        <form id="fileUploadForm" method="post" action="/insertref" enctype="multipart/form-data">
            <input type="hidden" name="lid" id="reflid" value="${ldto.lid}">
            <div id="file_upload"></div>
        </form>
    </div>

    <%-- 강의 설명 --%>
    <div id="lec_description">
        ${ldto.description}
    </div>

    <%-- 댓글 --%>
    <div id="reply">
        <form>
            <input type="text" name="content" id="content" placeholder="궁굼한 점이 있으신가요?">
            <button type="button" id="append_btn">쓰기</button>
        </form>
        <%-- 댓글 리스트 --%>
        <ul id="replyList"></ul>
    </div>
</div>

<script>
    let inits = {
        lid: '${ldto.lid}'
        , tid: '${ldto.tid}'
        , vid: '${vdto.vid}'
        , videopath: '${vdto.videopath}'
        , sessionId: '${sessionId}'
        , role: '${role}'
        , startdate: '${ldto.startdate}'
        , tsession: '${ldto.timesession}'
    }
    init(inits);
</script>
</body>
</html>
