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
<div class="header">
    <a class="home" href="/main"><img src="/resources/images/logo.png" alt="logo"></a>
    <div class="search">
        <form method="get" action="/searchlec" class="searchform">
            <input type="text" name="searchtxt" id="searchtxt" placeholder="찾고 싶은 강의가 있나요?">
            <button type="submit"><img src="/resources/images/search.png" alt="search"></button>
        </form>
    </div>
    <div class="users">
        <c:choose>
            <c:when test="${empty sessionScope or sessionScope.sessionId==null}">
                <a class="login" href="/login"><span>로그인</span></a>
            </c:when>
            <c:otherwise>
                <a class="login" href="/logout"><span>로그아웃</span></a>
                <a class="mypage" href="/mypage/${sessionScope.sessionId}"><img src="/resources/images/mypage.png" alt="mypage_icon"></a>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>
