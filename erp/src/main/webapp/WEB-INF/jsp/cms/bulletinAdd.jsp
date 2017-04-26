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
    <title>公告添加</title>
<style>
    .edit_h31 {
        border-bottom: 1px #ddd solid;
        overflow: hidden;
    }
</style>
</head>
<body>
<div class="page-container">
    <!-- 选项卡 -->

    <span class="form form-horizontal" id="form-article-add">
        <!-- 内容设置 -->
        <div class="row cl">
            <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">公告添加</h3>
        </div>
        <div class="content_set">

            <form action="bulletin/add" method="post" id="bulletinAddForm">
                <div class="item">
                    <div class="row cl">
                        <label class="form-label col-xs-4 col-sm-2">公告名称：</label>
                        <div class="formControls col-xs-8 col-sm-8">
                            <input type="text" class="input-text" value="" maxlength="30" placeholder="项目标题最多30字" id="bulletinTitle"
                                   name="bulletinTitle">
                        </div>
                    </div>
                    <div class="row cl">
                        <label class="form-label col-xs-4 col-sm-2">内容：</label>
                        <div class="formControls col-xs-8 col-sm-8">
                            <textarea name="bulletinContent" id="bulletinContent" cols="" rows="" class="textarea" placeholder="..." datatype="*10-100"
                                      dragonfly="true" nullmsg="公共内容不能为空！"
                                      onKeyUp="$.Huitextarealength(this,500)"></textarea>
                            <p class="textarea-numberbar"><em class="textarea-length">0</em>/500</p>
                        </div>
                    </div>

                    <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2 mt-30 mb-20">
                        <button class="btn btn-primary radius" id="contentAddBtn1" type="button" onclick="bulletinAdd()"><i
                                class="Hui-iconfont">&#xe632;</i> 保存</button>
                    </div>
                </div>
            </form>


        </div>

    </span>
</div>

<script type="text/javascript">
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
        // formData is an array; here we use $.param to convert it to a string to display it
        // but the form plugin does this for you automatically when it submits the data
        var queryString = $.param(formData);

        // jqForm is a jQuery object encapsulating the form element.  To access the
        // DOM element for the form do this:
        // var formElement = jqForm[0];

//        alert('About to submit: \n\n' + queryString);

        // here we could return false to prevent the form from being submitted;
        // returning anything other than false will allow the form submit to continue
        return true;
    }
    // post-submit callback
    function showResponse(responseText, statusText, xhr, $form)  {
        // for normal html responses, the first argument to the success callback
        // is the XMLHttpRequest object's responseText property

        // if the ajaxSubmit method was passed an Options Object with the dataType
        // property set to 'xml' then the first argument to the success callback
        // is the XMLHttpRequest object's responseXML property

        // if the ajaxSubmit method was passed an Options Object with the dataType
        // property set to 'json' then the first argument to the success callback
        // is the json data object returned by the server

//        alert('status: ' + statusText + '\n\nresponseText: \n' + responseText +
//            '\n\nThe output div should have already been updated with the responseText.');
        var data = responseText;
        var code = data.code;//200 is success，other is fail
        if(code=="200"){
            layer.msg('保存成功', {icon: 1, time: 1000});
            setTimeout(function() {
                layer_close();
            }, 2000);
        }else{
            layer.msg('错误代码: ' + data.code + ", " + data.msg, {icon: 5, time: 5000});
        }
    }
    function bulletinAdd() {
        $('#bulletinAddForm').ajaxSubmit(options);
    }
</script>
</body>
</html>
