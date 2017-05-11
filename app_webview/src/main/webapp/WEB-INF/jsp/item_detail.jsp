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
    <meta charset="UTF-8">
    <title>${itemDetail.itemName}</title>
    <meta id="viewport" name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=0" />
    <meta name="format-detection" content="telephone=no" />
    <link rel="stylesheet" href="css/index.css?v=123" media="screen" charset="utf-8">
</head>
<body>
<script type="text/javascript">
    !function(e,t){var n=e.documentElement,i="orientationchange"in window?"orientationchange":"resize",d=(/iPad|iPhone|iPod/.test(navigator.userAgent)&&!window.MSStream,function(){var t=n.clientWidth;t&&(t>=750?(t=750,e.body.style.width="750px"):e.body.style.width=t+"px",n.style.fontSize=100*(t/750)+"px",n.dataset.width=t,n.dataset.percent=100*(t/750))});d(),e.documentElement.classList.add("iosx"+t.devicePixelRatio),e.addEventListener&&t.addEventListener(i,d,!1)}(document,window)
</script>

<div class="pro_det">
    <header class="bg_fff">
        <img src="<%=imgURI%>${itemDetail.headerImage}" width="100%" alt="" />
        <div class="wrapper">
            <h4>
                <c:if test="${itemDetail.itemStatus == 10}">预约中</c:if>
                <c:if test="${itemDetail.itemStatus == 11}">认筹中</c:if>
                <c:if test="${itemDetail.itemStatus == 12}">已完成</c:if>
                | ${itemDetail.itemName}</h4>
                <span style="margin-right:1.2rem;"><img src="img/pro_det_icon1.jpg" style="width:.3rem" alt="" />${itemDetail.typeName}</span>
                <span><img src="img/pro_det_icon2.jpg" style="width:.3rem" alt="" />${itemDetail.privince}${itemDetail.city}</span>
            <div class="clear"></div>
            <p>
                ${itemDetail.itemProfile}
            </p>
        </div>
    </header>
    <div class="section1 bg_fff">
        <div class="process">
            <c:if test="${itemDetail.itemStatus == 10}">
                <div class="pointer" style="left:${itemDetail.appointPercent>1?100:itemDetail.appointPercent*100}%">
                    <em></em>
                    <div class="top">
                        <fmt:formatNumber type="number" value="${itemDetail.appointPercent*100}" maxFractionDigits="0"/>%
                    </div>
                </div>
            </c:if>
            <c:if test="${itemDetail.itemStatus == 11}">
                <div class="pointer" style="left:${itemDetail.completedPercent>1?100:itemDetail.completedPercent*100}%">
                    <em></em>
                    <div class="top">
                        <fmt:formatNumber type="number" value="${itemDetail.completedPercent*100}" maxFractionDigits="0"/>%
                    </div>
                </div>
            </c:if>
            <c:if test="${itemDetail.itemStatus == 12}">
                <div class="pointer" style="left:${itemDetail.completedPercent>1?100:itemDetail.completedPercent*100}%">
                    <em></em>
                    <div class="top">
                        <fmt:formatNumber type="number" value="${itemDetail.completedPercent*100}" maxFractionDigits="0"/>%
                    </div>
                </div>
            </c:if>
        </div>
        <div class="tab">
            <c:if test="${itemDetail.itemStatus == 10}">
                <span><b><fmt:formatNumber type="number" value="${itemDetail.targetAmount/10000}" maxFractionDigits="0"/>万元</b>目标金额</span>
                <span><b><fmt:formatNumber type="number" value="${itemDetail.appointAmount/10000}" maxFractionDigits="0"/>万元</b>已约金额</span>
                <span><b><fmt:formatNumber type="number" value="${itemDetail.appointPercent*100}" maxFractionDigits="0"/>%</b>完成度</span>
                <span><b>${itemDetail.leavelDay}</b>剩余时间</span>
            </c:if>
            <c:if test="${itemDetail.itemStatus == 11}">
                <span><b><fmt:formatNumber type="number" value="${itemDetail.targetAmount/10000}" maxFractionDigits="0"/>万元</b>目标金额</span>
                <span><b><fmt:formatNumber type="number" value="${itemDetail.completedAmount/10000}" maxFractionDigits="0"/>万元</b>已筹金额</span>
                <span><b><fmt:formatNumber type="number" value="${itemDetail.completedPercent*100}" maxFractionDigits="0"/>%</b>完成度</span>
                <span><b>${itemDetail.leavelDay}</b>剩余时间</span>
            </c:if>
            <c:if test="${itemDetail.itemStatus == 12}">
                <span><b><fmt:formatNumber type="number" value="${itemDetail.targetAmount/10000}" maxFractionDigits="0"/>万元</b>目标金额</span>
                <span><b><fmt:formatNumber type="number" value="${itemDetail.completedAmount/10000}" maxFractionDigits="0"/>万元</b>已筹金额</span>
                <span><b><fmt:formatNumber type="number" value="${itemDetail.completedPercent*100}" maxFractionDigits="0"/>%</b>完成度</span>
                <span><b>${itemDetail.leavelDay}</b>剩余时间</span>
            </c:if>

        </div>
    </div>

    <div class="section2 bg_fff">
        <span>项目发起人</span><var>${itemDetail.sponsorName}</var>
    </div>

    <div class="section3 bg_fff">
        <div class="leader">
            <span>领投人</span>
            <div class="">
                <em><var style="background-image:url(<%=imgURI%>${itemDetail.leadInvestorHeader})"></var> ${itemDetail.leadInvestorName}</em>
                <em class="">
                    &yen; <fmt:formatNumber type="number" value="${itemDetail.leadInvestorAmount}" maxFractionDigits="0"/>万
                </em>
            </div>
        </div>
        <div class="tit">
            已有${orderMap.totalCount}人跟投此项目
        </div>
        <div class="supporter">
            <c:forEach items="${orderMap.list}" var="order">
                <var style="background-image:url(<%=imgURI%>${order.avatar})"></var>
            </c:forEach>
            <c:if test="${orderMap.totalCount>6}">
                <span>·····</span>
            </c:if>
        </div>
    </div>
    <div class="bg_fff">
        <div class="menu">
            <span class="active"><a href="javascript:void(0)">投资回报</a></span>
            <c:forEach items="${contentList}" var="content">
                <span><a href="javascript:void(0)">${content.contentTitle}</a></span>
            </c:forEach>
            <%--<span><a href="javascript:void(0)">项目介绍</a></span>
            <span><a href="javascript:void(0)">投资方案</a></span>--%>
        </div>
    </div>

    <div class="section4">
        <div class="wrapper huibao" style="display:block">
            <c:forEach items="${levelList}" var="level">
                <div class="item">
                    <div class="top">
                        <span>${level.levelName}</span><em>¥ <fmt:formatNumber type="number" value="${level.levelAmount}" maxFractionDigits="0"/>/份</em>
                    </div>
                    <p>
                        ${level.levelDesc}
                    </p>
                    <div class="bot">
                        <c:if test="${itemDetail.itemStatus == 10}"><h5>已预约${level.bookNumber}/${level.totalNumber}份（每人限购${level.singleLimitNumber}份）<small>项目结束${itemDetail.leavelDay}天后发送</small> </h5></c:if>
                        <c:if test="${itemDetail.itemStatus == 11}"><h5>已认筹${level.completedNumber}/${level.totalNumber}份（每人限购${level.singleLimitNumber}份）<small>项目结束${itemDetail.leavelDay}天后发送</small> </h5></c:if>
                        <c:if test="${itemDetail.itemStatus == 12}"><h5>已认筹${level.completedNumber}/${level.totalNumber}份（每人限购${level.singleLimitNumber}份）<small>项目结束${itemDetail.leavelDay}天后发送</small> </h5></c:if>

                        <a href="javascript:openApp()">我要支持</a>
                    </div>
                </div>
            </c:forEach>
        </div>
        <c:forEach items="${contentList}" var="content">
            <div class="wrapper intro">
            <c:forEach items="${fn:split(content.contentInfo, ',')}" var="imgs">
                <img src="<%=imgURI%>${imgs}" width="100%" alt="" />
            </c:forEach>
            </div>
        </c:forEach>
    </div>

    <div style="height:1.5rem"></div>
    <div class="fixed">
        <span>下载美人谷app更多精彩等着你</span>
        <a href="javascript:openApp()">立即下载</a>
    </div>
</div>


</body>
<script type="text/javascript" src="js/zepto.js"></script>
<script type="text/javascript">
    /*
     * 智能机浏览器版本信息:
     *
     */
    var browser={
        versions:function(){
            var u = navigator.userAgent, app = navigator.appVersion;
            return {//移动终端浏览器版本信息
                trident: u.indexOf('Trident') > -1, //IE内核
                presto: u.indexOf('Presto') > -1, //opera内核
                webKit: u.indexOf('AppleWebKit') > -1, //苹果、谷歌内核
                gecko: u.indexOf('Gecko') > -1 && u.indexOf('KHTML') == -1, //火狐内核
                mobile: !!u.match(/AppleWebKit.*Mobile.*/)||!!u.match(/AppleWebKit/), //是否为移动终端
                ios: !!u.match(/\(i[^;]+;( U;)? CPU.+Mac OS X/), //ios终端
                android: u.indexOf('Android') > -1 || u.indexOf('Linux') > -1, //android终端或者uc浏览器
                iPhone: u.indexOf('iPhone') > -1 || u.indexOf('Mac') > -1, //是否为iPhone或者QQHD浏览器
                iPad: u.indexOf('iPad') > -1, //是否iPad
                webApp: u.indexOf('Safari') == -1 //是否web应该程序，没有头部与底部
            };
        }(),
        language:(navigator.browserLanguage || navigator.language).toLowerCase()
    }
    $(document).ready(function(){

        $('.menu span').on('click',function(){
            var index = $(this).index();
            $('.menu span').removeClass('active');
            $('.section4').find('.wrapper').hide();
            $(this).addClass('active');
            $('.section4').find('.wrapper').eq(index).show();
        })

    });
    function openApp() {
        var d = new Date();
        var t0 = d.getTime();
        if (navigator.userAgent.match(/(iPhone|iPod|iPad);?/i)) {
            location.href = 'https://itunes.apple.com/app/tap-black-to-white/id1226877868';
            /*if(navigator.userAgent.indexOf("QQ") != -1){
                alert("请点击右上角用Safari打开");
                return;
            }
            if(navigator.userAgent.indexOf("MicroMessenger") != -1){
                alert("请点击右上角用Safari打开");
                return;
            }
            */
            /*location.href = 'meirengu://ios/${itemDetail.itemId}';
            setTimeout(function() {
                location.href = 'https://itunes.apple.com/app/tap-black-to-white/id1226877868';
            }, 250);
            setTimeout(function() {
                location.reload();
            }, 1000);*/

        } else if (navigator.userAgent.match(/android/i)) {
            if(jumpToApp('meirengu://android/${itemDetail.itemId}')){
                //jumpToApp('meirengu://android/${itemDetail.itemId}');
                return;
            }else {
                var delay = setInterval(function(){
                    var d = new Date();
                    var t1 = d.getTime();
                    if( t1-t0<1000 && t1-t0>500){
                        window.location.href = "http://a.app.qq.com/o/simple.jsp?pkgname=com.meirengu.crowdfunding";
                    }
                    if(t1-t0>=2000){
                        clearInterval(delay);
                    }
                },25);
            }

            //window.location = "meirengu://android/${itemDetail.itemId}";
        }
    }

    function jumpToApp(src){
        // 通过iframe的方式试图打开APP，如果能正常打开，会直接切换到APP，并自动阻止a标签的默认行为
        // 否则打开a标签的href链接
        var ifr = document.createElement('iframe');
        ifr.src = src;
        ifr.style.display = 'none';
        document.body.appendChild(ifr);
        window.setTimeout(function(){
            document.body.removeChild(ifr);
        },2000);
    }
</script>
</html>

