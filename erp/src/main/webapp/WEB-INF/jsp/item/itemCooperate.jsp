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

    .huibao_tab_menu {
        border-bottom: 1px #ddd solid;
    }

    .huibao_tab_menu span {
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

    .huibao_tab_menu span.current {
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

    input[disabled=""] {
        background-color: #fff;
    }
</style>
</head>
<body>
<div class="page-container">
    <!-- 选项卡 -->

    <form action="" method="post" class="form form-horizontal" id="form-article-add">

        <!-- 基本信息 -->
        <div class="jiben_info">
            <div class="row cl">
                <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">基本信息</h3>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">项目名称：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="text" class="input-text" value="${item.itemName}" disabled >
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">项目简介：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <textarea name="" cols="" rows="" class="textarea" placeholder="..." datatype="*10-100"
                              dragonfly="true" onKeyUp="$.Huitextarealength(this,200)">${item.itemProfile}</textarea>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">众筹类型：</label>
                <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
					<select name="" class="select" disabled>
						<option value="0">${item.typeName}</option>
					</select>
					</span>
                </div>
                <label class="form-label col-xs-4 col-sm-2">项目分类：</label>
                <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
					<select name="" class="select">
						<option value="0">${item.className}</option>
					</select>
					</span>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">目标金额：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="${item.targetAmount}">
                </div>
                <label class="form-label col-xs-4 col-sm-2">预热天数：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="" placeholder="" id="" name="">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">项目方：</label>
                <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
					<select name="" class="select">
						<option value="0">宇智光</option>
						<option value="1">宇智光</option>
						<option value="11">宇智光</option>
						<option value="13">├二级分类</option>
					</select>
					</span>
                </div>
                <label class="form-label col-xs-4 col-sm-2">众筹天数：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="" placeholder="" id="" name="">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">项目地区：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="text" class="input-text" value="北京啊北京" disabled>
                </div>
            </div>

            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">项目头图：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <!-- 审核模式 查看图 -->
                    <div class="portfoliobox">
                        <div class="picbox">
                            <a href="temp/big/beauty.jpg" data-lightbox="gallery" data-title="项目头图"><img
                                    src="temp/Thumb/shufang.jpg"></a>
                        </div>
                    </div>

                </div>
            </div>

        </div>

        <!-- 内容设置 -->
        <div class="neirong_set">
            <div class="row cl">
                <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">内容信息</h3>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">主标题：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <input type="text" class="input-text" value="" maxlength="30" placeholder="项目标题最多30字" id="" name="">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">内容：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <textarea name="" cols="" rows="" class="textarea" placeholder="..." datatype="*10-100"
                              dragonfly="true" nullmsg="备注不能为空！" onKeyUp="$.Huitextarealength(this,200)"></textarea>
                    <p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
                </div>
            </div>
        </div>
        <!-- 回报信息 -->
        <div class="huibao_set">
            <div class="row cl">
                <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">回报信息</h3>
            </div>
            <div class="row cl huibao_tab_menu col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10">
                <span class="current">档位名称1</span>
                <span>档位名称2</span>
            </div>
            <div class="cl"></div>
            <div class="huibao_tab">
                <div class="row cl" style="display:block">
                    <label class="form-label col-xs-4 col-sm-2">档位名称：</label>
                    <div class="formControls col-xs-8 col-sm-8">
                        <input type="text" class="input-text" value="" maxlength="30" id="" name="">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">支持金额：</label>
                    <div class="formControls col-xs-8 col-sm-8">
                        <input type="text" class="input-text" value="" maxlength="30" id="" name="">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">回报描述：</label>
                    <div class="formControls col-xs-8 col-sm-8">
                        <textarea name="" cols="" rows="" class="textarea" placeholder="..." datatype="*10-100"
                                  dragonfly="true" nullmsg="备注不能为空！" onKeyUp="$.Huitextarealength(this,200)"></textarea>
                        <p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
                    </div>
                </div>

                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">总份数：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <input type="text" class="input-text" value="" placeholder="0即为无限制" maxlength="30" id=""
                               name="">
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">单人限额：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <input type="text" class="input-text" value="" placeholder="0即为无限制" maxlength="30" id=""
                               name="">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">回报时间：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <input type="text" class="input-text" value="" placeholder="100 天" maxlength="30" id="" name="">
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">是否分红：</label>
                    <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
						<select name="" class="select">
							<option value="0">是</option>
							<option value="1">否</option>
						</select>
						</span>
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">年化利率：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <input type="text" class="input-text" value="" placeholder="%" maxlength="30" id="" name="">
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">投资期限：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <input type="text" class="input-text" value="" placeholder=" 月" maxlength="30" id="" name="">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">收益方式：</label>
                    <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
						<select name="" class="select">
							<option value="0">一次性还款</option>
							<option value="1">等额本金...</option>
						</select>
						</span>
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">分红周期：</label>
                    <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
						<select name="" class="select">
							<option value="0">1月</option>
							<option value="1">3月</option>
							<option value="0">6月</option>
							<option value="1">12月</option>
						</select>
						</span>
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">是否需要地址：</label>
                    <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
						<select name="" class="select">
							<option value="0">是</option>
							<option value="1">否</option>
						</select>
						</span>
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">是否需要协议：</label>
                    <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
						<select name="" class="select">
							<option value="0">是</option>
							<option value="1">否</option>
						</select>
						</span>
                    </div>
                </div>
            </div>
            <div class="huibao_tab">
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">另外一档位名称：</label>
                    <div class="formControls col-xs-8 col-sm-8">
                        <input type="text" class="input-text" value="仿照第一个添加表格" maxlength="30" id="" name="">
                    </div>
                </div>
            </div>
        </div>
        <!-- 合作方式 -->
        <div class="hezuo_style">
            <div class="row cl">
                <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">合作方式</h3>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">佣金比例：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="" placeholder="已筹金额的百分比" id="" name="">
                </div>
                <label class="form-label col-xs-4 col-sm-2">保证金：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="" placeholder="已筹金额的百分比" id="" name="">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">预付分红金：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="" placeholder="/期" id="" name="">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">放款方式：</label>
                <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
					<select name="" class="select">
						<option value="0">一次性</option>
						<option value="1">俩次放款</option>
					</select>
					</span>
                </div>
                <label class="form-label col-xs-4 col-sm-2">首款比例：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="" placeholder="" id="30%（默认）" name="">
                </div>
            </div>
        </div>

        <style>
            .uploader-thum-container1 .uploader-list .item {
                float: left;
                display: inline;
                width: 25%;
            }
        </style>
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
                        <th>合同文件</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr class="text-c">
                        <td>发起人身份证正反面
                            <button class="btn btn-link radius ml-10" type="button">下载</button>
                        </td>
                        <td class="text-l">
                            <div id="uploader" class="wu-example">
                                <!--用来存放文件信息-->
                                <div id="thelist" class="uploader-list"></div>
                                <div class="btns">
                                    <div id="picker">选择图片</div>
                                    <button id="ctlBtn" class="btn btn-default" type="button">开始上传</button>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr class="text-c">
                        <td>发起人身份证正反面
                            <button class="btn btn-link radius ml-10" type="button">下载</button>
                        </td>
                        <td class="text-l">北京玉之光众筹
                            <button class="btn r va-m btn-secondary radius ml-10" type="button">上传</button>
                        </td>
                    </tr>
                    <tr class="text-c">
                        <td>发起人身份证正反面
                            <button class="btn btn-link radius ml-10" type="button">下载</button>
                        </td>
                        <td class="text-l">北京玉之光众筹
                            <button class="btn r va-m btn-secondary radius ml-10" type="button">上传</button>
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
                    <input type="text" class="input-text" value="" maxlength="30" id="" name="">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">股东身份证号：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="" placeholder="/期" id="" name="">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">股东地址：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="" placeholder="/期" id="" name="">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">担保人姓名：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="" placeholder="/期" id="" name="">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">担保人身份证号：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="" placeholder="/期" id="" name="">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">担保人地址：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="" placeholder="/期" id="" name="">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-8 col-sm-8  col-sm-offset-1" style="text-align:left;">投资人每投资100万元，股东向管理公司质押不低于
                    %的股份</label>

            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">项目方印章：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <div class="portfoliobox">
                        <div class="picbox">
                            <a href="temp/big/beauty.jpg" data-lightbox="gallery" data-title="项目头图"><img
                                    src="temp/Thumb/shufang.jpg"></a>
                        </div>
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
                        <th>合同文件</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr class="text-c">
                        <td>发起人身份证正反面
                            <button class="btn btn-link radius ml-10" type="button">下载</button>
                        </td>
                        <td class="text-l">
                            <div id="uploader" class="wu-example">
                                <!--用来存放文件信息-->
                                <div id="thelist" class="uploader-list"></div>
                                <div class="btns">
                                    <div id="picker">选择图片</div>
                                    <button id="ctlBtn" class="btn btn-default" type="button">开始上传</button>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr class="text-c">
                        <td>发起人身份证正反面
                            <button class="btn btn-link radius ml-10" type="button">下载</button>
                        </td>
                        <td class="text-l">
                            <div class="portfoliobox">
                                <div class="picbox">
                                    <a href="temp/big/beauty.jpg" data-lightbox="gallery" data-title="项目头图"><img
                                            src="temp/Thumb/shufang.jpg"></a>

                                    <a href="temp/big/beauty.jpg" data-lightbox="gallery" data-title="项目头图"><img
                                            src="temp/Thumb/shufang.jpg"></a>

                                    <a href="temp/big/beauty.jpg" data-lightbox="gallery" data-title="项目头图"><img
                                            src="temp/Thumb/shufang.jpg"></a>
                                </div>
                            </div>
                        </td>
                    </tr>
                    <tr class="text-c">
                        <td>发起人身份证正反面
                            <button class="btn btn-link radius ml-10" type="button">下载</button>
                        </td>
                        <td class="text-l">北京玉之光众筹
                            <button class="btn r va-m btn-secondary radius ml-10" type="button">上传</button>
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
                    <tr class="text-c">
                        <td>初审</td>
                        <td>通过</td>
                        <td>...</td>
                        <td>2017-02-15 15:33:33</td>
                        <td>财务</td>
                    </tr>
                    </tbody>
                </table>
            </div>
            <div class="cl"></div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2"></label>
                <div class="formControls col-xs-8 col-sm-8 text-c">
                    <button class="btn btn-danger radius size-L mt-20 mb-30" style="padding:0 30px" type="submit">提交复审
                    </button>
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
    // $('#datetimepicker').datetimepicker({
    // 	lang:$.datetimepicker.setLocale('ch'),
    // });
    //表单验证
    $(function () {
        $('.skin-minimal input').iCheck({
            checkboxClass: 'icheckbox-blue',
            radioClass: 'iradio-blue',
            increaseArea: '20%'
        });
        $("#form-article-add").validate({
            rules: {
                articletitle: {
                    required: true,
                }
            },
            onkeyup: false,
            focusCleanup: true,
            success: "valid",
            submitHandler: function (form) {
                //$(form).ajaxSubmit();
                var index = parent.layer.getFrameIndex(window.name);
                //parent.$('.btn-refresh').click();
                parent.layer.close(index);
            }
        })

    });

    // 文件上传
    jQuery(function () {
        var $ = jQuery,
                $list = $('#thelist'),
                $btn = $('#ctlBtn'),
                state = 'pending',
                uploader;

        uploader = WebUploader.create({

            // 不压缩image
            resize: false,

            // swf文件路径
            // swf: BASE_URL + '/js/Uploader.swf',

            // 文件接收服务端。
            server: 'http://webuploader.duapp.com/server/fileupload.php',

            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#picker'
        });

        // 当有文件添加进来的时候
        uploader.on('fileQueued', function (file) {
            $list.append('<div id="' + file.id + '" class="item">' +
                    '<h4 class="info">' + file.name + '</h4>' +
                    '<p class="state">等待上传...</p>' +
                    '</div>');
        });

        // 文件上传过程中创建进度条实时显示。
        uploader.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),
                    $percent = $li.find('.progress .progress-bar');

            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<div class="progress progress-striped active">' +
                        '<div class="progress-bar" role="progressbar" style="width: 0%">' +
                        '</div>' +
                        '</div>').appendTo($li).find('.progress-bar');
            }

            $li.find('p.state').text('上传中');

            $percent.css('width', percentage * 100 + '%');
        });

        uploader.on('uploadSuccess', function (file) {
            $('#' + file.id).find('p.state').text('已上传');
        });

        uploader.on('uploadError', function (file) {
            $('#' + file.id).find('p.state').text('上传出错');
        });

        uploader.on('uploadComplete', function (file) {
            $('#' + file.id).find('.progress').fadeOut();
        });

        uploader.on('all', function (type) {
            if (type === 'startUpload') {
                state = 'uploading';
            } else if (type === 'stopUpload') {
                state = 'paused';
            } else if (type === 'uploadFinished') {
                state = 'done';
            }

            if (state === 'uploading') {
                $btn.text('暂停上传');
            } else {
                $btn.text('开始上传');
            }
        });

        $btn.on('click', function () {
            if (state === 'uploading') {
                uploader.stop();
            } else {
                uploader.upload();
            }
        });
    });


</script>
</body>
</html>
