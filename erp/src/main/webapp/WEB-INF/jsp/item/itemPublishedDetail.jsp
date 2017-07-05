<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset=utf-8>
    <meta name=renderer content=webkit|ie-comp|ie-stand>
    <meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
    <meta name=viewport content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <meta http-equiv=Cache-Control content=no-siteapp/>
    <link rel=Bookmark href=favicon.ico>
    <link rel="Shortcut Icon" href=favicon.ico/>
    <meta name=keywords content=xxxxx>
    <meta name=description content=xxxxx>
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
    <div class="tabBar">
        <span class="current">项目信息</span><span>众筹信息</span>
    </div>
    <div class="tabCon" style="display:block;">
        <form action="" method="post" class="form form-horizontal" id="form-article-add">
            <!-- 基本信息 -->
            <div class="jiben_info">
                <div class="row cl">
                    <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">基本信息</h3>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">项目名称：</label>
                    <div class="formControls col-xs-8 col-sm-8">
                        ${item.itemName}
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">项目简介：</label>
                    <div class="formControls col-xs-8 col-sm-8">
                        ${item.itemProfile}
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">众筹类型：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${item.className}
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">项目分类：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${item.typeName}
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">目标金额：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${item.targetAmount}
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">预热天数：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${item.preheatingDays}
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
                        ${item.crowdDays}
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2" style="line-height:30px;">项目地区：</label>
                    <div class="formControls col-xs-8 col-sm-9">
                        ${item.privince} ${item.city}
                    </div>
                </div>

                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">项目头图：</label>
                    <div class="formControls col-xs-8 col-sm-8">
                        <!-- 审核模式 查看图 -->
                        <div class="portfoliobox">
                            <div class="picbox">
                                <a href="<%=imgPath%>${item.headerImage}" data-lightbox="gallery" data-title="项目头图"><img
                                        src="<%=imgPath%>${item.headerImage}?x-oss-process=image/resize,m_lfit,h_200,w_200"></a>
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
                <c:forEach items="${content}" var="content">
                    <div class="row cl">
                        <label class="form-label col-xs-4 col-sm-2">主标题：</label>
                        <div class="formControls col-xs-8 col-sm-8">
                            ${content.contentTitle}
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
                </c:forEach>
            </div>
            <!-- 回报信息 -->
            <div class="huibao_set">
                <div class="row cl">
                    <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">回报信息</h3>
                </div>
                <c:forEach var="level" items="${level}">
                    <div class="huibao_tab">
                        <div class="row cl" style="display:block">
                            <label class="form-label col-xs-4 col-sm-2">档位名称：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                ${level.levelName}
                            </div>
                            <label class="form-label col-xs-4 col-sm-2">支持金额：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                ${level.levelAmount}
                            </div>
                        </div>
                        <div class="row cl">
                            <label class="form-label col-xs-4 col-sm-2">回报描述：</label>
                            <div class="formControls col-xs-8 col-sm-8">
                                ${level.levelDesc}
                            </div>
                        </div>

                        <div class="row cl">
                            <label class="form-label col-xs-4 col-sm-2">总份数：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                ${level.totalNumber}份
                            </div>
                            <label class="form-label col-xs-4 col-sm-2">单人限额：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                ${level.singleLimitNumber}
                            </div>
                        </div>
                        <div class="row cl">
                            <label class="form-label col-xs-4 col-sm-2">回报时间：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                ${level.paybackDays}
                            </div>
                            <label class="form-label col-xs-4 col-sm-2">是否分红：</label>
                            <div class="formControls col-xs-8 col-sm-3">
                                <c:if test="${level.isShareBonus == 1}">是</c:if>
                                <c:if test="${level.isShareBonus == 0}">否</c:if>
                            </div>
                        </div>
                        <c:if test="${level.isShareBonus == 1}">
                            <div class="row cl">
                                <label class="form-label col-xs-4 col-sm-2">年化利率：</label>
                                <div class="formControls col-xs-8 col-sm-3">
                                        ${level.yearRate} %
                                </div>
                                <label class="form-label col-xs-4 col-sm-2">投资期限：</label>
                                <div class="formControls col-xs-8 col-sm-3">
                                        ${level.investmentPeriod} 月
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
                                    ${level.shareBonusPeriod} 月
                                </div>
                            </div>
                        </c:if>

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
                        ${cooperate.commissionRate} %
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">保证金：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${cooperate.guaranteeRate} %
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">预付分红金：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${cooperate.prepaidBonus} 期
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">放款方式：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <c:if test="${cooperate.loanMode == 1}">一次性</c:if>
                        <c:if test="${cooperate.loanMode == 2}">两次放款</c:if>
                    </div>
                    <c:if test="${cooperate.loanMode == 2}">
                        <label class="form-label col-xs-4 col-sm-2">首款比例：</label>
                        <div class="formControls col-xs-8 col-sm-3">
                            ${cooperate.firstRatio} %
                        </div>
                    </c:if>
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
                            <th>资质文件</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr class="text-c">
                            <td>发起人身份证正反面</td>
                            <td class="text-l">
                                <c:if test="${not empty cooperate.sponsorIdcard}">
                                    <c:forEach items="${fn:split(cooperate.sponsorIdcard, ',')}" var="imgs">
                                        <!-- 审核模式 查看图 -->
                                        <div class="portfoliobox" style="float: left;">
                                            <div class="picbox">
                                                <a href="${imageUrl}${imgs}" data-lightbox="gallery">
                                                    <img src="${imageUrl}${imgs}?x-oss-process=image/resize,m_lfit,h_100,w_100" />
                                                </a>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>发起人征信报告</td>
                            <td class="text-l">
                                <c:if test="${not empty cooperate.sponsorCredit}">
                                    <c:forEach items="${fn:split(cooperate.sponsorCredit, ',')}" var="imgs">
                                        <!-- 审核模式 查看图 -->
                                        <div class="portfoliobox" style="float: left;">
                                            <div class="picbox">
                                                <a href="${imageUrl}${imgs}" data-lightbox="gallery">
                                                    <img src="${imageUrl}${imgs}?x-oss-process=image/resize,m_lfit,h_100,w_100" />
                                                </a>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>企业法人身份证正反面</td>
                            <td class="text-l">
                                <c:if test="${not empty cooperate.principalIdcard}">
                                    <c:forEach items="${fn:split(cooperate.principalIdcard, ',')}" var="imgs">
                                        <!-- 审核模式 查看图 -->
                                        <div class="portfoliobox" style="float: left;">
                                            <div class="picbox">
                                                <a href="${imageUrl}${imgs}" data-lightbox="gallery">
                                                    <img src="${imageUrl}${imgs}?x-oss-process=image/resize,m_lfit,h_100,w_100" />
                                                </a>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>企业法人征信报告</td>
                            <td class="text-l">
                                <c:if test="${not empty cooperate.principalCredit}">
                                    <c:forEach items="${fn:split(cooperate.principalCredit, ',')}" var="imgs">
                                        <!-- 审核模式 查看图 -->
                                        <div class="portfoliobox" style="float: left;">
                                            <div class="picbox">
                                                <a href="${imageUrl}${imgs}" data-lightbox="gallery">
                                                    <img src="${imageUrl}${imgs}?x-oss-process=image/resize,m_lfit,h_100,w_100" />
                                                </a>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>营业执照</td>
                            <td class="text-l">
                                <c:if test="${not empty cooperate.businessLicence}">
                                    <c:forEach items="${fn:split(cooperate.businessLicence, ',')}" var="imgs">
                                        <!-- 审核模式 查看图 -->
                                        <div class="portfoliobox" style="float: left;">
                                            <div class="picbox">
                                                <a href="${imageUrl}${imgs}" data-lightbox="gallery">
                                                    <img src="${imageUrl}${imgs}?x-oss-process=image/resize,m_lfit,h_100,w_100" />
                                                </a>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>场地/土地租赁协议</td>
                            <td class="text-l">
                                <c:if test="${not empty cooperate.venueRentalAgreement}">
                                    <c:forEach items="${fn:split(cooperate.venueRentalAgreement, ',')}" var="imgs">
                                        <!-- 审核模式 查看图 -->
                                        <div class="portfoliobox" style="float: left;">
                                            <div class="picbox">
                                                <a href="${imageUrl}${imgs}" data-lightbox="gallery">
                                                    <img src="${imageUrl}${imgs}?x-oss-process=image/resize,m_lfit,h_100,w_100" />
                                                </a>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>新店装修效果图</td>
                            <td class="text-l">
                                <c:if test="${not empty cooperate.storeRenderings}">
                                    <c:forEach items="${fn:split(cooperate.storeRenderings, ',')}" var="imgs">
                                        <!-- 审核模式 查看图 -->
                                        <div class="portfoliobox" style="float: left;">
                                            <div class="picbox">
                                                <a href="${imageUrl}${imgs}" data-lightbox="gallery">
                                                    <img src="${imageUrl}${imgs}?x-oss-process=image/resize,m_lfit,h_100,w_100" />
                                                </a>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>公司章程</td>
                            <td class="text-l">
                                <c:if test="${not empty cooperate.corporateArticles}">
                                    <c:forEach items="${fn:split(cooperate.corporateArticles, ',')}" var="imgs">
                                        <!-- 审核模式 查看图 -->
                                        <div class="portfoliobox" style="float: left;">
                                            <div class="picbox">
                                                <a href="${imageUrl}${imgs}" data-lightbox="gallery">
                                                    <img src="${imageUrl}${imgs}?x-oss-process=image/resize,m_lfit,h_100,w_100" />
                                                </a>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>近一年流水，资产负债表</td>
                            <td class="text-l">
                                <c:if test="${not empty cooperate.assetWaterLiabilities}">
                                    <c:forEach items="${fn:split(cooperate.assetWaterLiabilities, ',')}" var="imgs">
                                        <!-- 审核模式 查看图 -->
                                        <div class="portfoliobox" style="float: left;">
                                            <div class="picbox">
                                                <a href="${imageUrl}${imgs}" data-lightbox="gallery">
                                                    <img src="${imageUrl}${imgs}?x-oss-process=image/resize,m_lfit,h_100,w_100" />
                                                </a>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
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
                        ${cooperate.shareholderName}
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">股东身份证号：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${cooperate.shareholderIdcard}
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">股东地址：</label>
                    <div class="formControls col-xs-8 col-sm-8">
                        ${cooperate.shareholderAddress}
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">担保人姓名：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${cooperate.guaranteeName}
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">担保人身份证号：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${cooperate.guaranteeIdcard}
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">担保人地址：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${cooperate.guaranteeAddress}
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-8 col-sm-8  col-sm-offset-1" style="text-align:left;">投资人每投资100万元，股东向管理公司质押不低于
                        ${cooperate.pledgedShares}%的股份</label>

                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">项目方印章：</label>
                    <div class="formControls col-xs-8 col-sm-8">
                        <div class="portfoliobox">
                            <div class="picbox">
                                <a href="<%=imgPath%>${cooperate.partnerSeal}" data-lightbox="gallery" data-title="项目方印章"><img
                                        src="<%=imgPath%>${cooperate.partnerSeal}?x-oss-process=image/resize,m_lfit,h_200,w_200"></a>
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
                            <th>资质文件</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr class="text-c">
                            <td>托管协议书</td>
                            <td class="text-l">
                                <c:if test="${not empty cooperate.escrowAgreement}">
                                    <c:forEach items="${fn:split(cooperate.escrowAgreement, ',')}" var="imgs">
                                        <!-- 审核模式 查看图 -->
                                        <div class="portfoliobox" style="float: left;">
                                            <div class="picbox">
                                                <a href="${imageUrl}${imgs}" data-lightbox="gallery">
                                                    <img src="${imageUrl}${imgs}?x-oss-process=image/resize,m_lfit,h_100,w_100" />
                                                </a>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>融资服务协议</td>
                            <td class="text-l">
                                <c:if test="${not empty cooperate.financeService}">
                                    <c:forEach items="${fn:split(cooperate.financeService, ',')}" var="imgs">
                                        <!-- 审核模式 查看图 -->
                                        <div class="portfoliobox" style="float: left;">
                                            <div class="picbox">
                                                <a href="${imageUrl}${imgs}" data-lightbox="gallery">
                                                    <img src="${imageUrl}${imgs}?x-oss-process=image/resize,m_lfit,h_100,w_100" />
                                                </a>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>融资管理协议</td>
                            <td class="text-l">
                                <c:if test="${not empty cooperate.financeManage}">
                                    <c:forEach items="${fn:split(cooperate.financeManage, ',')}" var="imgs">
                                        <!-- 审核模式 查看图 -->
                                        <div class="portfoliobox" style="float: left;">
                                            <div class="picbox">
                                                <a href="${imageUrl}${imgs}" data-lightbox="gallery">
                                                    <img src="${imageUrl}${imgs}?x-oss-process=image/resize,m_lfit,h_100,w_100" />
                                                </a>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>股权质押合同</td>
                            <td class="text-l">
                                <c:if test="${not empty cooperate.sharePledgeAgreement}">
                                    <c:forEach items="${fn:split(cooperate.sharePledgeAgreement, ',')}" var="imgs">
                                        <!-- 审核模式 查看图 -->
                                        <div class="portfoliobox" style="float: left;">
                                            <div class="picbox">
                                                <a href="${imageUrl}${imgs}" data-lightbox="gallery">
                                                    <img src="${imageUrl}${imgs}?x-oss-process=image/resize,m_lfit,h_100,w_100" />
                                                </a>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </td>
                        </tr>
                        <tr class="text-c">
                            <td>保证合同</td>
                            <td class="text-l">
                                <c:if test="${not empty cooperate.guarantyAgreement}">
                                    <c:forEach items="${fn:split(cooperate.guarantyAgreement, ',')}" var="imgs">
                                        <!-- 审核模式 查看图 -->
                                        <div class="portfoliobox" style="float: left;">
                                            <div class="picbox">
                                                <a href="${imageUrl}${imgs}" data-lightbox="gallery">
                                                    <img src="${imageUrl}${imgs}?x-oss-process=image/resize,m_lfit,h_100,w_100" />
                                                </a>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
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

        </form>
    </div>
    <div class="tabCon">
        <div class="row cl">
            <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">众筹信息</h3>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">目标金额：</label>
            <div class="formControls col-xs-8 col-sm-3">
                ${item.targetAmount}
            </div>
            <c:if test="${item.itemStatus == 10}">
                <label class="form-label col-xs-4 col-sm-2">已约金额：</label>
                <div class="formControls col-xs-8 col-sm-3">
                        ${item.appointAmount}
                </div>
            </c:if>
            <c:if test="${item.itemStatus != 10}">
                <label class="form-label col-xs-4 col-sm-2">已筹金额：</label>
                <div class="formControls col-xs-8 col-sm-3">
                        ${item.completedAmount}
                </div>
            </c:if>
        </div>
        <div class="row cl">
            <c:if test="${item.itemStatus == 10}">
                <label class="form-label col-xs-4 col-sm-2">完成度：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    ${item.appointPercent*100}%
                </div>
            </c:if>
            <c:if test="${item.itemStatus != 10}">
                <label class="form-label col-xs-4 col-sm-2">完成度：</label>
                <div class="formControls col-xs-8 col-sm-3">
                    ${item.completedPercent*100}%
                </div>
            </c:if>

            <label class="form-label col-xs-4 col-sm-2">剩余天数：</label>
            <div class="formControls col-xs-8 col-sm-3">
                ${item.leavelDay}天
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">预热时间：</label>
            <div class="formControls col-xs-8 col-sm-8">
                ${item.preheatingStartTime}至${item.preheatingEndTime}
                <%--<jsp:setProperty name="dateValue4" property="time" value="${item.preheatingStartTime}"/>
                <fmt:formatDate value="${dateValue4}" pattern="yyyy/MM/dd HH:mm:ss"/>
                至
                <jsp:setProperty name="dateValue1" property="time" value="${item.preheatingEndTime}"/>
                <fmt:formatDate value="${dateValue1}" pattern="yyyy/MM/dd HH:mm:ss"/>--%>
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">认筹时间：</label>
            <div class="formControls col-xs-8 col-sm-8">
                ${item.crowdStartTime}至${item.crowdEndTime}
                <%--<jsp:setProperty name="dateValue2" property="time" value="${item.crowdStartTime}"/>
                <fmt:formatDate value="${dateValue2}" pattern="yyyy/MM/dd HH:mm:ss"/>
                 至
                <jsp:setProperty name="dateValue3" property="time" value="${item.crowdEndTime}"/>
                <fmt:formatDate value="${dateValue3}" pattern="yyyy/MM/dd HH:mm:ss"/>--%>
            </div>
        </div>

        <div class="row cl">
            <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">支持记录（共计120人）</h3>
        </div>
        <div class="col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">
            <table class="table table-border table-bordered table-bg table-hover table-sort">
                <thead>
                <tr class="text-c">
                    <th>订单编号</th>
                    <th>账号</th>
                    <th>档位</th>
                    <th>份数</th>
                    <th>总额</th>
                    <th>支持时间</th>
                </tr>
                </thead>
                <tbody>
                <tr class="text-c">
                    <td>MR1231312</td>
                    <td>185111111123</td>
                    <td>5万元档</td>
                    <td>2</td>
                    <td>20,000.00</td>
                    <td>2017-03-02 15:33:42</td>
                </tr>
                <tr class="text-c">
                    <td>MR1231312</td>
                    <td>185111111123</td>
                    <td>5万元档</td>
                    <td>2</td>
                    <td>20,000.00</td>
                    <td>2017-03-02 15:33:42</td>
                </tr>
                <tr class="text-c">
                    <td>MR1231312</td>
                    <td>185111111123</td>
                    <td>5万元档</td>
                    <td>2</td>
                    <td>20,000.00</td>
                    <td>2017-03-02 15:33:42</td>
                </tr>
                </tbody>
            </table>
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

</script>
</body>
</html>

