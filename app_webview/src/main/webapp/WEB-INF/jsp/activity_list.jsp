<%@ page language="java" contentType="text/html;charset=UTF-8" import="com.meirengu.webview.utils.ConfigUtil" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String imgURI = ConfigUtil.getConfig("URI_IMG_PREFIX");
%>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>活动</title>
	<meta id="viewport" name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=0" />
	<meta name="format-detection" content="telephone=no" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/index.css" media="screen" charset="utf-8">
</head>
<body style='background-color:#f2f2f2'>
	<script type="text/javascript">
	!function(e,t){var n=e.documentElement,i="orientationchange"in window?"orientationchange":"resize",d=(/iPad|iPhone|iPod/.test(navigator.userAgent)&&!window.MSStream,function(){var t=n.clientWidth;t&&(t>=750?(t=750,e.body.style.width="750px"):e.body.style.width=t+"px",n.style.fontSize=100*(t/750)+"px",n.dataset.width=t,n.dataset.percent=100*(t/750))});d(),e.documentElement.classList.add("iosx"+t.devicePixelRatio),e.addEventListener&&t.addEventListener(i,d,!1)}(document,window)
    var flag = "";
    var mobile = "";
    function showInfoFromAppWithFlag(msg){
        console.log("来自App的消息: "+flag);
        flag = msg;
    }

    function showInfoFromAppWithMsg(msg){
        console.log("来自App的消息: "+msg);
        mobile = msg;
    }
	</script>

	<div class="findHotAct_cont">
    <div class="wrapper">
      <c:if test="${not empty list}">
        <c:forEach items="${list}" var="activity">
          <div class="item">
            <div class="top">
              <a href="${activity.activityLink}">
                <img src="<%=imgURI %>${activity.activityImage}" width="100%" alt="" />
                <span class="tit">进行中</span>
              </a>
            </div>
            <div class="txt">
              <h4>${activity.activityName} <img src="<%=request.getContextPath()%>/img/hot_icon.png" style="width:.6rem" alt="" /></h4>
            </div>
          </div>
        </c:forEach>
      </c:if>
      <c:if test="${empty list}">
          <div class="text-c rule">
              暂无活动内容！
          </div>
      </c:if>
    </div>
  </div>


</body>

</html>
