<%@ page language="java" import="java.util.*" pageEncoding="gbk"%>
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
<!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
<script src="<%=path%>/js/bootstrap.min.js"></script>
<script type="text/javascript">
      $(function(){
         $("#sel").click(function(){
			var com=$("#con").val();
			if("1"==com){
				$("#context").attr("name","sname");
			}else if("2"==com){
				$("#context").attr("name","sex");
			}
			$("#con_from").submit();
		});
      });
</script>

</head>

<body>
<div style="padding:0px; margin:0px;">
 <ul class="breadcrumb" style="  margin:0px; " >
    	<li><a href="#">系统管理</a></li>
        <li>医院列表</li>
    </ul>
</div>
<div class="row alert alert-info"  style="margin:0px; padding:3px;" >
<form action="<%=path%>//hospital/show" method="get" class="form-horizontal" id="con_from">
	<div class="col-sm-1" >条件:</div>
    <div class="col-sm-3">
    	<select id="con" class="form-control  input-sm">
        	<option value="1">姓名</option>
            <option value="2">性别</option>
        </select>
    </div>
    <div class="col-sm-3">
    	<input type="text" id="context" name="" class="form-control input-sm"/>
    </div>
    <input type="button" id="sel"   class="btn btn-danger"   value="查询"/>
    <input type="button"   class="btn btn-success"   value="添加" onClick="javascript:window.location='<%=path%>/itemPro/add'"/>
    </form>
</div>
<div class="row" style="padding:15px; padding-top:0px; ">
	<table class="table  table-condensed table-striped">
    	<tr>
            <th>项目名称</th>
            <th>副标题</th>
            <th>项目分类名称</th>
            <th>医生名称</th>
            <th>医院名称</th>
            <th>组合套餐</th>
            <th>售价</th>
             <th>操作</th>
        </tr>
        <c:forEach items="${map.data.list}" var="h">
        <tr>
            <td>${h.itemName}</td>
            <td>${h.subtitle}</td>
            <td>${h.icName}</td>
            <td>${h.doctorName}</td>
            <td>${h.hospitalName}</td>
            <c:choose>
                <c:when test="${h.itemFlag == '0'}">
                    <td>否</td>
                </c:when>
                <c:when test="${h.hospitalType == '1'}">
                    <td>是</td>
                </c:when>
                <c:otherwise>
                    <td>未知</td>
                </c:otherwise>
            </c:choose>
            <td>${h.sellingPrice}</td>
            <td>
                <a  href="<%=path%>/hospital/detail?hospitalId=${h.hospitalId}">查看</a>
            	<a  href="<%=path%>/hospital/detailUpdate?hospitalId=${h.hospitalId}">修改</a>
            </td>
        </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
