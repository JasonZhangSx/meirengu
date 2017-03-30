<%@ page contentType="text/html;charset=UTF-8" language="java" %>
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
</head>
</html>
<link href="/erp/lib/lightbox2/2.8.1/css/lightbox.css" rel="stylesheet" type="text/css">
<link href="/erp/lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css"/>
<link href="/erp/lib/datetimepicker/datetimepicker.css" rel="stylesheet" type="text/css"/>

</head>
<body>
<div class="page-container">
    <form action="" method="post" class="form form-horizontal" id="form-article-add">
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
        <div class="row cl">
            <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">基本信息</h3>
        </div>
        <div class="row cl">
            <div class="col-xs-10 col-sm-6">
                <label class="form-label col-xs-4 col-sm-4">用户ID：</label>
                <div class="formControls col-xs-8 col-sm-5">12332123213</div>
            </div>
            <div class="col-xs-10 col-sm-5">
                <label class="form-label col-xs-4 col-sm-4">用户帐号：</label>
                <div class="formControls col-xs-8 col-sm-6">133333333</div>
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-10 col-sm-6">
                <label class="form-label col-xs-4 col-sm-4">用户昵称：</label>
                <div class="formControls col-xs-8 col-sm-5">阿迪舒服</div>
            </div>
            <div class="col-xs-10 col-sm-5">
                <label class="form-label col-xs-4 col-sm-4">用户姓名： </label>
                <div class="formControls col-xs-8 col-sm-6">王二小</div>
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-10 col-sm-6">
                <label class="form-label col-xs-4 col-sm-4">性别：</label>
                <div class="formControls col-xs-8 col-sm-5">男</div>
            </div>
            <div class="col-xs-10 col-sm-5">
                <label class="form-label col-xs-4 col-sm-4">出生日期：</label>
                <div class="formControls col-xs-8 col-sm-6">1999/9/4</div>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">身份证号：</label>
            <div class="formControls col-xs-8 col-sm-8">
                52444322322222222
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">常用地址：</label>
            <div class="formControls col-xs-8 col-sm-8">
                xxxxxxxxxxxxxxxxxxxxxxxxx
            </div>
        </div>
        <div class="row cl">
            <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">资金信息</h3>
        </div>
        <div class="row cl">
            <div class="col-xs-10 col-sm-6">
                <label class="form-label col-xs-4 col-sm-4">账户余额：</label>
                <div class="formControls col-xs-8 col-sm-5">123123.00</div>
            </div>
            <div class="col-xs-10 col-sm-5">
                <label class="form-label col-xs-4 col-sm-4">累计投资金额：</label>
                <div class="formControls col-xs-8 col-sm-6">1231312</div>
            </div>
        </div>
        <div class="row cl">
            <div class="col-xs-10 col-sm-6">
                <label class="form-label col-xs-4 col-sm-4">累计分红金额：</label>
                <div class="formControls col-xs-8 col-sm-5">12312312.00</div>
            </div>
        </div>

        <div class="row cl">
            <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">收货地址</h3>
        </div>
        <div class="row cl col-sm-9  col-xm-10 col-sm-offset-1 mb-30">
            <table class="table table-border table-bordered table-bg table-hover table-sort">
                <thead>
                <tr class="text-c">
                    <th>收货人</th>
                    <th>联系方式</th>
                    <th>地址</th>
                    <th>邮编</th>
                    <th>使用次数</th>
                    <th>是否常用</th>
                </tr>
                </thead>
                <tbody>
                <tr class="text-c">
                    <td>1</td>
                    <td>玉之光</td>
                    <td>收益权众筹</td>
                    <td>4万元档</td>
                    <td>是</td>
                    <td>20000</td>
                </tr>
                </tbody>
            </table>
        </div>
    </form>
</div>

<script>$(function () {
    $(".Hui-aside ul a").on("click", function () {
        console.log($(this).attr("data-href")), $(".content_iframe").attr("src", $(this).attr("data-href"))
    })
})</script>


</body>
</html>
