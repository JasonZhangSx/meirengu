<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8"%>
<%
    String path = request.getContextPath()+"/";
%>
<!doctype html>
<html lang="en">
<head>
    <base href="<%=path%>">
    <meta charset="UTF-8">
    <title>美丽邀约-成为海伦合伙人</title>
    <meta id="viewport" name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=0" />
    <meta name="format-detection" content="telephone=no" />
    <style media="screen">
        /*reset.css*/
        body,h1,h2,h3,h4,h5,h6,p,ul,li,small,button,input,textarea,th,td,s{margin:0;padding:0}
        body,button,input,select,textarea{font-family:'SimHei',"Microsoft YaHei",Verdana,Arial,Helvetica,sans-serif}
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

        .helen{background-color: #82e5f0;color:#333;padding:0 .3rem;box-sizing: border-box;font-size: .3rem;}
        .banner{background:url(img/hl_banner.jpg) center 0 no-repeat;background-size:100%;padding-top: 4.75rem;}
        .section1{padding:.5rem 0 .6rem;line-height: .54rem;position: relative;text-align: center;background-color: rgba(255,255,255,0.9);}
        .section1 span{font-size: .36rem;color:#9a7760}
        .section1 .img1{position: absolute;top:0;left:0;transform: translate(0,-100%);-webkit-transform: translate(0,-100%)}
        .section1 .img2{position: absolute;bottom:0;left:0;transform: translate(0,100%);-webkit-transform: translate(0,100%)}

        .section2{margin-top:1.26rem;}
        .section2 .img1{vertical-align: top;}
        .section2 .wrapper{background-color: #eefdfc;overflow: hidden}
        .section2 .top{width:100%;margin-top:.66rem;display: flex;color:#fff;justify-content: center;align-items: center;}
        .section2 .top span{width:1.2rem;height:1.2rem;background-color: #ff6d5a;border-radius: 50%;text-align: center;box-sizing: border-box;padding:.25rem 0;line-height: .36rem;}
        .section2 .top em{color:#000;font-size: .36rem;margin:0 .1rem;}
        .section2 p{line-height: .78rem;padding: .5rem 0 .75rem;text-align: center;}
        .section2 p b{color: #fd426a;font-size: .46rem;font-family: 'SimSun'}

        .section3{padding:.66rem .38rem .45rem;background-color: #e8fafd;margin-top: .58rem;}
        .section3 p{line-height: .54rem;margin-top: .36rem;}

        .section4{background-color: #e8fafd;padding:.15rem;margin-top: .58rem;}
        .section4 .wrapper{border:.1rem #7be2ef solid;line-height: .5rem;padding:.35rem .24rem 1.2rem;}
        .section4 span{color:#000;font-size:.36rem; }

        .section5{background-color: #e3fbfb;margin-top: .58rem;padding:.65rem .38rem;text-align: center;}

        .bot{text-align: center;}
        .bot .img1{margin:.7rem 0 .51rem;}
        .bot .wrapper{background-color: #fff;padding:.39rem 0;border-radius: .1rem;}
        .bot .wrapper .top{padding: 0 .38rem;}
        .bot input{width:100%;height:.98rem;padding:0 .23rem;box-shadow: none;font-size: .3rem;box-sizing: border-box;border:none;border:1px #e5e5e5 solid;margin-bottom: .34rem;border-radius: .1rem;}
        .bot input[type="button"]{display: block;margin:auto;height:0.98rem;box-shadow: 0 0 .1rem .04rem rgba(84,232,254,0.4);line-height:0.98rem;border-radius: .14rem;font-size: .32rem;font-weight: bold;color:#fff;background-color: #62eefd;}
        .bot input[disabled=""]{background-color: #ddd;color:#929292;box-shadow: 0 0 .1rem .04rem rgba(0,0,0,0);}
        .err_msg{color:red;margin:0 0 .3rem;}
        .waiter{display: block;margin:.3rem .3rem .5rem;font-weight: bold;font-size: .38rem;height:.92rem;line-height: .92rem;color:#fff;text-align: center;border-radius: .1rem;}

        .sub_ok{position: fixed;top:0;width:100%;left:0;bottom:0;background-color: rgba(0,0,0,0.4);display: none;border-radius: .12rem;}
        .sub_ok .wrapper{width:6.08rem;background-color: #fff;text-align: center;line-height: .4rem;padding: 1.1rem 0;position: relative;left:50%;top:1.42rem;transform:translate(-50%,0);-webkit-transform:translate(-50%,0)}
        .sub_ok .wrapper h4{font-size: .42rem;}
        .sub_ok small{font-size: .26rem;display: block;margin-top: .26rem;}
        .sub_ok em{position: absolute;font-weight: bold;display: block;width:.6rem;height:.6rem;left:50%;margin-left:-0.3rem;bottom:-.2rem;background-color: #5c5c5c;color:#fff;text-align: center;line-height: .6rem;border-radius: 50%;}
    </style>
</head>
<body>
<script type="text/javascript">
    !function(e,t){var n=e.documentElement,i="orientationchange"in window?"orientationchange":"resize",d=(/iPad|iPhone|iPod/.test(navigator.userAgent)&&!window.MSStream,function(){var t=n.clientWidth;t&&(t>=750?(t=750,e.body.style.width="750px"):e.body.style.width=t+"px",n.style.fontSize=100*(t/750)+"px",n.dataset.width=t,n.dataset.percent=100*(t/750))});d(),e.documentElement.classList.add("iosx"+t.devicePixelRatio),e.addEventListener&&t.addEventListener(i,d,!1)}(document,window);
</script>

<div class="helen">
    <div class="banner">
        <div class="section1">
            <img src="img/hl_icon2.png" width="100%" class="img1" alt="" />
            <img src="img/hl_icon4.png" style="height:6.51rem"class="img3" alt="" />
            <img src="img/hl_icon1.png" width="100%" class="img2" alt="" />
        </div>
    </div>

    <div class="section2">
        <img src="img/blank.gif" data-echo="img/hl_img1.jpg" class="img1" width="100%" style="height:3.18rem" alt="" />
        <div class="wrapper">
            <div class="top">
                <span>股权<br/>分红</span><em>+</em><span>专享<br/>折扣</span><em>+</em><span>现金<br/>返利</span><em>+</em><span>医美项<br/>目赠送</span>
            </div>
            <p>
                <img src="img/blank.gif" data-echo="img/hl_icon5.png" style="height:4.29rem"class="img3" alt="" />
            </p>
        </div>
    </div>

    <div class="section3">
        <img src="img/blank.gif" data-echo="img/hl_img2.jpg" class="img1" style="height:4.77rem" width="100%" alt="" />
        <p>
            <img src="img/blank.gif" data-echo="img/hl_icon6.png" style="height:5.68rem"class="img3" alt="" />
        </p>
    </div>

    <div class="section4">
        <div class="wrapper">
            活动时间：<span>2017.6.1 - 6.30</span><br/><br/>活动地点：<br/>
            <span>Online     美人谷APP</span><br/>
            <span>Offline    青岛海伦美容医院</span><br/><br/>尊享席位：<span>“海伦合伙人”</span><br/><br/>
            限定席位：<span>60名</span><br/><br/>起始金额：<span>5万元，无上限</span><br/><br/>
            分红金额：<br/><span>预期收益为投资额的 10-15%／年</span>
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

    <div class="bot">
        <img src="img/blank.gif" data-echo="img/hl_img11.jpg" class="img1" style="height:3.91rem" alt="" />
        <div class="wrapper">
            <div class="top">
                <input type="text" placeholder="姓名" maxlength='10' name="name" value="">
                <input type="text" placeholder="电话" maxlength='11' name="tel" value="">
                <input type="text" placeholder="城市" maxlength='10' name="city" value="">
                <div class="err_msg"></div>
                <input type="button" name="name" value="点击成为“2017海伦合伙人”" class="submit">
            </div>

        </div>
    </div>

    <a href="tel:400-886-8382" class="waiter">
        客服热线：400-886-8382
    </a>
    <br/>

    <div class="sub_ok">
        <div class="wrapper">
            <em>×</em>
            <h4>恭喜您，报名成功！</h4><br/>
            稍后客服会联系您！<br/>
            您也可主动添加客服微信号：<br/><br/>
            BeautyValley_00<br/><br/>
            <img src="img/blank.gif" data-echo="img/hl_img12.jpg" style="width:2.79rem" class="img1" alt="" /><br/>
            <small>备注姓名+手机号</small>
        </div>
    </div>

</div>


</body>
<script type="text/javascript" src="js/zepto.js"></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="js/jquery.min.js"></script>
<script src="js/wx_share.js"></script>
<script type="text/javascript">
    window.Echo=(function(window,document,undefined){'use strict';var store=[],offset,throttle,poll;var _inView=function(el){var coords=el.getBoundingClientRect();return((coords.top>=0&&coords.left>=0&&coords.top)<=(window.innerHeight||document.documentElement.clientHeight)+parseInt(offset));};var _pollImages=function(){for(var i=store.length;i--;){var self=store[i];if(_inView(self)){self.src=self.getAttribute('data-echo');store.splice(i,1);}}};var _throttle=function(){clearTimeout(poll);poll=setTimeout(_pollImages,throttle);};var init=function(obj){var nodes=document.querySelectorAll('[data-echo]');var opts=obj||{};offset=opts.offset||0;throttle=opts.throttle||250;for(var i=0;i<nodes.length;i++){store.push(nodes[i]);}_throttle();if(document.addEventListener){window.addEventListener('scroll',_throttle,false);}else{window.attachEvent('onscroll',_throttle);}};return{init:init,render:_throttle};})(window,document);
    Echo.init({
        offset: 100,
        throttle: 50
    });

    function emp_test(a){return 0==a.replace(/^\s+|\s+$/g,"").length?!1:!0};

    $('.bot').on('click','.submit',function(){
        $('.bot .err_msg').hide();
        var name = $('.bot input[name="name"]').val(),
                tel = $('.bot input[name="tel"]').val(),
                city = $('.bot input[name="city"]').val();
        if (!emp_test(name)) {
            $('.bot .err_msg').show().text('* 姓名不能为空')
        }else if(!emp_test(tel)){
            $('.bot .err_msg').show().text('* 手机号码不能为空')
        }else if(!(/^1(3|4|5|7|8)\d{9}$/.test(tel))){
            $('.bot .err_msg').show().text('* 手机号码格式不正确')
        }else if(!emp_test(city)){
            $('.bot .err_msg').show().text('* 城市不能为空')
        }else{
            $.ajax({
                url:"activity/signup/submit",
                type:"POST",
                data:{"telphone":tel, "name":name, "city":city, "type":1},
                success: function(data){
                    if(data.code == 200 || data.code == 40200){
                        $(".bot .submit").attr("disabled","");
                        //提交成功
                        $('.sub_ok').show();
                        $('.sub_ok em').click(function(){
                            $('.sub_ok').hide();
                        });
                    }
                },
                error: function(XMLHttpRequest, textStatus, errorThrown){
                    console.log(XMLHttpRequest);
                    alert("请求失败，请重新进行提交");
                }
            });
        }
    })
</script>
</html>
