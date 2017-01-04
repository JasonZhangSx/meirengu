<%--
  Created by IntelliJ IDEA.
  User: 建新
  Date: 2016/12/28
  Time: 0:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>news_cms is running!</title>
    <form action="1" method="post">
        <input type="hidden" name="_method" value="PUT">
        <input type="submit" value="put">
    </form>

    <form action="insert" method="post">
        acId:<input type="text" name="acId"><br>
        url:<input type="text" name="url"><br>
        show:<input type="text" name="show"><br>
        sort:<input type="text" name="sort"><br>
        title:<input type="text" name="title"><br>
        content:<input type="text" name="content"><br>
        <input type="submit" value="post">
    </form>

    <form action="1" method="get">
        <input type="submit" value="get">
    </form>

    <form action="1" method="post">
        <input type="hidden" name="_method" value="DELETE">
        <input type="submit" value="delete">
    </form>
</head>
<body>

</body>
</html>
