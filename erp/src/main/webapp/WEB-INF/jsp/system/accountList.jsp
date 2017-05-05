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
    <title>操作员管理</title>
</head>
<body>
<section class="Hui-article-box" style="top: 0; left: 0">
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 操作员管理 <span class="c-gray en">&gt;</span> 操作员管理 <a
            class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"
            href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
    <div class="Hui-article">
        <article class="cl pd-20">
            <form action="list" method="get">
                <div class="text-c">
                    姓名：<input name="realName" type="text" class="input-text" style="width:120px;">　
                    账号：<input name="userName" type="text" class="input-text" style="width:120px;">　
                    <button name="" id="" class="btn btn-success radius" type="submit"><i class="Hui-iconfont">&#xe665;</i>
                        查 询
                    </button>
                </div>
            </form>
            <div class="cl pd-5 bg-1 bk-gray mt-20">
  				<span class="l">
            <a class="btn btn-primary radius" onClick="account_add('系统管理-操作员-添加','updateOrAdd','')"  href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添 加</a>
          </span>
            </div>

            <div class="mt-20">
                <table class="table table-border table-bordered table-bg table-hover table-sort">
                    <thead>
                    <tr class="text-c">
                        <th>编号</th>
                        <th>用户名</th>
                        <th>姓名</th>
                        <th>电话</th>
                        <%--<th>角色</th>--%>
                        <%--<th>部门</th>--%>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${accountList}" var="account">
                        <tr class="text-c">
                            <td>${account.id}</td>
                            <td>${account.userName}</td>
                            <td>${account.realName}</td>
                            <td>${account.userPhone}</td>
                            <c:choose>
                                <c:when test="${account.status==1}">
                                    <td>启用</td>
                                </c:when>
                                <c:when test="${account.status==0}">
                                    <td>禁用</td>
                                </c:when>
                            </c:choose>
                            <td class="f-14 td-manage">
                                <c:choose>
                                    <c:when test="${account.status==1}">
                                        <a style="text-decoration:none" onClick="account_stop(this,${account.id})" href="javascript:;" title="禁用"><i class="Hui-iconfont">&#xe631;</i></a>
                                    </c:when>
                                    <c:when test="${account.status==0}">
                                        <a style="text-decoration:none" onClick="account_start(this,${account.id})" href="javascript:;" title="启用"><i class="Hui-iconfont">&#xe6e1;</i></a>
                                    </c:when>
                                </c:choose>
                                <a title="编辑" href="javascript:;" onclick="account_add('系统管理-操作员-编辑','updateOrAdd',${account.id})" class="ml-5" style="text-decoration:none"><i class="Hui-iconfont">&#xe6df;</i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </article>
    </div>
</section>

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
<!-- 时间插件 -->
<link href="lib/datetimepicker/datetimepicker.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="lib/datetimepicker/datetimepicker.js"></script>
<script>
    $('.table-sort').dataTable({
        "aaSorting": [[0, "desc"]],
        "bStateSave": true,
    });
    //*账户-添加*/
    function account_add(title, url, id, w, h) {
        var index = layer.open({
            type: 2,
            title: title,
            content: url+'?id='+id
        });
        layer.full(index);
    }
    // 时间
    $('#datetimepicker2,#datetimepicker3').datetimepicker({
        yearOffset: 0,
        lang: $.datetimepicker.setLocale('ch'),
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y/m/d',
    });

    /*操作员禁用*/
    function account_stop(obj,id){
        layer.confirm('确认要禁用吗？',function(index){
            $.ajax({
                url:"update",
                type:"POST",
                data:{
                    "id":id,
                    "status":"0"
                },
                success : function(data) {
                    var map = eval("("+data+")");
                    if(map.code==200){
                        layer.msg('已禁用!', {icon: 5, time: 1000});
                        location.href="list";
                    }else{
                        layer.msg('操作失败! 请重试!', {icon: 5, time: 1000});
                    }
                }
            })
        });
    }
    /*操作员启用*/
    function account_start(obj,id){
        layer.confirm('确认要启用吗？',function(index){
            $.ajax({
                url:"update",
                type:"POST",
                data:{
                    "id":id,
                    "status":"1"
                },
                success : function(data) {
                    var map = eval("("+data+")");
                    if(map.code==200){
                        layer.msg('已启用!',{icon: 6,time:1000});
                        location.href="list";
                    }else{
                        layer.msg('操作失败! 请重试!', {icon: 5, time: 1000});
                    }
                }
            })
        });
    }
</script>
</body>
</html>

