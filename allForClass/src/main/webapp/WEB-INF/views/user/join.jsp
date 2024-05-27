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
    <script defer src="/resources/js/user/join.js"></script>
    <link rel="stylesheet" href="/resources/css/user/join.css">
</head>
<body>
<div class="join_wrap">
    <h1>회 원 가 입</h1>
    <form method="post" action="/join_result">
        <ul>
            <li class="join_box">
                <label for="email" class="join_txt">이 메 일</label>
                <div class="emailCk_box">
                    <input type="text" name="email" id="email" class="join_input" required>
                    <input type="button" value="중복 체크" id="emailCk" class="emailCk_btn" <%-- onclick="checkEmail()"--%>>
                </div>
                <%--<span id="email_ok" class="email_ok">사용 가능한 이메일입니다.</span>
                <span id="email_already" class="email_already">중복된 이메일입니다.</span>--%>
                <div id="check"></div>
            </li>
            <li class="join_box">
                <label for="pwd" class="join_txt">비 밀 번 호</label>
                <input type="password" name="pwd" id="pwd"  class="join_input" required>
            </li>
            <li class="join_box">
                <label for="uname" class="join_txt">이 름</label>
                <input type="text" name="uname" id="uname" class="join_input" required>
            </li>
            <li class="join_box">
                <label for="role" class="join_txt">회원 구분</label>
                <select name="role" id="role" class="join_select">
                    <option value="1">수강생</option>
                    <option value="2">강사</option>
                    <option value="3" hidden>관리자</option>
                </select>
            </li>
            <button type="submit" class="join_btn">가입하기</button>
        </ul>
    </form>

</div>

</body>
</html>
