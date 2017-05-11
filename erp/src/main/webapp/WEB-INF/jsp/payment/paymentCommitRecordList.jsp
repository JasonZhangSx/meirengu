<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %><%--
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
    <title>打款记录</title>
</head>
<body>
<section class="Hui-article-box" style="top: 0; left: 0">
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 财务管理 <span class="c-gray en">&gt;</span> 待打款记录 <a
            class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"
            href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
    <div class="Hui-article">
        <article class="cl pd-20">
            <form action="getPaymentCommitRecord" method="get">
                <div class="text-c">
                    合作方名称：<input name="partnerName" type="text" class="input-text" style="width:120px;">　
                    项目名称：<input name="itemName" type="text" class="input-text" style="width:120px;">　
                    <button name="" id="" class="btn btn-success radius" type="submit"><i class="Hui-iconfont">&#xe665;</i>
                        查 询
                    </button>
                </div>
            </form>
            <div class="cl pd-5 bg-1 bk-gray mt-20">
  				<span class="l">
            <a class="btn btn-primary radius" href="javascript:;"><i class="Hui-iconfont">&#xe634;</i> 导出</a>
          </span>
                <span class="r" style="line-height:30px;">共有数据：<strong>1</strong> 条</span></div>

            <div class="mt-20">
                <table class="table table-border table-bordered table-bg table-hover table-sort">
                    <thead>
                    <tr class="text-c">
                        <th>序号</th>
                        <th>合作方名称</th>
                        <th>项目名称</th>
                        <th>打款类型</th>
                        <th>应付款</th>
                        <th>实付款</th>
                        <th>凭据</th>
                        <th>打款时间</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${paymentCommitList}" var="paymentCommit">
                        <tr class="text-c">
                            <td>${paymentCommit.id}</td>
                            <td>${paymentCommit.partnerName}</td>
                            <td>${paymentCommit.itemName}</td>
                            <c:choose>
                                <c:when test="${paymentCommit.commitType==1}">
                                    <td>首款</td>
                                </c:when>
                                <c:when test="${paymentCommit.commitType==2}">
                                    <td>尾款</td>
                                </c:when>
                            </c:choose>
                            <td>${paymentCommit.shouldAmount}</td>
                            <td>${paymentCommit.actualAmount}</td>
                            <td>    <a style="text-decoration:none" class="ml-5" href="javascript:;" onClick="project_edit('财务-打款记录-查看','财务-待打款列表-打款.html','10001')" title="查看">查看</a>
                                <a style="text-decoration:none" class="ml-5" href="javascript:;" title="下载">下载</a>
                            </td>
                            <td>
                                <c:set var="commitTime" value="${paymentCommit.commitTime}" scope="session"/>
                                <%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(String.valueOf(session.getAttribute("commitTime"))))%>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
            <!-- <div class="mt-20">
                <table class="table table-border table-bordered table-bg table-hover table-sort">
                    <thead>
                        <tr class="text-c">
                            <th>流水单号</th><th>合作方</th><th>项目名称</th><th>收款期数</th><th>应收</th><th>实收</th>
                            <th>收款类型</th><th>凭据</th><th>收款时间</th>
                        </tr>
                    </thead>
                    <tbody>
                        <tr class="text-c">
                            <td>1</td><td>医疗</td><td>医疗方面的合作方</td><td>15个</td><td>15个</td><td>15个</td><td>15个</td>
                            <td>
                                <a style="text-decoration:none" class="ml-5" href="javascript:;" title="查看"><i class="Hui-iconfont">&#xe725;</i></a>
                                <a style="text-decoration:none" class="ml-5" href="javascript:;" title="下载"><i class="Hui-iconfont">&#xe640;</i></a>
                            </td>
                            <td>2016-02-01 15:33:33</td>
                        </tr>
                    </tbody>
                </table>
            </div> -->

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
        "bSort" : false,
//        "aaSorting": [[0, "asc"]],
        "bStateSave": true,
    });
    //*项目-编辑*/
    function project_edit(title, url, id, w, h) {
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

</script>
</body>
</html>

