<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="../common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset=utf-8>
    <base href="<%=basePath %>">
    <meta name=renderer content=webkit|ie-comp|ie-stand>
    <meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
    <meta name=viewport content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <meta http-equiv=Cache-Control content=no-siteapp/>
    <link rel=Bookmark href=favicon.ico>
    <link rel="Shortcut Icon" href=favicon.ico/>
    <meta name=keywords content=xxxxx>
    <meta name=description content=xxxxx>
    <title>添加有限合伙公司</title>
    <script type="text/javascript" src="lib/datetimepicker/datetimepicker.js"></script>
</head>
</html>
</head>
<body>
<div class="page-container">
    <form action="partner_ship/save" method="post" class="form form-horizontal" id="form-article-add">
        <style>
            .edit_h31 {
                border-bottom: 1px #ddd solid;
                overflow: hidden;
            }

            .formControls {
                line-height: 30px;
            }

            .clearfix section,.clearfix{float:left;display:inline;}
            .img-box .upimg-div .z_file{overflow: hidden}
        </style>
        <!-- 基本信息 -->

        <div>
            <div class="row cl">
                <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">添加有限合伙公司信息</h3>
            </div>
            <div class="row cl">
                <input type="hidden" id="id" name="id" value="${id}">
                <label class="form-label col-xs-4 col-sm-2">企业名称：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${companyName}" id="companyName" name="companyName" minlength="1" maxlength="30"
                           placeholder="最多不超过25字">
                </div>
                <label class="form-label col-xs-4 col-sm-2">成立时间：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="ml-10 input-text" style="width:auto" placeHolder="年/月/日" value="${establishmentTime}" name="establishmentTime" id="establishmentTime"/>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">证件号：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${idcard}" id="idcard" name="idcard" minlength="1" maxlength="30">
                </div>
                <label class="form-label col-xs-4 col-sm-2">执行事务合伙人：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${executivePartner}" id="executivePartner" name="executivePartner" minlength="1" maxlength="30">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">最多投资人数量：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${limitNum}" id="limitNum" name="limitNum" minlength="1" maxlength="30" placeholder="最多不超过50人">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">企业地址：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="text" class="input-text" value="${companyAddress}" id="companyAddress" name="companyAddress" minlength="1" maxlength="30">
                </div>
            </div>

            <br/><br/>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2"></label>
                <div class="formControls col-xs-8 col-sm-8 text-c">
                    <button class="btn btn-primary radius size-L mt-20 mb-30" style="padding:0 30px" onclick="addVersion()" type="button">保 存
                    </button>
                </div>
            </div>

            <br/><br/><br/><br/>
        </div>
    </form>
</div>
<script>$(function () {
    $(".Hui-aside ul a").on("click", function () {
        console.log($(this).attr("data-href")), $(".content_iframe").attr("src", $(this).attr("data-href"))
    })})

    $('#establishmentTime').datetimepicker({
        lang: $.datetimepicker.setLocale('ch'),
    });
</script>
<script>
    // prepare Options Object
    var options = {
        beforeSubmit:  showRequest,  // pre-submit callback
        success:       showResponse,  // post-submit callback
        error : function() {
            alert('error!');
        },
        timeout : 3000
    };
    // pre-submit callback
    function showRequest(formData, jqForm, options) {
        var queryString = $.param(formData);
        return true;
    }
    // post-submit callback
    function showResponse(responseText, statusText, xhr, $form)  {
        var data = responseText;
        var code = data.code;//200 is success，other is fail
        if(code == "200"){
            layer.msg('保存成功', {icon: 1, time: 1000});
            setTimeout(function() {
                layer_close();
            }, 2000);
        }else{
            layer.msg('错误代码: ' + data.code + ", " + data.msg, {icon: 5, time: 5000});
        }
    }
    function addVersion() {
        var companyName = $("#companyName").val();
        var establishmentTime = $("#establishmentTime").val();
        var idcard = $("#idcard").val();
        var executivePartner = $("#executivePartner").val();
        var limitNum = $("#limitNum").val();
        var companyAddress = $("#companyAddress").val();

        if(companyName == null || companyName == '' || companyName == undefined){
            alert("请填写企业名称！");
            return;
        }
        if(establishmentTime == null || establishmentTime== '' || establishmentTime == undefined){
            alert("请填写成立时间！");
            return false;
        }
        if(idcard == null || idcard == "" || idcard == undefined){
            alert("请填写企业证件号！");
            return false;
        }
        if(executivePartner == null || executivePartner == "" || executivePartner == undefined){
            alert("请填写执行事务合伙人！");
            return false;
        }
        if(limitNum == null || limitNum == "" || limitNum == undefined){
            alert("请填写投资人数量！");
            return false;
        }
        if(companyAddress == null || companyAddress == "" || companyAddress == undefined){
            alert("请填写企业地址！");
            return false;
        }
        $('#form-article-add').ajaxSubmit(options);

    }
</script>

</body>
</html>
