<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath()+"/";
%>
<!doctype html>
<html lang="en">
<head>
    <base href="<%=path%>">
    <meta charset="UTF-8">
    <title>美人谷-海伦合伙人</title>
    <meta id="viewport" name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=0" />
    <meta name="format-detection" content="telephone=no" />
    <style media="screen">
        /*reset.css*/
        body,h1,h2,h3,h4,h5,h6,p,ul,li,small,button,input,textarea,th,td,s{margin:0;padding:0}
        body,button,input,select,textarea{font-family:"Microsoft YaHei",Verdana,Arial,Helvetica,sans-serif}
        input[type=number]::-webkit-inner-spin-button,input[type=number]::-webkit-outer-spin-button{-webkit-appearance:none;margin:0}
        h1,h2,h3,h4,h5,h6{font-size:100%}address,cite,dfn,em,var{font-style:normal}
        small{line-height:1}ul,ol{list-style:none}a{text-decoration:none;color:inherit}
        input:focus{outline:0}a:hover{text-decoration:npne}q:before,q:after{content:''}legend{color:#000}fieldset,img{border:0}
        button,input,select,textarea{font-size:100%}table{border-collapse:collapse;border-spacing:0}
        .clearfix{display:block}.clearfix:after{content:'';display:block;clear:both;overflow:hidden;line-height:0}
        .clear{clear:both;height:0;line-height: 0;overflow: hidden;}
        img{vertical-align: top;}
        input[type="button"]{-webkit-appearance:none}

        html {height:auto;min-height: 100%;position:relative;margin:0 auto;max-width: 750px;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;}
        body {height:auto;min-height: 100%;position:absolute;top:0;width:100%;line-height:1;color:#202020;-webkit-tap-highlight-color: rgba(0,0,0,0);background-color: #fff;}
        a:focus,button:focus{outline:none;}

        .helen{background-color: #82e5f0;color:#333;padding:0 .3rem;box-sizing: border-box;font-size: .3rem;overflow: hidden;}

        .section5,.section2 .wrapper{box-shadow: 0 .05rem .04rem .02rem rgba(125, 225, 242, 0.42);}
        .section1{width: 6.9rem;height:3.3rem;background:url(img/hl_land_icon1.png);background-size: 100%;margin-top:.4rem;
            display: flex;align-items: center;flex-direction: column;justify-content: center;font-size: .3rem;
        }
        .section1 b{font-size: .36rem;}
        .section1 b.b1{font-size: .46rem;color:#ff6d5a;font-family: 'simSun'}
        .section1 span{margin:.4rem 0 .24rem;}

        .section6{text-align: center;background-color: #fff;margin-top: .38rem;box-shadow: 0 .05rem .04rem .02rem rgba(53, 208, 234, 0.42);}
        .section6 p.p3{text-align: center;margin-top: .38rem;}
        .section6 p.p3 b{font-size: .36rem;}
        .section6 p.p3 span{display: block;margin:.46rem auto;}

        .bot{padding:0 .3rem;margin-top:.06rem;}
        .bot input[type="button"]{width:100%;display: block;text-shadow: margin:auto;height:0.98rem;border:none;line-height:100%;border-radius: .14rem;font-size: .42rem;letter-spacing: .02rem;font-weight: bold;color:#fff;
            background-color: #62eefd;
            background: -webkit-linear-gradient(top,#feee59,#ffcb3c);
            box-shadow: 0 0.04rem .1rem .03rem rgba(251,190,60,0.4);
            text-shadow: 0.03rem 0.03rem 0.03rem rgba(214,163,55,0.5)
        }

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
        <img src="img/hl_joinsuc2.png" style="width:3.5rem" alt="" />
    </div>

    <div class="section6">
        <img src="img/hl_joinsuc1.png" style="width:4.16rem;margin-top:.53rem;" alt="" />
        <img src="img/hl_sc_ecode.png" style="width:3.4rem;margin-top:.45rem;" alt="" />
        <p class="p3">
            添加客服微信BeautyValley_00<br/>
            <span>客服电话：<b>400-886-8382</b></span><br/>
        </p>
        <div class="bot">
            <input type="button" name="name" value="分享美丽财富" class="submit">
        </div>
        <br/><br/>
    </div>

    <img src="img/hl_joinsuc_img1.jpg" style="width:100%;max-width:750px;margin-top:.35rem;" alt="" onclick="openApp()" />

</div>
<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="js/jquery.min.js"></script>
<script type="text/javascript">

    var title='美丽邀约：加入成为海伦合伙人';
    var desc='股权权分红+专享折扣+现金返利+医美项目赠送。享有股权分红，预期年化高达15%。专享折扣低至6折！更有现金返现10%，尊享推荐奖励！以及最高价值143700元医美项目免费赠送。';
    var link='https://api.meirenguvip.com/webview/helen/land/'+${mobile};
    var imgUrl='https://api.meirenguvip.com/webview/img/signup_share.jpg';

    $('.share').click(function(){
        $(this).hide();
    });

    $(".submit").click(function () {
        $(".share").show();
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

        var m_t = $('.menu_wrapper').offset().top;
        $(window).scroll(function(){
            var b_t = $('body').scrollTop();
            if(b_t < m_t){
                $('.menu_wrapper').css({
                    'position':'static'
                })
            }else{
                $('.menu_wrapper').css({
                    'position':'fixed'
                })
            }
        })

    });
    function openApp() {
        var d = new Date();
        var t0 = d.getTime();
        if (navigator.userAgent.match(/(iPhone|iPod|iPad);?/i)) {
            location.href = 'https://itunes.apple.com/app/tap-black-to-white/id1226877868';
        } else if (navigator.userAgent.match(/android/i)) {
            window.location.href = "http://a.app.qq.com/o/simple.jsp?pkgname=com.meirengu.crowdfunding";
        }
    }

</script>

</body>

</html>
