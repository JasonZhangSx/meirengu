<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	<meta id="viewport" name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=0" />
	<meta name="format-detection" content="telephone=no" />
	<link rel="stylesheet" href="./hb_static/css/index.css" media="screen" charset="utf-8">
</head>
<body style='background-color:#fff;'>
	<script type="text/javascript">
	!function(e,t){var n=e.documentElement,i="orientationchange"in window?"orientationchange":"resize",d=(/iPad|iPhone|iPod/.test(navigator.userAgent)&&!window.MSStream,function(){var t=n.clientWidth;t&&(t>=750?(t=750,e.body.style.width="750px"):e.body.style.width=t+"px",n.style.fontSize=100*(t/750)+"px",n.dataset.width=t,n.dataset.percent=100*(t/750))});d(),e.documentElement.classList.add("iosx"+t.devicePixelRatio),e.addEventListener&&t.addEventListener(i,d,!1)}(document,window)
	</script>

	<div class="outSide_reg_cont">
		<div class="mask" style="display:none">
			<img src="hb_static/img/regiter_suc.png" style="width:5.58rem" alt="" />
		</div>
		<div class="wrapper">
			<header>
				<img src="hb_static/img/invite_banner.jpg" style="width:100%" alt="" />
			</header>
			<div class="content">
				<div class="coupon">
					<div class="left">
						抵扣券
					</div>
					<div class="right">
						<var>500</var>元现金
					</div>
				</div>
				<table>
					<tr>
						<th colspan="2">活动规则</th>
					</tr>
					<tr>
						<td style="width:.36rem;">1.</td><td>注册成功，即可获取500元现金抵扣券，可在个人中心，我的抵扣券查看。</td>
					</tr>
					<tr>
						<td>2.</td><td>抵扣券使用期限：发自放之日起2个月内有效。</td>
					</tr>
					<tr>
						<td>3.</td><td>活动期限：长期有效。</td>
					</tr>
				</table>
			</div>
		</div>
		<a href="javascript:void(0)" class="receive start" onClick="invokeA()" style="display:block;">
			领 取
		</a>
		<a href="javascript:void(0)" class="receive end">
			已领取
		</a>
  </div>


</body>
<script type="text/javascript" src="./hb_static/js/zepto.js"></script>
<script type="text/javascript">
	$(function(){
		// $('.receive.start').on('click',function(){
		//
		// 	// suc
		// 	alert('领取成功');
		// 	$(this).hide();
		// 	$('.receive.end').show()
		// })


	})
	function invokeA(){
		// suc
		alert('领取成功');
		$('.receive.start').hide();
		$('.receive.end').show()
	}
</script>
</html>
