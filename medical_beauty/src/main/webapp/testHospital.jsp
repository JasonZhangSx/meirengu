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
<a href="/hospital/selectionHospital.do">点击测试甄选医院页面数据</a>
<form id="form" name="form" method="post" action="/hospital/addHospital.do" enctype="multipart/form-data">
    <table border="0" align="center">
        <tr>
            <td>医院姓名：</td>
            <td><input name="hospitalName" type="input" size="20" value="123" ></td>
            <td>上传文件1：</td>
            <td><input name="certificatePicOne" type="file" size="20" ></td>
            <td>上传文件2：</td>
            <td><input name="certificatePicTwo" type="file" size="20" ></td>
        </tr>
        <tr>
            <td></td><td>
            <input type="submit" name="submit" value="提交测试添加医生" >
            <input type="reset" name="reset" value="重置" >
        </td>
        </tr>
    </table>
</form>
<form id="form1" name="form1" method="post" action="/hospital/updateHospital.do" enctype="multipart/form-data">
    <table border="0" align="center">
        <tr>
            <td>医院姓名：</td>
            <td><input name="hospitalName" type="input" size="20" ></td>
        </tr>
        <tr>
            <td></td><td>
            <input type="submit" name="submit" value="提交测试修改医生" >
            <input type="reset" name="reset" value="重置" >
        </td>
        </tr>
    </table>
</form>
<form id="form2" name="form2" method="patch" action="/hospital/deleteHospital.do" enctype="multipart/form-data">
    <table border="0" align="center">
        <tr>
            <td>医院id：</td>
            <td><input name="hospitalName" type="input" size="20" ></td>
        </tr>
        <tr>
            <td></td><td>
            <input type="submit" name="submit" value="提交测试删除医生" >
            <input type="reset" name="reset" value="重置" >
        </td>
        </tr>
    </table>
</form>
</body>
</html>
