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
    <title>Title</title>
</head>
<body>

<h1>강의 등록 신청</h1>

<form method="post" action="insertlec_result" enctype="multipart/form-data">

    <div>
        <label for="imgfile">강의 썸네일 등록</label><br>
        <input type="file" id="imgfile" name="imgfile">
    </div>

    <div>
        <ul>
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
                <input type="text" name="price" id="price" required>
            </li>
            <li>
                <label for="startdate">개강일</label>
                <input type="date" name="startdate" id="startdate" min=${datestr} value=${datestr}>
            </li>
            <li>
                <label>강의세션</label>
                <input type="radio" name="timesession" id="time1" value="time1*09:00-12:00" checked>
                <label for="time1">09:00-12:00</label>
                <input type="radio" name="timesession" id="time2" value="time2*12:00-15:00">
                <label for="time2">12:00-15:00</label>
                <input type="radio" name="timesession" id="time3" value="time3*15:00-18:00">
                <label for="time3">15:00-18:00</label>
                <input type="radio" name="timesession" id="time4" value="time4*18:00-21:00">
                <label for="time4">18:00-21:00</label>
            </li>
        </ul>
    </div>

    <div>
        <label for="description">강의 설명</label><br>
        <textarea name="description" id="description" placeholder="강의 설명입니다." cols=40 rows="6"></textarea>
    </div>

    <div>
        <input type="submit" value="강의 등록 신청하기"/><br>
    </div>

</form>

</body>
</html>
