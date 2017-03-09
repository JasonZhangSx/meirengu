<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
    <!--必要样式-->
    <link href="http://hovertree.com/ziyuan/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="http://hovertree.com/texiao/bootstrap/4/css/city-picker.css" rel="stylesheet" type="text/css" />
</head>
<script>
    function saveReport() {
// jquery 表单提交
        $("#hospitalDataForm").ajaxSubmit(function(message) {
            var map = eval(message);
            if(confirm(map.msg+",是否进入医院列表"))
            {
                //如果是true ，那么就把页面转向thcjp.cnblogs.com
                location.href="<%=path%>/hospital/show";
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
        <li>医院管理</li>
        <li>医院详情</li>

    </ul>
</div>

<form id="hospitalDataForm"  enctype="multipart/form-data" action="<%=path%>/hospital" class="form-horizontal" method="post" onsubmit="return saveReport();"> >
    <h5 class="page-header alert-info" style="padding:10px; margin:0px; margin-bottom:5px;">基本信息</h5>
    <c:forEach items="${map.data.list}" var="h">
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">医院名称</label>
                <div class="col-sm-9">
                    <input type="text" value="${h.hospitalName}" name="hospitalName" readonly="readonly"   class="form-control input-sm" placeholder="请输入医院名称"/>
                </div>
            </div>
        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">类型</label>
                <div class="col-sm-9">
                    <select class="form-control input-sm" name="hospitalType">
                        <c:choose>
                            <c:when test="${h.hospitalType == '1'}">
                                <option selected="selected" value="1">公立</option>
                            </c:when>
                            <c:when test="${h.hospitalType == '2'}">
                                <option selected="selected" value="2">民营</option>
                            </c:when>
                            <c:otherwise>
                                <option selected="selected" value="0">未知</option>
                            </c:otherwise>
                        </c:choose>

                    </select>
                </div>
            </div>
        </div>

    </div>
    <!-- 开始2 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">医院类型</label>
                <div class="col-sm-9">
                    <select class="form-control input-sm" name="hospitalClass">
                        <c:choose>
                            <c:when test="${h.hospitalClass == '1'}">
                                <option selected="selected" value="1">公立三级综合医院</option>
                            </c:when>
                            <c:when test="${h.hospitalClass == '2'}">
                                <option selected="selected" value="2">整形外科医院</option>
                            </c:when>
                            <c:when test="${h.hospitalClass == '3'}">
                                <option selected="selected" value="3">民营医疗美容医院</option>
                            </c:when>
                            <c:otherwise>
                                <option selected="selected" value="0">未知</option>
                            </c:otherwise>
                        </c:choose>
                    </select>
                </div>
            </div>
        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">logo图片</label>
                <div class="col-sm-9">
                    <tr>
                        <%--<input type="file" name="hospitalLogoImg_200_200" multiple="multiple"  class="form-control input-sm"/>--%>
                        <%--<input type="file" name="hospitalLogoImg_200_200" class="form-control input-sm"/>--%>
                            <a href="http://image.meirenguvip.com/hospital/${h.hospitalId}/${h.hospitalLogo}">点击查看</a>
                    </tr>
                </div>
            </div>
        </div>
    </div>
    <!-- 结束2 -->
    <!-- 开始3 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">医院网站</label>
                <div class="col-sm-9">
                    <input type="text" readonly="readonly"  value="${h.hospitalWebsite}" name="hospitalWebsite" class="form-control input-sm" placeholder="请输入医院网站"/>
                </div>
            </div>

        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">医院地址</label>
                <div class="col-sm-9">
                    <input type="text"  readonly="readonly"  value="${h.hospitalAddress}" name="hospitalAddress" class="form-control input-sm" placeholder="请输入医院地址"/>
                </div>
            </div>
        </div>
    </div>
    <!-- 结束3 -->
    <!-- 开始4 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">医院电话</label>
                <div class="col-sm-9">
                    <input type="text" readonly="readonly"  value="${h.hospitalTel}" name="hospitalTel" class="form-control input-sm" placeholder="请输入医院电话"/>
                </div>
            </div>

        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">资格证书</label>
                <div class="col-sm-9">
                    <c:set value="${ fn:split(h.certificatePic, ',') }" var="str1" />
                    <c:forEach items="${ str1 }" var="s">
                        <a href="http://image.meirenguvip.com/hospital/${h.hospitalId}/${s}">点击查看</a>
                    </c:forEach>
                </div>
            </div>
        </div>
    </div>
    <!-- 结束4 -->
    <!-- 开始4 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">联系人</label>
                <div class="col-sm-9">
                    <input type="text" readonly="readonly"  value="${h.contacts}"  name="contacts" class="form-control input-sm" placeholder="请输入联系人名称"/>
                </div>
            </div>

        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">联系人电话</label>
                <div class="col-sm-9">
                    <input type="text" readonly="readonly"  value="${h.contactsTel}"  name="contactsTel" class="form-control input-sm" placeholder="请输入联系人电话"/>
                </div>
            </div>
        </div>
    </div>
    <!-- 结束4 -->
    <!-- 开始5 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">联系邮箱</label>
                <div class="col-sm-9">
                    <input type="text" name="email" readonly="readonly"  value="${h.email}"  class="form-control input-sm" placeholder="请输入联系邮箱 "/>
                </div>
            </div>

        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">成立时间</label>
                <div class="col-sm-9">
                    <%--<input type="date" name="hiredate" readonly="readonly"  value="${h.setupTime}"  class="form-control input-sm" placeholder="请输入成立时间"/>--%>
                        <fmt:formatDate value="${h.setupTime}" pattern="yyyy-MM-dd"/>
                </div>
            </div>

        </div>
    </div>
    <!-- 结束5 -->
    <!-- 开始6 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">医院图片</label>
                <div class="col-sm-9">
                    <c:set value="${ fn:split(h.hospitalPic, ',') }" var="str2" />
                    <c:forEach items="${ str2 }" var="s2">
                        <a href="http://image.meirenguvip.com/hospital/${h.hospitalId}/${s2}">点击查看</a>
                    </c:forEach>
                </div>
            </div>

        </div>
    </div>
    <!-- 结束6 -->
    <h5 class="page-header alert-info" style="padding:10px; margin:0px; margin-bottom:5px;">医院简介</h5>
    <div class="row">
        <div class="col-sm-10">
            <div class="form-group">
                <label class="col-sm-3 control-label">医院简介</label>
                <div class="col-sm-9">
                    <textarea class="form-control" readonly="readonly" name="hospitalRemark">${h.hospitalRemark}</textarea>
                </div>
            </div>

        </div>

    </div>
    </c:forEach>
    <%--<h5 class="page-header alert-info" style="padding:10px; margin:0px; margin-bottom:5px;">账号信息</h5>--%>
    <%--<div class="row">--%>
    <%--<div class="col-sm-5">--%>
    <%--<div class="form-group">--%>
    <%--<label class="col-sm-3 control-label">账号</label>--%>
    <%--<div class="col-sm-9">--%>
    <%--<input type="text" name="number" class="form-control input-sm" placeholder="请输入账号 "/>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%----%>
    <%--</div>--%>
    <%--<div class="col-sm-5">--%>
    <%--<div class="form-group">--%>
    <%--<label class="col-sm-3 control-label">密码</label>--%>
    <%--<div class="col-sm-9">--%>
    <%--<input type="password" name="password" class="form-control input-sm" placeholder="请输入密码"/>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</div>--%>
    <div class="row">
        <div class="col-sm-3 col-sm-offset-4">
            <input  type="submit" class="btn btn-success" value="保存"/>
            <input  type="reset" class="btn  btn-danger" value="取消"/>
        </div>
    </div>
</form>

</body>
</html>
