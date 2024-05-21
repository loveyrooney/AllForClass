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
    <script src="https://cdn.portone.io/v2/browser-sdk.js"></script>
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
                    <c:when test="${!empty isReserved and !isReserved}">
                        <button class="lec_regi_btn" type="button" onclick="requestPayment()">수강하기</button>
                    </c:when>
                    <c:when test="${!empty isReserved and isReserved}">
                        <button class="lec_regi_btn" type="button" onclick="">수강취소</button>
                    </c:when>
                    <c:otherwise>
                        <a class="lec_regi_btn" href="/login">수강하기</a>
                    </c:otherwise>
                </c:choose>
                <c:if test="${!empty role}">
                    <a class="lec_enter_btn" href="">강의실 입장</a>
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
    <%--function requestPayment() {--%>
    <%--    PortOne.requestPayment({--%>
    <%--        storeId: "store-4ff4af41-85e3-4559-8eb8-0d08a2c6ceec", // 고객사 storeId로 변경해주세요.--%>
    <%--        channelKey: "channel-key-9987cb87-6458-4888-b94e-68d9a2da896d", // 콘솔 결제 연동 화면에서 채널 연동 시 생성된 채널 키를 입력해주세요.--%>
    <%--        paymentId: `payment${crypto.randomUUID()}`,--%>
    <%--        orderName: "나이키 와플 트레이너 2 SD",--%>
    <%--        totalAmount: 1000,--%>
    <%--        currency: "CURRENCY_KRW",--%>
    <%--        payMethod: "CARD",--%>
    <%--        customer: {--%>
    <%--            fullName: "포트원",--%>
    <%--            phoneNumber: "010-0000-1234",--%>
    <%--            email: "test@portone.io",--%>
    <%--        },--%>
    <%--    });--%>
    <%--}--%>
</script>
</body>
</html>
