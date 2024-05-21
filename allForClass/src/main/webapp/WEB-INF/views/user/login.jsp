<%--
  Created by IntelliJ IDEA.
  User: FULL8-007
  Date: 2024-05-21
  Time: 오전 9:10
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/user/login.css">
</head>
<body>
<div class="login_wrap">
<h1>로 그 인</h1>
<form method="post" action="/login_result">
    <ul>
        <li class="login_box">
            <label for="email" class="login_txt">이 메 일</label>
            <input type="text" name="email" id="email" class="login_input" required>
        </li>
        <li class="login_box">
            <label for="pwd" class="login_txt">비밀번호</label>
            <input type="password" name="pwd" id="pwd"  class="login_input" required>
        </li>
        <li class="login_btn">
            <button type="submit" class="login_btn">로그인</button>
            <a href="/join"><button type="button" class="login_join">회원가입>></button></a>
        </li>
    </ul>
</form>

</div>
</body>
</html>
