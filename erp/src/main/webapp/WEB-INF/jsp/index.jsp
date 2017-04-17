<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
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
    <!--[if lt IE 9]>
    <script type="text/javascript" src="lib/html5.js"></script>
    <script type="text/javascript" src="lib/respond.min.js"></script>
    <![endif]-->
    <link rel=stylesheet type=text/css href="static/h-ui/css/H-ui.min.css" />
    <link rel=stylesheet type=text/css href="static/h-ui.admin/css/H-ui.admin.css" />
    <link rel=stylesheet type=text/css href="lib/Hui-iconfont/1.0.8/iconfont.css" />
    <link rel=stylesheet type=text/css href="static/h-ui.admin/skin/default/skin.css" id=skin />
    <link rel=stylesheet type=text/css href="static/h-ui.admin/css/style.css" />
    <!--[if IE 6]>
    <script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js"></script>
    <script>DD_belatedPNG.fix('*');</script><![endif]--> </head>
</html>
<title>index</title>
</head>
<body>
<!-- header -->
<header class=navbar-wrapper>
    <div class="navbar navbar-fixed-top" style=background-color:#44b549>
        <div class="container-fluid cl"><a class="logo navbar-logo f-l mr-10 hidden-xs"
                                           href="/aboutHui.shtml" >Meirengu-ERP</a> <a
                class="logo navbar-logo-m f-l mr-10 visible-xs" href="/aboutHui.shtml" >H-ui</a> <span
                class="logo navbar-slogan f-l mr-10 hidden-xs">v1.0.0</span> <a aria-hidden=false
                                                                                class="nav-toggle Hui-iconfont visible-xs"
                                                                                href=javascript:;>&#xe667;</a>
            <nav id=Hui-userbar class="nav navbar-nav navbar-userbar hidden-xs">
                <ul class=cl>
                    <li>管理员</li>
                    <li class="dropDown dropDown_hover"><a href=# class=dropDown_A>admin <i class=Hui-iconfont>
                        &#xe6d5;</i></a>
                        <ul class="dropDown-menu menu radius box-shadow">
                            <li><a href=#>个人信息</a></li>
                            <li><a href=#>切换账户</a></li>
                            <li><a href=#>退出</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</header>
<aside class=Hui-aside><input runat=server id=divScrollValue type=hidden value=""/>
    <div class="menu_dropdown bk_2">
        <dl id=menu-article>
            <dt><i class=Hui-iconfont style=font-size:20px>&#xe63a;</i> 众筹项目<i class="Hui-iconfont menu_dropdown-arrow">
                &#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a href=javascript:void(0) data-href="item/create_list" title=新建项目列表>新建项目列表</a></li>
                    <li><a href=javascript:void(0) data-href="item/verify_list" title=待初审项目>待初审项目</a></li>
                    <li><a href=javascript:void(0) data-href="item/cooperate_list" title=待合作项目>待合作项目</a></li>
                    <li><a href=javascript:void(0) data-href="item/review_list" title=待复审项目>待复审项目</a></li>
                    <li><a href=javascript:void(0) data-href="item/publish_list" title=待发布项目列表>待发布项目列表</a></li>
                    <li><a href=javascript:void(0) data-href="item/published_list" title=已发布项目列表>已发布项目列表</a></li>
                    <li><a href=javascript:void(0) data-href="item/completed_list" title=已完成项目>已完成项目</a></li>
                </ul>
            </dd>
        </dl>
        <dl id=menu-article>
            <dt><i class=Hui-iconfont style=font-size:20px>&#xe687;</i> 订单管理<i class="Hui-iconfont menu_dropdown-arrow">
                &#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a href=javascript:void(0) data-href="order_appointment/view" title=待审核预约订单>待审核预约订单</a></li>
                    <li><a href=javascript:void(0) data-href="order_candidate/view" title=候补预约列表>候补预约列表</a></li>
                    <li><a href=javascript:void(0) data-href="order/view" title=订单列表>订单列表</a></li>
                    <li><a href=javascript:void(0) data-href="refund/view" title=待审核退款订单>待审核退款订单</a></li>
                    <li><a href=javascript:void(0) data-href=订单-退款订单列表.html title=退款订单列表>退款订单列表</a></li>
                </ul>
            </dd>
        </dl>
        <dl id=menu-article>
            <dt><i class=Hui-iconfont style=font-size:20px>&#xe607;</i> 用户管理<i class="Hui-iconfont menu_dropdown-arrow">
                &#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a href=javascript:void(0) data-href=user/tolist title=项目列表>用户列表</a></li>
                    <li><a href=javascript:void(0) data-href=inviter/tolist title=项目列表>邀请记录</a></li>
                </ul>
            </dd>
        </dl>
        <dl id=menu-article>
            <dt><i class=Hui-iconfont style=font-size:20px>&#xe607;</i> 运营管理<i class="Hui-iconfont menu_dropdown-arrow">
                &#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a href=javascript:void(0) data-href=activity/tolist title=活动列表>活动列表</a></li>
                    <li><a href=javascript:void(0) data-href=运营-抵扣券查询.html title=抵扣券查询>抵扣券查询</a></li>
                </ul>
            </dd>
        </dl>
        <dl id=menu-article>
            <dt><i class=Hui-iconfont style=font-size:20px>&#xe62b;</i> 合作方管理<i
                    class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a href=javascript:void(0) data-href="partner/class/list" title=项目列表>行业分类管理</a></li>
                    <li><a href=javascript:void(0) data-href="partner/list" title=项目列表>合作方列表</a></li>
                </ul>
            </dd>
        </dl>
        <dl id=menu-article>
            <dt><i class=Hui-iconfont style=font-size:20px>&#xe6b5;</i> 财务管理<i class="Hui-iconfont menu_dropdown-arrow">
                &#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a href=javascript:void(0) data-href=财务-待打款列表.html title=待打款列表>待打款列表</a></li>
                    <li><a href=javascript:void(0) data-href=财务-打款记录.html title=打款记录>打款记录</a></li>
                    <li><a href=javascript:void(0) data-href=财务-待收款项列表.html title=代收款项列表>待收款项列表</a></li>
                    <li><a href=javascript:void(0) data-href=财务-收款记录.html title=收款记录>收款记录</a></li>
                    <li><a href=javascript:void(0) data-href=财务-放款分红记录.html title=放款分红记录>放款分红记录</a></li>
                    <li><a href=javascript:void(0) data-href=财务-支付通道管理.html title=放款分红记录>支付通道管理</a></li>
                    <li><a href=javascript:void(0) data-href=record/rechargeRecord title=用户充值记录>用户充值记录</a></li>
                    <li><a href=javascript:void(0) data-href=record/withdrawalsRecord title=用户提现记录>用户提现记录</a></li>
                    <li><a href=javascript:void(0) data-href=财务-抵扣券核销.html title=放款分红记录>抵扣券核销</a></li>
                </ul>
            </dd>
        </dl>
        <dl id=menu-article>
            <dt><i class=Hui-iconfont style=font-size:20px>&#xe60c;</i> CMS管理系统<i
                    class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a href=javascript:void(0) data-href=CMS-轮播图管理.html title=轮播图管理>CMS-轮播图管理</a></li>
                    <li><a href=javascript:void(0) data-href=CMS-公告列表.html title=公告列表>CMS-公告列表</a></li>
                    <li><a href=javascript:void(0) data-href=CMS-常见问题分类.html title=常见问题分类>CMS-常见问题分类</a></li>
                    <li><a href=javascript:void(0) data-href=CMS-常见问题列表.html title=常见问题列表>CMS-常见问题列表</a></li>
                    <li><a href=javascript:void(0) data-href=CMS-意见反馈列表.html title=意见反馈列表>CMS-意见反馈列表</a></li>
                </ul>
            </dd>
        </dl>
        <dl id=menu-article>
            <dt><i class=Hui-iconfont style=font-size:20px>&#xe61d;</i> 系统设置<i class="Hui-iconfont menu_dropdown-arrow">
                &#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a href=javascript:void(0) data-href=系统-操作员管理.html title=操作员管理>操作员管理</a></li>
                    <li><a href=javascript:void(0) data-href=系统-角色管理.html title=角色管理>角色管理</a></li>
                    <li><a href=javascript:void(0) data-href=系统-部门管理.html title=部门管理>部门管理</a></li>
                </ul>
            </dd>
        </dl>
    </div>
</aside>
<div class="dislpayArrow hidden-xs"><a class=pngfix href=javascript:void(0) data-href=javascript:void(0);
                                       onclick=displaynavbar(this)></a></div>
<!-- wrapper -->
<section class="Hui-article-box">
    <iframe src="item/create_list" width="100%" class="content_iframe" height="100%" scrolling="yes"></iframe>
</section>
<%--<section class="Hui-article-box">
	<nav class="breadcrumb"><i class="Hui-iconfont"></i> <a href="/" class="maincolor">首页</a> <span class="c-999 en">&gt;</span><span class="c-666">我的桌面</span> <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div class="Hui-article">
		<article class="cl pd-20">
			<p class="f-20 text-success">欢迎回来！</p>
			<p>登录次数：18 </p>
			<p>上次登录IP：222.35.131.79.1  上次登录时间：2014-6-14 11:19:55</p>

		</article>

	</div>
</section>--%>
<!-- footer -->
<script src="lib/jquery/1.9.1/jquery.min.js" ></script>
<script src="lib/layer/2.4/layer.js" ></script>
<script src="lib/jquery.validation/1.14.0/jquery.validate.js" ></script>
<script src="lib/jquery.validation/1.14.0/validate-methods.js" ></script>
<script src="lib/jquery.validation/1.14.0/messages_zh.js" ></script>
<script src="static/h-ui/js/H-ui.js" ></script>
<script src="static/h-ui.admin/js/H-ui.admin.page.js" ></script>
<script>$(function () {
    $(".Hui-aside ul a").on("click", function () {
        console.log($(this).attr("data-href")), $(".content_iframe").attr("src", $(this).attr("data-href"))
    })
})</script>
</body>
</html>
