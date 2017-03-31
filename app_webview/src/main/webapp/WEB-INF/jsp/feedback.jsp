<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	<meta id="viewport" name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=0" />
	<meta name="format-detection" content="telephone=no" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/index.css" media="screen" charset="utf-8">
</head>
<body>
	<script type="text/javascript">
	!function(e,t){var n=e.documentElement,i="orientationchange"in window?"orientationchange":"resize",d=(/iPad|iPhone|iPod/.test(navigator.userAgent)&&!window.MSStream,function(){var t=n.clientWidth;t&&(t>=750?(t=750,e.body.style.width="750px"):e.body.style.width=t+"px",n.style.fontSize=100*(t/750)+"px",n.dataset.width=t,n.dataset.percent=100*(t/750))});d(),e.documentElement.classList.add("iosx"+t.devicePixelRatio),e.addEventListener&&t.addEventListener(i,d,!1)}(document,window)
	</script>

	<div class="findFeedBack_cont">
    <div class="wrapper">
        <form name="feedbackForm" action="<%=request.getContextPath()%>/feedback" method="post" accept-charset="UTF-8">
        <div class="p-r">
            <textarea name="feedback_content" rows="6" cols="40" onkeyup="$.textarealength(this,150)" placeholder="请输入您的问题，我们会第一时间为您解决。"></textarea>
            <p class="textarea-numberbar"><em class="textarea-length">0</em> / 150</p>
        </div>
        <a href="javascript:document.feedbackForm.submit();">提 交</a>
        </form>
    </div>
  </div>


</body>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/zepto.js"></script>
<script>
!function($) {
  $.textarealength = function(obj, maxlength) {
    var v = $(obj).val();
    var l = v.length;
    if (l > maxlength) {
      v = v.substring(0, maxlength);
      $(obj).val(v);
    }
    $(obj).parent().find(".textarea-length").text(v.length);
  }
} ($);
</script>
</html>
