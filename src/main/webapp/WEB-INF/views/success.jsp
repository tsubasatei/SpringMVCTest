<%--
  Created by IntelliJ IDEA.
  User: XT
  Date: 2019/10/3
  Time: 19:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- 解决 tomcat7 以上版本 JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS --%>
<%@ page isErrorPage="true" %>
<%-- 解决 requestScope 域对象数据不显示 --%>
<%@ page  isELIgnored = "false" %>
<%-- 引入 fmt 标签--%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
<head>
    <title>Hello World</title>
</head>
<body>
    <h2>SUCCESS!</h2>
    time: ${requestScope.time }

    <br><br>
    names: ${requestScope.names}

    <br><br>
    request user: ${requestScope.user}
    <br>
    session user: ${sessionScope.user}
    <br><br>

    request it: ${requestScope.it}
    <br>
    session it: ${sessionScope.it}
    <br><br>

    implicitModel user: ${requestScope.user}
    <br>

    <fmt:message key="i18n.username"></fmt:message>
    <br>
    <fmt:message key="i18n.password"></fmt:message>

</body>
</html>
