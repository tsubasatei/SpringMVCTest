<%--
  Created by IntelliJ IDEA.
  User: XT
  Date: 2019/11/6
  Time: 15:30
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <fmt:message key="i18n.username"></fmt:message>

    <br>
    <a href="/i18n2">I18N2 Page</a>

    <br>
    <a href="/springMVC/testI18n?locale=zh_CN">中文</a>

    <br>
    <a href="/springMVC/testI18n?locale=en_US">英文</a>
</body>
</html>
