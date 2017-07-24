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
    <title>记录详情</title>
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
    <form action="ip_white/save" method="post" class="form form-horizontal" id="form-article-add">
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
                <input type="hidden" id="id" name="id" value="${id}">
                <label class="form-label col-xs-4 col-sm-2">IP：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${ip}" id="ip" name="ip" minlength="1" maxlength="30">
                </div>
                <label class="form-label col-xs-4 col-sm-2">类型：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <span class="select-box">
                    <select id="type" name="type" class="select">
                        <c:if test="${not empty type}">
                            <c:if test="${type == 1}">
                                <option value="1" selected>内部</option>
                                <option value="2">合作方</option>
                            </c:if>
                            <c:if test="${type == 2}">
                                <option value="1">内部</option>
                                <option value="2" selected>合作方</option>
                            </c:if>
                        </c:if>
                        <c:if test="${empty type}">
                            <option value="1">内部</option>
                            <option value="2">合作方</option>
                        </c:if>
                    </select>
                    </span>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">描述：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <textarea id="description" name="description" cols="" rows="" class="textarea" placeholder="..."
                      datatype="*10-100" dragonfly="true" onKeyUp="$.Huitextarealength(this,50)">${description}</textarea>
                    <p class="textarea-numberbar"><em class="textarea-length">0</em>/50</p>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">开放的URL：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="text" class="input-text" placeholder="多个以,隔开" value="${url}" id="url" name="url">

                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">状态：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <span class="select-box">
                    <select id="status" name="status" class="select">
                        <c:if test="${not empty status}">
                            <c:if test="${status == 1}">
                                <option value="1" selected>启用</option>
                                <option value="2">禁用</option>
                            </c:if>
                            <c:if test="${status == 2}">
                                <option value="1">启用</option>
                                <option value="2" selected>禁用</option>
                            </c:if>
                        </c:if>
                        <c:if test="${empty status}">
                            <option value="1">启用</option>
                            <option value="2">禁用</option>
                        </c:if>
                    </select>
                    </span>
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
        var ip = $("#ip").val();
        var description = $("#description").val();
        var url = $("#url").val();

        if(ip == null || ip == '' || ip == undefined){
            alert("IP不能为空");
            return;
        }
        if(description == null || description == '' || description == undefined){
            alert("描述不能为空");
            return;
        }

        if(url == null || url == "" || url == undefined){
            alert("开放的URL链接不能为空！");
            return false;
        }
        $('#form-article-add').ajaxSubmit(options);

    }
</script>

</body>
</html>
