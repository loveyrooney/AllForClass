<%--
  Created by IntelliJ IDEA.
  User: FULL8-011
  Date: 2024-05-20
  Time: 오후 5:56
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/mainlist.css">
</head>
<body>
<div class="mainlist_wrap">
<%--배너--%>
<article>
    <img src="/resources/images/banner.png" alt="banner" class="mainlist_banner">
</article>

<%--강의 리스트--%>
<article>
    <h3 class="list_line">강의 목록</h3>
    <div class="mainlist_lec">
        <c:forEach var="item" items="${list}" >
            <ul>
                    <li><a href="/detail_lec/${item.lid}"><img src="/getImage/${item.imgpath}" alt="lecture_img"></a></li>
                    <li class="mainlist_lecname"><a href="/detail_lec/${item.lid}">${item.lname}</a></li>
                    <li class="mainlist_lecprice"><fmt:formatNumber pattern="###,###,###" value="${item.price}"/>원</li>
            </ul>
        </c:forEach>
    </div>

</article>
</div>
</body>
</html>
