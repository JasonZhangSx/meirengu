<%@ page contentType="text/html;charset=UTF-8" language="java" import="com.meirengu.webview.utils.ConfigUtil" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%
    String path = request.getContextPath()+"/";
    String imgURI = ConfigUtil.getConfig("URI_IMG_PREFIX");
%>
<!doctype html>
<html lang="en">
<head>
    <base href="<%=path %>">
    <title>Document</title>
    <meta id="viewport" name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=0" />
    <meta name="format-detection" content="telephone=no" />
    <link rel="stylesheet" href="css/index.css" media="screen" charset="utf-8">
</head>
<body style='background-color:#fff;'>
<script type="text/javascript">
    !function(e,t){var n=e.documentElement,i="orientationchange"in window?"orientationchange":"resize",d=(/iPad|iPhone|iPod/.test(navigator.userAgent)&&!window.MSStream,function(){var t=n.clientWidth;t&&(t>=750?(t=750,e.body.style.width="750px"):e.body.style.width=t+"px",n.style.fontSize=100*(t/750)+"px",n.dataset.width=t,n.dataset.percent=100*(t/750))});d(),e.documentElement.classList.add("iosx"+t.devicePixelRatio),e.addEventListener&&t.addEventListener(i,d,!1)}(document,window)
</script>

<div class="meit_bd">
    <div class="wrapper">
        <a href="javascript:void(0)" class="item clearfix">
            <div class="left" style="background-image:url(https://dummyimage.com/234x150/b293a4/fff&text=someImg)"></div>
            <div class="right">
                <h4>5月PMI为51.2连续10个月在荣枯线上方</h4>
                <var>2017-05-31</var>
            </div>
        </a>
        <a href="javascript:void(0)" class="item clearfix">
            <div class="left" style="background-image:url(https://dummyimage.com/234x150/b293a4/fff&text=someImg)"></div>
            <div class="right">
                <h4>5月PMI为51.2连续10个月在荣枯线上方网贷存管热度两重天的疯狂：银行喝汤与吃肉同在</h4>
                <var>2017-05-31</var>
            </div>
        </a>
        <a href="javascript:void(0)" class="item clearfix">
            <div class="left" style="background-image:url(https://dummyimage.com/234x150/b293a4/fff&text=someImg)"></div>
            <div class="right">
                <h4>5月PMI为51.2连续10个月在荣枯线上方</h4>
                <var>2017-05-31</var>
            </div>
        </a>
        <a href="javascript:void(0)" class="item clearfix">
            <div class="left" style="background-image:url(https://dummyimage.com/234x150/b293a4/fff&text=someImg)"></div>
            <div class="right">
                <h4>5月PMI为51.2连续10个月在荣枯线上方网贷存管热度两重天的疯狂：银行喝汤与吃肉同在</h4>
                <var>2017-05-31</var>
            </div>
        </a>
        <a href="javascript:void(0)" class="item clearfix">
            <div class="left" style="background-image:url(https://dummyimage.com/234x150/b293a4/fff&text=someImg)"></div>
            <div class="right">
                <h4>5月PMI为51.2连续10个月在荣枯线上方</h4>
                <var>2017-05-31</var>
            </div>
        </a>
        <a href="javascript:void(0)" class="item clearfix">
            <div class="left" style="background-image:url(https://dummyimage.com/234x150/b293a4/fff&text=someImg)"></div>
            <div class="right">
                <h4>5月PMI为51.2连续10个月在荣枯线上方网贷存管热度两重天的疯狂：银行喝汤与吃肉同在</h4>
                <var>2017-05-31</var>
            </div>
        </a>
    </div>
</div>
</body>
</html>

