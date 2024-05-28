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
<%--    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH" crossorigin="anonymous">--%>
    <link rel="stylesheet" href="/resources/css/admin/updatelec.css">
    <script src="/resources/js/admin/updatelec.js"></script>
</head>
<body>

<h1 class="update_lec_h1">강의 등록 확인</h1>

<%--강의 승인 폼--%>
<div class="form_container">
<form method="post" action="/updatelec_result">
    <div class="form_top">

        <%--썸네일 이미지--%>
        <div class="update_lec_imgupload">
            <img src="/getImage/${dto.imgpath}" alt="lecture_img" class="img-thumbnail">
        </div>

        <%--강의 정보--%>
        <ul class="update_lec_select">
            <input type="text" name="lid" id="lid" value="${dto.lid}" hidden>
            <li>
                <label for="subject" class="update_lec_label">과목</label>
                <select name="subject" id="subject">
                    <option value="국어" ${dto.subject == '국어'? 'selected="selected"' : '' }>국어</option>
                    <option value="영어" ${dto.subject == '영어'? 'selected="selected"' : '' }>영어</option>
                    <option value="수학" ${dto.subject == '수학'? 'selected="selected"' : '' }>수학</option>
                </select>
            </li>
            <li>
                <label for="lname" class="update_lec_label">강의명</label>
                <input type="text" name="lname" id="lname" value="${dto.lname}" class="update_lec_input" required>
            </li>
            <li>
                <label for="price" class="update_lec_label">가격</label>
                <input type="number" name="price" id="price" value="${dto.price}" class="update_lec_input" required> 원
            </li>
            <li>
                <label for="entry" class="update_lec_label">수강인원</label>
                <input type="number" name="entry" id="entry" value="${dto.entry}" class="update_lec_input" required> 명
            </li>
            <li>
                <label for="startdate" class="update_lec_label">개강일</label>
                <input type="date" name="startdate" id="startdate" value="${dto.startdate}" required>
            </li>
            <li>
                <label class="update_lec_label">강의세션</label>
                <div class="update_lec_radio">
                    <ul>
                        <li><input type="radio" name="timesession" id="time1"
                                   value="time1*09:00-12:00" ${dto.timesession == 'time1*09:00-12:00'? 'checked="checked"' : '' }
                                   required>
                            <label for="time1">09:00-12:00</label></li>
                        <li><input type="radio" name="timesession" id="time2"
                                   value="time2*12:00-15:00" ${dto.timesession == 'time2*12:00-15:00'? 'checked="checked"' : '' }
                                   required>
                            <label for="time2">12:00-15:00</label></li>
                        <li><input type="radio" name="timesession" id="time3"
                                   value="time3*15:00-18:00"  ${dto.timesession == 'time3*15:00-18:00'? 'checked="checked"' : '' }
                                   required>
                            <label for="time3">15:00-18:00</label></li>
                        <li><input type="radio" name="timesession" id="time4"
                                   value="time4*18:00-21:00"  ${dto.timesession == 'time4*18:00-21:00'? 'checked="checked"' : '' }
                                   required>
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

    <%--강의 설명 텍스트 입력--%>
    <div class="update_desc row">
        <label for="description" class="update_lec_label">강의 설명</label><br>
        <textarea name="description" id="description" class="form-control">${dto.description}</textarea>
    </div>

    <%--버튼--%>
    <div class="update_submit row">
        <button type="button" id="deletelec_btn">강의 삭제</button>
        <input type="reset" id="resetlec_btn" value="되돌리기">
        <input type="submit" id="updatelec_btn" value="수정">
    </div>
    <div class="update_submit row">
        <button type="button" id="confirm_btn">강의 등록 승인</button>
    </div>
</form>

</div>

<%--<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
        crossorigin="anonymous"></script>--%>
<script>
    // 스크립트로 강의정보 값 넘겨주기
    let inits = {
        tid          :${dto.tid}
        , lid        :${dto.lid}
        , startdate  : ''
        , timesession: ''
    }
    inits['startdate'] = `${dto.startdate}`;
    inits['timesession'] = `${dto.timesession}`;
    init(inits);
</script>
</body>
</html>
