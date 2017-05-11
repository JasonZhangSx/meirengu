<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="../common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath %>">
    <meta charset=utf-8>
    <meta name=renderer content=webkit|ie-comp|ie-stand>
    <meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
    <meta name=viewport content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <meta http-equiv=Cache-Control content=no-siteapp/>
    <link rel=Bookmark href=favicon.ico>
    <link rel="Shortcut Icon" href=favicon.ico/>
    <meta name=keywords content=xxxxx>
    <meta name=description content=xxxxx>
    <!--[if lt IE 9]>
    <script type="text/javascript" src="lib/html5.js"></script>
    <script type="text/javascript" src="lib/respond.min.js"></script>
    <script src="static/upload-file/ajaxfileupload.js" />
    <![endif]-->
    <link rel=stylesheet type=text/css href="static/h-ui/css/H-ui.min.css"/>
    <link rel=stylesheet type=text/css href="static/h-ui.admin/css/H-ui.admin.css"/>
    <link rel=stylesheet type=text/css href="lib/Hui-iconfont/1.0.8/iconfont.css"/>
    <link rel=stylesheet type=text/css href="static/h-ui.admin/skin/default/skin.css" id=skin/>
    <link rel=stylesheet type=text/css href="static/h-ui.admin/css/style.css" />
    <link rel="stylesheet" type="text/css" href="static/upload-file/upload.css" />
    <!--[if IE 6]>
    <script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js"></script>
    <script>DD_belatedPNG.fix('*');</script><![endif]-->
    <link href="lib/lightbox2/2.8.1/css/lightbox.css" rel="stylesheet" type="text/css">
    <link href="lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css"/>
    <link href="lib/datetimepicker/datetimepicker.css" rel="stylesheet" type="text/css"/>
    <title>项目编辑</title>
    <style>
        .select-box1 {
            padding-left: 0;
        }

        .select-box1 select {
            font-size: 14px;
            height: 31px;
            line-height: 1.42857;
            padding: 4px;
            border: 1px #ddd solid;
        }

        .edit_h31 {
            border-bottom: 1px #ddd solid;
            overflow: hidden;
        }

        .tabCon {
            width: 100%;
        }

        .tabCon {
            display: none;
            overflow: hidden;
            width：100%;
        }

        .tabBar {
            border: none;
            position: fixed;
            top: 30px;
            right: 20px;
            z-index: 9999;
        }

        .tabBar span {
            display: block;
            float: none;
            font-size: 20px;
            line-height: 30px;
            padding: 5px 15px;
            font-weight: normal;
            color: #a7a5a5
        }

        .tabBar span.current {
            background-color: #5a98de;
        }

        .tabCon .form-label {
            margin-top: 3px;
            cursor: text;
            text-align: right;
        }

        .tabCon .row {
            margin-top: 15px;
        }

        .clearfix section,.clearfix{float:left;display:inline;}
        .img-box .upimg-div .z_file{overflow: hidden}
    </style>
    <script type="text/javascript">
        $('#datetimepicker').datetimepicker({
            lang: $.datetimepicker.setLocale('ch'),
        });

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
                            var imgs = $("#"+inputId).val();
                            if(imgs == null || imgs == ''){
                                $("#"+inputId).val(data[i]);
                            }else{
                                $("#"+inputId).val(imgs+","+data[i]);
                            }

                            imgStr += '<section class="up-section fl">'
                                    +'  <span class="up-span"></span>'
                                    +'  <img class="close-upimg" src="static/upload-file/a7.png">'
                                    +'  <img class="up-img" src="http://test.img.meirenguvip.com/'+data[i]+'">'
                                    +'  <p class="img-name-p">'+data[i]+'</p>'
                                    +'  <input id="taglocation" name="taglocation" value="" type="hidden">'
                                    +'  <input id="tags" name="tags" value="" type="hidden">'
                                    +'</section>';
                        }

                        $("#"+parentId).append(imgStr);
                    }
                }
            }

        }

    </script>
</head>
<body>
<div class="page-container">
    <!-- 选项卡 -->

    <span class="form form-horizontal" id="form-article-add">
        <!-- 基本信息 -->
        <div>
            <form action="add" id="itemAddForm" method="post">

                <input type="hidden" name="itemId" id="itemId" value="${item.itemId}">
                <div class="row cl">
                    <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">基本信息</h3>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">项目名称：</label>
                    <div class="formControls col-xs-8 col-sm-8">
                        <input type="text" class="input-text" value="${item.itemName}" id="itemName" name="itemName"
                               maxlength="30" required placeholder="项目标题最多30字">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">项目简介：</label>
                    <div class="formControls col-xs-8 col-sm-8">
                        <textarea id="itemProfile" name="itemProfile" cols="" rows="" class="textarea" placeholder="..."
                                  datatype="*10-100"
                                  dragonfly="true" nullmsg="备注不能为空！">${item.itemProfile}</textarea>
                        <p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">众筹类型：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <span class="select-box">
                            <select name="typeId" id="typeId" class="select">
                                <c:forEach items="${type}" var="type">
                                    <c:if test="${type.typeId == item.typeId}">
                                        <option value="${type.typeId}" selected>${type.typeName}</option>
                                    </c:if>
                                    <c:if test="${type.typeId != item.typeId}">
                                        <option value="${type.typeId}">${type.typeName}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </span>
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">项目分类：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <span class="select-box">
                            <select name="classId" id="classId" class="select">
                                <c:forEach items="${itemClass}" var="itemClass">
                                    <c:if test="${itemClass.classId == item.typeId}">
                                        <option value="${itemClass.classId}" selected>${itemClass.className}</option>
                                    </c:if>
                                    <c:if test="${itemClass.classId != item.typeId}">
                                        <option value="${itemClass.classId}">${itemClass.className}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </span>
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">目标金额：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <input type="text" class="input-text" value="${item.targetAmount}" placeholder="" id="targetAmount"
                               name="targetAmount" required>
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">预热天数：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <input type="text" class="input-text" value="${item.preheatingDays}" placeholder=""
                               id="preheatingDays" name="preheatingDays">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">项目方：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <span class="select-box">
                            <select name="partnerId" id="partnerId" class="select">
                                <c:forEach items="${partner}" var="partner">
                                    <c:if test="${item.partnerId == partner.partnerId}">
                                        <option value="${partner.partnerId}" selected>${partner.partnerName}</option>
                                    </c:if>
                                    <c:if test="${item.partnerId != partner.partnerId}">
                                        <option value="${partner.partnerId}">${partner.partnerName}</option>
                                    </c:if>
                                </c:forEach>
                            </select>
                        </span>
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">众筹天数：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <input type="text" class="input-text" value="${item.crowdDays}" placeholder="" id="crowdDays"
                               name="crowdDays">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2" style="line-height:30px;">项目地区：</label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <label data-toggle="distpicker" style="display:block;width:100%">
                            <span class="select-box select-box1" style="border:none;">
                            <select name="provinceSelect" id="provinceSelect" class="col-sm-3 col-xs-8" data-province="---- 选择省 ----" onchange="getCity()">
                                <c:forEach items="${provinces}" var="provinces">
                                    <option value="${provinces.areaId}">${provinces.areaName}</option>
                                </c:forEach>
                            </select>
                            <select name="" id="citySelect" class="col-sm-3 col-xs-8 col-sm-offset-1" data-city="---- 选择市 ----" onchange="getArea()"></select>
                            <select name="areaId" id="areaSelect" class="col-sm-3 col-xs-8 col-sm-offset-1" data-district="---- 选择区 ----" onchange=""></select>
                            </span>
                        </label>
                    </div>
                </div>

                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">项目头图：</label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <input type="hidden" id="headerImage" name="headerImage" value="${item.headerImage}">
                        <div class="img-box full">
                            <section class=" img-section">
                                <div class="z_photo upimg-div clearfix">
                                    <div id="imgParent0" class="clearfix">
                                        <c:if test="${not empty item.headerImage}">
                                            <section class="up-section fl">
                                                <span class="up-span"></span>
                                                <img class="close-upimg" src="static/upload-file/a7.png">
                                                <img class="up-img" src="http://test.img.meirenguvip.com/${item.headerImage}">
                                                <p class="img-name-p">${item.headerImage}</p>
                                                <input id="taglocation" name="taglocation" value="" type="hidden">
                                                <input id="tags" name="tags" value="" type="hidden">
                                            </section>
                                        </c:if>
                                    </div>
                                    <section class="z_file fl">
                                        <img src="static/upload-file/a11.png" class="add-img">
                                        <input type="file" name="file" id="file0" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" multiple="" onchange="uploadFile('file0','item','imgParent0','headerImage')">
                                    </section>
                                 </div>
                             </section>
                        </div>
                    </div>
                </div>

                <div class="row cl">
                    <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2 mt-30 mb-20">
                        <button class="btn btn-primary radius" type="button" onclick="itemAdd()">保存</button>
                    </div>
                </div>
            </form>
        </div>

        <!-- 内容设置 -->
        <div>
            <div class="row cl">
                <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">内容设置</h3>
            </div>
            <div class="row cl content_tab_menu col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10">
                <div class="wrapper">
                    <c:if test="${not empty content}">
                        <c:forEach items="${content}" var="content" varStatus="status">
                            <c:if test="${status.count == 1}">
                                <span class="current" cnum="${status.count-1}">${content.contentTitle}<var></var></span>
                            </c:if>
                            <c:if test="${status.count != 1}">
                                <span cnum="${status.count-1}">${content.contentTitle}<var></var></span>
                            </c:if>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty content}">
                        <span cnum="0">内容<var></var></span>
                    </c:if>
                </div>
                <em>+</em>
            </div>
            <div class="cl"></div>
            <div class="content_set">
                <c:if test="${not empty content}">
                    <c:forEach items="${content}" var="content" varStatus="status">
                        <c:if test="${status.count == 1}">
                            <div class="item">
                        </c:if>
                        <c:if test="${status.count != 1}">
                            <div class="item" style="display: none;">
                        </c:if>
                            <form action="content/add" method="post" id="contentAddForm${status.count-1}">
                                <input type="hidden" name="itemId" id="itemId${status.count-1}"  value="${item.itemId}">
                                <input type="hidden" name="contentType" value="1">
                                <input type="hidden" id="contentId${status.count-1}" name="contentId" value="${content.contentId}">
                                <div class="row cl">
                                    <label class="form-label col-xs-4 col-sm-2">主标题：</label>
                                    <div class="formControls col-xs-8 col-sm-8">
                                        <input type="text" class="input-text" value="${content.contentTitle}" maxlength="30" placeholder="项目标题最多30字" id="contentTitle${status.count-1}"
                                               name="contentTitle">
                                    </div>
                                </div>
                                <div class="row cl">
                                    <label class="form-label col-xs-4 col-sm-2">内容：</label>
                                    <div class="formControls col-xs-8 col-sm-8">
                                        <input type="hidden" id="contentInfo${status.count-1}" name="contentInfo" value="${content.contentInfo}">
                                        <div class="img-box full">
                                            <section class=" img-section">
                                                <div class="z_photo upimg-div clearfix">
                                                    <div id="imgParent1${status.count-1}" class="clearfix">
                                                        <c:forEach items="${fn:split(content.contentInfo, ',')}" var="imgs">
                                                            <section class="up-section fl">
                                                                <span class="up-span"></span>
                                                                <img class="close-upimg" src="static/upload-file/a7.png">
                                                                <img class="up-img" src="http://test.img.meirenguvip.com/${imgs}">
                                                                <p class="img-name-p">${imgs}</p>
                                                                <input id="taglocation" name="taglocation" value="" type="hidden">
                                                                <input id="tags" name="tags" value="" type="hidden">
                                                            </section>
                                                        </c:forEach>
                                                    </div>
                                                    <section class="z_file fl">
                                                        <img src="static/upload-file/a11.png" class="add-img">
                                                        <input type="file" name="file" id="file1${status.count-1}" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" multiple="" onchange="uploadFile('file1${status.count-1}','item','imgParent1${status.count-1}','contentInfo${status.count-1}')">
                                                    </section>
                                                 </div>
                                             </section>
                                        </div>
                                    </div>
                                </div>

                                <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2 mt-30 mb-20">
                                    <button class="btn btn-primary radius" id="contentAddBtn${status.count-1}" type="button"  onclick="contentAdd()"><i
                                            class="Hui-iconfont">&#xe632;</i> 保存</button>
                                </div>
                            </form>
                        </div>
                    </c:forEach>
                </c:if>
                <c:if test="${empty content}">
                    <div class="item">
                        <form action="content/add" method="post" id="contentAddForm0">
                            <input type="hidden" name="itemId" id="itemId0"  value="${item.itemId}">
                            <input type="hidden" name="contentType" value="1">
                            <input type="hidden" id="contentId0" name="contentId" value="0">
                            <div class="row cl">
                                <label class="form-label col-xs-4 col-sm-2">主标题：</label>
                                <div class="formControls col-xs-8 col-sm-8">
                                    <input type="text" class="input-text" value="" maxlength="30" placeholder="项目标题最多30字" id="contentTitle${status.count-1}"
                                           name="contentTitle">
                                </div>
                            </div>
                            <div class="row cl">
                                <label class="form-label col-xs-4 col-sm-2">内容：</label>
                                <div class="formControls col-xs-8 col-sm-8">
                                    <input type="hidden" id="contentInfo0" name="contentInfo" value="">
                                    <div class="img-box full">
                                        <section class=" img-section">
                                            <div class="z_photo upimg-div clearfix">
                                                <div id="imgParent10" class="clearfix">
                                                </div>
                                                <section class="z_file fl">
                                                    <img src="static/upload-file/a11.png" class="add-img">
                                                    <input type="file" name="file" id="file10" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" multiple="" onchange="uploadFile('file10','item','imgParent10','contentInfo0')">
                                                </section>
                                             </div>
                                         </section>
                                    </div>
                                </div>
                            </div>

                            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2 mt-30 mb-20">
                                <button class="btn btn-primary radius" id="contentAddBtn0" type="button"  onclick="contentAdd()"><i
                                        class="Hui-iconfont">&#xe632;</i> 保存</button>
                            </div>
                        </form>
                    </div>
                </c:if>
            </div>

            <div class="huibao_set">
                <div class="row cl">
                    <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">回报设置</h3>
                </div>

                <style>
                    .huibao_tab_menu, .content_tab_menu {
                        border-bottom: 1px #ddd solid;
                    }

                    .huibao_tab_menu span var, .content_tab_menu span var {
                        z-index: 222;
                        position: absolute;
                        display: none;
                        width: 12px;
                        height: 12px;
                        border-radius: 50%;
                        font-size: 10px;
                        line-height: 10px;
                        text-align: center;
                        background-color: red;
                        color: #fff;
                        right: 2px;
                        top: 1px;
                    }

                    .huibao_tab_menu span var::before, .content_tab_menu span var::before {
                        content: "－";
                    }

                    .huibao_tab_menu span:hover var, .content_tab_menu span:hover var {
                        display: block;
                    }

                    .huibao_tab_menu span, .huibao_tab_menu em, .content_tab_menu span, .content_tab_menu em {
                        float: left;
                        display: inline;
                        position: relative;
                        line-height: 28px;
                        border-top-left-radius: 8px;
                        border-top-right-radius: 8px;
                        color: #a7a5a5;
                        padding: 5px 15px;
                        cursor: pointer;
                        background-color: #e8e8e8;
                    }

                    .huibao_tab_menu span.current, .content_tab_menu span.current {
                        background-color: #5a98de;
                        color: #fff;
                    }

                    .huibao_tab_menu em, .content_tab_menu em {
                        cursor: pointer;
                        font-style: normal;
                        font-size: 16px;
                        font-weight: 400;
                        background-color: #5eb95e;
                        color: #fff;
                    }

                    .huibao_set .huibao_tab, .content_set .item {
                        display: none;
                    }
                </style>

                <div class="row cl huibao_tab_menu col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10">
                    <div class="wrapper">
                        <c:if test="${not empty level}">
                            <c:forEach items="${level}" var="level" varStatus="status">
                                <c:if test="${status.count == 1}">
                                    <span class="current" cnum="${status.count-1}">${level.levelName}<var></var></span>
                                </c:if>
                                <c:if test="${status.count != 1}">
                                    <span cnum="${status.count-1}">${level.levelName}<var></var></span>
                                </c:if>
                            </c:forEach>
                        </c:if>
                        <c:if test="${empty level}">
                            <span class="current" cnum="0">档位<var></var></span>
                        </c:if>
                    </div>
                    <em>+</em>
                </div>
                <div class="cl"></div>
                <div class="huibao_wrapper">
                    <c:if test="${not empty level}">
                        <c:forEach items="${level}" var="level" varStatus="status">
                            <div class="huibao_tab">
                                <form id="levelAddForm${status.count-1}" >
                                    <input type="hidden" id="itemId1${status.count-1}" name="itemId"  value="${item.itemId}">
                                    <input type="hidden" id="levelId${status.count-1}" name="levelId" value="${level.levelId}">
                                    <div class="row cl" style="display:block">
                                        <label class="form-label col-xs-4 col-sm-2">档位名称：</label>
                                        <div class="formControls col-xs-8 col-sm-8">
                                            <input type="text" class="input-text" value="${level.levelName}" maxlength="30" id="levelName${status.count-1}" name="levelName">
                                        </div>
                                    </div>
                                    <div class="row cl">
                                        <label class="form-label col-xs-4 col-sm-2">支持金额：</label>
                                        <div class="formControls col-xs-8 col-sm-8">
                                            <input type="text" class="input-text" value="${level.levelAmount}" maxlength="30" id="levelAmount${status.count-1}" name="levelAmount">
                                        </div>
                                    </div>
                                    <div class="row cl">
                                        <label class="form-label col-xs-4 col-sm-2">回报描述：</label>
                                        <div class="formControls col-xs-8 col-sm-8">
                                            <textarea name="levelDesc" id="levelDesc${status.count-1}" cols="" rows="" class="textarea" placeholder="..." datatype="*10-100"
                                                      dragonfly="true" nullmsg="备注不能为空！">${level.levelDesc}</textarea>
                                        </div>
                                    </div>

                                    <div class="row cl">
                                        <label class="form-label col-xs-4 col-sm-2">总份数：</label>
                                        <div class="formControls col-xs-8 col-sm-3">
                                            <input type="text" class="input-text" value="${level.totalNumber}" placeholder="0即为无限制" maxlength="30" id="totalNumber${status.count-1}"
                                                   name="totalNumber">
                                        </div>
                                        <label class="form-label col-xs-4 col-sm-2">单人限额：</label>
                                        <div class="formControls col-xs-8 col-sm-3">
                                            <input type="text" class="input-text" value="${level.singleLimitNumber}" placeholder="0即为无限制" maxlength="30" id="singleLimitNumber${status.count-1}"
                                                   name="singleLimitNumber">
                                        </div>
                                    </div>
                                    <div class="row cl">
                                        <label class="form-label col-xs-4 col-sm-2">回报时间：</label>
                                        <div class="formControls col-xs-8 col-sm-3">
                                            <input type="text" class="input-text" value="${level.paybackDays}" placeholder="100 天" maxlength="30" id="paybackDays${status.count-1}"
                                                   name="paybackDays">
                                        </div>
                                        <label class="form-label col-xs-4 col-sm-2">是否分红：</label>
                                        <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                                        <select id="isShareBonus${status.count-1}" name="isShareBonus" class="select">
                                            <c:if test="${level.isShareBonus == 1}">
                                                <option value="1">是</option>
                                                <option value="0">否</option>
                                            </c:if>
                                            <c:if test="${level.isShareBonus == 0}">
                                                <option value="1">是</option>
                                                <option value="0" selected>否</option>
                                            </c:if>
                                        </select>
                                        </span>
                                        </div>
                                    </div>
                                    <div class="row cl">
                                        <label class="form-label col-xs-4 col-sm-2">年化利率：</label>
                                        <div class="formControls col-xs-8 col-sm-3">
                                            <input type="text" class="input-text" value="${level.yearRate}" placeholder="%" maxlength="30" id="yearRate${status.count-1}"
                                                   name="yearRate">
                                        </div>
                                        <label class="form-label col-xs-4 col-sm-2">投资期限：</label>
                                        <div class="formControls col-xs-8 col-sm-3">
                                            <input type="text" class="input-text" value="${level.investmentPeriod}" placeholder=" 月" maxlength="30" id="investmentPeriod${status.count-1}"
                                                   name="investmentPeriod">
                                        </div>
                                    </div>
                                    <div class="row cl">
                                        <label class="form-label col-xs-4 col-sm-2">收益方式：</label>
                                        <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                                        <select name="revenueModel" id="revenueModel${status.count-1}" class="select">
                                            <c:if test="${level.revenueModel == 1}">
                                                <option value="1">一次性还款</option>
                                                <option value="2">按月还息到期还本</option>
                                            </c:if>
                                            <c:if test="${level.revenueModel == 2}">
                                                <option value="1">一次性还款</option>
                                                <option value="2" selected>按月还息到期还本</option>
                                            </c:if>
                                        </select>
                                        </span>
                                        </div>
                                        <label class="form-label col-xs-4 col-sm-2">分红周期：</label>
                                        <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                                        <select name="shareBonusPeriod" id="shareBonusPeriod${status.count-1}" class="select">
                                            <c:if test="${level.shareBonusPeriod == 1}">
                                                <option value="1" selected>1月</option>
                                                <option value="3">3月</option>
                                                <option value="6">6月</option>
                                                <option value="12">12月</option>
                                            </c:if>
                                            <c:if test="${level.shareBonusPeriod == 3}">
                                                <option value="1">1月</option>
                                                <option value="3" selected>3月</option>
                                                <option value="6">6月</option>
                                                <option value="12">12月</option>
                                            </c:if>
                                            <c:if test="${level.shareBonusPeriod == 6}">
                                                <option value="1">1月</option>
                                                <option value="3">3月</option>
                                                <option value="6" selected>6月</option>
                                                <option value="12">12月</option>
                                            </c:if>
                                            <c:if test="${level.shareBonusPeriod == 12}">
                                                <option value="1">1月</option>
                                                <option value="3">3月</option>
                                                <option value="6">6月</option>
                                                <option value="12" selected>12月</option>
                                            </c:if>
                                        </select>
                                        </span>
                                        </div>
                                    </div>
                                    <div class="row cl">
                                        <label class="form-label col-xs-4 col-sm-2">是否需要地址：</label>
                                        <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                                        <select name="isNeedAddress" id="isNeedAddress${status.count-1}" class="select">
                                            <c:if test="${level.isNeedAddress == 1}">
                                                <option value="1" selected>是</option>
                                                <option value="0">否</option>
                                            </c:if>
                                            <c:if test="${level.isNeedAddress == 0}">
                                                <option value="1">是</option>
                                                <option value="0" selected>否</option>
                                            </c:if>
                                        </select>
                                        </span>
                                        </div>
                                        <label class="form-label col-xs-4 col-sm-2">是否需要协议：</label>
                                        <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                                        <select name="isNeedAgreement" id="isNeedAgreement${status.count-1}" class="select">
                                            <c:if test="${level.isNeedAgreement == 1}">
                                                <option value="1" selected>是</option>
                                                <option value="0">否</option>
                                            </c:if>
                                            <c:if test="${level.isNeedAgreement == 0}">
                                                <option value="1">是</option>
                                                <option value="0" selected>否</option>
                                            </c:if>
                                        </select>
                                        </span>
                                        </div>
                                    </div>
                                    <div class="row cl">
                                        <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2 mt-30 mb-20">
                                            <button class="btn btn-secondary radius" type="button" id="levelAddBtn${status.count-1}"  onclick="levelAdd()"><i
                                                    class="Hui-iconfont">&#xe632;</i> 保存
                                            </button>
                                        </div>
                                    </div>
                                </form>
                            </div>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty level}">
                        <div class="huibao_tab">
                            <form id="levelAddForm0" >
                                <input type="hidden" id="itemId10" name="itemId"  value="${item.itemId}">
                                <input type="hidden" id="levelId0" name="levelId" value="0">
                                <div class="row cl" style="display:block">
                                    <label class="form-label col-xs-4 col-sm-2">档位名称：</label>
                                    <div class="formControls col-xs-8 col-sm-8">
                                        <input type="text" class="input-text" value="" maxlength="30" id="levelName0" name="levelName">
                                    </div>
                                </div>
                                <div class="row cl">
                                    <label class="form-label col-xs-4 col-sm-2">支持金额：</label>
                                    <div class="formControls col-xs-8 col-sm-8">
                                        <input type="text" class="input-text" value="" maxlength="30" id="levelAmount0" name="levelAmount">
                                    </div>
                                </div>
                                <div class="row cl">
                                    <label class="form-label col-xs-4 col-sm-2">回报描述：</label>
                                    <div class="formControls col-xs-8 col-sm-8">
                                        <textarea name="levelDesc" id="levelDesc0" cols="" rows="" class="textarea" placeholder="..." datatype="*10-100"
                                                  dragonfly="true" nullmsg="备注不能为空！"
                                                  onKeyUp="$.Huitextarealength(this,200)"></textarea>
                                        <p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
                                    </div>
                                </div>

                                <div class="row cl">
                                    <label class="form-label col-xs-4 col-sm-2">总份数：</label>
                                    <div class="formControls col-xs-8 col-sm-3">
                                        <input type="text" class="input-text" value="" placeholder="0即为无限制" maxlength="30" id="totalNumber0"
                                               name="totalNumber">
                                    </div>
                                    <label class="form-label col-xs-4 col-sm-2">单人限额：</label>
                                    <div class="formControls col-xs-8 col-sm-3">
                                        <input type="text" class="input-text" value="" placeholder="0即为无限制" maxlength="30" id="singleLimitNumber0"
                                               name="singleLimitNumber">
                                    </div>
                                </div>
                                <div class="row cl">
                                    <label class="form-label col-xs-4 col-sm-2">回报时间：</label>
                                    <div class="formControls col-xs-8 col-sm-3">
                                        <input type="text" class="input-text" value="" placeholder="100 天" maxlength="30" id="paybackDays0"
                                               name="paybackDays">
                                    </div>
                                    <label class="form-label col-xs-4 col-sm-2">是否分红：</label>
                                    <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                                    <select id="isShareBonus0" name="isShareBonus" class="select">
                                        <option value="1">是</option>
                                        <option value="0">否</option>
                                    </select>
                                    </span>
                                    </div>
                                </div>
                                <div class="row cl">
                                    <label class="form-label col-xs-4 col-sm-2">年化利率：</label>
                                    <div class="formControls col-xs-8 col-sm-3">
                                        <input type="text" class="input-text" value="" placeholder="%" maxlength="30" id="yearRate0"
                                               name="yearRate">
                                    </div>
                                    <label class="form-label col-xs-4 col-sm-2">投资期限：</label>
                                    <div class="formControls col-xs-8 col-sm-3">
                                        <input type="text" class="input-text" value="" placeholder=" 月" maxlength="30" id="investmentPeriod0"
                                               name="investmentPeriod">
                                    </div>
                                </div>
                                <div class="row cl">
                                    <label class="form-label col-xs-4 col-sm-2">收益方式：</label>
                                    <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                                    <select name="revenueModel" id="revenueModel0" class="select">
                                        <option value="1">一次性还款</option>
                                        <option value="2">按月还息到期还本</option>
                                    </select>
                                    </span>
                                    </div>
                                    <label class="form-label col-xs-4 col-sm-2">分红周期：</label>
                                    <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                                    <select name="shareBonusPeriod" id="shareBonusPeriod0" class="select">
                                        <option value="1">1月</option>
                                        <option value="3">3月</option>
                                        <option value="6">6月</option>
                                        <option value="12">12月</option>
                                    </select>
                                    </span>
                                    </div>
                                </div>
                                <div class="row cl">
                                    <label class="form-label col-xs-4 col-sm-2">是否需要地址：</label>
                                    <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                                    <select name="isNeedAddress" id="isNeedAddress0" class="select">
                                        <option value="1">是</option>
                                        <option value="0">否</option>
                                    </select>
                                    </span>
                                    </div>
                                    <label class="form-label col-xs-4 col-sm-2">是否需要协议：</label>
                                    <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                                    <select name="isNeedAgreement" id="isNeedAgreement0" class="select">
                                        <option value="1">是</option>
                                        <option value="0">否</option>
                                    </select>
                                    </span>
                                    </div>
                                </div>
                                <div class="row cl">
                                    <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2 mt-30 mb-20">
                                        <button class="btn btn-secondary radius" type="button" id="levelAddBtn0"  onclick="levelAdd()"><i
                                                class="Hui-iconfont">&#xe632;</i> 保存
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </c:if>

                </div>
            </div>

        </div>
        <!-- 保存提交 -->
        <div class="row cl">
            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2 mt-30 mb-20">
                <button class="btn btn-primary radius" type="button" onclick="verify()"><i class="Hui-iconfont">&#xe632;</i> 提交审核
                </button>
                <button onClick="layer_close();" class="btn btn-default radius" type="button">
                    &nbsp;&nbsp;取消&nbsp;&nbsp;</button>
            </div>
        </div>

    </span>
</div>

<script src=lib/jquery/1.9.1/jquery.min.js></script>
<script src=lib/layer/2.4/layer.js></script>
<script src=lib/jquery.validation/1.14.0/jquery.validate.js></script>
<script src=lib/jquery.validation/1.14.0/validate-methods.js></script>
<script src=lib/jquery.validation/1.14.0/messages_zh.js></script>
<script src=static/h-ui/js/H-ui.js></script>
<script src=static/h-ui.admin/js/H-ui.admin.page.js></script>
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript" src="lib/webuploader/0.1.5/webuploader.min.js"></script>
<script type="text/javascript" src="lib/ueditor/1.4.3/ueditor.config.js"></script>
<script type="text/javascript" src="lib/ueditor/1.4.3/ueditor.all.min.js"></script>
<script type="text/javascript" src="lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
<!-- 换灯箱 -->
<script type="text/javascript" src="lib/lightbox2/2.8.1/js/lightbox.min.js"></script>
<script type="text/javascript" src="lib/datetimepicker/datetimepicker.js"></script>
<script type="text/javascript" src="lib/jsp/item/itemEdit.js"/>

<script type="text/javascript">

    $('#datetimepicker').datetimepicker({
        lang: $.datetimepicker.setLocale('ch'),
    });

</script>
</body>
</html>
