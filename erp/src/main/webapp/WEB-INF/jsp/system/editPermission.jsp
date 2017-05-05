<%--
  Created by IntelliJ IDEA.
  User: xiaoyang
  Date: 2017/3/31
  Time: 16:29
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <base href="${basePath}">
    <meta charset=utf-8>
    <meta name=renderer content=webkit|ie-comp|ie-stand>
    <meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
    <meta name=viewport content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <meta http-equiv=Cache-Control content=no-siteapp/>
    <link rel=Bookmark href=favicon.ico>
    <link rel="Shortcut Icon" href=favicon.ico/>
    <meta name=keywords content=xxxxx>
    <meta name=description content=xxxxx>
    <title>操作员修改</title>
</head>
<body>
<div class="page-container">
    <form action="updateOrAdd" method="post" class="form form-horizontal" id="permissionUpOrAdd" onsubmit="return saveReport();">
        <style>
            .edit_h31 {
                border-bottom: 1px #ddd solid;
                overflow: hidden;
            }

            .formControls {
                line-height: 30px;
            }
        </style>
        <!-- 基本信息 -->

        <div>
            <input type="text" class="input-text"  id="id"  maxlength="30"
                   value="${permission.id}" style="display: none" name="id">
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">权限名称：</label>
                <div class="formControls col-xs-8 col-sm-5">
                    <input type="text" class="input-text"  id="name"  maxlength="30"
                           value="${permission.name}" placeholder="用户名最多10字" name="name">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">权限值：</label>
                <div class="formControls col-xs-8 col-sm-5">
                    <input type="text" class="input-text"  id="value"  maxlength="30"
                           value="${permission.value}"  placeholder="真实姓名最多10字" name="value">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">权限描述：</label>
                <div class="formControls col-xs-8 col-sm-5">
                    <input type="text" class="input-text"  id="description"  maxlength="30"
                           value="${permission.description}" placeholder="手机号最多11字" name="description">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">父权限：</label>
                <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
				<select name="parentId" class="select">
                    <c:choose>
                        <c:when test="${permission.parentId eq 0}">
                            <option disabled="disabled">已是一级菜单</option>
                        </c:when>
                        <c:otherwise>
                            <option value="0">一级菜单</option>
                            <c:forEach var="p" items="${permissionList}" varStatus="status">
                                <c:choose>
                                    <c:when test="${p.id==permission.parentId}">
                                        <option value="${p.id}" selected="selected">${p.name}</option>
                                    </c:when>
                                    <c:otherwise>
                                        <option value="${p.id}">${p.name}</option>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                </select>
				</span>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2"></label>
                <div class="formControls col-xs-8 col-sm-8 text-c">
                    <button class="btn btn-primary radius size-L mt-20 mb-30" style="padding:0 30px" type="submit">确 认
                    </button>
                </div>
            </div>

            <br/><br/><br/><br/>
        </div>


    </form>
</div>

<script src=lib/jquery/1.9.1/jquery.min.js></script>
<script src=lib/layer/2.4/layer.js></script>
<script src=lib/jquery.validation/1.14.0/jquery.validate.js></script>
<script src=lib/jquery.validation/1.14.0/validate-methods.js></script>
<script src=lib/jquery.validation/1.14.0/messages_zh.js></script>
<script src=static/h-ui/js/H-ui.js></script>
<script src=static/h-ui.admin/js/H-ui.admin.page.js></script>
<script>$(function () {
    $(".Hui-aside ul a").on("click", function () {
        console.log($(this).attr("data-href")), $(".content_iframe").attr("src", $(this).attr("data-href"))
    })
})</script>
<script type="text/javascript" src="lib/webuploader/0.1.5/webuploader.min.js"></script>
<!-- 时间插件 -->
<link href="lib/datetimepicker/datetimepicker.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="lib/datetimepicker/datetimepicker.js"></script>

<script>
    // 时间
    $('#datetimepicker2,#datetimepicker3').datetimepicker({
        yearOffset: 0,
        lang: $.datetimepicker.setLocale('ch'),
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y/m/d',
    });

    function saveReport() {
// jquery 表单提交
        $("#permissionUpOrAdd").ajaxSubmit(function(result) {
            if (result==""){
                layer.msg('操作失败!', {icon: 5, time: 1000});
            }else {
                var map = eval("("+result+")");
                if (map.code==200){
                    if(confirm("操作成功,是否进入权限列表"))
                    {
                        //如果是true ，那么就把页面转向thcjp.cnblogs.com
                        parent.location.href="all";
                    }
                }else {
                    layer.msg('操作失败!', {icon: 5, time: 1000});
                }
            }
// 对于表单提交成功后处理，message为提交页面saveReport.htm的返回内容
        });

        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
    }
</script>

</body>
</html>

