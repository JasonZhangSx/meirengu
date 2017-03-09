<%--
  Created by IntelliJ IDEA.
  User: xiaoyang
  Date: 2017/3/1
  Time: 16:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=path%>">
    <title>首页</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="<%=path%>/css/bootstrap.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="<%=path%>/js/jquery.min.js"></script>
    <script src="<%=path%>/js/jquery-form.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="<%=path%>/js/bootstrap.min.js"></script>
</head>
<script>
    function saveReport() {
// jquery 表单提交
        $("#serviceCodeCheckForm").ajaxSubmit(function(message) {
            var map = eval(message);
            if(map.code==200)
            {
                alert("服务码验证成功");
            }else {
                alert(map.msg);
            }
// 对于表单提交成功后处理，message为提交页面saveReport.htm的返回内容
        });

        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
    }
</script>
<body>
<div style="padding:0px; margin:0px;">
    <ul class="breadcrumb" style="  margin:0px; " >
        <li><a href="#">系统管理</a></li>
        <li>服务码验证</li>
    </ul>
</div>
<div class="row alert alert-info"  style="margin:0px; padding:3px;" >
    <form id="serviceCodeCheckForm" class="form-horizontal" action="<%=path%>/service" method="post"  class="form-horizontal" onsubmit="return saveReport();">
        <div class="col-sm-2">服务码:</div>
        <div class="col-sm-3">
            <input type="text"  name="serviceCode" class="form-control input-sm"/>
        </div>
        <input type="submit"   class="btn btn-danger"     value="验证"/>
    </form>
</div>
</div>

</body>
</html>

