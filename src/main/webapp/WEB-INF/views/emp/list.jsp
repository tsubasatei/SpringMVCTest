<%--
  Created by IntelliJ IDEA.
  User: XT
  Date: 2019/11/5
  Time: 12:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page isErrorPage="true" %>
<%-- 解决 requestScope 域对象数据不显示 --%>
<%--<%@ page  isELIgnored = "false" %>--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>
<head>
    <title>员工列表</title>

    <%--
        SpringMVC 处理静态资源：
        1. 为什么会有这样的问题：
        优雅的 REST 风格的资源 URL 不希望带 .html 或 .do 等后缀
        若将 DispatcherServlet 请求映射配置为 /
        则 SpringMVC 将捕获 WEB 容器的所有请求，包括静态资源的请求，SpringMVC 会将他们当成一个普通请求处理
        因此找不到对应处理器将导致错误

        2. 解决：在 SpringMVC 的配置文件中配置 <mvc:default-servlet-handler/>

        3. 注意：引入 js 代码必须写成 <script></script>, 千万不能简写，不要再犯这种错误
    --%>
    <script type="text/javascript" src="${pageContext.request.contextPath}/assets/js/jquery-1.9.1.min.js"></script>
    <script>
        $(function () {
            $(".delete").click(function () {
                var href = $(this).attr("href");
                $("#deleteBtnForm").attr("action", href).submit();
                return false;
            })
        })
    </script>
</head>
<body>

    <%--${requestScope.emps}--%>
    <form id="deleteBtnForm" action="#" method="post">
        <input type="hidden" name="_method" value="delete">
    </form>
    <a href="/emp/emp">添加</a>
    <c:if test="${emps == null}">
        还没有员工信息
    </c:if>
    <c:if test="${emps != null}">
        <table border="1" cellpadding="10" cellspacing="0">
            <thead>
            <tr>
                <td>ID</td>
                <td>LastName</td>
                <td>Email</td>
                <td>Gender</td>
                <td>DepartmentName</td>
                <td>Birth</td>
                <td>Edit</td>
                <td>Delete</td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${emps}" var="emp">
                <tr>
                    <td>${emp.id}</td>
                    <td>${emp.lastName}</td>
                    <td>${emp.email}</td>
                    <td>${emp.gender=='0'? '女' : '男'}</td>
                    <td>${emp.department.departmentName}</td>
                    <td><fmt:formatDate value="${emp.birth}" pattern="yyyy-MM-dd"></fmt:formatDate> </td>
                    <td><a href="/emp/emp/${emp.id}">编辑</a></td>
                    <td><a class="delete" href="/emp/emp/${emp.id}">删除</a></td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </c:if>
</body>
</html>
