<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="../common/common.jsp" %>
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
    <title>合作方添加</title>
    <link href="lib/lightbox2/2.8.1/css/lightbox.css" rel="stylesheet" type="text/css">
    <link href="lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css"/>
    <link href="lib/datetimepicker/datetimepicker.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="static/upload-file/upload.css" />
    <%--<script src="static/js/ajaxfileupload.js"/>--%>

</head>
<body>
<div class="page-container">
    <form action="partner/add" method="post" class="form form-horizontal" id="form-article-add">
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
        <script type="text/javascript">
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

                                imgStr = '<div class="picbox" style="margin: 5px; width: 100px; height: 100px; float: left;">'
                                        +'<a href="http://test.img.meirenguvip.com/'+data[i]+'" data-lightbox="gallery">'
                                        +'<img src="http://test.img.meirenguvip.com/'+data[i]+'?x-oss-process=image/resize,m_lfit,h_100,w_100"></a>'
                                        +'</div>';

                                /*imgStr += '<section class="up-section fl">'
                                        +'  <span class="up-span"></span>'
                                        +'  <img class="close-upimg" src="static/upload-file/a7.png">'
                                        +'  <img class="up-img" src="http://test.img.meirenguvip.com/'+data[i]+'">'
                                        +'  <p class="img-name-p">'+data[i]+'</p>'
                                        +'  <input id="taglocation" name="taglocation" value="" type="hidden">'
                                        +'  <input id="tags" name="tags" value="" type="hidden">'
                                        +'</section>';*/
                            }

                            $("#"+parentId).append(imgStr);
                        }
                    }
                }

            }

        </script>

        <!-- 合作方 添加 -->
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">合作方名称：</label>
            <div class="formControls col-xs-8 col-sm-3">
                <input type="text" class="input-text" value="" id="partnerName" name="partnerName" maxlength="30"
                       placeholder="项目标题最多30字">
            </div>
            <label class="form-label col-xs-4 col-sm-2">公司成立日：</label>
            <div class="formControls col-xs-8 col-sm-3">
                <input type="text" class="ml-10 input-text" style="width:auto" placeHolder="年/月/日" value=""
                       id="partnerCreateDay" name="partnerCreateDay"/>
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">注册资金：</label>
            <div class="formControls col-xs-8 col-sm-3">
                <input type="text" class="input-text" value="" placeholder="" id="partnerRegistCapital" name="partnerRegistCapital">
            </div>
            <label class="form-label col-xs-4 col-sm-2">公司估值：</label>
            <div class="formControls col-xs-8 col-sm-3">
                <input type="text" class="input-text" value="" placeholder="" id="partnerValuation" name="partnerValuation">
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">行业类型：</label>
            <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
				<select name="typeId" id="typeId" class="select">
                    <c:forEach items="${classList}" var="classList">
                        <option value="${classList.classId}">${classList.className}</option>
                    </c:forEach>
				</select>
				</span>
            </div>
            <label class="form-label col-xs-4 col-sm-2">服务专员：</label>
            <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
				<select name="accountId" id="accountId" class="select">
					<option value="1">专员a</option>
					<option value="2">专员b</option>
				</select>
				</span>
            </div>
        </div>

        <div class="row cl">
            <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">企业信息</h3>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">企业名称：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <input type="text" class="input-text" value="" id="enterpriseName" name="enterpriseName" maxlength="30"
                       placeholder="项目标题最多30字">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">证件号：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <input type="text" class="input-text" value="" id="idNumber" name="idNumber" maxlength="30"
                       placeholder="项目标题最多30字">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2" style="line-height:30px;">企业地址：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <label data-toggle="distpicker" style="display:block;width:100%">
					<span class="select-box select-box1" style="border:none;">
                        <input type="text" class="input-text" name="enterpriseAddress" id="enterpriseAddress">
					</span>
                </label>
            </div>
        </div>

        <div class="row cl">
            <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">法人信息</h3>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">负责人姓名：</label>
            <div class="formControls col-xs-8 col-sm-3">
                <input type="text" class="input-text" value="" placeholder="" id="principalName" name="principalName">
            </div>
            <label class="form-label col-xs-4 col-sm-2">身份证号：</label>
            <div class="formControls col-xs-8 col-sm-3">
                <input type="text" class="input-text" value="" placeholder="" id="principalIdcard"
                       name="principalIdcard">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">联系方式：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <input type="text" class="input-text" value="" id="principalTelephone" name="principalTelephone"
                       maxlength="30"
                       placeholder="项目标题最多30字">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">传 真：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <input type="text" class="input-text" value="" id="principalFax" name="principalFax" maxlength="30"
                       placeholder="项目标题最多30字">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2" style="line-height:30px;">联系地址：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <label data-toggle="distpicker" style="display:block;width:100%">
					<span class="select-box select-box1" style="border:none;">
                        <input type="text" class="input-text" name="principalAddress" id="principalAddress">
					</span>
                </label>
            </div>
        </div>

        <div class="row cl">
            <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">联系人信息</h3>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">联系人姓名：</label>
            <div class="formControls col-xs-8 col-sm-3">
                <input type="text" class="input-text" value="" placeholder="" id="contactsName" name="contactsName">
            </div>
            <label class="form-label col-xs-4 col-sm-2">身份证号：</label>
            <div class="formControls col-xs-8 col-sm-3">
                <input type="text" class="input-text" value="" placeholder="" id="contactsIdcard" name="contactsIdcard">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">联系方式：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <input type="text" class="input-text" value="" id="contactsTelephone" name="contactsTelephone"
                       maxlength="30"
                       placeholder="项目标题最多30字">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">传 真：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <input type="text" class="input-text" value="" id="contactsFax" name="contactsFax" maxlength="30"
                       placeholder="项目标题最多30字">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2" style="line-height:30px;">联系地址：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <label data-toggle="distpicker" style="display:block;width:100%">
					<span class="select-box select-box1" style="border:none;">
                        <input type="text" class="input-text" name="contactsAddress" id="contactsAddress">
					</span>
                </label>
            </div>
        </div>

        <div class="row cl">
            <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">收款账户信息</h3>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">开户行名称：</label>
            <div class="formControls col-xs-8 col-sm-3">
                <input type="text" class="input-text" value="" placeholder="" id="bankName" name="bankName">
            </div>
            <label class="form-label col-xs-4 col-sm-2">账户名：</label>
            <div class="formControls col-xs-8 col-sm-3">
                <input type="text" class="input-text" value="" placeholder="" id="bankAccount" name="bankAccount">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">银行账号：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <input type="text" class="input-text" value="" id="bankCard" name="bankCard" maxlength="30"
                       placeholder="项目标题最多30字">
            </div>
        </div>

        <div class="row cl">
            <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">资质文件</h3>
        </div>
        <div class="col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">
            <table class="table table-border table-bordered table-bg table-hover table-sort">
                <thead>
                <tr class="text-c">
                    <th width="240">资料类型</th>
                    <th>合同文件</th>
                </tr>
                </thead>
                <tbody>
                <tr class="text-c">
                    <td>发起人身份证正反面
                        <button class="btn btn-link radius ml-10" type="button">下载</button>
                    </td>
                    <td class="text-l">
                        <input type="hidden" id="imagePrincipal" name="imagePrincipal">
                        <div class="img-box full">
                            <section class=" img-section">
                                <div class="z_photo upimg-div clearfix">
                                    <div id="imgParent0" class="clearfix">
                                    </div>
                                    <section class="z_file fl" style="width:100px;height:30px;text-align: center;">
                                        <label class="u_img">上传图片</label>
                                        <input type="file" name="file" id="file0" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" onchange="uploadFile('file0','partner','imgParent0','imagePrincipal')">
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
                    <td class="text-l"><!-- upload?foldName=item-->
                        <input type="hidden" id="imageBusinessLicence" name="imageBusinessLicence">
                        <div class="img-box full">
                            <section class=" img-section">
                                <div class="z_photo upimg-div clearfix">
                                    <div id="imgParent1" class="clearfix">
                                    </div>
                                    <section class="z_file fl" style="width:100px;height:30px;text-align: center;">
                                        <label class="u_img">上传图片</label>
                                        <input type="file" name="file" id="file1" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" onchange="uploadFile('file1','partner','imgParent1','imageBusinessLicence')">
                                    </section>
                                </div>
                            </section>
                        </div>
                    </td>
                </tr>
                <tr class="text-c">
                    <td>开户行
                        <button class="btn btn-link radius ml-10" type="button">下载</button>
                    </td>
                    <td class="text-l">
                        <input type="hidden" id="imageBank" name="imageBank">
                        <div class="img-box full">
                            <section class=" img-section">
                                <div class="z_photo upimg-div clearfix">
                                    <div id="imgParent2" class="clearfix">
                                    </div>
                                    <section class="z_file fl" style="width:100px;height:30px;text-align: center;">
                                        <label class="u_img">上传图片</label>
                                        <input type="file" name="file" id="file2" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" onchange="uploadFile('file2','partner','imgParent2','imageBank')">
                                    </section>
                                </div>
                            </section>
                        </div>
                    </td>
                </tr>
                <tr class="text-c">
                    <td>医疗机构执业许可证
                        <button class="btn btn-link radius ml-10" type="button">下载</button>
                    </td>
                    <td class="text-l">
                        <input type="hidden" id="imageProfessionalLicense" name="imageProfessionalLicense">
                        <div class="img-box full">
                            <section class=" img-section">
                                <div class="z_photo upimg-div clearfix">
                                    <div id="imgParent3" class="clearfix">
                                    </div>
                                    <section class="z_file fl" style="width:100px;height:30px;text-align: center;">
                                        <label class="u_img">上传图片</label>
                                        <input type="file" name="file" id="file3" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" onchange="uploadFile('file3','partner','imgParent3','imageProfessionalLicense')">
                                    </section>
                                </div>
                            </section>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"></label>
            <div class="formControls col-xs-8 col-sm-8 text-c">
                <button class="btn btn-primary radius size-L mt-20 mb-30" style="padding:0 30px" type="submit">添 加
                </button>
            </div>
        </div>
        <br/><br/><br/>
    </form>
</div>

<script>

    $(".Hui-aside ul a").on("click", function () {
        console.log($(this).attr("data-href")), $(".content_iframe").attr("src", $(this).attr("data-href"))
    });

    $('#partnerCreateDay').datetimepicker({
        lang: $.datetimepicker.setLocale('ch'),
    });

</script>
</body>
</html>
