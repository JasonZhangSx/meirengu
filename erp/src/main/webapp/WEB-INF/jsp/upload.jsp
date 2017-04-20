<%--
  Created by IntelliJ IDEA.
  User: 建新
  Date: 2017/4/19
  Time: 17:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>图片上传</title>
</head>
<body>
    <form action="upload" method="post" enctype="multipart/form-data">
        <input type="text" name="foldName">
        <input type="file" name="file">
        <input type="submit" value="上传">
    </form>
</body>
</html>
