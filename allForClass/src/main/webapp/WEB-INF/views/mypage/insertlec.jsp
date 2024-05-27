<%--
  Created by IntelliJ IDEA.
  User: FULL8-006
  Date: 2024-05-20
  Time: 오후 7:21
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="/resources/css/mypage/insertlec.css">
    <script src="/resources/js/mypage/insertlec.js"></script>
</head>
<body class="container">

<h1 class="insert_lec_h1">강의 등록 신청</h1>

<form method="post" action="insertlec_result" enctype="multipart/form-data">

    <div class="row justify-content-center">
        <div class="col-md-5 insert_lec_imgupload">
            <div id="image_container">
                <img id="green_image" class="img-thumbnail" src="/resources/images/default.png">
            </div>
            <label for="imgfile">
                <div class="lecimg_upload_btn">강의 썸네일 등록</div>
            </label>
            <input type="file" id="imgfile" name="imgfile" onchange="setPreview(event);">
        </div>


        <ul class="col-md-5 insert_lec_select">
            <input type="text" name="tid" value="${tid}" hidden>
            <li>
                <label for="subject">과목</label>
                <select name="subject" id="subject">
                    <option value="국어">국어</option>
                    <option value="영어">영어</option>
                    <option value="수학">수학</option>
                </select>
            </li>
            <li>
                <label for="lname">강의명</label>
                <input type="text" name="lname" id="lname" required>
            </li>
            <li>
                <label for="price">가격</label>
                <input type="number" name="price" id="price" required> 원
            </li>
            <li>
                <label for="entry">수강인원</label>
                <input type="number" name="entry" id="entry" required> 명
            </li>
            <li>
                <label for="startdate">개강일</label>
                <input type="date" name="startdate" id="startdate" min=${datestr} value=${datestr} required>
            </li>
            <li>
                <label>강의세션</label>
                <div class="insert_lec_radio">
                    <ul>
                        <li><input type="radio" name="timesession" id="time1" value="time1*09:00-12:00" required>
                            <label for="time1">09:00-12:00</label></li>
                        <li><input type="radio" name="timesession" id="time2" value="time2*12:00-15:00" required>
                            <label for="time2">12:00-15:00</label></li>
                        <li><input type="radio" name="timesession" id="time3" value="time3*15:00-18:00" required>
                            <label for="time3">15:00-18:00</label></li>
                        <li><input type="radio" name="timesession" id="time4" value="time4*18:00-21:00" required>
                            <label for="time4">18:00-21:00</label></li>
                        <li>
                            <div id="lec_time_check"></div>
                        </li>
                    </ul>
                </div>
            </li>
        </ul>
    </div>
    <%--상단 블럭--%>


    <div class="insertlec_desc row">
        <label for="description">강의 설명</label><br>
        <textarea name="description" id="description" class="form-control" placeholder="강의 설명을 입력해 주세요."></textarea>
    </div>

    <div class="insertlec_submit row">
        <input id="insertlec_btn" type="submit" value="강의 등록 신청하기"/><br>
    </div>

</form>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
<script>
    // 스크립트로 회원계정 아이디 값 넘겨주기
    init(${tid});
</script>
</body>
</html>
