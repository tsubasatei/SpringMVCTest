<%--
  Created by IntelliJ IDEA.
  User: XT
  Date: 2019/11/6
  Time: 19:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error</title>
</head>
<body>
    <h1>Error Page</h1>
    ${requestScope.exception}
    <hr>
    ${requestScope.ex}
</body>
</html>
