<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
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
    <link rel=stylesheet type=text/css href="static/h-ui/css/H-ui.min.css"/>
    <link rel=stylesheet type=text/css href="static/h-ui.admin/css/H-ui.admin.css"/>
    <link rel=stylesheet type=text/css href="lib/Hui-iconfont/1.0.8/iconfont.css"/>
    <link rel=stylesheet type=text/css href="static/h-ui.admin/skin/default/skin.css" id=skin/>
    <link rel=stylesheet type=text/css href="static/h-ui.admin/css/style.css" />
    <!--[if IE 6]>
    <script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js"></script>
    <script>DD_belatedPNG.fix('*');</script><![endif]--> </head>
</html>
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
</style>
</head>
<body>
<div class="page-container">
    <!-- 选项卡 -->

    <span class="form form-horizontal" id="form-article-add">
        <!-- 基本信息 -->
        <div>
            <form action="add" id="itemAddForm" method="post">

                <input type="hidden" name="itemId" id="itemId">
                <div class="row cl">
                    <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">基本信息</h3>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">项目名称：</label>
                    <div class="formControls col-xs-8 col-sm-8">
                        <input type="text" class="input-text" value="" id="itemName" name="itemName"
                               maxlength="30" required placeholder="项目标题最多30字">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">项目简介：</label>
                    <div class="formControls col-xs-8 col-sm-8">
                        <textarea id="itemProfile" name="itemProfile" cols="" rows="" class="textarea" placeholder="..."
                                  datatype="*10-100"
                                  dragonfly="true" nullmsg="备注不能为空！"
                                  onKeyUp="$.Huitextarealength(this,200)"></textarea>
                        <p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">众筹类型：</label>
                    <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                        <select name="typeId" id="typeId" class="select">
                            <c:forEach items="${type}" var="type">
                                <option value="${type.typeId}">${type.typeName}</option>
                            </c:forEach>
                        </select>
                        </span>
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">项目分类：</label>
                    <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                        <select name="classId" id="classId" class="select">
                            <c:forEach items="${itemClass}" var="itemClass">
                                <option value="${itemClass.classId}">${itemClass.className}</option>
                            </c:forEach>
                        </select>
                        </span>
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">目标金额：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <input type="text" class="input-text" value="" placeholder="" id="targetAmount"
                               name="targetAmount" required>
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">预热天数：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <input type="text" class="input-text" value="" placeholder=""
                               id="preheatingDays" name="preheatingDays">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">项目方：</label>
                    <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                        <select name="partnerId" id="partnerId" class="select">
                            <c:forEach items="${partner}" var="partner">
                                <option value="${partner.partnerId}">${partner.partnerName}</option>
                            </c:forEach>
                        </select>
                        </span>
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">众筹天数：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <input type="text" class="input-text" value="" placeholder="" id="crowdDays"
                               name="crowdDays">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2" style="line-height:30px;">项目地区：</label>
                    <div class="formControls col-xs-8 col-sm-9">
                        <label data-toggle="distpicker" style="display:block;width:100%">
                            <span class="select-box select-box1" style="border:none;">
                            <select name="areaId" class="col-sm-3 col-xs-8" data-province="---- 选择省 ----">
                                <c:forEach items="${provinces}" var="provinces">
                                    <option value="${provinces.areaId}">${provinces.areaName}</option>
                                </c:forEach>
                            </select>
                              <select name="" class="col-sm-3 col-xs-8 col-sm-offset-1" data-city="---- 选择市 ----"></select>
                              <select name="" class="col-sm-3 col-xs-8 col-sm-offset-1"
                                      data-district="---- 选择区 ----"></select>
                            </span>
                        </label>
                    </div>
                </div>

                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">项目头图：</label>
                    <input type="hidden" id="headerImage" name="headerImage">
                    <div class="formControls col-xs-8 col-sm-8">
                        <!-- 图片上传模块 -->
                        <div class="uploader-list-container">
                            <div class="queueList">
                                <div id="dndArea" class="placeholder">
                                    <div id="filePicker-2"></div>
                                    <p>或将照片拖到这里，单次最多可选300张</p>
                                </div>
                            </div>
                            <div class="statusBar" style="display:none;">
                                <div class="progress"><span class="text">0%</span> <span class="percentage"></span></div>
                                <div class="info"></div>
                                <div class="btns">
                                    <div id="filePicker2"></div>
                                    <div class="uploadBtn">开始上传</div>
                                </div>
                            </div>
                        </div>
                        <%--<input type="file" name="file" id="file"> <input type="button" id="uploadButton" value="开始上传">--%>
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
                    <span class="current" cnum="0">项目介绍<var></var></span>
                    <span cnum="1">融资方案<var></var></span>
                </div>
                <%--<em>+</em>--%>
            </div>
            <div class="cl"></div>
            <div class="content_set">
                <form action="content/add" method="post" id="contentAddForm0">
                    <input type="hidden" name="itemId" id="itemId0">
                    <input type="hidden" name="contentType" value="1">
                    <input type="hidden" id="contentId0" name="contentId" value="0">
                    <div class="item">
                        <div class="row cl">
                            <label class="form-label col-xs-4 col-sm-2">主标题：</label>
                            <div class="formControls col-xs-8 col-sm-8">
                                <input type="text" class="input-text" value="" maxlength="30" placeholder="项目标题最多30字" id="contentTitle0"
                                       name="contentTitle">
                            </div>
                        </div>
                        <div class="row cl">
                            <label class="form-label col-xs-4 col-sm-2">内容：</label>
                            <div class="formControls col-xs-8 col-sm-8">
                                <textarea name="contentInfo" id="contentInfo0" cols="" rows="" class="textarea" placeholder="..." datatype="*10-100"
                                          dragonfly="true" nullmsg="备注不能为空！"
                                          onKeyUp="$.Huitextarealength(this,200)"></textarea>
                                <p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
                            </div>
                        </div>

                        <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2 mt-30 mb-20">
                            <button class="btn btn-primary radius" id="contentAddBtn0" type="button"  onclick="contentAdd()"><i
                                    class="Hui-iconfont">&#xe632;</i> 保存</button>
                        </div>
                    </div>
                </form>

                <form action="content/add" method="post" id="contentAddForm1">
                    <input type="hidden" name="itemId" id="itemId1">
                    <input type="hidden" name="contentType" value="2">
                    <input type="hidden" id="contentId1" name="contentId" value="0">
                    <div class="item">
                        <div class="row cl">
                            <label class="form-label col-xs-4 col-sm-2">主标题：</label>
                            <div class="formControls col-xs-8 col-sm-8">
                                <input type="text" class="input-text" value="" maxlength="30" placeholder="项目标题最多30字" id="contentTitle1"
                                       name="contentTitle">
                            </div>
                        </div>
                        <div class="row cl">
                            <label class="form-label col-xs-4 col-sm-2">内容：</label>
                            <div class="formControls col-xs-8 col-sm-8">
                                <textarea name="contentInfo" id="contentInfo1" cols="" rows="" class="textarea" placeholder="..." datatype="*10-100"
                                          dragonfly="true" nullmsg="备注不能为空！"
                                          onKeyUp="$.Huitextarealength(this,200)"></textarea>
                                <p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
                            </div>
                        </div>

                        <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2 mt-30 mb-20">
                            <button class="btn btn-primary radius" id="contentAddBtn1" type="button" onclick="contentAdd()"><i
                                    class="Hui-iconfont">&#xe632;</i> 保存</button>
                        </div>
                    </div>
                </form>


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
                        <span class="current" cnum="0">档位1<var></var></span>
                    </div>
                    <em>+</em>
                </div>
                <div class="cl"></div>
                <div class="huibao_wrapper">
                    <div class="huibao_tab">
                        <form id="levelAddForm0" >
                            <input type="hidden" id="itemId10" name="itemId">
                            <input type="hidden" id="levelId0" name="levelId" value="0">
                            <div class="row cl" style="display:block">
                                <label class="form-label col-xs-4 col-sm-2">档位名称：</label>
                                <div class="formControls col-xs-8 col-sm-8">
                                    <input type="text" class="input-text" value="" maxlength="30" id="levelName0" name="levelName1">
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
                                <select name="revenueModel" id="revenue_model0" class="select">
                                    <option value="1">一次性还款</option>
                                    <option value="2">按月还息到期还本</option>
                                </select>
                                </span>
                                </div>
                                <label class="form-label col-xs-4 col-sm-2">分红周期：</label>
                                <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                                <select name="shareBonusPeriod" id="shareBonusPeriod" class="select">
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
                </div>
            </div>


            <div class="hide_huibao_tab" style="display:none">
                <div class="huibao_tab">
                    <form id="levelAddForm$1" >
                        <input type="hidden" id="itemId1$1" name="itemId">
                        <input type="hidden" id="levelId$1" name="levelId" value="0">
                        <div class="row cl" style="display:block">
                            <label class="form-label col-xs-4 col-sm-2">档位名称：</label>
                            <div class="formControls col-xs-8 col-sm-8">
                                <input type="text" class="input-text" value="" maxlength="30" id="levelName$1" name="levelName">
                            </div>
                        </div>
                        <div class="row cl">
                            <label class="form-label col-xs-4 col-sm-2">支持金额：</label>
                            <div class="formControls col-xs-8 col-sm-8">
                                <input type="text" class="input-text" value="" maxlength="30" id="levelAmount$1" name="levelAmount">
                            </div>
                        </div>
                        <div class="row cl">
                            <label class="form-label col-xs-4 col-sm-2">回报描述：</label>
                            <div class="formControls col-xs-8 col-sm-8">
                                <textarea name="levelDesc" id="levelDesc$1" cols="" rows="" class="textarea" placeholder="..." datatype="*10-100"
                                          dragonfly="true" nullmsg="备注不能为空！"
                                          onKeyUp="$.Huitextarealength(this,200)"></textarea>
                                <p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
                            </div>
                        </div>

                        <div class="row cl">
                            <label class="form-label col-xs-4 col-sm-2">总份数：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                <input type="text" class="input-text" value="" placeholder="0即为无限制" maxlength="30" id="totalNumber$1"
                                       name="totalNumber">
                            </div>
                            <label class="form-label col-xs-4 col-sm-2">单人限额：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                <input type="text" class="input-text" value="" placeholder="0即为无限制" maxlength="30" id="singleLimitNumber$1"
                                       name="singleLimitNumber">
                            </div>
                        </div>
                        <div class="row cl">
                            <label class="form-label col-xs-4 col-sm-2">回报时间：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                <input type="text" class="input-text" value="" placeholder="100 天" maxlength="30" id="paybackDays$1"
                                       name="paybackDays">
                            </div>
                            <label class="form-label col-xs-4 col-sm-2">是否分红：</label>
                            <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                            <select id="isShareBonus$1" name="isShareBonus" class="select">
                                <option value="1">是</option>
                                <option value="0">否</option>
                            </select>
                            </span>
                            </div>
                        </div>
                        <div class="row cl">
                            <label class="form-label col-xs-4 col-sm-2">年化利率：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                <input type="text" class="input-text" value="" placeholder="%" maxlength="30" id="yearRate$1"
                                       name="yearRate">
                            </div>
                            <label class="form-label col-xs-4 col-sm-2">投资期限：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                <input type="text" class="input-text" value="" placeholder=" 月" maxlength="30" id="investmentPeriod$1"
                                       name="investmentPeriod">
                            </div>
                        </div>
                        <div class="row cl">
                            <label class="form-label col-xs-4 col-sm-2">收益方式：</label>
                            <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                            <select name="revenueModel" id="revenue_model$1" class="select">
                                <option value="1">一次性还款</option>
                                <option value="2">按月还息到期还本</option>
                            </select>
                            </span>
                            </div>
                            <label class="form-label col-xs-4 col-sm-2">分红周期：</label>
                            <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                            <select name="shareBonusPeriod" id="shareBonusPeriod$1" class="select">
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
                            <select name="isNeedAddress" id="isNeedAddress$1" class="select">
                                <option value="1">是</option>
                                <option value="0">否</option>
                            </select>
                            </span>
                            </div>
                            <label class="form-label col-xs-4 col-sm-2">是否需要协议：</label>
                            <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
                            <select name="isNeedAgreement" id="isNeedAgreement$1" class="select">
                                <option value="1">是</option>
                                <option value="0">否</option>
                            </select>
                            </span>
                            </div>
                        </div>
                        <div class="row cl">
                            <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2 mt-30 mb-20">
                                <button class="btn btn-secondary radius" type="button" id="levelAddBtn$1"  onclick="levelAdd()"><i
                                        class="Hui-iconfont">&#xe632;</i> 保存
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="hide_content_item" style="display:none;">
                <div class="item">
                    <div class="row cl">
                        <label class="form-label col-xs-4 col-sm-2">主标题：</label>
                        <div class="formControls col-xs-8 col-sm-8">
                            <input type="text" class="input-text" value="" maxlength="30" placeholder="项目标题最多30字" id=""
                                   name="">
                        </div>
                    </div>
                    <div class="row cl">
                        <label class="form-label col-xs-4 col-sm-2">内容：</label>
                        <div class="formControls col-xs-8 col-sm-8">
                            <textarea name="" cols="" rows="" class="textarea" placeholder="..." datatype="*10-100"
                                      dragonfly="true" nullmsg="备注不能为空！"
                                      onKeyUp="$.Huitextarealength(this,200)"></textarea>
                            <p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
                        </div>
                    </div>
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

    (function ($) {
        var uploader;
        // 大图上传 当domReady的时候开始初始化
        $(function () {
            var $wrap = $('.uploader-list-container'),

            // 图片容器
                    $queue = $('<ul class="filelist"></ul>')
                            .appendTo($wrap.find('.queueList')),

            // 状态栏，包括进度和控制按钮
                    $statusBar = $wrap.find('.statusBar'),

            // 文件总体选择信息。
                    $info = $statusBar.find('.info'),

            // 上传按钮
                    $upload = $wrap.find('.uploadBtn'),

            // 没选择文件之前的内容。
                    $placeHolder = $wrap.find('.placeholder'),

                    $progress = $statusBar.find('.progress').hide(),

            // 添加的文件数量
                    fileCount = 0,

            // 添加的文件总大小
                    fileSize = 0,

            // 优化retina, 在retina下这个值是2
                    ratio = window.devicePixelRatio || 1,

            // 缩略图大小
                    thumbnailWidth = 110 * ratio,
                    thumbnailHeight = 110 * ratio,

            // 可能有pedding, ready, uploading, confirm, done.
                    state = 'pedding',

            // 所有文件的进度信息，key为file id
                    percentages = {},
            // 判断浏览器是否支持图片的base64
                    isSupportBase64 = (function () {
                        var data = new Image();
                        var support = true;
                        data.onload = data.onerror = function () {
                            if (this.width != 1 || this.height != 1) {
                                support = false;
                            }
                        }
                        data.src = "data:image/gif;base64,R0lGODlhAQABAIAAAAAAAP///ywAAAAAAQABAAACAUwAOw==";
                        return support;
                    })(),

            // 检测是否已经安装flash，检测flash的版本
                    flashVersion = (function () {
                        var version;

                        try {
                            version = navigator.plugins['Shockwave Flash'];
                            version = version.description;
                        } catch (ex) {
                            try {
                                version = new ActiveXObject('ShockwaveFlash.ShockwaveFlash')
                                        .GetVariable('$version');
                            } catch (ex2) {
                                version = '0.0';
                            }
                        }
                        version = version.match(/\d+/g);
                        return parseFloat(version[0] + '.' + version[1], 10);
                    })(),

                    supportTransition = (function () {
                        var s = document.createElement('p').style,
                                r = 'transition' in s ||
                                        'WebkitTransition' in s ||
                                        'MozTransition' in s ||
                                        'msTransition' in s ||
                                        'OTransition' in s;
                        s = null;
                        return r;
                    })(),

            // WebUploader实例
                    uploader;

            if (!WebUploader.Uploader.support('flash') && WebUploader.browser.ie) {

                // flash 安装了但是版本过低。
                if (flashVersion) {
                    (function (container) {
                        window['expressinstallcallback'] = function (state) {
                            switch (state) {
                                case 'Download.Cancelled':
                                    alert('您取消了更新！')
                                    break;

                                case 'Download.Failed':
                                    alert('安装失败')
                                    break;

                                default:
                                    alert('安装已成功，请刷新！');
                                    break;
                            }
                            delete window['expressinstallcallback'];
                        };

                        var swf = 'expressInstall.swf';
                        // insert flash object
                        var html = '<object type="application/' +
                                'x-shockwave-flash" data="' + swf + '" ';

                        if (WebUploader.browser.ie) {
                            html += 'classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" ';
                        }

                        html += 'width="100%" height="100%" style="outline:0">' +
                                '<param name="movie" value="' + swf + '" />' +
                                '<param name="wmode" value="transparent" />' +
                                '<param name="allowscriptaccess" value="always" />' +
                                '</object>';

                        container.html(html);

                    })($wrap);

                    // 压根就没有安转。
                } else {
                    $wrap.html('<a href="http://www.adobe.com/go/getflashplayer" target="_blank" border="0"><img alt="get flash player" src="http://www.adobe.com/macromedia/style_guide/images/160x41_Get_Flash_Player.jpg" /></a>');
                }

                return;
            } else if (!WebUploader.Uploader.support()) {
                alert('Web Uploader 不支持您的浏览器！');
                return;
            }

            // 实例化
            uploader = WebUploader.create({
                pick: {
                    id: '#filePicker-2',
                    label: '点击选择图片'
                },
                formData: {
                    uid: 123
                },
                dnd: '#dndArea',
                paste: '#uploader',
                swf: 'lib/webuploader/0.1.5/Uploader.swf',
                chunked: false,
                chunkSize: 512 * 1024,
                server: 'upload?foldName=item',
                // runtimeOrder: 'flash',

                // accept: {
                //     title: 'Images',
                //     extensions: 'gif,jpg,jpeg,bmp,png',
                //     mimeTypes: 'image/*'
                // },

                // 禁掉全局的拖拽功能。这样不会出现图片拖进页面的时候，把图片打开。
                disableGlobalDnd: true,
                fileNumLimit: 300,
                fileSizeLimit: 200 * 1024 * 1024,    // 200 M
                fileSingleSizeLimit: 50 * 1024 * 1024,    // 50 M
                duplicate:true
            });

            // 拖拽时不接受 js, txt 文件。
            uploader.on('dndAccept', function (items) {
                var denied = false,
                        len = items.length,
                        i = 0,
                // 修改js类型
                        unAllowed = 'text/plain;application/javascript ';

                for (; i < len; i++) {
                    // 如果在列表里面
                    if (~unAllowed.indexOf(items[i].type)) {
                        denied = true;
                        break;
                    }
                }

                return !denied;
            });

            uploader.on('dialogOpen', function () {
                console.log('here');
            });

            uploader.on( 'uploadSuccess', function( file, response ) {
                if(response.code == 200){
                    $("#headerImage").val("item/"+response.data);
                }
                console.log("response: "+response.data);
            });

            // uploader.on('filesQueued', function() {
            //     uploader.sort(function( a, b ) {
            //         if ( a.name < b.name )
            //           return -1;
            //         if ( a.name > b.name )
            //           return 1;
            //         return 0;
            //     });
            // });

            // 添加“添加文件”的按钮，
            uploader.addButton({
                id: '#filePicker2',
                label: '继续添加'
            });

            uploader.on('ready', function () {
                window.uploader = uploader;
            });

            // 当有文件添加进来时执行，负责view的创建
            function addFile(file) {
                var $li = $('<li id="' + file.id + '">' +
                                '<p class="title">' + file.name + '</p>' +
                                '<p class="imgWrap"></p>' +
                                '<p class="progress"><span></span></p>' +
                                '</li>'),

                        $btns = $('<div class="file-panel">' +
                                '<span class="cancel">删除</span>' +
                                '<span class="rotateRight">向右旋转</span>' +
                                '<span class="rotateLeft">向左旋转</span></div>').appendTo($li),
                        $prgress = $li.find('p.progress span'),
                        $wrap = $li.find('p.imgWrap'),
                        $info = $('<p class="error"></p>'),

                        showError = function (code) {
                            switch (code) {
                                case 'exceed_size':
                                    text = '文件大小超出';
                                    break;

                                case 'interrupt':
                                    text = '上传暂停';
                                    break;

                                default:
                                    text = '上传失败，请重试';
                                    break;
                            }

                            $info.text(text).appendTo($li);
                        };

                if (file.getStatus() === 'invalid') {
                    showError(file.statusText);
                } else {
                    // @todo lazyload
                    $wrap.text('预览中');
                    uploader.makeThumb(file, function (error, src) {
                        var img;

                        if (error) {
                            $wrap.text('不能预览');
                            return;
                        }

                        if (isSupportBase64) {
                            img = $('<img src="' + src + '">');
                            $wrap.empty().append(img);
                        } else {
                            $.ajax('lib/webuploader/0.1.5/server/preview.php', {
                                method: 'POST',
                                data: src,
                                dataType: 'json'
                            }).done(function (response) {
                                if (response.result) {
                                    img = $('<img src="' + response.result + '">');
                                    $wrap.empty().append(img);
                                } else {
                                    $wrap.text("预览出错");
                                }
                            });
                        }
                    }, thumbnailWidth, thumbnailHeight);

                    percentages[file.id] = [file.size, 0];
                    file.rotation = 0;
                }

                file.on('statuschange', function (cur, prev) {
                    if (prev === 'progress') {
                        $prgress.hide().width(0);
                    } else if (prev === 'queued') {
                        $li.off('mouseenter mouseleave');
                        $btns.remove();
                    }

                    // 成功
                    if (cur === 'error' || cur === 'invalid') {
                        console.log(file.statusText);
                        showError(file.statusText);
                        percentages[file.id][1] = 1;
                    } else if (cur === 'interrupt') {
                        showError('interrupt');
                    } else if (cur === 'queued') {
                        percentages[file.id][1] = 0;
                    } else if (cur === 'progress') {
                        $info.remove();
                        $prgress.css('display', 'block');
                    } else if (cur === 'complete') {
                        $li.append('<span class="success"></span>');
                    }

                    $li.removeClass('state-' + prev).addClass('state-' + cur);
                });

                $li.on('mouseenter', function () {
                    $btns.stop().animate({height: 30});
                });

                $li.on('mouseleave', function () {
                    $btns.stop().animate({height: 0});
                });

                $btns.on('click', 'span', function () {
                    var index = $(this).index(),
                            deg;

                    switch (index) {
                        case 0:
                            uploader.removeFile(file);
                            return;

                        case 1:
                            file.rotation += 90;
                            break;

                        case 2:
                            file.rotation -= 90;
                            break;
                    }

                    if (supportTransition) {
                        deg = 'rotate(' + file.rotation + 'deg)';
                        $wrap.css({
                            '-webkit-transform': deg,
                            '-mos-transform': deg,
                            '-o-transform': deg,
                            'transform': deg
                        });
                    } else {
                        $wrap.css('filter', 'progid:DXImageTransform.Microsoft.BasicImage(rotation=' + (~~((file.rotation / 90) % 4 + 4) % 4) + ')');
                        // use jquery animate to rotation
                        // $({
                        //     rotation: rotation
                        // }).animate({
                        //     rotation: file.rotation
                        // }, {
                        //     easing: 'linear',
                        //     step: function( now ) {
                        //         now = now * Math.PI / 180;

                        //         var cos = Math.cos( now ),
                        //             sin = Math.sin( now );

                        //         $wrap.css( 'filter', "progid:DXImageTransform.Microsoft.Matrix(M11=" + cos + ",M12=" + (-sin) + ",M21=" + sin + ",M22=" + cos + ",SizingMethod='auto expand')");
                        //     }
                        // });
                    }


                });

                $li.appendTo($queue);
            }

            // 负责view的销毁
            function removeFile(file) {
                var $li = $('#' + file.id);

                delete percentages[file.id];
                updateTotalProgress();
                $li.off().find('.file-panel').off().end().remove();
            }

            function updateTotalProgress() {
                var loaded = 0,
                        total = 0,
                        spans = $progress.children(),
                        percent;

                $.each(percentages, function (k, v) {
                    total += v[0];
                    loaded += v[0] * v[1];
                });

                percent = total ? loaded / total : 0;


                spans.eq(0).text(Math.round(percent * 100) + '%');
                spans.eq(1).css('width', Math.round(percent * 100) + '%');
                updateStatus();
            }

            function updateStatus() {
                var text = '', stats;

                if (state === 'ready') {
                    text = '选中' + fileCount + '张图片，共' +
                            WebUploader.formatSize(fileSize) + '。';
                } else if (state === 'confirm') {
                    stats = uploader.getStats();
                    if (stats.uploadFailNum) {
                        text = '已成功上传' + stats.successNum + '张照片至XX相册，' +
                                stats.uploadFailNum + '张照片上传失败，<a class="retry" href="#">重新上传</a>失败图片或<a class="ignore" href="#">忽略</a>'
                    }

                } else {
                    stats = uploader.getStats();
                    text = '共' + fileCount + '张（' +
                            WebUploader.formatSize(fileSize) +
                            '），已上传' + stats.successNum + '张';

                    if (stats.uploadFailNum) {
                        text += '，失败' + stats.uploadFailNum + '张';
                    }
                }

                $info.html(text);
            }

            function setState(val) {

                var file, stats;

                if (val === state) {
                    return;
                }

                $upload.removeClass('state-' + state);
                $upload.addClass('state-' + val);
                state = val;

                switch (state) {
                    case 'pedding':
                        $placeHolder.removeClass('element-invisible');
                        $queue.hide();
                        $statusBar.addClass('element-invisible');
                        uploader.refresh();
                        break;

                    case 'ready':
                        $placeHolder.addClass('element-invisible');
                        $('#filePicker2').removeClass('element-invisible');
                        $queue.show();
                        $statusBar.removeClass('element-invisible');
                        uploader.refresh();
                        break;

                    case 'uploading':
                        $('#filePicker2').addClass('element-invisible');
                        $progress.show();
                        $upload.text('暂停上传');
                        break;

                    case 'paused':
                        $progress.show();
                        $upload.text('继续上传');
                        break;

                    case 'confirm':
                        $progress.hide();
                        $('#filePicker2').removeClass('element-invisible');
                        $upload.text('开始上传');

                        stats = uploader.getStats();
                        if (stats.successNum && !stats.uploadFailNum) {
                            setState('finish');
                            return;
                        }
                        break;
                    case 'finish':
                        stats = uploader.getStats();
                        if (stats.successNum) {
                            alert('上传成功');
                        } else {
                            // 没有成功的图片，重设
                            state = 'done';
                            location.reload();
                        }
                        break;
                }

                updateStatus();
            }

            uploader.onUploadProgress = function (file, percentage) {
                var $li = $('#' + file.id),
                        $percent = $li.find('.progress span');

                $percent.css('width', percentage * 100 + '%');
                percentages[file.id][1] = percentage;
                updateTotalProgress();
            };

            uploader.onFileQueued = function (file) {
                fileCount++;
                fileSize += file.size;

                if (fileCount === 1) {
                    $placeHolder.addClass('element-invisible');
                    $statusBar.show();
                }

                addFile(file);
                setState('ready');
                updateTotalProgress();
            };

            uploader.onFileDequeued = function (file) {
                fileCount--;
                fileSize -= file.size;

                if (!fileCount) {
                    setState('pedding');
                }

                removeFile(file);
                updateTotalProgress();

            };

            uploader.on('all', function (type) {
                var stats;
                switch (type) {
                    case 'uploadFinished':
                        setState('confirm');
                        break;

                    case 'startUpload':
                        setState('uploading');
                        break;

                    case 'stopUpload':
                        setState('paused');
                        break;

                }
            });

            uploader.onError = function (code) {
                alert('Eroor: ' + code);
            };

            $upload.on('click', function () {
                if ($(this).hasClass('disabled')) {
                    return false;
                }

                if (state === 'ready') {
                    uploader.upload();
                } else if (state === 'paused') {
                    uploader.upload();
                } else if (state === 'uploading') {
                    uploader.stop();
                }
            });

            $info.on('click', '.retry', function () {
                uploader.retry();
            });

            $info.on('click', '.ignore', function () {
                alert('todo');
            });

            $upload.addClass('state-' + state);
            updateTotalProgress();
        });

    })(jQuery);


</script>
</body>
</html>
