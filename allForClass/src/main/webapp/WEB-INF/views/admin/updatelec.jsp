<%--
  Created by IntelliJ IDEA.
  User: bk
  Date: 2024/05/26
  Time: 12:32 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Title</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">
    <link rel="stylesheet" href="/resources/css/admin/updatelec.css">
    <script src="/resources/js/admin/updatelec.js"></script>
</head>
<body class="container">

<h1 class="update_lec_h1">강의 등록 승인</h1>

<form method="post" action="confirm">
    <div class="row justify-content-center">

        <div class="col-md-5 update_lec_imgupload">
            <img src="/getImage/${dto.imgpath}" alt="lecture_img" class="img-thumbnail">
        </div>

        <ul class="col-md-5 update_lec_select">
            <input type="text" name="lid" id="lid" value="${dto.lid}" hidden>
            <li>
                <label for="subject">과목</label>
                <select name="subject" id="subject">
                    <option value="국어" ${dto.subject == '국어'? 'selected="selected"' : '' }>국어</option>
                    <option value="영어" ${dto.subject == '영어'? 'selected="selected"' : '' }>영어</option>
                    <option value="수학" ${dto.subject == '수학'? 'selected="selected"' : '' }>수학</option>
                </select>
            </li>
            <li>
                <label for="lname">강의명</label>
                <input type="text" name="lname" id="lname" value="${dto.lname}" required>
            </li>
            <li>
                <label for="price">가격</label>
                <input type="number" name="price" id="price" value="${dto.price}" required> 원
            </li>
            <li>
                <label for="entry">수강인원</label>
                <input type="number" name="entry" id="entry"  value="${dto.entry}" required> 명
            </li>
            <li>
                <label for="startdate">개강일</label>
                <input type="date" name="startdate" id="startdate" value="${dto.startdate}" required>
            </li>
            <li>
                <label>강의세션</label>
                <div class="update_lec_radio">
                    <ul>
                        <li><input type="radio" name="timesession" id="time1"
                                   value="time1*09:00-12:00" ${dto.timesession == 'time1*09:00-12:00'? 'checked="checked"' : '' } required>
                            <label for="time1">09:00-12:00</label></li>
                        <li><input type="radio" name="timesession" id="time2"
                                   value="time2*12:00-15:00" ${dto.timesession == 'time2*12:00-15:00'? 'checked="checked"' : '' } required>
                            <label for="time2">12:00-15:00</label></li>
                        <li><input type="radio" name="timesession" id="time3"
                                   value="time3*15:00-18:00"  ${dto.timesession == 'time3*15:00-18:00'? 'checked="checked"' : '' } required>
                            <label for="time3">15:00-18:00</label></li>
                        <li><input type="radio" name="timesession" id="time4"
                                   value="time4*18:00-21:00"  ${dto.timesession == 'time4*18:00-21:00'? 'checked="checked"' : '' } required>
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

    <div class="update_desc row">
        <label for="description">강의 설명</label><br>
        <textarea name="description" id="description" class="form-control">${dto.description}</textarea>
    </div>

    <div class="update_submit row">
        <%--<button id="deletelec_btn">강의 삭제</button>--%>
        <input id="resetlec_btn" type="reset" value="취소">
        <input id="confirm_btn" type="submit" value="강의 등록 승인">
    </div>
</form>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>
<script>
    // 스크립트로 회원계정 아이디 값 넘겨주기
    init(${dto.tid});
</script>
</body>
</html>
