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
        input:focus{outline:0}a:hover{text-decoration:none}q:before,q:after{content:''}legend{color:#000}fieldset,img{border:0}
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
        .section2{margin-top:.5rem;}
        .section2 .img1{vertical-align: top;}
        .section2 .wrapper{background-color: #eefdfc;overflow: hidden}
        .section2 .top{width:100%;margin-top:.66rem;display: flex;color:#fff;justify-content: center;align-items: center;}
        .section2 .top span{width:1.2rem;height:1.2rem;background-color: #ff6d5a;border-radius: 50%;text-align: center;box-sizing: border-box;padding:.25rem 0;line-height: .36rem;}
        .section2 .top em{color:#000;font-size: .36rem;margin:0 .1rem;}
        .section2 p{line-height: .78rem;padding: .5rem 0 .75rem;text-align: center;}
        .section2 p b{color: #fd426a;font-size: .46rem;font-family: 'SimSun'}

        .section5{background-color: #e3fbfb;margin-top: .58rem;padding:.65rem .38rem;text-align: center;}
        .section4{padding:.41rem .3rem 0;}
        .section4 input[type="text"]{width:100%;-webkit-appearance:none;border-radius: .2rem;border:1px #e5e5e5 solid;line-height: .88rem;box-sizing: border-box;font-size: .28rem;padding:0 .35rem;}
        .section4>input,.section4>div{margin:.16rem 0;}
        .v_code{position: relative;}
        .v_code input[type="button"]{-webkit-appearance:none;border:none;background:none;position: absolute;display: block;right:0;top:0;line-height: .88rem;width: 2.18rem;text-align: center;border-left:1px #e5e5e5 solid;color:#82e5f0;}
        .err_msg{color:red;margin:.3rem 0 ;text-align: center;}

        .section6{text-align: center;}
        .section6 p.p3{text-align: center;margin-top: .32rem;}
        .section6 p.p3 b{font-size: .36rem;}
        .section6 p.p3 span{display: block;margin:.7rem auto;}

        .bot{padding:0 .3rem;margin-top:.42rem;}
        .bot input[type="button"]{width:100%;display: block;text-shadow: margin:auto;height:0.98rem;border:none;line-height:100%;border-radius: .14rem;font-size: .42rem;letter-spacing: .02rem;font-weight: bold;color:#fff;
            background-color: #62eefd;
            background: -webkit-linear-gradient(top,#feee59,#ffcb3c);
            box-shadow: 0 0.04rem .1rem .03rem rgba(251,190,60,0.4);
            text-shadow: 0.03rem 0.03rem 0.03rem rgba(214,163,55,0.5)
        }
        .bot input[disabled=""]{
            background-color: #ddd;background: -webkit-linear-gradient(top,#ddd,#ddd);
            color:#bdbdbd;
            box-shadow: 0 0 .08rem .03rem rgba(0,0,0,0);}
    </style>
</head>
<body>
<script type="text/javascript">
    !function(e,t){var n=e.documentElement,i="orientationchange"in window?"orientationchange":"resize",d=(/iPad|iPhone|iPod/.test(navigator.userAgent)&&!window.MSStream,function(){var t=n.clientWidth;t&&(t>=750?(t=750,e.body.style.width="750px"):e.body.style.width=t+"px",n.style.fontSize=100*(t/750)+"px",n.dataset.width=t,n.dataset.percent=100*(t/750))});d(),e.documentElement.classList.add("iosx"+t.devicePixelRatio),e.addEventListener&&t.addEventListener(i,d,!1)}(document,window);
</script>

<div class="helen">
    <div class="section1">
        <img src="img/hl_land_icon2.png" style="width:1.56rem" alt="" />
        <span>你的好友 <c:out value="${fn:substring(userPhone, 0, 3)}****${fn:substring(userPhone, 7, 11)}" />邀请您加入</span>
        <input type="hidden" value="${userPhone}" id="inviteMobile">
        <div>
            “<b> 海伦合伙人 </b>”
        </div>
    </div>
    <div class="section2">
        <img src="img/blank.gif" data-echo="img/hl_img1.jpg" class="img1" width="100%" style="height:3.18rem" alt="" />
        <div class="wrapper">
            <div class="section4">
                <input type="text" placeholder="请输入手机号" maxlength="11" name="tel" value="">
                <div class="v_code">
                    <input type="text" placeholder="请输入验证码" maxlength="6" name="ecode" value="">
                    <input type="button" value="获取验证码" class="getEcode">
                </div>
                <div class="err_msg"></div>
            </div>
            <div class="bot">
                <input type="button" name="name" value="立即报名" class="submit">
            </div>
            <div class="top">
                <span>股权<br/>分红</span><em>+</em><span>专享<br/>折扣</span><em>+</em><span>现金<br/>返利</span><em>+</em><span>医美项<br/>目赠送</span>
            </div>
            <p>
                <img src="img/blank.gif" data-echo="img/hl_icon5.png?v=1053" style="height:4.97rem"class="img3" alt="" />
            </p>
        </div>
    </div>

    <div class="section5">
        <img src="img/blank.gif" data-echo="img/hl_img3.jpg" style="height:6.42rem" width="100%" alt="" />
        <img src="img/blank.gif" data-echo="img/hl_img8.jpg" style="height:7.06rem" width="100%" alt="" />
        <img src="img/blank.gif" data-echo="img/hl_img9.jpg" style="height:7.11rem" alt="" />
        <img src="img/blank.gif" data-echo="img/hl_img4.jpg" style="margin-top:.5rem;height:3.24rem" alt="" />
        <img src="img/blank.gif" data-echo="img/hl_img5.jpg" style="margin-top:.5rem;height:3.24rem" alt="" />
        <img src="img/blank.gif" data-echo="img/hl_img6.jpg" style="margin-top:.5rem;height:3.24rem" alt="" />
        <img src="img/blank.gif" data-echo="img/hl_img7.jpg" style="margin-top:.5rem;height:2.46rem" alt="" />
        <img src="img/blank.gif" data-echo="img/hl_img10.jpg" style="margin-top:.5rem;height:2.92rem" width="100%" alt="" />
    </div>

    <div class="section6">
        <img src="img/hl_sc_ecode.png" style="width:3.4rem;margin-top:.84rem;" alt="" />
        <p class="p3">
            添加客服微信BeautyValley_00<br/>
            <span>客服电话：<b>400-886-8382</b></span><br/>
        </p>
    </div>

</div>


</body>
<script type="text/javascript" src="js/zepto.js"></script>
<script type="text/javascript">
    window.Echo=(function(window,document,undefined){'use strict';var store=[],offset,throttle,poll;var _inView=function(el){var coords=el.getBoundingClientRect();return((coords.top>=0&&coords.left>=0&&coords.top)<=(window.innerHeight||document.documentElement.clientHeight)+parseInt(offset));};var _pollImages=function(){for(var i=store.length;i--;){var self=store[i];if(_inView(self)){self.src=self.getAttribute('data-echo');store.splice(i,1);}}};var _throttle=function(){clearTimeout(poll);poll=setTimeout(_pollImages,throttle);};var init=function(obj){var nodes=document.querySelectorAll('[data-echo]');var opts=obj||{};offset=opts.offset||0;throttle=opts.throttle||250;for(var i=0;i<nodes.length;i++){store.push(nodes[i]);}_throttle();if(document.addEventListener){window.addEventListener('scroll',_throttle,false);}else{window.attachEvent('onscroll',_throttle);}};return{init:init,render:_throttle};})(window,document);
    Echo.init({
        offset: 300,
        throttle: 50
    });

    function emp_test(a){return 0==a.replace(/^\s+|\s+$/g,"").length?!1:!0};

    var numa = 60;//倒计时

    // 提交
    $('.bot .submit').on('click',function(){
        $('.err_msg').hide();
        var tel = $('.section4 input[name="tel"]').val(),
                ecode = $('.section4 input[name="ecode"]').val(),
                inviteMobile = $("#inviteMobile").val();
        if(!emp_test(tel)){
            $('.err_msg').show().text('* 您的手机号码不能为空')
        }else if(!(/^1(3|4|5|7|8)\d{9}$/.test(tel))){
            $('.err_msg').show().text('* 您的手机号码格式不正确')
        }else if(!emp_test(ecode)){
            $('.err_msg').show().text('* 验证码不能为空')
        }else{
            //提交成功
            $.ajax({
                url: "helen/register",
                type : "POST",
                data : {"mobile":tel, "checkCode":ecode, "inviteMobile":inviteMobile},
                success : function(data){
                    if(data.code == 200){
                        //提交成功
                        $('.sub_ok').show();
                        $('.sub_ok em').click(function(){
                            $('.sub_ok').hide();
                        });
                        window.location.href="helen/register/success/"+tel;
                    }else if(data.code == 40014){
                        $('.err_msg').show().text('* 验证码无效');
                    }else if(data.code == 40016){
                        $('.err_msg').show().text('* 您的手机号码格式不正确');
                    }
                }
            });

        }
    })
    // 获取验证码
    $('.section4 .getEcode').on('click',function(_this){
        $('.err_msg').empty().hide();
        var tel = $('.section4 input[name="tel"]');

        if(!emp_test(tel.val())){
            $('.err_msg').show().text('* 您的手机号码不能为空')
            return false;
        }else if(!(/^1(3|4|5|7|8)\d{9}$/.test(tel.val()))){
            $('.err_msg').show().text('* 您的手机号码格式不正确');
            return false;
        }else{
            $.ajax({
                url: "helen/register/code",
                type : "POST",
                data : {"mobile":tel.val()},
                async: false,
                success : function(data){
                    console.log("code: "+data.code);
                    if(data.code == 200){
                        //提交成功
                    }
                }
            });
        }
        $(this).attr('disabled','disabled');
        tel.attr('disabled','disabled');
        var num = numa,_this = $(this);
        console.log(_this.val());
        _this.val(num + 's')
        var timer = setInterval(function(){
            if(num>1){
                _this.val(--num + 's');
            }else{
                _this.removeAttr('disabled').val('获取验证码');
                tel.removeAttr('disabled');
                clearInterval(timer);
            }
        },1000);
    })

</script>
</html>
