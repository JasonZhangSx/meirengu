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
    <title>添加领投人</title>
    <link rel="stylesheet" type="text/css" href="static/upload-file/upload.css" />
</head>
</html>
</head>
<body>
<div class="page-container">
    <form action="partner/class/save" method="post" class="form form-horizontal" id="form-article-add">
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
                <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">添加行业分类信息</h3>
            </div>
            <div class="row cl">
                <input type="hidden" id="classId" name="classId" value="${classId}">
                <label class="form-label col-xs-4 col-sm-2">分类名称：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${className}" id="className" name="className" minlength="1" maxlength="30"
                           placeholder="最多不超过25字">
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">描述：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <textarea id="classDescription" name="classDescription" cols="" rows="" class="textarea" placeholder="..." datatype="*10-100" dragonfly="true"
                              nullmsg="描述不能为空！">${classDescription}</textarea>
                    <%--<p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>--%>
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
        var className = $("#className").val();
        var classDescription = $("#classDescription").val();

        if(className == null || className == '' || className == undefined){
            alert("请填写分类名称！");
            return;
        }
        if(classDescription == null || classDescription== '' || classDescription == undefined){
            alert("请选择分类描述！");
            return false;
        }
        $('#form-article-add').ajaxSubmit(options);

    }
</script>

</body>
</html>
