<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: FULL8-011
  Date: 2024-05-20
  Time: 오후 7:11
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="/resources/css/purchase/detail_lec.css">
</head>
<body>
<div class="detail_wrap">
    <section class="lec_thumbnail">
        <img src="/resources/images/${dto.imgpath}" alt="lecture_img">
        <p>${dto.startdate} | ${dto.timesession}</p>
    </section>
    <section class="lec_info">
        <ul>
            <li>과목> ${dto.subject}</li>
            <li>${dto.lname}</li>
            <li>강사 : ${dto.tname} (${dto.temail})</li>
            <li>${dto.price}원 | 20명 중 2명 남음</li>
            <li class="btns">
                <c:choose>
                    <c:when test="">
                        <a class="lec_regi_btn" href="">수강하기</a>
                    </c:when>
                    <c:otherwise>
                        <a class="lec_regi_btn" href="">수강취소</a>
                    </c:otherwise>
                </c:choose>
<%--                <c:if test="">--%>
                    <a class="lec_enter_btn" href="">강의실 입장</a>
<%--                </c:if>--%>
            </li>
        </ul>
    </section>
    <section class="lec_description">
        <h3>강의 설명</h3>
        <p>${dto.description}</p>
    </section>
</div>
</body>
</html>
