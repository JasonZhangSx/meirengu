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
    <![endif]-->
    <link rel=stylesheet type=text/css href="static/h-ui/css/H-ui.min.css" />
    <link rel=stylesheet type=text/css href="static/h-ui.admin/css/H-ui.admin.css" />
    <link rel=stylesheet type=text/css href="lib/Hui-iconfont/1.0.8/iconfont.css" />
    <link rel=stylesheet type=text/css href="static/h-ui.admin/skin/default/skin.css" id=skin/>
    <link rel=stylesheet type=text/css href="static/h-ui.admin/css/style.css" />
    <link rel="stylesheet" type="text/css" href="static/upload-file/upload.css" />
    <!--[if IE 6]>
    <script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js"></script>
    <script>DD_belatedPNG.fix('*');</script><![endif]-->

    <link href="lib/lightbox2/2.8.1/css/lightbox.css" rel="stylesheet" type="text/css">
    <link href="lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css"/>
    <link href="lib/datetimepicker/datetimepicker.css" rel="stylesheet" type="text/css"/>
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


        .huibao_tab_menu,.content_tab_menu  {
            border-bottom: 1px #ddd solid;
        }

        .huibao_tab_menu span,.content_tab_menu span {
            float: left;
            display: inline;
            line-height: 28px;
            border-top-left-radius: 8px;
            border-top-right-radius: 8px;
            color: #a7a5a5;
            padding: 5px 15px;
            cursor: pointer;
            background-color: #e8e8e8;
        }

        .huibao_tab_menu span.current ,.content_tab_menu span.current{
            background-color: #5a98de;
            color: #fff;
        }

        .huibao_set .huibao_tab {
            display: none;
        }

        /* 去掉输入样式 */
        .jiben_info input[type="text"], .jiben_info select, .jiben_info textarea, .jiben_info option, .jiben_info .select-box,
        .neirong_set input[type="text"], .neirong_set select, .neirong_set textarea, .neirong_set option, .neirong_set .select-box,
        .huibao_set input[type="text"], .huibao_set select, .huibao_set textarea, .huibao_set option, .huibao_set .select-box {
            -webkit-appearance: initial;
            border: none !important;
            cursor: auto
        }

        input[disabled=""],textarea[disabled=""] {
            background-color: #fff;
        }
        .uploader-thum-container1 .uploader-list .item {
            float: left;
            display: inline;
            width: 25%;
        }
        .clearfix section,.clearfix{float:left;display:inline;}
        .img-box .upimg-div .z_file{overflow: hidden}

        .u_img, .fl{
            background: #00b7ee;
            cursor: pointer;
            width: 100px;
            height: 30px;
            color: #fff;
            text-align: center;
            border-radius: 3px;
            overflow: hidden;
            padding-top: 8px;
            border: none;
        }
    </style>
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
        function uploadFile(fileId, foldName, parentId, inputId, type) {
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
                            if(type == 1){
                                imgStr += '<div class="picbox" style="margin: 5px; width: 100px; height: 100px;">'
                                        + '   <a href="http://test.img.meirenguvip.com/'+data[i]+'" data-lightbox="gallery">'
                                        + '   <img src="http://test.img.meirenguvip.com/'+data[i]+'?x-oss-process=image/resize,m_lfit,h_100,w_100"></a>'
                                        + '</div>';
                            }else if(type == 2){
                                imgStr += '<section class="up-section fl">'
                                        +'  <span class="up-span"></span>'
                                        +'  <img class="close-upimg" src="static/upload-file/a7.png">'
                                        +'  <img class="up-img" src="http://test.img.meirenguvip.com/'+data[i]+'">'
                                        +'  <p class="img-name-p">'+data[i]+'</p>'
                                        +'  <input id="taglocation" name="taglocation" value="" type="hidden">'
                                        +'  <input id="tags" name="tags" value="" type="hidden">'
                                        +'</section>';
                            }

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

    <div class="form form-horizontal" id="form-article-add">

        <!-- 基本信息 -->
        <div class="jiben_info">
            <div class="row cl">
                <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">基本信息</h3>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">项目名称：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="text" class="input-text" value="${item.itemName}" disabled id="articletitle" name="articletitle"
                           maxlength="30" placeholder="项目标题最多30字" id="" name="">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">项目简介：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <textarea name="" cols="" rows="" class="textarea" placeholder="..." datatype="*10-100"  disabled
                              dragonfly="true" nullmsg="备注不能为空！" onKeyUp="$.Huitextarealength(this,200)">${item.itemProfile}</textarea>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">众筹类型：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <c:forEach items="${type}" var="type">
                        <c:if test="${type.typeId == item.typeId}">
                            <input type="text" class="input-text" value="${type.typeName}">
                        </c:if>
                    </c:forEach>
                </div>
                <label class="form-label col-xs-4 col-sm-2">项目分类：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <c:forEach items="${itemClass}" var="itemClass">
                        <c:if test="${itemClass.classId == item.typeId}">
                            <input type="text" class="input-text" value="${itemClass.className}">
                        </c:if>
                    </c:forEach>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">目标金额：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${item.targetAmount}" disabled >
                </div>
                <label class="form-label col-xs-4 col-sm-2">预热天数：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${item.preheatingDays}" disabled >
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">项目方：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <c:forEach items="${partner}" var="partner">
                        <c:if test="${item.partnerId == partner.partnerId}">
                            <input type="text" class="input-text" value="${partner.partnerName}">
                        </c:if>
                    </c:forEach>
                </div>
                <label class="form-label col-xs-4 col-sm-2">众筹天数：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${item.crowdDays}" disabled >
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2" style="line-height:30px;">项目地区：</label>
                <div class="formControls col-xs-8 col-sm-9">
                    <label data-toggle="distpicker" style="display:block;width:100%">
						<span class="select-box select-box1" style="border:none;">
					  	<select name="" class="col-sm-3 col-xs-8" data-province="---- 选择省 ----"></select>
						  <select name="" class="col-sm-3 col-xs-8 col-sm-offset-1" data-city="---- 选择市 ----"></select>
						  <select name="" class="col-sm-3 col-xs-8 col-sm-offset-1"
                                  data-district="---- 选择区 ----"></select>
						</span>
                    </label>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">项目头图：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <!-- 审核模式 查看图 -->
                    <div class="portfoliobox">
                        <div class="picbox">
                            <a href="${imageUrl}${item.headerImage}" data-lightbox="gallery"><img
                                    src="${imageUrl}${item.headerImage}?x-oss-process=image/resize,m_lfit,h_200,w_200"></a>
                        </div>
                    </div>

                </div>
            </div>
        </div>

        <!-- 内容设置 -->
        <div class="neirong_set">
            <div class="row cl">
                <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">内容设置</h3>
            </div>
            <div class="row cl content_tab_menu col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10">
                <div class="wrapper">
                    <c:if test="${not empty content}">
                        <c:forEach items="${content}" var="content" varStatus="status">
                            <c:if test="${status.count == 1}">
                                <span class="current">${content.contentTitle}<var></var></span>
                            </c:if>
                            <c:if test="${status.count != 1}">
                                <span>${content.contentTitle}<var></var></span>
                            </c:if>
                        </c:forEach>
                    </c:if>
                    <c:if test="${empty content}">
                        <span>内容<var></var></span>
                    </c:if>
                </div>
                <%--<em>+</em>--%>
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

                                <c:forEach items="${fn:split(content.contentInfo, ',')}" var="imgs">
                                    <!-- 审核模式 查看图 -->
                                    <div class="portfoliobox" style="float: left;">
                                        <div class="picbox">
                                            <a href="${imageUrl}${imgs}" data-lightbox="gallery">
                                                <img src="${imageUrl}${imgs}?x-oss-process=image/resize,m_lfit,h_200,w_200" /></a>
                                        </div>
                                    </div>
                                </c:forEach>

                            </div>
                        </div>
                    </div>
                    </c:forEach>
                    </c:if>
                </div>
            </div>
            <!-- 回报信息 -->
            <div class="huibao_set">
                <div class="row cl">
                    <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">回报信息</h3>
                </div>


                <div class="row cl huibao_tab_menu col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10">
                    <c:forEach items="${level}" var="level" varStatus="status">
                        <c:if test="${status.index == 0}">
                            <span class="current">${level.levelName}</span>
                        </c:if>
                        <c:if test="${status.index != 0}">
                            <span>${level.levelName}</span>
                        </c:if>
                    </c:forEach>
                </div>
                <div class="cl"></div>
                <c:forEach items="${level}" var="level">
                    <div class="huibao_tab">
                        <div class="row cl" style="display:block">
                            <label class="form-label col-xs-4 col-sm-2">档位名称：</label>
                            <div class="formControls col-xs-8 col-sm-8">
                                <input type="text" class="input-text" value="${level.levelName}" maxlength="30" disabled >
                            </div>
                        </div>
                        <div class="row cl">
                            <label class="form-label col-xs-4 col-sm-2">支持金额：</label>
                            <div class="formControls col-xs-8 col-sm-8">
                                <input type="text" class="input-text" value="${level.levelAmount}" maxlength="30" disabled >
                            </div>
                        </div>
                        <div class="row cl">
                            <label class="form-label col-xs-4 col-sm-2">回报描述：</label>
                            <div class="formControls col-xs-8 col-sm-8">
                            <textarea name="" cols="" rows="" class="textarea" placeholder="..." datatype="*10-100"
                                      dragonfly="true" nullmsg="备注不能为空！" disabled
                                      onKeyUp="$.Huitextarealength(this,200)">${level.levelDesc}</textarea>
                            </div>
                        </div>

                        <div class="row cl">
                            <label class="form-label col-xs-4 col-sm-2">总份数：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                <input type="text" class="input-text" value="${level.totalNumber}" disabled>
                            </div>
                            <label class="form-label col-xs-4 col-sm-2">单人限额：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                <input type="text" class="input-text" value="${level.singleLimitNumber}" disabled >
                            </div>
                        </div>
                        <div class="row cl">
                            <label class="form-label col-xs-4 col-sm-2">回报时间：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                <input type="text" class="input-text" value="${level.paybackDays}天" maxlength="30" disabled >
                            </div>
                            <label class="form-label col-xs-4 col-sm-2">是否分红：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                <c:if test="${level.isShareBonus == 1}">是</c:if>
                                <c:if test="${level.isShareBonus == 0}">否</c:if>
                            </div>
                        </div>
                        <div class="row cl">
                            <label class="form-label col-xs-4 col-sm-2">年化利率：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                <input type="text" class="input-text" value="${level.yearRate}%" maxlength="30" id="" name="">
                            </div>
                            <label class="form-label col-xs-4 col-sm-2">投资期限：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                <input type="text" class="input-text" value="${level.investmentPeriod}月" maxlength="30" disabled >
                            </div>
                        </div>
                        <div class="row cl">
                            <label class="form-label col-xs-4 col-sm-2">收益方式：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                <c:if test="${level.revenueModel == 1}">一次性还款</c:if>
                                <c:if test="${level.revenueModel == 2}">按月还息到期还本</c:if>
                            </div>
                            <label class="form-label col-xs-4 col-sm-2">分红周期：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                    ${level.shareBonusPeriod}月
                            </div>
                        </div>
                        <div class="row cl">
                            <label class="form-label col-xs-4 col-sm-2">是否需要地址：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                <c:if test="${level.isNeedAddress == 1}">是</c:if>
                                <c:if test="${level.isNeedAddress == 0}">否</c:if>
                            </div>
                            <label class="form-label col-xs-4 col-sm-2">是否需要协议：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                <c:if test="${level.isNeedAgreement == 1}">是</c:if>
                                <c:if test="${level.isNeedAgreement == 0}">否</c:if>
                            </div>
                        </div>
                    </div>
                </c:forEach>
            </div>
            <!-- 合作方式 -->
            <div class="hezuo_style">
                <div class="row cl">
                    <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">合作方式</h3>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">佣金比例：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <input type="text" class="input-text" value="" placeholder="已筹金额的百分比" id="commissionRate" name="commissionRate">
                    </div>
                </div>
                <c:if test="${item.typeId != 3}">
                    <div class="row cl">
                        <label class="form-label col-xs-4 col-sm-2">保证金：</label>
                        <div class="formControls col-xs-8 col-sm-3">
                            <input type="text" class="input-text" value="" placeholder="已筹金额的百分比" id="guaranteeRate" name="guaranteeRate">
                        </div>
                        <label class="form-label col-xs-4 col-sm-2">预付分红金：</label>
                        <div class="formControls col-xs-8 col-sm-3">
                            <input type="text" class="input-text" value="" placeholder="/期" id="prepaidBonus" name="prepaidBonus">
                        </div>
                    </div>
                </c:if>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">放款方式：</label>
                    <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                        <select name="loanMode" id="loanMode" class="select">
                            <option value="1">一次性</option>
                            <option value="2">两次放款</option>
                        </select>
                        </span>
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">首款比例：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <input type="text" class="input-text" value="30" placeholder="" id="firstRatio" name="firstRatio">
                    </div>
                </div>
            </div>


            <!-- 补充资料-收益权股权类 -->
            <div class="cailiao_buchong">

                <div class="row cl">
                    <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">补充资料-收益权股权类</h3>
                </div>
                <div class="col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">
                    <table class="table table-border table-bordered table-bg table-hover table-sort">
                        <thead>
                        <tr class="text-c">
                            <th width="240">资料类型</th>
                            <th>资质文件</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr class="text-c">
                            <td>发起人身份证正反面
                                <button class="btn btn-link radius ml-10" type="button">下载</button>
                            </td>
                            <td class="text-l">
                                <input type="hidden" id="sponsorIdcard" name="sponsorIdcard">
                                <div class="img-box full">
                                    <section class=" img-section">
                                        <div class="z_photo upimg-div clearfix">
                                            <div id="imgParent0" class="clearfix">
                                            </div>
                                            <section class="z_file fl" style="width:100px;height:30px;">
                                                <label class="u_img">上传图片</label>
                                                <input type="file" name="file" id="file0" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" onchange="uploadFile('file0','item','imgParent0','headerImage', 1)">
                                            </section>
                                        </div>
                                    </section>
                                </div>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>发起人征信报告
                                <button class="btn btn-link radius ml-10" type="button">下载</button>
                            </td>
                            <td class="text-l">
                                <input type="hidden" id="sponsorCredit" name="sponsorCredit">
                                <div class="img-box full">
                                    <section class=" img-section">
                                        <div class="z_photo upimg-div clearfix">
                                            <div id="imgParent1" class="clearfix">
                                                <%--<div class="portfoliobox">--%>
                                                <%--<div class="picbox" style="margin: 5px; width: 100px; height: 100px;">
                                                    <a href="http://test.img.meirenguvip.com/item/1493870878824.jpg" data-lightbox="gallery">
                                                        <img src="http://test.img.meirenguvip.com/item/1493870878824.jpg?x-oss-process=image/resize,m_lfit,h_100,w_100"></a>
                                                </div>--%>
                                                <%--</div>--%>
                                            </div>
                                            <section class="z_file fl" style="width:100px;height:30px;text-align: center;">
                                                <label class="u_img">上传图片</label>
                                                <input type="file" name="file" id="file1" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" onchange="uploadFile('file1','item','imgParent1','sponsorCredit', 1)">
                                            </section>
                                        </div>
                                    </section>
                                </div>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>企业法人身份证正反面
                                <button class="btn btn-link radius ml-10" type="button">下载</button>
                            </td>
                            <td class="text-l">
                                <input type="hidden" id="principalIdcard" name="principalIdcard">
                                <div class="img-box full">
                                    <section class=" img-section">
                                        <div class="z_photo upimg-div clearfix">
                                            <div id="imgParent2" class="clearfix"></div>
                                            <section class="z_file fl" style="width:100px;height:30px;">
                                                <label class="u_img">上传图片</label>
                                                <input type="file" name="file" id="file2" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" onchange="uploadFile('file2','item','imgParent2','principalIdcard',1)">
                                            </section>
                                        </div>
                                    </section>
                                </div>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>企业法人征信报告
                                <button class="btn btn-link radius ml-10" type="button">下载</button>
                            </td>
                            <td class="text-l">
                                <input type="hidden" id="principalCredit" name="principalCredit">
                                <div class="img-box full">
                                    <section class=" img-section">
                                        <div class="z_photo upimg-div clearfix">
                                            <div id="imgParent3" class="clearfix"></div>
                                            <section class="z_file fl" style="width:100px;height:30px;">
                                                <label class="u_img">上传图片</label>
                                                <input type="file" name="file" id="file3" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" onchange="uploadFile('file3','item','imgParent3','principalCredit', 1)">
                                            </section>
                                        </div>
                                    </section>
                                </div>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>营业执照
                                <button class="btn btn-link radius ml-10" type="button">下载</button>
                            </td>
                            <td class="text-l">
                                <input type="hidden" id="businessLicence" name="businessLicence">
                                <div class="img-box full">
                                    <section class=" img-section">
                                        <div class="z_photo upimg-div clearfix">
                                            <div id="imgParent4" class="clearfix"></div>
                                            <section class="z_file fl" style="width:100px;height:30px;">
                                                <label class="u_img">上传图片</label>
                                                <input type="file" name="file" id="file4" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" onchange="uploadFile('file4','item','imgParent4','businessLicence', 1)">
                                            </section>
                                        </div>
                                    </section>
                                </div>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>场地/土地租赁协议
                                <button class="btn btn-link radius ml-10" type="button">下载</button>
                            </td>
                            <td class="text-l">
                                <input type="hidden" id="venueRentalAgreement" name="venueRentalAgreement">
                                <div class="img-box full">
                                    <section class=" img-section">
                                        <div class="z_photo upimg-div clearfix">
                                            <div id="imgParent5" class="clearfix"></div>
                                            <section class="z_file fl" style="width:100px;height:30px;">
                                                <label class="u_img">上传图片</label>
                                                <input type="file" name="file" id="file5" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" onchange="uploadFile('file5','item','imgParent5','venueRentalAgreement, 1')">
                                            </section>
                                        </div>
                                    </section>
                                </div>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>新店装修效果图
                                <button class="btn btn-link radius ml-10" type="button">下载</button>
                            </td>
                            <td class="text-l">
                                <input type="hidden" id="storeRenderings" name="storeRenderings">
                                <div class="img-box full">
                                    <section class=" img-section">
                                        <div class="z_photo upimg-div clearfix">
                                            <div id="imgParent6" class="clearfix"></div>
                                            <section class="z_file fl" style="width:100px;height:30px;">
                                                <label class="u_img">上传图片</label>
                                                <input type="file" name="file" id="file6" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" onchange="uploadFile('file6','item','imgParent6','storeRenderings', 1)">
                                            </section>
                                        </div>
                                    </section>
                                </div>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>公司章程
                                <button class="btn btn-link radius ml-10" type="button">下载</button>
                            </td>
                            <td class="text-l">
                                <input type="hidden" id="corporateArticles" name="corporateArticles">
                                <div class="img-box full">
                                    <section class=" img-section">
                                        <div class="z_photo upimg-div clearfix">
                                            <div id="imgParent7" class="clearfix"></div>
                                            <section class="z_file fl" style="width:100px;height:30px;">
                                                <label class="u_img">上传图片</label>
                                                <input type="file" name="file" id="file7" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" onchange="uploadFile('file7','item','imgParent7','corporateArticles', 1)">
                                            </section>
                                        </div>
                                    </section>
                                </div>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>近一年流水，资产负债表
                                <button class="btn btn-link radius ml-10" type="button">下载</button>
                            </td>
                            <td class="text-l">
                                <input type="hidden" id="assetWaterLiabilities" name="assetWaterLiabilities">
                                <div class="img-box full">
                                    <section class=" img-section">
                                        <div class="z_photo upimg-div clearfix">
                                            <div id="imgParent8" class="clearfix"></div>
                                            <section class="z_file fl" style="width:100px;height:30px;">
                                                <label class="u_img">上传图片</label>
                                                <input type="file" name="file" id="file8" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" onchange="uploadFile('file8','item','imgParent8','assetWaterLiabilities', 1)">
                                            </section>
                                        </div>
                                    </section>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- 投资合同补充信息  -->
            <div class="hezuo_style">
                <div class="row cl">
                    <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">投资合同补充信息</h3>
                </div>
                <div class="row cl" style="display:block">
                    <label class="form-label col-xs-4 col-sm-2">股东姓名：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <input type="text" class="input-text" value="" maxlength="30" id="shareholderName" name="shareholderName">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">股东身份证号：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <input type="text" class="input-text" value="" id="shareholderIdcard" name="shareholderIdcard">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">股东地址：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <input type="text" class="input-text" value="" placeholder="" id="shareholderAddress" name="shareholderAddress">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">担保人姓名：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <input type="text" class="input-text" value="" placeholder="" id="guaranteeName" name="guaranteeName">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">担保人身份证号：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <input type="text" class="input-text" value="" placeholder="" id="guaranteeIdcard" name="guaranteeIdcard">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">担保人地址：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <input type="text" class="input-text" value="" placeholder="" id="guaranteeAddress" name="guaranteeAddress">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-8 col-sm-8  col-sm-offset-1" style="text-align:left;">投资人每投资100万元，股东向管理公司质押不低于
                        <input type="text" style="border-left-width:0px;border-top-width:0px;border-right-width:0px;border-bottom-color:black; width: 50px; text-align: center;" id="pledgedShares" name="pledgedShares">
                        %的股份</label>

                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">项目方印章：</label>
                    <div class="formControls col-xs-8 col-sm-8">
                        <input type="hidden" id="partnerSeal" name="partnerSeal">
                        <div class="img-box full">
                            <section class=" img-section">
                                <div class="z_photo upimg-div clearfix">
                                    <div id="imgParent9" class="clearfix"></div>
                                    <section class="z_file">
                                        <img src="static/upload-file/a11.png" class="add-img">
                                        <input type="file" name="file" id="file9" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" onchange="uploadFile('file9','item','imgParent9','partnerSeal', 2)">
                                    </section>
                                </div>
                            </section>
                        </div>
                    </div>
                </div>
            </div>


            <div class="cailiao_buchong">

                <div class="row cl">
                    <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">合同</h3>
                </div>
                <div class="col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">
                    <table class="table table-border table-bordered table-bg table-hover table-sort">
                        <thead>
                        <tr class="text-c">
                            <th width="240">资料类型</th>
                            <th>资质文件</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr class="text-c">
                            <td>托管协议书
                                <button class="btn btn-link radius ml-10" type="button">下载</button>
                            </td>
                            <td class="text-l">
                                <input type="hidden" id="escrowAgreement" name="escrowAgreement">
                                <div class="img-box full">
                                    <section class=" img-section">
                                        <div class="z_photo upimg-div clearfix">
                                            <div id="imgParent10" class="clearfix"></div>
                                            <section class="z_file fl" style="width:100px;height:30px;">
                                                <label class="u_img">上传图片</label>
                                                <input type="file" name="file" id="file10" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" onchange="uploadFile('file10','item','imgParent10','escrowAgreement', 1)">
                                            </section>
                                        </div>
                                    </section>
                                </div>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>融资服务协议
                                <button class="btn btn-link radius ml-10" type="button">下载</button>
                            </td>
                            <td class="text-l">
                                <input type="hidden" id="financeService" name="financeService">
                                <div class="img-box full">
                                    <section class=" img-section">
                                        <div class="z_photo upimg-div clearfix">
                                            <div id="imgParent11" class="clearfix"></div>
                                            <section class="z_file fl" style="width:100px;height:30px;">
                                                <label class="u_img">上传图片</label>
                                                <input type="file" name="file" id="file11" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" onchange="uploadFile('file11','item','imgParent11','financeService', 1)">
                                            </section>
                                        </div>
                                    </section>
                                </div>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>融资管理协议
                                <button class="btn btn-link radius ml-10" type="button">下载</button>
                            </td>
                            <td class="text-l">
                                <input type="hidden" id="financeManage" name="financeManage">
                                <div class="img-box full">
                                    <section class=" img-section">
                                        <div class="z_photo upimg-div clearfix">
                                            <div id="imgParent12" class="clearfix"></div>
                                            <section class="z_file fl" style="width:100px;height:30px;">
                                                <label class="u_img">上传图片</label>
                                                <input type="file" name="file" id="file12" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" onchange="uploadFile('file12','item','imgParent12','financeManage', 1)">
                                            </section>
                                        </div>
                                    </section>
                                </div>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>股权质押合同
                                <button class="btn btn-link radius ml-10" type="button">下载</button>
                            </td>
                            <td class="text-l">
                                <input type="hidden" id="sharePledgeAgreement" name="sharePledgeAgreement">
                                <div class="img-box full">
                                    <section class=" img-section">
                                        <div class="z_photo upimg-div clearfix">
                                            <div id="imgParent13" class="clearfix"></div>
                                            <section class="z_file fl" style="width:100px;height:30px;">
                                                <label class="u_img">上传图片</label>
                                                <input type="file" name="file" id="file13" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" onchange="uploadFile('file13','item','imgParent13','sharePledgeAgreement', 1)">
                                            </section>
                                        </div>
                                    </section>
                                </div>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>保证合同
                                <button class="btn btn-link radius ml-10" type="button">下载</button>
                            </td>
                            <td class="text-l">
                                <input type="hidden" id="guarantyAgreement" name="guarantyAgreement">
                                <div class="img-box full">
                                    <section class=" img-section">
                                        <div class="z_photo upimg-div clearfix">
                                            <div id="imgParent14" class="clearfix"></div>
                                            <section class="z_file fl" style="width:100px;height:30px;">
                                                <label class="u_img">上传图片</label>
                                                <input type="file" name="file" id="file14" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" onchange="uploadFile('file14','item','imgParent14','guarantyAgreement', 1)">
                                            </section>
                                        </div>
                                    </section>
                                </div>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                </div>
            </div>


            <!-- 审核记录 -->
            <div class="shenhe_record">
                <div class="row cl">
                    <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">审核记录</h3>
                </div>
                <div class="col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">
                    <table class="table table-border table-bordered table-bg table-hover table-sort">
                        <thead>
                        <tr class="text-c">
                            <th width="100">审核阶段</th>
                            <th width="100">审核结果</th>
                            <th>备注</th>
                            <th>审核时间</th>
                            <th width="100">审核人</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${record}" var="record">
                            <tr class="text-c">
                                <td>
                                    <c:if test="${record.operateType == 1}">初审</c:if>
                                    <c:if test="${record.operateType == 2}">设置合作</c:if>
                                    <c:if test="${record.operateType == 3}">复审</c:if>
                                    <c:if test="${record.operateType == 4}">发布</c:if>
                                    <c:if test="${record.operateType == 5}">下架</c:if>
                                </td>
                                <td>
                                    <c:if test="${record.operateStatus == 1}">通过</c:if>
                                    <c:if test="${record.operateStatus == 0}">不通过</c:if>
                                </td>
                                <td>${record.operateRemark}</td>
                                <td>
                                    <jsp:useBean id="dateValue" class="java.util.Date"/>
                                    <jsp:setProperty name="dateValue" property="time" value="${record.operateTime}"/>
                                    <fmt:formatDate value="${dateValue}" pattern="yyyy/MM/dd HH:mm:ss"/>
                                </td>
                                <td>${record.operateAccount}</td>
                            </tr>
                        </c:forEach>
                        </tbody>
                    </table>
                </div>
            </div>

            <!-- 复审审核 -->
            <div class="fushen">
                <form action="item/review" method="post">
                    <div class="row cl">
                        <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">复审审核</h3>
                    </div>
                    <div class="row cl">
                        <input type="hidden" name="itemId" id="itemId" value="${item.itemId}">
                        <label class="form-label col-xs-4 col-sm-2"></label>
                        <label class="col-xs-4 col-sm-1 mr-20">
                            <input type="radio" class="radio" value="1" name="operateStatus" id="feedback-1"> 通过</label>
                        <label>
                            <input type="radio" class="radio" value="0" name="operateStatus" id="feedback-2"> 不通过</label>
                    </div>
                    <div class="row cl">
                        <label class="form-label col-xs-4 col-sm-2">备注：</label>
                        <div class="formControls col-xs-8 col-sm-8">
                    <textarea name="operateRemark" cols="" rows="" class="textarea" placeholder="..." datatype="*10-100"
                              dragonfly="true" nullmsg="备注不能为空！" onKeyUp="$.Huitextarealength(this,200)"></textarea>
                            <p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
                        </div>
                    </div>
                    <div class="row cl">
                        <label class="form-label col-xs-4 col-sm-2"></label>
                        <div class="formControls col-xs-8 col-sm-8 text-c">
                            <button class="btn btn-danger radius size-L mt-20 mb-30" style="padding:0 30px" type="submit">复审确认
                            </button>
                        </div>
                    </div>
                </form>

            </div>
        </div>
    </div>

</div>

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
}), $(".toTop").show()</script>

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript" src="lib/webuploader/0.1.5/webuploader.min.js"></script>

<!-- 换灯箱 -->
<script type="text/javascript" src="lib/lightbox2/2.8.1/js/lightbox.min.js"></script>
<script type="text/javascript" src="lib/datetimepicker/datetimepicker.js"></script>

<script type="text/javascript">
    // tab切换
    $(function () {
        $.Huitab(".huibao_set .huibao_tab_menu span", ".huibao_set .huibao_tab", "current", "click", "0")
        $.Huitab(".tabBar span", ".tabCon", "current", "click", "0")
    });

    //内容tab切换
    $('body').on('click', '.content_tab_menu span', function (event) {
        console.log($(event.target));
        $('.content_tab_menu span').removeClass('current');
        $(event.target).addClass('current');
        var index = $(event.target).index();
        contentIndex = index;
        $('.content_set .item').hide();
        $('.content_set .item').eq(index).show();
        console.log(contentIndex);
    });

</script>
</body>
</html>
