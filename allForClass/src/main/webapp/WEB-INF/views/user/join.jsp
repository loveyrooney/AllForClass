<%--
  Created by IntelliJ IDEA.
  User: FULL8-007
  Date: 2024-05-21
  Time: 오후 3:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<div class="join_wrap">
    <h1>회 원 가 입</h1>
    <form method="post" action="/join_result">
        <ul>
            <li class="join_box">
                <label for="email" class="login_txt">이 메 일</label>
                <input type="text" name="email" id="email" class="login_input" required>
            </li>
            <li class="join_box">
                <label for="pwd" class="login_txt">비밀번호</label>
                <input type="password" name="pwd" id="pwd"  class="login_input" required>
            </li>
            <li class="join_box">
                <label for="uname" class="login_txt">이  름</label>
                <input type="text" name="uname" id="uname" class="login_input" required>
            </li>
            <li class="join_box">
                <label for="role">회원 구분</label>
                <select name="role" id="role">
                    <option value="1">수강생</option>
                    <option value="2">강사</option>
                    <option value="3" hidden>관리자</option>
                </select>
            </li>
            <button type="submit" class="login_btn">가입하기</button>
        </ul>
    </form>

</div>

</body>
</html>
