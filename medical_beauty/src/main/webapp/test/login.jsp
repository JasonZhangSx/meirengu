<%--
  Created by IntelliJ IDEA.
  User: xiaoyang
  Date: 2017/2/16
  Time: 20:49
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
</head>
<body>
<form id="form1" name="form1" method="post" action="/home/login.do">
    <p align="center">用户登录</p>
    <table width="296" border="1" align="center" bgcolor="#00FF99">
        <tr>
            <td width="98" height="34">用户名：</td>
            <td width="182"><label><input name="loginName"
                                          type="text" id="loginName" /> </label></td>
        </tr>

        <tr>
            <td height="36">密码：</td>
            <td><label> <input name="password" type="password"
                               id="password" />
            </label></td>
        </tr>
        <tr>
            <td height="35" colspan="2"><label>      
                <input type="submit" name="Submit" value="提交" />
            </label> <label>       <input
                    type="reset" name="Submit2" value="重置" />
            </label></td>
        </tr>
    </table>
</form>
</body>
</html>