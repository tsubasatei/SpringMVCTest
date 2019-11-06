<%--
  Created by IntelliJ IDEA.
  User: XT
  Date: 2019/10/3
  Time: 19:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>首页</title>
</head>
<body>

    <br><br>
    <a href="/springMVC/testSimpleMappingExceptionResolver?i=10">Test SimpleMappingExceptionResolver</a>

    <br><br>
    <a href="/springMVC/testDefaultHandlerExceptionResolver">Test DefaultHandlerExceptionResolver</a>

    <br><br>
    <a href="/springMVC/testResponseStatusExceptionResolver?i=1">Test ResponseStatusExceptionResolver</a>

    <br><br>
    <a href="/springMVC/testExceptionHandlerExceptionResolver?i=1">Test ExceptionHandlerExceptionResolver</a>

    <br><br>
    <form action="/springMVC/testFileUpload" method="post" enctype="multipart/form-data">
        file: <input type="file" name="file">
        <br>
        desc: <input type="text" name="desc">
        <br>
        <input type="submit" value="testFileUpload">
    </form>

    <!--
        关于国际化:
        1. 在页面上能够根据浏览器语言设置的情况对文本(不是内容), 时间, 数值进行本地化处理
        2. 可以在 bean 中获取国际化资源文件 Locale 对应的消息
        3. 可以通过超链接切换 Locale, 而不再依赖于浏览器的语言设置情况

        解决:
        1. 使用 JSTL 的 fmt 标签
        2. 在 bean 中注入 ResourceBundleMessageSource 的示例, 使用其对应的 getMessage 方法即可
        3. 配置 LocalResolver 和 LocaleChangeInterceptor
    -->

    <%--<a href="/i18n">I18N Page</a>--%>
    <a href="/springMVC/testI18n">I18N Page</a>
    <br><br>

    <a href="/springMVC/testResponseEntity">Test ResponseEntity</a>
    <br><br>

    <form action="/springMVC/testHttpMessageConverter" method="post" enctype="multipart/form-data">
        file: <input type="file" name="file">
        <br>
        desc: <input type="text" name="desc">
        <br>
        <input type="submit" value="testHttpMessageConverter">
    </form>

    <br><br>
    <a id="testJson" href="/springMVC/testJson">Test Json</a>

    <br><br>
    <a href="/emp/emps">员工列表</a>

    <br><br>
    <a href="/springMVC/testRedirect">Test Redirect</a>

    <br><br>
    <a href="/springMVC/testHelloView">Test HelloView</a>

    <br><br>
    <a href="/springMVC/testViewAndViewResolver">Test ViewAndViewResolver</a>
    <br><br>
    <%--
        模拟修改操作：
        1. 原始数据为：1, Tom, 123456, tom@163.com, 30
        2. 密码不能修改
        3. 表单回显，模拟操作直接在表单填写对象的属性值
    --%>
    <form action="/springMVC/testModelAttribute" method="post">
        <input type="hidden" name="id" value="1">
        username: <input type="text" name="username" value="Tom"><br>
        email: <input type="text" name="email" value="tom@163.com"><br>
        age: <input type="text" name="age" value="30"><br>
        <button type="submit">Submit</button>
    </form>
    <a href="/springMVC/testSessionAttributes">Test SessionAttributes</a>
    <br><br>

    <a href="/springMVC/testMap">Test Map</a>
    <br><br>

    <a href="/springMVC/testModelAndView">Test ModelAndView</a>
    <br><br>

    <a href="/springMVC/testServletAPI">Test ServletAPI</a>
    <br><br>

    <form action="/springMVC/testPOJO" method="post">
        用户名：<input type="text" name="username"><br>
        密码：<input type="password" name="password"><br>
        邮箱：<input type="email" name="email"><br>
        年龄：<input type="text" name="age"><br>
        省份：<input type="text" name="address.province"><br>
        城市：<input type="text" name="address.city"><br>
        <input type="submit" value="Submit POJO">
    </form>

    <a href="/springMVC/testCookieValue">Test CookieValue</a>
    <br><br>

    <a href="/springMVC/testRequestHeader">Test RequestHeader</a>
    <br><br>

    <a href="/springMVC/testRequestParam?username=springmvc&age=10">Test RequestParam</a>
    <br><br>

    <form action="/springMVC/testRestDelete/1" method="post">
        <input type="hidden" name="_method" value="DELETE">
        <input type="submit" value="Test Rest Delete">
    </form>
    <br>

    <form action="/springMVC/testRestPut/1" method="post">
        <input type="hidden" name="_method" value="PUT">
        <input type="submit" value="Test Rest Put">
    </form>
    <br>

    <form action="/springMVC/testRestPost" method="post">
        <input type="submit" value="Test Rest Post">
    </form>
    <br>

    <a href="/springMVC/testRestGet/1">Test Rest Get</a>
    <br><br>

    <a href="/springMVC/testPathVariable/1">Test PathVariable</a>
    <br><br>

    <a href="/springMVC/testAnt/xy/abc">Test Ant</a>
    <br><br>

    <a href="/springMVC/testParamsAndHeaders?username=springmvc&age=16">Test ParamsAndHeaders</a>
    <br><br>

    <form action="/springMVC/testMethod" method="post">
        <input type="submit" value="Test Method">
    </form>
    <br>

    <a href="/springMVC/testRequestMapping">Test RequestMapping</a>
    <br><br>

    <a href="/helloWorld">Hello World</a>

    <script src="${pageContext.request.contextPath}/assets/js/jquery-1.9.1.min.js"></script>
    <script>
        $(function () {
            $("#testJson").click(function () {
                var url = this.href;
                var params = {};
                $.post(url, params, function (data) {
                    if (data != null) {
                        for (var i=0; i<data.length; i++) {
                            var id = data[i].id;
                            var lastName = data[i].lastName;

                            alert("id=" + id + ", lastName=" + lastName);
                        }
                    }
                });
                return false;
            });
        })
    </script>
</body>
</html>
