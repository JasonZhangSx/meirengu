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
<title>��ҳ</title>
<!-- �� Bootstrap ���� CSS �ļ� -->
<link rel="stylesheet" href="<%=path%>/css/bootstrap.min.css">
<!-- jQuery�ļ��������bootstrap.min.js ֮ǰ���� -->
<script src="<%=path%>/js/jquery.min.js"></script>
<!-- ���µ� Bootstrap ���� JavaScript �ļ� -->
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
    	<li><a href="#">ϵͳ����</a></li>
        <li>ҽԺ�б�</li>
    </ul>
</div>
<div class="row alert alert-info"  style="margin:0px; padding:3px;" >
<form action="<%=path%>/hospital/show" method="get" class="form-horizontal" id="con_from">
	<div class="col-sm-1" >����:</div>
    <div class="col-sm-3">
    	<select id="con" class="form-control  input-sm">
        	<option value="1">����</option>
            <option value="2">�Ա�</option>
        </select>
    </div>
    <div class="col-sm-3">
    	<input type="text" id="context" name="" class="form-control input-sm"/>
    </div>
    <input type="button" id="sel"   class="btn btn-danger"   value="��ѯ"/>
    <input type="button"   class="btn btn-success"   value="���" onClick="javascript:window.location='<%=path%>/view/hospital/hospital_add.jsp'"/>
    </form>
</div>
<div class="row" style="padding:15px; padding-top:0px; ">
	<table class="table  table-condensed table-striped">
    	<tr>
            <th>ҽԺ����</th>
            <th>ҽԺ����</th>
            <th>ҽԺ��ַ</th>
            <th>ҽԺ�绰</th>
            <th>ҽ������</th>
            <th>������</th>
             <th>����</th>
        </tr>
        <c:forEach items="${map.data.list}" var="h">
        <tr>
            <td>${h.hospitalName}</td>
            <c:choose>
                <c:when test="${h.hospitalType == '1'}">
                    <td>����</td>
                </c:when>
                <c:when test="${h.hospitalType == '2'}">
                    <td>��Ӫ</td>
                </c:when>
                <c:otherwise>
                    <td>δ֪</td>
                </c:otherwise>
            </c:choose>
            <td>${h.hospitalAddress}</td>
            <td>${h.hospitalTel}</td>
            <td>${h.doctorSum}</td>
            <td>${h.caseSum}</td>
            <td>
                <a  href="<%=path%>/hospital/detail?hospitalId=${h.hospitalId}">�鿴</a>
            	<a  href="<%=path%>/hospital/detailUpdate?hospitalId=${h.hospitalId}">�޸�</a>
            </td>
        </tr>
        </c:forEach>
    </table>
</div>

</body>
</html>
