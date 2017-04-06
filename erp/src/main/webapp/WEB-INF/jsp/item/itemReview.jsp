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
    <link rel=stylesheet type=text/css href="static/h-ui/css/H-ui.min.css" />
    <link rel=stylesheet type=text/css href="static/h-ui.admin/css/H-ui.admin.css" />
    <link rel=stylesheet type=text/css href="lib/Hui-iconfont/1.0.8/iconfont.css" />
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
    .huibao_set input[type="text"], .huibao_set select, .huibao_set textarea, .huibao_set option, .huibao_set .select-box,
    .hezuo_style input[type="text"], .hezuo_style select, .hezuo_style textarea, .hezuo_style option, .hezuo_style .select-box {
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
                    <input type="text" class="input-text" value="" disabled id="articletitle" name="articletitle"
                           maxlength="30" placeholder="项目标题最多30字" id="" name="">
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">项目简介：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <textarea name="" cols="" rows="" class="textarea" placeholder="..." datatype="*10-100"
                              dragonfly="true" nullmsg="备注不能为空！" onKeyUp="$.Huitextarealength(this,200)"></textarea>
                    <p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">众筹类型：</label>
                <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
					<select name="" class="select" disabled>
						<option value="0">一级分类</option>
						<option value="1">一级分类</option>
						<option value="11">├二级分类</option>
						<option value="12">├二级分类</option>
						<option value="13">├二级分类</option>
					</select>
					</span>
                </div>
                <label class="form-label col-xs-4 col-sm-2">项目分类：</label>
                <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
					<select name="" class="select">
						<option value="0">一级分类</option>
						<option value="1">一级分类</option>
						<option value="11">├二级分类</option>
						<option value="12">├二级分类</option>
						<option value="13">├二级分类</option>
					</select>
					</span>
                </div>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">目标金额：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    <input type="text" class="input-text" value="" placeholder="" id="" name="">
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
                            <div class="portfoliobox">
                                <div class="picbox l mr-10">
                                    <a href="temp/big/beauty.jpg" data-lightbox="gallery" data-title="项目头图"><img
                                            src="temp/Thumb/shufang.jpg"></a>
                                </div>
                                <div class="picbox l mr-10">
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
        </div>


        <!-- 复审审核 -->
        <div class="fushen">
            <div class="row cl">
                <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">复审审核</h3>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2"></label>
                <label class="col-xs-4 col-sm-1 mr-20"><input type="radio" class="radio" value="1" name="radio-feedback"
                                                              id="feedback-1"> 通过</label>
                <label><input type="radio" class="radio" value="2" name="radio-feedback" id="feedback-2"> 不通过</label>
            </div>
            <div class="row cl">
                <label class="form-label col-xs-4 col-sm-2">备注：</label>
                <div class="formControls col-xs-8 col-sm-8">
                    <textarea name="" cols="" rows="" class="textarea" placeholder="..." datatype="*10-100"
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
    // tab切换
    $(function () {
        $.Huitab(".huibao_set .huibao_tab_menu span", ".huibao_set .huibao_tab", "current", "click", "0")
        $.Huitab(".tabBar span", ".tabCon", "current", "click", "0")
    });
    $('#datetimepicker').datetimepicker({
        lang: $.datetimepicker.setLocale('ch'),
    });
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

        $list = $("#fileList"),
                $btn = $("#btn-star"),
                state = "pending",
                uploader;

        var uploader = WebUploader.create({
            auto: true,
            swf: 'lib/webuploader/0.1.5/Uploader.swf',

            // 文件接收服务端。
            server: 'http://lib.h-ui.net/webuploader/0.1.5/server/fileupload.php',

            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#filePicker',

            // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            resize: false,
            // 只允许选择图片文件。
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }
        });
        uploader.on('fileQueued', function (file) {
            var $li = $(
                            '<div id="' + file.id + '" class="item">' +
                            '<div class="pic-box"><img></div>' +
                            '<div class="info">' + file.name + '</div>' +
                            '<p class="state">等待上传...</p>' +
                            '</div>'
                    ),
                    $img = $li.find('img');
            $list.append($li);

            // 创建缩略图
            // 如果为非图片文件，可以不用调用此方法。
            // thumbnailWidth x thumbnailHeight 为 100 x 100
            uploader.makeThumb(file, function (error, src) {
                if (error) {
                    $img.replaceWith('<span>不能预览</span>');
                    return;
                }

                $img.attr('src', src);
            }, thumbnailWidth, thumbnailHeight);
        });
        // 文件上传过程中创建进度条实时显示。
        uploader.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),
                    $percent = $li.find('.progress-box .sr-only');

            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<div class="progress-box"><span class="progress-bar radius"><span class="sr-only" style="width:0%"></span></span></div>').appendTo($li).find('.sr-only');
            }
            $li.find(".state").text("上传中");
            $percent.css('width', percentage * 100 + '%');
        });

        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploader.on('uploadSuccess', function (file) {
            $('#' + file.id).addClass('upload-state-success').find(".state").text("已上传");
        });

        // 文件上传失败，显示上传出错。
        uploader.on('uploadError', function (file) {
            $('#' + file.id).addClass('upload-state-error').find(".state").text("上传出错");
        });

        // 完成上传完了，成功或者失败，先删除进度条。
        uploader.on('uploadComplete', function (file) {
            $('#' + file.id).find('.progress-box').fadeOut();
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

    (function ($) {


    })(jQuery);


</script>
</body>
</html>

