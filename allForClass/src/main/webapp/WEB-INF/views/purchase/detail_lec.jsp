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
    <script src="/resources/js/purchase/detail_lec.js"></script>
    <script src="https://cdn.portone.io/v2/browser-sdk.js"></script>
</head>
<body>
<div class="detail_wrap">
    <section class="lec_thumbnail">
        <img src="/resources/images/${dto.imgpath}" alt="lecture_img">
        <p><span id="startdate">${dto.startdate}</span> | <span id="timesession">${dto.timesession}</span></p>
    </section>
    <section class="lec_info">
        <ul>
            <li>과목> ${dto.subject}</li>
            <li id="lname">${dto.lname}</li>
            <li>강사 : ${dto.tname} (${dto.temail})</li>
            <li><span id="price">${dto.price}</span>원 | <span id="entry">${dto.entry}</span>명 중 ${reserve}명 남음</li>
            <li class="btns">
                <c:choose>
                    <c:when test="${!empty available and available==1}">
                        <c:if test="${empty role}">
                            <a class="lec_enter_btn" href="/login">수강하기</a>
                        </c:if>
                        <c:if test="${!empty role and role.equals('student')}">
                            <c:if test="${empty pid}">
                                <button class="lec_regi_btn" id="pay" type="button">수강하기</button>
                            </c:if>
                            <c:if test="${!empty pid}">
                                <button class="lec_regi_btn" id="refund" type="button">수강취소</button>
                            </c:if>
                        </c:if>
                    </c:when>
                    <c:otherwise>
                        <button class="lec_block_btn" type="button">수강마감</button>
                    </c:otherwise>
                </c:choose>
                <c:if test="${!empty enterroom and enterroom==1}">
                    <a class="lec_enter_btn" href="/room/${dto.lid}">강의실 입장</a>
                </c:if>
            </li>
        </ul>
    </section>
    <section class="lec_description">
        <h3>강의 설명</h3>
        <p>${dto.description}</p>
    </section>
</div>
<script>
    let inits = {
        p_lid: ${dto.lid}
        ,p_uid:0
        ,storeId:''
        ,channelKey:''
        ,fullName:''
        ,email:''
        ,p_pid:0
    }
    inits['p_uid'] = ${sessionScope.sessionId}
    inits['storeId'] = `${storeId}`;
    inits['channelKey'] = `${channelKey}`;
    inits['fullName'] = `${user.uname}`;
    inits['email'] = `${user.email}`;
    inits['p_pid'] = ${pid}
    init(inits);
</script>
</body>
</html>
