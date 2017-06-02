<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%
    String path = request.getContextPath()+"/";
%>
<!doctype html>
<html lang="en">
<head>
    <base href="<%=path%>">
    <meta charset="UTF-8">
    <title>海伦计划-寻找美丽代言人</title>
    <meta id="viewport" name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=0" />
    <meta name="format-detection" content="telephone=no" />
    <style media="screen">
        /*reset.css*/
        body,h1,h2,h3,h4,h5,h6,p,ul,li,small,button,input,textarea,th,td,s{margin:0;padding:0}
        body,button,input,select,textarea{font-family:"Microsoft YaHei",Verdana,Arial,Helvetica,sans-serif}
        input[type=number]::-webkit-inner-spin-button,input[type=number]::-webkit-outer-spin-button{-webkit-appearance:none;margin:0}
        h1,h2,h3,h4,h5,h6{font-size:100%}address,cite,dfn,em,var{font-style:normal}
        small{line-height:1}ul,ol{list-style:none}a{text-decoration:none;color:inherit}
        input:focus{outline:0}a:hover{text-decoration:none}q:before,q:after{content:''}legend{color:#000}fieldset,img{border:0}
        button,input,select,textarea{font-size:100%}table{border-collapse:collapse;border-spacing:0}
        .clearfix{display:block}.clearfix:after{content:'';display:block;clear:both;overflow:hidden;line-height:0}
        .clear{clear:both;height:0;line-height: 0;overflow: hidden;}
        img{vertical-align: top;}
        input[type="button"]{-webkit-appearance:none}

        html {height:auto;min-height: 100%;position:relative;margin:0 auto;max-width: 750px;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;}
        body {height:auto;min-height: 100%;position:absolute;top:0;width:100%;line-height:1;color:#202020;-webkit-tap-highlight-color: rgba(0,0,0,0);background-color: #fff;}
        a:focus,button:focus{outline:none;}

        .helen{background-color: #fbc6e6;color:#333;padding:0 .3rem;box-sizing: border-box;font-size: .3rem;overflow: hidden;}

        .tit{font-size: .32rem;font-weight: normal;line-height: .50rem;margin-top:.3rem;}
        .tit var{color:#fe6b8a;font-weight: bold;}
        .section6{box-shadow: 0 .05rem .04rem .02rem rgba(232, 130, 192, 0.42);}
        .section1{width: 6.9rem;height:3.3rem;background:url(img/hl_land_icon1.png);background-size: 100%;margin-top:.4rem;
            display: flex;align-items: center;flex-direction: column;justify-content: center;font-size: .3rem;
        }
        .section1{font-size: .3rem;}
        .section1 b{color:#49e3e5;}
        .section1 small{font-size: .2rem;}
        .section1 span{margin:.38rem 0 .28rem}

        .section6,.section7{text-align: center;background-color: #fff;margin-top: .38rem;overflow: hidden;border:.04rem #fe6b8a solid;border-radius: .2rem;}
        .section6 p.p3{text-align: center;margin-top: .38rem;}
        .section6 p.p3 b{font-size: .36rem;}
        .section6 p.p3 span{display: block;margin:.46rem auto;}
        hr{border:none;height:0;line-height: 0;border-top:1px #bfbfbf dashed;margin-top: .36rem;}

        .section6 table{font-size: .28rem;text-align: center;width:5.85rem;margin:0 auto;line-height: .78rem;}
        .section6 table tr td:nth-child(1){text-align: left;}
        .section6 b{color:#fe6b8a;}
        .section6 b.b2{font-size: .32rem;}

        .bot{padding:0 .3rem;margin-top:.06rem;}
        .bot input[type="button"]{width:100%;display: block;text-shadow: margin:auto;height:0.98rem;border:none;line-height:100%;border-radius: .14rem;font-size: .42rem;letter-spacing: .02rem;font-weight: bold;color:#fff;
            background-color: #62eefd;
            background: -webkit-linear-gradient(top,#feee59,#ffcb3c);
            box-shadow: 0 0.04rem .1rem .03rem rgba(251,190,60,0.42);
            text-shadow: 0.03rem 0.03rem 0.03rem rgba(214,163,55,0.5)
        }
        .sub_now{position: fixed;font-weight: bold;font-family: 'Microsoft Yahei';letter-spacing: .04rem;bottom:0;width:100%;max-width:750px;color:#fff;font-size: .42rem;text-align: center;line-height: 1rem;background-color: #49e3e5;
            text-shadow: 0.03rem 0.03rem 0.03rem rgba(0,0,0,0.2)
        }

        .section7{border:none;box-sizing: border-box;padding:0 .23rem .12rem;	box-shadow: 0 0.04rem .1rem .03rem rgba(202,202,202,0.72);}
        .section7 img{margin:.42rem auto .24rem;display: block;}
        .section7 li{line-height: .82rem;font-size: .36rem;display: flex;justify-content: space-between;font-family: 'Arial';border-bottom: 1px #bfbfbf dashed;padding:0 .28rem 0 .26rem;box-sizing: border-box;}
        .section7 li:nth-last-child(1){border:none;}
        .section7 li var{font-size: .28rem;color:#666;}
        .norecord{display: flex;height:3.88rem;flex-direction: column;justify-content: center;color:#666666;font-size: .26rem;margin-bottom: .42rem}
        .norecord span{margin-top:.28rem;}

        .share{position: fixed;z-index:66;background-color: rgba(0, 0, 0, 0.5);width:100%;left:0;top:0;bottom:0;text-align: right;}
        .share img{float:right;margin:.4rem .68rem 0 0;}
    </style>
</head>
<body>
<script type="text/javascript">
    !function(e,t){var n=e.documentElement,i="orientationchange"in window?"orientationchange":"resize",d=(/iPad|iPhone|iPod/.test(navigator.userAgent)&&!window.MSStream,function(){var t=n.clientWidth;t&&(t>=750?(t=750,e.body.style.width="750px"):e.body.style.width=t+"px",n.style.fontSize=100*(t/750)+"px",n.dataset.width=t,n.dataset.percent=100*(t/750))});d(),e.documentElement.classList.add("iosx"+t.devicePixelRatio),e.addEventListener&&t.addEventListener(i,d,!1)}(document,window);
</script>
<div class="share" style="display: none;">
    <img src="img/hl_joinsuc5.png" style="width:4.32rem;" alt="" />
</div>
<div class="helen">
    <div class="section1">
        <img src="img/hl_land_icon2.png" style="width:1.56rem" alt="" />
        <span>正式成为 “<b>美丽代言人</b>”</span>
        <small>轻松分享 | 快速收益 | 美丽代言</small>
    </div>

    <div class="section6">
        <h4 class="tit">现在分享，并<var>截屏给客服</var><br/>即可<var>免费</var>获得以下豪礼</h4>
        <hr>
        <table>
            <tr>
                <td>线雕</td><td><s>原价 <b>15800</b> 元</s></td><td><b class="b2">免费!</b></td>
            </tr>
            <tr>
                <td>乌金翘睫美眼术</td><td><s>原价 <b>13800</b> 元</s></td><td><b class="b2">免费!</b></td>
            </tr>
            <tr>
                <td>美版超声刀</td><td><s>原价 <b>12800</b> 元</s></td><td><b class="b2">免费!</b></td>
            </tr>
            <tr>
                <td>自体活细胞填充</td><td><s>原价 <b>12800</b> 元</s></td><td><b class="b2">免费!</b></td>
            </tr>
        </table>
        <img src="img/hl_sc_ecode.png" style="width:3.4rem;margin-top:.45rem;" alt="" />
        <p class="p3">
            添加客服微信，码上兑豪礼<br/>
        </p>
        <br/><br/>
    </div>

    <div class="section7">
        <c:if test="${not empty data}">
            <img src="img/hl_joinsuc3.png" style="width:4.12rem;" alt="" />
            <ul>
                <c:forEach items="${data.list}" var="record">
                    <li><span><c:out value="${fn:substring(record.invitedUserPhone, 0, 3)}****${fn:substring(record.invitedUserPhone, 7, 11)}" /></span><var>
                        <jsp:useBean id="dateValue" class="java.util.Date"/>
                        <jsp:setProperty name="dateValue" property="time" value="${record.registerTime}"/>
                        <fmt:formatDate value="${dateValue}" pattern="yyyy-MM-dd"/>
                    </var></li>
                </c:forEach>
            </ul>
        </c:if>
        <c:if test="${empty data}">
            <div class="norecord">
                <img src="img/hl_joinsuc4.png" style="width:2rem;" alt="" />
                <span>您还没有邀请记录</span>
            </div>
        </c:if>
    </div>

    <br/><br/><br/><br/><br/>
</div>

<div class="sub_now">即刻转发得豪礼</div>

</body>
<script type="text/javascript" src="js/zepto.js"></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="js/jquery.min.js"></script>
<script type="text/javascript">

    var title='美丽邀约：加入成为海伦合伙人';
    var desc='股权权分红+专享折扣+现金返利+医美项目赠送。享有股权分红，预期年化高达15%。专享折扣低至6折！更有现金返现10%，尊享推荐奖励！以及最高价值143700元医美项目免费赠送。';
    var link='https://api.meirenguvip.com/webview/helen/land/'+${mobile};
    var imgUrl='https://api.meirenguvip.com/webview/img/signup_share.jpg';

    var flag = '';

    function showInfoFromAppWithFlag(msg){
        flag = msg;
        if(flag == 2){
            $(".share").hide();
        }else if(flag == 1){
            $(".share").hide();
        }else{
            $(".share").show();
        }
    }

    $('.share').click(function(){
        $(this).hide();
    });
    
    $(".sub_now").click(function () {
        if(flag == null || flag == '' || flag == undefined){
            $(".share").show();
            return;
        }else if(flag == 1){
            window.webkit.messageHandlers.shareApp.postMessage({"url":link, "title":title, "description":desc, "thumbUrl":imgUrl});
            return;
        }else if(flag == 2){
            window.AndroidWebView.shareApp(link,title,desc,imgUrl);
            return;
        }
    });

    jQuery(document).ready(function(){
        var url_=encodeURIComponent(window.location.href);
        $.ajax({
            type : "get",
            async: false,
            url:"https://api.meirenguvip.com/wxcs/service/dtwxconfig?url="+url_,
            dataType: "jsonp",
            jsonp: "jsonpCallback",
            jsonpCallback:"success_jsonpCallback",
            success : function (json){
                wx.config({
                    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
                    appId: 'wx22ae3075d64a4f81', // 必填，公众号的唯一标识
                    timestamp: json.timestamp, // 必填，生成签名的时间戳
                    nonceStr: json.noncestr, // 必填，生成签名的随机串
                    signature: json.signature,// 必填，签名
                    jsApiList: ["onMenuShareTimeline","onMenuShareAppMessage","onMenuShareQQ","onMenuShareWeibo"] // 必填，需要使用的JS接口列表
                });

            }

        });
    });

    //通过ready接口处理成功验证
    wx.ready(function(){
        // “分享到朋友圈”
        wx.onMenuShareTimeline({
            title: title,
            link: link,
            imgUrl: imgUrl
        });

        // “分享给朋友”
        wx.onMenuShareAppMessage({
            title: title,
            desc: desc,
            link: link,
            imgUrl: imgUrl
        });

        //“分享到QQ”
        wx.onMenuShareQQ({
            title: title,
            desc: desc,
            link: link,
            imgUrl: imgUrl
        });
        //“分享到微博”
        wx.onMenuShareWeibo({
            title: title,
            desc: desc,
            link: link,
            imgUrl: imgUrl
        });

    });

</script>
</html>

