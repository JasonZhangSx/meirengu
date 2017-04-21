<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	<meta id="viewport" name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=0" />
	<meta name="format-detection" content="telephone=no" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/index.css" media="screen" charset="utf-8">
</head>
<body style='background-color:#f2f2f2'>
	<script type="text/javascript">
	!function(e,t){var n=e.documentElement,i="orientationchange"in window?"orientationchange":"resize",d=(/iPad|iPhone|iPod/.test(navigator.userAgent)&&!window.MSStream,function(){var t=n.clientWidth;t&&(t>=750?(t=750,e.body.style.width="750px"):e.body.style.width=t+"px",n.style.fontSize=100*(t/750)+"px",n.dataset.width=t,n.dataset.percent=100*(t/750))});d(),e.documentElement.classList.add("iosx"+t.devicePixelRatio),e.addEventListener&&t.addEventListener(i,d,!1)}(document,window)
	</script>

	<div class="findNot_cont">
    <div class="wrapper">
          <c:if test="${not empty list}">
            <c:forEach items="${list}" var="bulletin">
              <a href="<%=request.getContextPath()%>/bulletins/${bulletin.bulletinId}" class="item border_b1">
                <div class="tit clearfix">
                    <h4>${bulletin.bulletinTitle}</h4>
                </div>
                <div class="txt">
                    ${bulletin.bulletinContent}
                </div>
                <div class="date">
                    <jsp:useBean id="dateValue" class="java.util.Date"/>
                    <jsp:setProperty name="dateValue" property="time" value="${bulletin.createTime}"/>
                    <fmt:formatDate value="${dateValue}" pattern="yyyy/MM/dd/ HH:mm:ss"/>
                </div>
              </a>
            </c:forEach>
          </c:if>
          <c:if test="${empty list}">
              <div class="text-c rule">
                  暂无公告内容！
              </div>
          </c:if>
    </div>
  </div>


</body>

</html>
