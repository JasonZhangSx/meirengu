<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
                                           href="/aboutHui.shtml" >美融城管理系统</a> <a
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
        <c:forEach var="item" items="${accounts.permissionsList}" varStatus="status">
            <dl id=menu-article>
                <dt><i class=Hui-iconfont style=font-size:20px>&#xe63a;</i> ${item.name}<i class="Hui-iconfont menu_dropdown-arrow">
                    &#xe6d5;</i></dt>
                <dd>
                    <ul>
                        <c:forEach var="itemList" items="${item.list}" varStatus="status">
                            <li><a href=javascript:void(0) data-href="${itemList.value}" title= ${itemList.name}> ${itemList.name}</a></li>
                        </c:forEach>
                    </ul>
                </dd>
            </dl>
        </c:forEach>
    </div>
</aside>
<div class="dislpayArrow hidden-xs"><a class=pngfix href=javascript:void(0) data-href=javascript:void(0);
                                       onclick=displaynavbar(this)></a></div>
<!-- wrapper -->
<section class="Hui-article-box">
    <iframe src="blank.html" width="100%" class="content_iframe" height="100%" scrolling="yes"></iframe>
</section>
<%--<section class="Hui-article-box">
	<nav class="breadcrumb"><i class="Hui-iconfont"></i> <a href="/" class="maincolor">首页</a> <span class="c-999 en">&gt;</span><span class="c-666">我的桌面</span> <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" ><i class="Hui-iconfont">&#xe68f;</i></a></nav>
	<div class="Hui-article">
		<article class="cl pd-20">
			<p class="f-20 text-success" style="text-align: center;">欢迎登录美融城管理系统！</p>
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
