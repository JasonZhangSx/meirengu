<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>美人谷</title>
	<meta id="viewport" name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=0" />
	<meta name="format-detection" content="telephone=no" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/index.css" media="screen" charset="utf-8">
</head>
<body style='background-color:#fff;'>
	<script type="text/javascript">
	!function(e,t){var n=e.documentElement,i="orientationchange"in window?"orientationchange":"resize",d=(/iPad|iPhone|iPod/.test(navigator.userAgent)&&!window.MSStream,function(){var t=n.clientWidth;t&&(t>=750?(t=750,e.body.style.width="750px"):e.body.style.width=t+"px",n.style.fontSize=100*(t/750)+"px",n.dataset.width=t,n.dataset.percent=100*(t/750))});d(),e.documentElement.classList.add("iosx"+t.devicePixelRatio),e.addEventListener&&t.addEventListener(i,d,!1)}(document,window)
	</script>

	<div class="outSide_reg_cont">
		<div class="mask" style="display:none">
			<img src="<%=request.getContextPath()%>/img/regiter_suc.png" style="width:5.58rem" alt="" />
		</div>
		<div class="wrapper">
			<header>
				<img src="<%=request.getContextPath()%>/img/invite_banner.jpg" style="width:100%" alt="" />
			</header>
			<div class="content">
				<form action="<%=request.getContextPath()%>/invite/register" method="post" accept-charset="UTF-8" name="inviteRigsterForm" id="inviteRigsterForm">
					<input type="text" class="tel" placeholder="请输入注册手机号码" maxlength="11" name="register_phone" value="" />
					<input type="hidden" name="inviter_phone" value="${inviterPhone}" />
				</form>

				<div class="err_msg">
					<c:if test="${not empty jsonObject}">
						<c:choose>
							<c:when test="${jsonObject.code == 200}">领取成功，可在个人中心，我的抵扣券查看。</c:when>
							<c:when test="${jsonObject.code == 40016}">手机格式错误，请输入正确格式。</c:when>
							<c:when test="${jsonObject.code == 50109}">邀请人不存在。</c:when>
							<c:when test="${jsonObject.code == 50107}">您已经注册领取，可在个人中心，我的抵扣券查看。</c:when>
							<c:otherwise>真不巧，服务器开小差了，请稍后再试！</c:otherwise>
						</c:choose>
					</c:if>
				</div>
				<button type="button" name="button" class="register">
					立即领取500元现金券
				</button>
				<p class="border_t1">
					<img src="<%=request.getContextPath()%>/img/find_icon5.png" style="width:.33rem" alt="" /><br/>
					<b>美人谷是什么？</b>
					美人谷是一个新型的众筹平台，专注于医美健康领域，<br/>致力于缔造理财生活新体验。
				</p>
				<span class="span1">
					<var>
						<img src="<%=request.getContextPath()%>/img/register_icon1.png" style="width:.5rem" alt="" />更贴心
					</var>
					<em>
						全新医美体验，<br/>美丽不花钱。
					</em>
				</span>
				<span class="span2">
					<var>
						<img src="<%=request.getContextPath()%>/img/register_icon2.png" style="width:.46rem" alt="" />高收益
					</var>
					<em>
						预期年化收益率<br/>8% - 15%。
					</em>
				</span>
				<span class="span3">
					<var>
						<img src="<%=request.getContextPath()%>/img/register_icon3.png" style="width:.4rem" alt="" />更安全
					</var>
					<em>
						6大保障体系，<br/>资金安全不用愁。
					</em>
				</span>
			</div>
		</div>
  </div>


</body>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/zepto.js"></script>
<script type="text/javascript">
function emp_test(a){return 0==a.replace(/^\s+|\s+$/g,"").length?!1:!0};
$('.register').on('click',function(){
	$('.err_msg').empty();
	var tel = $('input.tel');
	if(!emp_test(tel.val())){
		$('.err_msg').empty().text('* 手机号码不能为空');
		return false;
	}else if(!(/^1(3|4|5|7|8)\d{9}$/.test(tel.val()))){
		$('.err_msg').empty().text('* 手机号码格式不正确')
		return false;
	}else{
		document.getElementById("inviteRigsterForm").submit();
		//$('.mask').show();
	}
})
$('.mask').on('click',function(){
	$(this).hide();
})
$('.mask img').on('click',function(e){
	e.stopPropagation()
})
</script>
</html>
