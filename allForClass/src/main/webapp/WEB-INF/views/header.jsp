<%--
  Created by IntelliJ IDEA.
  User: FULL8-011
  Date: 2024-05-20
  Time: 오후 6:55
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<div id="header">
    <a class="home" href="/main"><img src="/resources/images/logo.png" alt="logo"></a>
<%--    <c:choose>--%>
<%--        <c:when test="${empty sessionScope.sessionID}">--%>
<%--            <a class="login" href="login.do"><span>로그인</span></a>--%>
<%--        </c:when>--%>
<%--        <c:otherwise>--%>
<%--            <a class="login" href="logout.do"><span>로그아웃</span></a>--%>
            <a class="mypage" href="/mypage"><span>마이페이지</span></a>
<%--        </c:otherwise>--%>
<%--    </c:choose>--%>
</div>
</body>
</html>
