<%--
  Created by IntelliJ IDEA.
  User: xiaoyang
  Date: 2016/12/30
  Time: 16:27
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>医院测试页面</title>
</head>
<body>
<form id="form3" name="form3" method="post" action="/hospital" enctype="multipart/form-data">
    <table border="0" align="center">
        <tr>
            <input type="hidden" name="_method" value="DELETE">
            <td>医院姓名：</td>
            <td><input name="hospitalName" type="input" size="20" value="123"  ></td>
            <td>上传测试文件：</td>
            <td><input name="testImg" type="file" size="20" ></td>
            <td><input name="testImg1" type="file" size="20" ></td>
        </tr>
        <tr>
            <td></td><td>
            <input type="submit" name="submit" value="提交测试图片上传" >
            <input type="reset" name="reset" value="重置" >
        </td>
        </tr>
    </table>
</form>
</body>
</html>
