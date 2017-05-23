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
    <title>添加版本信息</title>
</head>
</html>
</head>
<body>
<div class="page-container">
    <form action="version/save" method="post" class="form form-horizontal" id="form-article-add">
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
            <div class="row cl">
                <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">添加版本信息</h3>
            </div>
            <div class="row cl">
                <input type="hidden" id="id" name="id" value="${id}">
                <label class="form-label col-xs-4 col-sm-2">APP名称：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${appName}" id="appName" name="appName" minlength="1" maxlength="30"
                           placeholder="最多不超过25字">
                </div>
                <label class="form-label col-xs-4 col-sm-2">设备分类：</label>
                <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                    <select id="appId" name="appId" class="select" onchange="changeValue()">
                        <c:if test="${not empty appId}">
                            <c:if test="${appId == 1}">
                                <option value="1" selected>安卓</option>
                                <option value="2">IOS</option>
                            </c:if>
                            <c:if test="${appId == 2}">
                                <option value="1">安卓</option>
                                <option value="2" selected>IOS</option>
                            </c:if>
                        </c:if>
                        <c:if test="${empty appId}">
                            <option value="1">安卓</option>
                            <option value="2">IOS</option>
                        </c:if>
                    </select>
                    </span>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">产品code：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${appCode}" id="appCode" name="appCode" minlength="1" maxlength="30"
                           placeholder="最多不超过25字">
                </div>
                <label class="form-label col-xs-4 col-sm-2">产品渠道标识：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${appChannel}" id="appChannel" name="appChannel" minlength="1" maxlength="30"
                           placeholder="7位的数字">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">是否为里程碑式版本：</label>
                <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                    <select id="versionMilepost" name="versionMilepost" class="select">
                        <c:if test="${not empty versionMilepost}">
                            <c:if test="${versionMilepost == 0}">
                                <option value="0" selected>否</option>
                                <option value="1">是</option>
                            </c:if>
                            <c:if test="${versionMilepost == 1}">
                                <option value="0">否</option>
                                <option value="1" selected>是</option>
                            </c:if>
                        </c:if>
                        <c:if test="${empty versionMilepost}">
                            <option value="0">否</option>
                            <option value="1">是</option>
                        </c:if>
                    </select></span>
                </div>
                <label class="form-label col-xs-4 col-sm-2">版本大小：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${versionSize}" id="versionSize" name="versionSize" minlength="1" maxlength="30">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">版本号：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${versionCode}" id="versionCode" name="versionCode" minlength="1" maxlength="30"
                           placeholder="x.x.x">
                </div>
                <label class="form-label col-xs-4 col-sm-2">上一个版本号：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${versionCodeBefore}" id="versionCodeBefore" name="versionCodeBefore" minlength="1" maxlength="30"
                           placeholder="x.x.x">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">更新地址：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="url" class="input-text" value="${downloadUrl}" id="downloadUrl" name="downloadUrl">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">升级信息标题：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${updateTitle}" id="updateTitle" name="updateTitle" minlength="1" maxlength="30" >
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">升级信息内容：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <textarea id="updateContent" name="updateContent" cols="" rows="" minlength="1" class="textarea" placeholder="..." datatype="*10-100"
                              dragonfly="true" nullmsg="升级内容不能为空！" onKeyUp="$.Huitextarealength(this,255)">${updateContent}</textarea>
                    <p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">升级类型：</label>
                <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                    <select id="updateType" name="updateType" class="select">
                        <c:if test="${not empty updateType}">
                            <c:if test="${updateType == 1}">
                                <option value="1" selected>自检更新（自动升级、推送升级、检测升级）</option>
                                <option value="2">强制更新</option>
                                <option value="3">增量更新</option>
                            </c:if>
                            <c:if test="${updateType == 2}">
                                <option value="1">自检更新（自动升级、推送升级、检测升级）</option>
                                <option value="2" selected>强制更新</option>
                                <option value="3">增量更新</option>
                            </c:if>
                            <c:if test="${updateType == 3}">
                                <option value="1">自检更新（自动升级、推送升级、检测升级）</option>
                                <option value="2">强制更新</option>
                                <option value="3" selected>增量更新</option>
                            </c:if>
                        </c:if>
                        <c:if test="${empty updateType}">
                            <option value="1">自检更新（自动升级、推送升级、检测升级）</option>
                            <option value="2">强制更新</option>
                            <option value="3">增量更新</option>
                        </c:if>
                    </select></span>
                </div>
                <label class="form-label col-xs-4 col-sm-2">版本状态：</label>
                <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                    <select id="status" name="status" class="select">
                        <c:if test="${not empty status}">
                            <c:if test="${status == 1}">
                                <option value="1" selected>最新版本</option>
                                <option value="0">老版本</option>
                            </c:if>
                            <c:if test="${status == 0}">
                                <option value="1">最新版本</option>
                                <option value="0" selected>老版本</option>
                            </c:if>
                        </c:if>
                        <c:if test="${empty status}">
                            <option value="1">最新版本</option>
                            <option value="0">老版本</option>
                        </c:if>
                    </select></span>
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
    })
})</script>
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
        var appName = $("#appName").val();
        var appId = $("#appId").val();
        var appCode = $("#appCode").val();
        var versionSize = $("#versionSize").val();
        var versionCode = $("#versionCode").val();
        var versionCodeBefore = $("#versionCodeBefore").val();
        var downloadUrl = $("#downloadUrl").val();
        var updateTitle = $("#updateTitle").val();
        var updateContent = $("#updateContent").val();
        if(appName == null || appName == '' || appName == undefined){
            alert("app名称不能为空");
            return;
        }
        if(appId == null || appId== '' || appId == undefined){
            alert("请选择设备分类！");
            return false;
        }
        if(appCode == null || appCode== "" || appCode == undefined){
            alert("请填写渠道标识！");
            return false;
        }
        if(versionSize == null || versionSize == "" || versionSize == undefined){
            alert("请填写版本大小！");
            return false;
        }
        if(versionSize == null || versionSize == "" || versionSize == undefined){
            alert("请填写版本大小！");
            return false;
        }
        if(versionCode == null || versionCode == "" || versionCode == undefined){
            alert("请填写版本号！");
            return false;
        }
        if(versionCodeBefore == null || versionCodeBefore == "" || versionCodeBefore == undefined){
            alert("请填写上一个版本号！");
            return false;
        }
        if(downloadUrl == null || downloadUrl == "" || downloadUrl == undefined){
            alert("请填写更新地址！");
            return false;
        }
        if(updateTitle == null || updateTitle == "" || updateTitle == undefined){
            alert("请填写升级信息标题！");
            return false;
        }
        if(updateContent == null || updateContent == "" || updateContent == undefined){
            alert("请填写升级信息内容！");
            return false;
        }
        $('#form-article-add').ajaxSubmit(options);

    }
</script>

</body>
</html>
