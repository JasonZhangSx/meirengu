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
    <title>抵扣券批次添加</title>
<style>
    .edit_h31 {
        border-bottom: 1px #ddd solid;
        overflow: hidden;
    }
</style>
    <!-- 时间插件 -->
    <link href="/erp/lib/datetimepicker/datetimepicker.css" rel="stylesheet" type="text/css"/>
    <script type="text/javascript" src="/erp/lib/datetimepicker/datetimepicker.js"></script>
</head>
<body>
<div class="page-container">
    <!-- 选项卡 -->

    <span class="form form-horizontal" id="form-article-add">
        <div class="row cl">
            <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">抵扣券批次添加</h3>
        </div>
        <div class="content_set">

            <form action="rebate_batch/add" method="post" id="form">
                <div class="item">
                    <div class="row cl">
                        <label class="form-label col-xs-4 col-sm-2">抵扣券名称：</label>
                        <div class="formControls col-xs-8 col-sm-3">
                            <input type="text" class="input-text" value="" id="rebateName" name="rebateName"
                                   maxlength="15" required placeholder="名称最多15字">
                        </div>
                        <label class="form-label col-xs-4 col-sm-2">抵扣券适用范围：</label>
                        <div class="formControls col-xs-8 col-sm-3">
                            <input type="text" class="input-text" value="" id="rebateScope" name="rebateScope"
                                   maxlength="20" required placeholder="范围描述最多20字">
                        </div>
                    </div>
                    <div class="row cl">
                        <label class="form-label col-xs-4 col-sm-2">抵扣券类型：</label>
                        <div class="formControls col-xs-8 col-sm-3">
                            <span class="select-box">
                            <select name="rebateType" id="rebateType" class="select">
                                <option value="0">--请选择--</option>
                                <option value="1">无门槛券</option>
                                <option value="2">满减券</option>
                            </select>
                            </span>
                        </div>
                        <label class="form-label col-xs-4 col-sm-2" ><span style="display: none"  id="rebateLimitAmountTitle">满减型抵扣券限额：</span></label>
                        <div class="formControls col-xs-8 col-sm-3" style="display: none" id="rebateLimitAmountDiv">
                            <input type="number" class="input-text" style="width:100%" min="1" max="999999" id="rebateLimitAmount">
                        </div>
                    </div>
                    <div class="row cl">
                        <label class="form-label col-xs-4 col-sm-2">抵扣券标识：</label>
                        <div class="formControls col-xs-8 col-sm-3">
                            <input type="number" class="input-text" style="width:100%" min="1" max="999" name="rebateMark">
                        </div>
                        <label class="form-label col-xs-4 col-sm-2">抵扣券金额：</label>
                        <div class="formControls col-xs-8 col-sm-3">
                            <input type="number" class="input-text" style="width:100%" min="1" max="999999" name="rebateAmount">
                        </div>
                    </div>
                    <div class="row cl">
                        <label class="form-label col-xs-4 col-sm-2">抵扣券使用限制：</label>
                        <div class="formControls col-xs-8 col-sm-3">
                            <span class="select-box">
                            <select name="rebateUseRange" id="rebateUseRange" class="select" >
                                <option value="0">--请选择--</option>
                                <option value="1">固定项目</option>
                                <option value="2">某类项目</option>
                                <option value="3">所有项目</option>
                            </select>
                            </span>
                        </div>

                        <label class="form-label col-xs-4 col-sm-2">
                            <span id="rebateUseRangeTitle"></span>
                        </label>
                        <div class="formControls col-xs-8 col-sm-3" style="display: none" id="rebateUseRangeValueDiv">
                            <select id="rebateUseCategory" class="select">
                                <option value="0">--请选择--</option>
                                <option value="1">产品众筹项目</option>
                                <option value="2">收益权众筹项目</option>
                                <option value="3">股权众筹项目</option>
                            </select>
                            <input type="number" class="input-text" style="width:100%" min="1" max="999" id="rebateUseSpecific">
                        </div>
                    </div>
                    <div class="row cl">
                        <label class="form-label col-xs-4 col-sm-2">抵扣券限领次数：</label>
                        <div class="formControls col-xs-8 col-sm-3">
                            <span class="select-box">
                            <select name="rebateLimit" id="rebateLimit" class="select">
                                <option value="0">--请选择--</option>
                                <option value="1">每天一次</option>
                                <option value="2">永久一次</option>
                                <option value="3">不限次数</option>
                            </select>
                            </span>
                        </div>
                        <label class="form-label col-xs-4 col-sm-2">批次抵扣券发放数量：</label>
                        <div class="formControls col-xs-8 col-sm-3">
                            <input type="number" class="input-text" style="width:100%" min="1" max="999999" name="batchCount">
                        </div>
                    </div>
                    <div class="row cl">
                        <label class="form-label col-xs-4 col-sm-2">抵扣券有效时间类型：</label>
                        <div class="formControls col-xs-8 col-sm-3">
                            <span class="select-box">
                            <select name="validType" id="validType" class="select">
                                <option value="0">--请选择--</option>
                                <option value="1">按绝对时间过期</option>
                                <option value="2">按相对时间过期</option>
                            </select>
                            </span>
                        </div>
                        <label class="form-label col-xs-4 col-sm-2">
                            <span id="validDateTitle"></span>
                        </label>
                        <div class="formControls col-xs-8 col-sm-3" style="display: none" id="validDateValueDiv">
                            <input type="datetime" class="input-text" style="width: 40%;float:left;" value="" id="validStartTime" placeholder="开始日期">
                            <input type="datetime" class="input-text" style="width: 40%;float:right;" value="" id="validEndTime" placeholder="结束日期">
                            <input type="number" class="input-text" style="width:100%"  min="1" max="360" style="display: none" id="validDays">
                        </div>
                    </div>
                    <div class="row cl">
                        <label class="form-label col-xs-4 col-sm-2">预算金额：</label>
                        <div class="formControls col-xs-8 col-sm-3">
                            <input type="number" class="input-text" style="width:100%" min="1" max="999999" name="budgetAmount">
                        </div>
                        <label class="form-label col-xs-4 col-sm-2">来源渠道：</label>
                        <div class="formControls col-xs-8 col-sm-3">
                            <input type="text" class="input-text" value="" id="channel" name="channel"
                                   maxlength="30">
                        </div>
                    </div>
                    <div class="row cl">
                        <label class="form-label col-xs-4 col-sm-2">备注：</label>
                        <div class="formControls col-xs-8 col-sm-8">
                            <input type="text" class="input-text" value="" id="remarks" name="remarks"
                                    maxlength="50">
                        </div>
                    </div>

                    <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2 mt-30 mb-20">
                        <button class="btn btn-primary radius" id="submitBtn" type="button" onclick="submitFrom()"><i
                                class="Hui-iconfont">&#xe632;</i> 保存</button>
                    </div>
                </div>
            </form>
        </div>
    </span>
</div>

<script type="text/javascript">

    // 抵扣券类型下拉框change事件
    $('#rebateType').on('change',function(){
        var value = $("#rebateType option:selected" ).val();
        if (value == 0) {
            //隐藏
            $('#rebateLimitAmountTitle').hide();
            $('#rebateLimitAmountDiv').hide();
            $('#rebateLimitAmount').attr("name","");
        } else if (value == 1) {
            $('#rebateLimitAmountTitle').hide();
            $('#rebateLimitAmountDiv').hide();
            $('#rebateLimitAmount').attr("name","");
        } else if (value == 2) {
            $('#rebateLimitAmountTitle').show();
            $('#rebateLimitAmountDiv').show();
            $('#rebateLimitAmount').attr("name","rebateLimitAmount");
        }
    });
    //抵扣券使用限制下拉框change事件
    $('#rebateUseRange').on('change',function(){
        var value = $("#rebateUseRange option:selected" ).val();
        if (value == 0) {
            //隐藏
            $('#rebateUseRangeTitle').html("");
            $('#rebateUseRangeValueDiv').hide();
            $('#rebateUseCategory').hide();
            $('#rebateUseCategory').attr("name","");
            $('#rebateUseSpecific').hide();
            $('#rebateUseSpecific').attr("name","");
        } else if (value == 1) {
            $('#rebateUseRangeTitle').html("项目id：");
            $('#rebateUseRangeValueDiv').show();
            $('#rebateUseCategory').hide();
            $('#rebateUseCategory').attr("name","");
            $('#rebateUseSpecific').show();
            $('#rebateUseSpecific').attr("name","rebateUseRangeValue");
        } else if (value == 2) {
            $('#rebateUseRangeTitle').html("项目类型：");
            $('#rebateUseRangeValueDiv').show();
            $('#rebateUseCategory').show();
            $('#rebateUseCategory').attr("name","rebateUseRangeValue");
            $('#rebateUseSpecific').hide();
            $('#rebateUseSpecific').attr("name","");
        } else if (value == 3) {
            $('#rebateUseRangeTitle').html("");
            $('#rebateUseRangeValueDiv').hide();
            $('#rebateUseCategory').hide();
            $('#rebateUseCategory').attr("name","");
            $('#rebateUseSpecific').hide();
            $('#rebateUseSpecific').attr("name","");
        }
    });
    // 抵扣券有效时间类型下拉框change事件
    $('#validType').on('change',function(){
        var value = $("#validType option:selected" ).val();
        if (value == 0) {
            //隐藏
            $('#validDateTitle').html("");
            $('#validDateValueDiv').hide();
            $('#validStartTime').hide();
            $('#validStartTime').attr("name","");
            $('#emptyStr').hide();
            $('#validEndTime').hide();
            $('#validEndTime').attr("name","");
            $('#validDays').hide();
            $('#validDays').attr("name","");
        } else if (value == 1) {
            $('#validDateTitle').html("有效期：");
            $('#validDateValueDiv').show();
            $('#validStartTime').show();
            $('#validStartTime').attr("name","validStartTime");
            $('#emptyStr').show();
            $('#validEndTime').show();
            $('#validEndTime').attr("name","validEndTime");
            $('#validDays').hide();
            $('#validDays').attr("name","");
        } else if (value == 2) {
            $('#validDateTitle').html("有效天数：");
            $('#validDateValueDiv').show();
            $('#validStartTime').hide();
            $('#validStartTime').attr("name","");
            $('#emptyStr').hide();
            $('#validEndTime').hide();
            $('#validEndTime').attr("name","");
            $('#validDays').show();
            $('#validDays').attr("name","validDays");
        }
    });
    // 时间
    $('#validStartTime,#validEndTime').datetimepicker({
        yearOffset: 0,
        lang: $.datetimepicker.setLocale('ch'),
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y/m/d',
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
    function submitFrom() {
        $('#form').ajaxSubmit(options);
    }
</script>
</body>
</html>
