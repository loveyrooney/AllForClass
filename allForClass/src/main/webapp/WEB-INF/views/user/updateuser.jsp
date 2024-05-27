<%--
  Created by IntelliJ IDEA.
  User: bk
  Date: 2024/05/26
  Time: 9:01 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/resources/css/user/updateuser.css">
</head>
<body>

<div id="out_box">
    <div id="update_top">
        <h1>회원 정보 수정</h1>
    </div>

    <section id="update_section">
        <form method="post" action="/updateuser_result">

            <input type="text" name="uid" id="uid" value="${dto.uid}" hidden>

            <ul>
                <li>
                    <label for="email">이메일</label>
                </li>
                <li>
                    <input type="text" name="email" id="email" value="${dto.email}" class="update_input" required>
                    <button type="button" id="emailCk">중복 체크</button>
                </li>
                <li>
                    <div id="check"></div>
                </li>
                <li>
                    <label for="pwd">비밀번호</label>
                </li>
                <li>
                    <input type="password" name="pwd" id="pwd" class="update_input">
                </li>
                <li>
                    <label for="uname">이름</label>
                </li>
                <li>
                    <input type="text" name="uname" id="uname" value="${dto.uname}" class="update_input" required>
                </li>
                <li>
                    <input type="reset" id="updatereset_btn" value="취소">
                    <input type="submit" id="updateuser_btn" value="수정하기">
                </li>
            </ul>
        </form>
    </section>
</div>


<button type="button" id="deleteuser_btn">회원 탈퇴하기 ></button>

</section>


<script>
    var userEmail = "${dto.email}";
</script>
<script src="/resources/js/user/updateuser.js"></script>
</body>
</html>
