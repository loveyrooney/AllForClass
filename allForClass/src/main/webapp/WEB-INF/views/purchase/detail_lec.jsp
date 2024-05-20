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
<body>
<ul>
    <li><img src="/resources/images/${dto.imgpath}" alt="lecture_img"></li>
    <li>${dto.lname} ${dto.tname}</li>
</ul>
</body>
</html>
