<%--
  Created by IntelliJ IDEA.
  User: FULL8-011
  Date: 2024-05-20
  Time: 오후 6:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>${title}</title>
  <link rel="stylesheet" href="/resources/css/main.css">
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Black+Han+Sans&family=Noto+Sans+KR:wght@100..900&display=swap" rel="stylesheet">


</head>
<body>
<div id="wrap" class="poor-story-regular">
  <div class="content_wrap">
    <header id="header">
      <jsp:include page="header.jsp"/>
    </header>
    <section id="jsp_body">
      <jsp:include page="${body}"/>
    </section>
  </div>
  <footer id="footer">
    <jsp:include page="footer.jsp"/>
  </footer>
</div>
</body>
</html>
