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
    <title>添加文章</title>
    <link rel="stylesheet" type="text/css" href="static/upload-file/upload.css" />
    <script type="text/javascript" charset="utf-8" src="lib/ueditor/1.4.3/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="lib/ueditor/1.4.3/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>
</head>
</html>
</head>
<body>
<div class="page-container">
    <form action="article/save" method="post" class="form form-horizontal" id="form-article-add">
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
                <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">添加文章</h3>
            </div>
            <div class="row cl">
                <input type="hidden" id="id" name="articleId" value="${articleId}">
                <label class="form-label col-xs-4 col-sm-2">文章标题：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${articleTitle}" id="articleTitle" name="articleTitle" minlength="1" maxlength="30"
                           placeholder="最多不超过25字">
                </div>
                <label class="form-label col-xs-4 col-sm-2">文章分类：</label>
                <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                    <select id="acId" name="acId" class="select">
                        <c:if test="${not empty acId}">
                            <c:forEach items="${classList}" var="list" varStatus="status">
                                <c:if test="${list.acId == acId}">
                                    <option value="${list.acId}" selected>${list.acName}</option>
                                </c:if>
                                <c:if test="${list.acId != acId}">
                                    <option value="${list.acId}">${list.acName}</option>
                                </c:if>
                            </c:forEach>
                        </c:if>
                         <c:if test="${empty acId}">
                             <c:forEach items="${classList}" var="list" varStatus="status">
                                 <option value="${list.acId}">${list.acName}</option>
                             </c:forEach>
                         </c:if>
                    </select>
                    </span>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">跳转链接：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="url" class="input-text" value="${articleUrl}" id="articleUrl" name="articleUrl">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">文章标签(多个以,分割)：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="text" class="input-text" value="${articleLabel}" id="articleLabel" name="articleLabel">

                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">是否显示：</label>
                <div class="formControls col-xs-8 col-sm-3">
                     <span class="select-box">
                    <select id="articleShow" name="articleShow" class="select">
                        <c:if test="${not empty articleShow}">
                            <c:if test="${articleShow == 1}">
                                <option value="1" selected>是</option>
                                <option value="0">否</option>
                            </c:if>
                            <c:if test="${articleShow == 0}">
                                <option value="1">是</option>
                                <option value="0" selected>否</option>
                            </c:if>
                        </c:if>
                        <c:if test="${empty articleShow}">
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </c:if>
                    </select>
                    </span>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">排序：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${articleSort}" id="articleSort" placeholder="排序字段越小越靠前" name="articleSort">
                </div>
                <label class="form-label col-xs-4 col-sm-2">是否为轮播图文章：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <span class="select-box">
                    <select id="articleIsBanner" name="articleIsBanner" class="select">
                        <c:if test="${not empty articleIsBanner}">
                            <c:if test="${articleIsBanner == 1}">
                                <option value="1" selected>是</option>
                                <option value="0">否</option>
                            </c:if>
                            <c:if test="${articleIsBanner == 0}">
                                <option value="1">是</option>
                                <option value="0" selected>否</option>
                            </c:if>
                        </c:if>
                        <c:if test="${empty articleIsBanner}">
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </c:if>
                    </select>
                    </span>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">是否为推荐文章：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <span class="select-box">
                    <select id="articleIsCommend" name="articleIsCommend" class="select">
                        <c:if test="${not empty articleIsCommend}">
                            <c:if test="${articleIsCommend == 1}">
                                <option value="1" selected>是</option>
                                <option value="0">否</option>
                            </c:if>
                            <c:if test="${articleIsCommend == 0}">
                                <option value="1">是</option>
                                <option value="0" selected>否</option>
                            </c:if>
                        </c:if>
                        <c:if test="${empty articleIsCommend}">
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </c:if>
                    </select>
                    </span>
                </div>
                <label class="form-label col-xs-4 col-sm-2">是否发布：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <span class="select-box">
                    <select id="articleIsPublish" name="articleIsPublish" class="select">
                        <c:if test="${not empty articleIsPublish}">
                            <c:if test="${articleIsPublish == 1}">
                                <option value="1" selected>是</option>
                                <option value="0">否</option>
                            </c:if>
                            <c:if test="${articleIsPublish == 0}">
                                <option value="1">是</option>
                                <option value="0" selected>否</option>
                            </c:if>
                        </c:if>
                        <c:if test="${empty articleIsPublish}">
                            <option value="1">是</option>
                            <option value="0">否</option>
                        </c:if>
                    </select>
                    </span>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">文章头图：</label>
                <div class="formControls col-xs-8 col-sm-9">
                    <input type="hidden" id="articleImg" name="articleImg" value="${articleImg}">
                    <div class="img-box full">
                        <section class=" img-section">
                            <div class="z_photo upimg-div clearfix">
                                <div id="imgParent" class="clearfix">
                                    <c:if test="${not empty articleImg}">
                                        <section class="up-section fl">
                                            <span class="up-span"></span>
                                            <img class="close-upimg" src="static/upload-file/a7.png" onclick="removePic(this)">
                                            <img class="up-img" src="http://test.img.meirenguvip.com/${articleImg}">
                                            <p class="img-name-p">${articleImg}</p>
                                            <input id="taglocation" name="taglocation" value="" type="hidden">
                                            <input id="tags" name="tags" value="" type="hidden">
                                        </section>
                                    </c:if>
                                </div>
                                <section class="z_file fl">
                                    <img src="static/upload-file/a11.png" class="add-img">
                                    <input type="file" name="file" id="file" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" onchange="uploadFile('file','article','imgParent','articleImg')">
                                </section>
                            </div>
                        </section>
                    </div>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">文章内容：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="hidden" name="articleContent" id="articleContent">
                    <script id="editor" type="text/plain" style="width:1024px;height:500px;"></script>
                    <code id="articleContentTemp" style="display: none;">
                        ${articleContent}
                    </code>
                </div>
            </div>

            <br/><br/>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2"></label>
                <div class="formControls col-xs-8 col-sm-8 text-c">
                    <button class="btn btn-primary radius size-L mt-20 mb-30" style="padding:0 30px" onclick="addArticle()" type="button">保 存
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

    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl = function(action) {
        if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
            return '/upload?foldName=article';
        } else if (action == 'uploadvideo') {
            return '/upload?foldName=article';
        } else {
            return this._bkGetActionUrl.call(this, action);
        }
    }
    $(document).ready(function(){
        ue.addListener("ready", function () {
            // editor准备好之后才可以使用
            var content = $("#articleContentTemp").html();
            ue.setContent(content);
        });
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
                                +'  <img class="up-img" src="http://test.img.meirenguvip.com/'+data[i]+'">'
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
    function addArticle() {
        var articleTitle = $("#articleTitle").val();
        var articleUrl = $("#articleUrl").val();
        var articleImg = $("#articleImg").val();
        var content = UE.getEditor('editor').getContent();
        var articleContent = $("#articleContent").val(content);
        var articleSort = $("#articleSort").val();

        if(articleTitle == null || articleTitle == '' || articleTitle == undefined){
            alert("文章标题不能为空");
            return;
        }
        if(articleSort == null || articleSort == '' || articleSort == undefined){
            alert("文章排序不能为空");
            return;
        }

        if(articleImg == null || articleImg == "" || articleImg == undefined){
            alert("文章头图不能为空！");
            return false;
        }
        if(articleUrl == null || articleUrl== '' || articleUrl == undefined){
            if(articleContent == null || articleContent == '' || articleContent == undefined){
                alert("跳转链接和文章内容二选一！");
            }
            return false;
        }
        $('#form-article-add').ajaxSubmit(options);

    }
</script>

</body>
</html>
