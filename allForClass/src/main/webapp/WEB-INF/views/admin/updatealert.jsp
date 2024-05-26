<%--
  Created by IntelliJ IDEA.
  User: bk
  Date: 2024/05/26
  Time: 1:35 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<script>
    if(${result == 1}){
        alert('강의 정보를 수정하였습니다.');
        location.href="/updatelec/${lid}";
    } else {
        alert('수정에 실패하였습니다.');
        location.href="/updatelec/${lid}";
    }
</script>
</body>
</html>
