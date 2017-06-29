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
    <form action="investor/save" method="post" class="form form-horizontal" id="form-article-add">
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
                <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">添加领投人信息</h3>
            </div>
            <div class="row cl">
                <input type="hidden" id="id" name="id" value="${id}">
                <label class="form-label col-xs-4 col-sm-2">领投人名称：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${investorName}" id="investorName" name="investorName" minlength="1" maxlength="30"
                           placeholder="最多不超过25字">
                </div>
                <label class="form-label col-xs-4 col-sm-2">领投人类型：</label>
                <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                    <select id="investorType" name="investorType" class="select">
                        <c:if test="${not empty investorType}">
                            <c:if test="${investorType == 1}">
                                <option value="1" selected>公司</option>
                                <option value="2">个人</option>
                            </c:if>
                            <c:if test="${investorType == 2}">
                                <option value="1">公司</option>
                                <option value="2" selected>个人</option>
                            </c:if>
                        </c:if>
                        <c:if test="${empty investorType}">
                            <option value="1">公司</option>
                            <option value="2">个人</option>
                        </c:if>
                    </select>
                    </span>
                </div>
            </div>
            <div class="row cl">
                <c:if test="${not empty investorType}">
                    <c:if test="${investorType == 1}">
                        <div id="principal_name">
                            <label class="form-label col-xs-4 col-sm-2">营业执照号：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                <input type="text" class="input-text" value="${investorBusinessLicence}" id="investorBusinessLicence" name="investorBusinessLicence" minlength="1" maxlength="30"
                                       placeholder="最多不超过25字">
                            </div>
                            <label class="form-label col-xs-4 col-sm-2">公司法人：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                <input type="text" class="input-text" value="${principalName}" id="principalName" name="principalName" minlength="1" maxlength="30">
                            </div>
                        </div>
                    </c:if>
                    <c:if test="${investorType == 2}">
                        <div id="principal_name" style="display:none;">
                            <label class="form-label col-xs-4 col-sm-2">营业执照号：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                <input type="text" class="input-text" value="${investorBusinessLicence}" id="investorBusinessLicence" name="investorBusinessLicence" minlength="1" maxlength="30"
                                       placeholder="最多不超过25字">
                            </div>
                            <label class="form-label col-xs-4 col-sm-2">公司法人：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                <input type="text" class="input-text" value="${principalName}" id="principalName" name="principalName" minlength="1" maxlength="30">
                            </div>
                        </div>
                    </c:if>
                </c:if>
                <c:if test="${empty investorType}">
                    <div id="principal_name">
                        <label class="form-label col-xs-4 col-sm-2">营业执照号：</label>
                        <div class="formControls col-xs-8 col-sm-3">
                            <input type="text" class="input-text" value="${investorBusinessLicence}" id="investorBusinessLicence" name="investorBusinessLicence" minlength="1" maxlength="30"
                                   placeholder="最多不超过25字">
                        </div>
                        <label class="form-label col-xs-4 col-sm-2">公司法人：</label>
                        <div class="formControls col-xs-8 col-sm-3">
                            <input type="text" class="input-text" value="${principalName}" id="principalName" name="principalName" minlength="1" maxlength="30">
                        </div>
                    </div>
                </c:if>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">身份证号：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${investorIdcard}" id="investorIdcard" name="investorIdcard" minlength="1" maxlength="30">
                </div>
                <label class="form-label col-xs-4 col-sm-2">联系方式：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${investorTelphone}" id="investorTelphone" name="investorTelphone" minlength="1" maxlength="30">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">领投人地址：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="text" class="input-text" value="${investorAddress}" id="investorAddress" name="investorAddress" minlength="1" maxlength="30">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">公司：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${investorCompany}" id="investorCompany" name="investorCompany" minlength="1" maxlength="30">
                </div>
                <label class="form-label col-xs-4 col-sm-2">职位：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${investorPosition}" id="investorPosition" name="investorPosition" minlength="1" maxlength="30">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">简介：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <textarea id="investorIntroduction" name="investorIntroduction" cols="" rows="" class="textarea" placeholder="..." datatype="*10-100" dragonfly="true"
                              nullmsg="简介不能为空！">${investorIntroduction}</textarea>
                    <%--<p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>--%>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">投资理念：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <textarea id="investorIdea" name="investorIdea" cols="" rows="" class="textarea" placeholder="..." datatype="*10-100" dragonfly="true"
                              nullmsg="简介不能为空！">${investorIdea}</textarea>
                    <%--<p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>--%>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">领投人头像：</label>
                <div class="formControls col-xs-8 col-sm-9">
                    <input type="hidden" id="investorImage" name="investorImage">
                    <div class="img-box full">
                        <section class=" img-section">
                            <div class="z_photo upimg-div clearfix">
                                <div id="imgParent" class="clearfix">
                                <c:if test="${not empty investorImage}">
                                    <section class="up-section fl">
                                        <span class="up-span"></span>
                                        <img class="close-upimg" src="static/upload-file/a7.png" onclick="removePic(this)">
                                        <img class="up-img" src="<%=imgPath%>${investorImage}">
                                        <p class="img-name-p">${investorImage}</p>
                                        <input id="taglocation" name="taglocation" value="" type="hidden">
                                        <input id="tags" name="tags" value="" type="hidden">
                                    </section>
                                </c:if>
                                </div>
                                <section class="z_file fl">
                                    <img src="static/upload-file/a11.png" class="add-img">
                                    <input type="file" name="file" id="file" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" onchange="uploadFile('file','investor','imgParent','investorImage')">
                                </section>
                            </div>
                        </section>
                    </div>
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

    var xhr;
    if(window.XMLHttpRequest){
        xhr = new XMLHttpRequest();
    }else{
        xhr = new ActiveXObject('Microsoft.XMLHTTP');
    }

    function printFileInfo(){

        var picFile = document.getElementById("pic");
        var files = picFile.files;
        for(var i=0; i<files.length; i++){
            var file = files[i];
            var div = document.createElement("div")
            div.innerHTML = "第("+ (i+1) +") 个文件的名字："+ file.name +
                    " , 文件类型："+ file.type +" , 文件大小:"+ file.size
            document.body.appendChild( div)
        }
    }

    //上传失败
    function uploadFailed(evt) {
        alert("上传失败");
    }

    /**
     * 侦查附件上传情况 ,这个方法大概0.05-0.1秒执行一次
     */
    function onprogress(evt){
        var loaded = evt.loaded;       //已经上传大小情况
        var tot = evt.total;       //附件总大小
        var per = Math.floor(100*loaded/tot);   //已经上传的百分比
        $("#son").html( per +"%" );
        $("#son").css("width" , per +"%");
    }

    //上传文件
    function uploadFile(fileId, foldName, parentId, inputId) {
        //alert(fileId+"|"+foldName+"|"+parentId);
        //将上传的多个文件放入formData中
        var picFileList = $("#"+fileId).get(0).files;
        var formData = new FormData();
        for(var i=0; i< picFileList.length; i++){
            formData.append("file" , picFileList[i] );
        }
        formData.append("foldName", foldName);

        //监听事件
        xhr.upload.addEventListener("progress", onprogress, false);
        xhr.addEventListener("error", uploadFailed, false);//发送文件和表单自定义参数
        xhr.open("POST", "uploadMultiple");
        //记得加入上传数据formData
        xhr.send(formData);
        //异步接受响应
        xhr.onreadystatechange = function(){
            if(xhr.readyState == 4){
                if(xhr.status == 200){
                    var responseData = eval('(' + xhr.responseText + ')');
                    if(responseData.code != 200){
                        alert("上传失败");
                        return;
                    }
                    console.log(responseData);
                    var data = responseData.data;
                    var imgStr = "";
                    for(var i = 0; i < data.length; i++){
                        //var imgs = $("#"+inputId).val();
                        $("#"+inputId).val(data[i]);
                        /*if(imgs == null || imgs == ''){
                            $("#"+inputId).val(data[i]);
                        }else{
                            $("#"+inputId).val(imgs+","+data[i]);
                        }*/

                        imgStr += '<section class="up-section fl">'
                                +'  <span class="up-span"></span>'
                                +'  <img class="close-upimg" src="static/upload-file/a7.png" onclick="removePic(this)">'
                                +'  <img class="up-img" src="<%=imgPath %>'+data[i]+'">'
                                +'  <p class="img-name-p">'+data[i]+'</p>'
                                +'  <input id="taglocation" name="taglocation" value="" type="hidden">'
                                +'  <input id="tags" name="tags" value="" type="hidden">'
                                +'</section>';
                    }

                    $("#"+parentId).children().remove();
                    $("#"+parentId).append(imgStr);
                }
            }
        }

    }

    function removePic(obj){
        $(obj).parent().remove();
        $("#investorImage").val("");
    }

    $("#investorType").change(function(){
        var type = $("#investorType").val();
        if(type == 1){
            $("#principal_name").show();
        }else if(type == 2){
            $("#investorBusinessLicence").val("");
            $("#principalName").val("");
            $("#principal_name").hide();
        }
    });
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
        var investorName = $("#investorName").val();
        var investorType = $("#investorType").val();
        var principalName = $("#principalName").val();
        var investorBusinessLicence = $("#investorBusinessLicence").val();
        var investorIdcard = $("#investorIdcard").val();
        var investorTelphone = $("#investorTelphone").val();
        var investorAddress = $("#investorAddress").val();
        var investorImage = $("#investorImage").val();
        var investorCompany = $("#investorCompany").val();
        var investorPosition = $("#investorPosition").val();
        var investorIntroduction = $("#investorIntroduction").val();
        var investorIdea = $("#investorIdea").val();

        if(investorName == null || investorName == '' || investorName == undefined){
            alert("投资人名称不能为空");
            return;
        }
        if(investorType == null || investorType== '' || investorType == undefined){
            alert("请选择投资人类型！");
            return false;
        }
        if(investorType == 1){
            if(investorBusinessLicence == null || investorBusinessLicence == "" || investorBusinessLicence == undefined){
                alert("请填写营业执照号！");
                return false;
            }
            if(principalName == null || principalName== "" || principalName == undefined){
                alert("请填写公司法人！");
                return false;
            }
        }
        if(investorIdcard == null || investorIdcard == "" || investorIdcard == undefined){
            alert("请填写身份证号！");
            return false;
        }
        if(investorTelphone == null || investorTelphone == "" || investorTelphone == undefined){
            alert("请填写联系电话！");
            return false;
        }
        if(investorAddress == null || investorAddress == "" || investorAddress == undefined){
            alert("请填写地址！");
            return false;
        }
        if(investorCompany == null || investorCompany == "" || investorCompany == undefined){
            alert("请填写公司！");
            return false;
        }
        if(investorPosition == null || investorPosition == "" || investorPosition == undefined){
            alert("请填写职位！");
            return false;
        }
        if(investorIntroduction == null || investorIntroduction == "" || investorIntroduction == undefined){
            alert("请填写简介！");
            return false;
        }
        if(investorIdea == null || investorIdea == "" || investorIdea == undefined){
            alert("请填写投资理念！");
            return false;
        }
        if(investorImage == null || investorImage == "" || investorImage == undefined){
            alert("请选择领投人头像！");
            return false;
        }
        $('#form-article-add').ajaxSubmit(options);

    }
</script>

</body>
</html>
