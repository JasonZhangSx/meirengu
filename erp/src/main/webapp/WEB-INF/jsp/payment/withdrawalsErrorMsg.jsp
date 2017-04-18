<%--
  Created by IntelliJ IDEA.
  User: xiaoyang
  Date: 2017/4/14
  Time: 19:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html> <head> <meta charset=utf-8>
    <meta name=renderer content=webkit|ie-comp|ie-stand>
    <meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
    <meta name=viewport content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <meta http-equiv=Cache-Control content=no-siteapp /> <link rel=Bookmark href=favicon.ico>
    <link rel="Shortcut Icon" href=favicon.ico />
    <meta name=keywords content=xxxxx>
    <meta name=description content=xxxxx> <!--[if lt IE 9]>
    <script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script><![endif]--> </head></html>

<title>图片列表</title>
</head>
<body>
<article class="cl pd-20">
    <form action="withdrawalsErrorConfirm" method="post" class="form form-horizontal" id="form-change-password">
        <input type=text style="display:none" name="orderSn" value="${orderSn}">

        <div class="row cl">
            <label class="form-label col-xs-2 col-sm-1">备注：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <textarea name="msg" cols="" rows="" class="textarea"  placeholder="请输入不通过原因" datatype="*10-100" dragonfly="true" nullmsg="备注不能为空！" onKeyUp="$.Huitextarealength(this,200)"></textarea>
                <p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
            </div>
        </div>

        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-2 col-sm-offset-1">
                <input class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;确定&nbsp;&nbsp;">
            </div>
        </div>
    </form>
</article>

<script>

    //*项目-编辑*/
    //*项目-编辑*/
    function project_edit(title,url,id,w,h){
        layer_show(title,url,w,h);
    }
    // 时间
    $('#datetimepicker2,#datetimepicker3').datetimepicker({
        yearOffset:0,
        lang:$.datetimepicker.setLocale('ch'),
        timepicker:false,
        format:'Y-m-d',
        formatDate:'Y/m/d',
    });

</script>
</body>
</html>