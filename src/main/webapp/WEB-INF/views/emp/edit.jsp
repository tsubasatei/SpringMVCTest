<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %><%--
  Created by IntelliJ IDEA.
  User: XT
  Date: 2019/11/5
  Time: 21:26
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>编辑</title>
</head>
<body>
<form:form action="${pageContext.request.contextPath}/emp/emp" method="post" modelAttribute="employee">
    <input type="hidden" name="_method" value="put">
    <form:hidden path="id" />
    LastName: <form:input path="lastName" ></form:input>
    <br>
    Email: <form:input path="email"></form:input>
    <br>
    <%
        Map<String, String> genders = new HashMap<>();
        genders.put("0", "女");
        genders.put("1", "男");

        request.setAttribute("genders", genders);
    %>
    Gender:<form:radiobuttons path="gender" items="${genders}"/>
    <br>
    DepartmentName: <form:select path="department.id" items="${depts}"
                                 itemLabel="departmentName" itemValue="id"/>
    <br>
    <%-- 默认日期格式用 / 分隔--%>
    <fmt:formatDate value="${employee.birth }" pattern="yyyy-MM-dd" var="birthday"/>
    Birth: <form:input path="birth" value="${birthday}"/>
    <br>
    <input type="submit" value="修改"/>
</form:form>
</body>
</html>
