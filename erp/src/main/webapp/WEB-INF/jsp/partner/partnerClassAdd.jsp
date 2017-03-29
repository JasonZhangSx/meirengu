<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="../common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset=utf-8>
    <meta name=renderer content=webkit|ie-comp|ie-stand>
    <meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
    <meta name=viewport content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <meta http-equiv=Cache-Control content=no-siteapp/>
    <link rel=Bookmark href=favicon.ico>
    <link rel="Shortcut Icon" href=favicon.ico/>
    <meta name=keywords content=xxxxx>
    <meta name=description content=xxxxx>
    <link href="/erp/lib/lightbox2/2.8.1/css/lightbox.css" rel="stylesheet" type="text/css">
    <link href="/erp/lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css"/>
    <link href="/erp/lib/datetimepicker/datetimepicker.css" rel="stylesheet" type="text/css"/>

</head>
<body>
<div class="page-container">
    <form action="add" method="post" class="form form-horizontal" id="form-article-add">
        <style>
            .select-box1 {
                padding-left: 0;
            }

            .select-box1 select {
                font-size: 14px;
                height: 31px;
                line-height: 31px;
                padding: 0 4px;
                border: 1px #ddd solid;
            }

            .edit_h31 {
                border-bottom: 1px #ddd solid;
                overflow: hidden;
            }
        </style>

        <!-- 合作方分类》添加 -->
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">分类名称：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <input type="text" class="input-text" value="" id="articletitle" name="className" maxlength="30"
                       placeholder="项目标题最多30字" id="" name="">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">描述：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <textarea name="classDescription" cols="" rows="" class="textarea" placeholder="..." datatype="*10-100" dragonfly="true"
                          nullmsg="备注不能为空！" onKeyUp="$.Huitextarealength(this,200)"></textarea>
                <p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"></label>
            <div class="formControls col-xs-8 col-sm-8 text-c">
                <button id="button-id" class="btn btn-primary radius size-L mt-20 mb-30" style="padding:0 30px" type="button">添加
                </button>
            </div>
        </div>

    </form>
</div>
<script>$(function () {
    $(".Hui-aside ul a").on("click", function () {
        console.log($(this).attr("data-href")), $(".content_iframe").attr("src", $(this).attr("data-href"))
    });
    $("#button-id").click(function(){
        $("#form-article-add").submit();
    });
})</script>
</body>
</html>
