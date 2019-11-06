<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %><%--
  Created by IntelliJ IDEA.
  User: XT
  Date: 2019/11/5
  Time: 12:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%-- 解决 requestScope 域对象数据不显示 --%>
<%@ page  isELIgnored = "false" %>
<%-- 解决 tomcat7 以上版本 JSPs only permit GET, POST or HEAD. Jasper also permits OPTIONS --%>
<%@ page isErrorPage="true" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<body>

    <%-- String --> Employee 类型转换 --%>
    <form action="/springMVC/testConvert" method="post">
        Employee: <input type="text" name="employee" placeholder="GG-gg@163.com-1-1005"/>
        <input type="submit" value="Submit"/>
    </form>

    <br><br>

<%--${depts}--%>
<%--
    1. WHY 使用 form 标签呢？
    可以更快速的开发出表单页面，而且可以更方便的进行表单值的回显
    path 属性对应 html 表单标签的 name 属性值

    2. 注意：
        可以通过 modelAttribute 属性指定绑定的模型属性
        若没有指定该属性，则默认从 request 域对象中读取 command 的表单 bean
        如果该属性值也不存在，则会发生错误
--%>
<form:form action="${pageContext.request.contextPath}/emp/emp" method="post" modelAttribute="employee">
    <form:errors path="*"></form:errors>
    <br><br>
    <c:if test="${employee.id == null}">
        LastName: <form:input path="lastName"></form:input>
        <form:errors path="lastName"/>
    </c:if>
    <c:if test="${employee.id != null}">
        <input type="hidden" name="_method" value="put">
        <form:hidden path="id" />
        <%--
            对于 _method 不能使用 form:hidden 标签，因为 modelAttribute 对应的 bean 没有 _method 这个属性
            <form:hidden path="_method" value="PUT" />
        --%>
    </c:if>
    <br>
    Email: <form:input path="email"></form:input>
    <form:errors path="email"/>
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
    <%--
        默认日期格式用 / 分隔
        1. 数据类型转换
        2. 数据类型转换
        3. 数据校验

        1). 如何校验 ? 注解 ?
            ①. 使用 JSR 303 验证标准
            ②. 加入 hibernate validator 验证框架的 jar 包
            ③. 在 SpringMVC 配置文件中添加 <mvc:annotation-driven />
            ④. 需要在 bean 的属性上添加对应的注解
            ⑤. 在目标方法 bean 类型的前面添加 @Valid 注解

        2). 验证出错转向到哪一个页面 ?
        注意: 需校验的 Bean 对象和其绑定结果对象或错误对象时成对出现的，它们之间不允许声明其他的入参

        3). 错误消息 ? 如何显示, 如何把错误消息进行国际化
    --%>
    <fmt:formatDate value="${employee.birth }" pattern="yyyy-MM-dd" var="birthday"/>
    Birth: <form:input path="birth" value="${birthday}"/>
    <form:errors path="birth"/>
    <br>
    Salary: <form:input path="salary"/>
    <br>

    <input type="submit" value="${employee.id == null ? '添加' : '修改'}"/>
</form:form>

</body>
<head>
    <title>Title</title>
</head>
</html>
