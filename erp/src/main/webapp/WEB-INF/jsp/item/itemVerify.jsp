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
        input[type="text"], select, textarea, option, .select-box {
            -webkit-appearance: initial;
            border: none !important;
            cursor: auto
        }

        input[disabled=""],textarea[disabled=""] {
            background-color: #fff;
        }
    </style>
</head>
<body>
<div class="page-container">
    <!-- 选项卡 -->

    <form action="" method="post" class="form form-horizontal" id="form-article-add">

        <!-- 基本信息 -->
        <div>
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
        <div>
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
                                                <a href="${imageUrl}${imgs}" data-lightbox="gallery"><img
                                                        src="${imageUrl}${imgs}?x-oss-process=image/resize,m_lfit,h_200,w_200"></a>
                                            </div>
                                        </div>
                                    </c:forEach>

                                </div>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
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

        </div>

        <!-- 初审审核 -->
        <form action="item/verify" id="verifyForm" method="post">
            <div class="row cl">
                <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">初审审核</h3>
            </div>
            <div class="row cl">
                <input type="hidden" name="itemId" value="${item.itemId}" id="itemId">
                <label class="form-label col-xs-4 col-sm-2"></label>
                <label class="col-xs-4 col-sm-1 mr-20">
                    <input type="radio" class="radio" value="1" name="operateStatus" id="feedback-1" checked> 通过
                </label>
                <label>
                    <input type="radio" class="radio" value="0" name="operateStatus" id="feedback-2"> 不通过
                </label>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">备注：</label>
                <div class="formControls col-xs-8 col-sm-8">
                <textarea name="operateRemark" id="operateRemark" cols="" rows="" style="border:1px #ddd solid!important" class="textarea"
                          placeholder="..." datatype="*10-100" dragonfly="true" nullmsg="备注不能为空！"
                          onKeyUp="$.Huitextarealength(this,200)"></textarea>
                    <p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2"></label>
                <div class="formControls col-xs-8 col-sm-8 text-c">
                    <button class="btn btn-danger radius size-L mt-20 mb-30" style="padding:0 30px" type="button" onclick="verify()">初审确认
                    </button>
                </div>
            </div>
        </form>
    </form>
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
})</script>

<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script type="text/javascript" src="lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script type="text/javascript" src="lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script type="text/javascript" src="lib/webuploader/0.1.5/webuploader.min.js"></script>
<script type="text/javascript" src="lib/ueditor/1.4.3/ueditor.config.js"></script>
<script type="text/javascript" src="lib/ueditor/1.4.3/ueditor.all.min.js"></script>
<script type="text/javascript" src="lib/ueditor/1.4.3/lang/zh-cn/zh-cn.js"></script>

<!-- 省市区 -->
<script type="text/javascript" src="lib/distpicker/distpicker.data.js"></script>
<script type="text/javascript" src="lib/distpicker/distpicker.js"></script>

<!-- 换灯箱 -->
<script type="text/javascript" src="lib/lightbox2/2.8.1/js/lightbox.min.js"></script>
<script type="text/javascript" src="lib/datetimepicker/datetimepicker.js"></script>

<script type="text/javascript">
    function verify(){
        $.ajax({
            type: "POST",
            url: "item/verify",
            data: {'itemId':$("#itemId").val(), 'operateStatus':$("input[name='operateStatus']:checked").val(), 'operateRemark':$("#operateRemark").val()},
            dataType: "json",
            success: function(data){
                console.log(data);
                if(data.code == '200'){
                    layer_close();
                }else {
                    alert("提交初审失败")
                }
            }
        });
    }
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

    $('#datetimepicker').datetimepicker({
        lang: $.datetimepicker.setLocale('ch'),
    });

</script>
</body>
</html>

