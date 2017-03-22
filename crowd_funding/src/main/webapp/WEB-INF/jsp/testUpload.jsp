<%--
  Created by IntelliJ IDEA.
  User: 建新
  Date: 2017/3/22
  Time: 16:38
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
    <form action="/cf/upload/test" enctype="multipart/form-data" method="post">
        <input type="file" name="file">
        <input type="submit" value="上传">
    </form>
</body>
</html>
