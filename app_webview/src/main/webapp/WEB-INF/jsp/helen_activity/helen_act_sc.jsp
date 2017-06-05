<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
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
        body,h1,h2,h3,h4,h5,h6,p,ul,li,small,button,input,textarea,th,td,s,hr{margin:0;padding:0}
        body,button,input,select,textarea{font-family:"Microsoft YaHei",'SimHei',Verdana,Arial,Helvetica,sans-serif}
        input[type=number]::-webkit-inner-spin-button,input[type=number]::-webkit-outer-spin-button{-webkit-appearance:none;margin:0}
        h1,h2,h3,h4,h5,h6{font-size:100%}address,cite,dfn,em,var{font-style:normal}
        small{line-height:1}ul,ol{list-style:none}a{text-decoration:none;color:inherit}
        input:focus{outline:0}a:hover{text-decoration:npne}q:before,q:after{content:''}legend{color:#000}fieldset,img{border:0}
        button,input,select,textarea{font-size:100%}table{border-collapse:collapse;border-spacing:0}
        .clearfix{display:block}.clearfix:after{content:'';display:block;clear:both;overflow:hidden;line-height:0}
        .clear{clear:both;height:0;line-height: 0;overflow: hidden;}
        img{vertical-align: top;}
        input[type="button"]{-webkit-appearance:none}

        .ft36{font-size: .36rem;}

        html {height:auto;min-height: 100%;position:relative;margin:0 auto;max-width: 750px;-ms-text-size-adjust: 100%;-webkit-text-size-adjust: 100%;}
        body {height:auto;min-height: 100%;position:absolute;top:0;width:100%;line-height:1;color:#202020;-webkit-tap-highlight-color: rgba(0,0,0,0);background-color: #fff;}
        a:focus,button:focus{outline:none;}

        .helen,body{background-color: #fbc6e6;color:#333;padding:0 .3rem;box-sizing: border-box;font-size: .28rem;}
        body{padding: 0}
        hr{border:none;height:0;line-height: 0;border-top:1px #bfbfbf dashed;margin-top: .36rem;}

        .section1{
            background: -webkit-linear-gradient(top, #fdd4ed 20%, #fbc6e6);
        }
        .section1 .wpr{text-align: center;margin:auto;width:6.45rem;color:#6e6e6e;background-color: #fff;border:0.06rem #fe6b8a solid;line-height: .45rem;padding:.38rem 0 .58rem}
        .section1 .wpr span{color:#fe6b8a;font-weight: bold;}

        .tit{color:#fff;font-weight: bold;font-size: .32rem;margin:.46rem auto .34rem;height:.69rem;background-color:#fd6db1;display: inline-block;
            padding:0 .7rem;line-height: .69rem;border-radius: 2rem;position: relative;
            background-size:0.15rem 0.15rem;
            text-shadow: 0.03rem 0.03rem 0.03rem rgba(0,0,0,0.2);
            box-shadow: 0 .04rem .05rem .01rem rgba(233,62,159,.42);
            background-image: -webkit-linear-gradient(135deg,rgba(255,255,255,0.15) 25%,transparent 25%,transparent 50%,rgba(255,255,255,0.15) 50%,rgba(255,255,255,0.15) 75%,transparent 75%,transparent);
            background-image: linear-gradient(135deg,rgba(255,255,255,0.15) 25%,transparent 25%,transparent 50%,rgba(255,255,255,0.15) 50%,rgba(255,255,255,0.15) 75%,transparent 75%,transparent);
        }
        .tit:before{content:"";position: absolute;display: block;width:100%;left:0;top:0;bottom:0;border-radius: 2rem;
            background: -webkit-linear-gradient(top,rgba(255,255,255,0.2) 50%,rgba(0,0,0,.1));
        }

        /*大层阴影*/
        .section2,.section1 .wpr,.section3,.section4{
            box-shadow: 0 .05rem .04rem .02rem rgba(202, 202, 202, 0.42);
        }
        .section2,.section3,.section4{box-sizing: border-box;margin-top: .44rem;padding:0 .23rem;background-color: #fff;border-radius: .12rem;overflow: hidden;text-align: center;}
        .section2 p{text-align: left;font-size: .28rem;line-height: .5rem;}
        .section2 p.p2{text-align: center;line-height: .58rem;margin-top: .1rem;}
        .section2 p.p2 b{color:#fe6b8a;font-size: .36rem;}
        .section2 p.p3{text-align: center;margin-top: .36rem;}
        .section2 p.p3 b{font-size: .36rem;}
        .section2 p.p3 span{display: block;margin:.3rem auto;}

        .section3 p{overflow: hidden;line-height: .55rem;font: .28rem;}
        .section3 .b1{display: block;margin: .3rem auto;}
        .section3 p b{color:#fe6b8a;font-size: .36rem;}
        .section3 p span{display: block;margin:.45rem auto 0;}
        .section3 p.p2{line-height: 1rem;text-align: left;padding-top:.4rem;}
        .section3 p.p2 small{display: block;line-height: .55rem;font-size:.28rem;}
        .section3 p.p2 var{color: #fe6b8a;}
        .section3 p.p2 b{color:#fe6b8a;font-size: .36rem;}
        .section3 table{width:100%;line-height: .7rem;border-spacing: .09rem;border-collapse: separate;}
        .section3 table th{font-size: .26rem;background-color: #ffe1e8;font-weight: normal;}
        .section3 table td{font-size: .28rem;font-weight: bold;background-color: #ffe1e8;}

        .section4{padding-top:.23rem;padding-bottom: .23rem;}
        .section4 input[type="text"]{width:100%;-webkit-appearance:none;border-radius: .2rem;border:1px #e5e5e5 solid;line-height: .88rem;box-sizing: border-box;font-size: .28rem;padding:0 .35rem;}
        .section4>input,.section4>div{margin:.16rem 0;}
        .v_code{position: relative;}
        .v_code input[type="button"]{-webkit-appearance:none;border:none;background:none;position: absolute;display: block;right:0;top:0;line-height: .88rem;width: 2.18rem;text-align: center;border-left:1px #e5e5e5 solid;color:#82e5f0;}

        .sub_now{position: fixed;font-weight: bold;font-family: 'Microsoft Yahei';letter-spacing: .04rem;bottom:0;width:100%;max-width:750px;color:#fff;font-size: .42rem;text-align: center;line-height: 1rem;background-color: #49e3e5;
            text-shadow: 0.03rem 0.03rem 0.03rem rgba(0,0,0,0.2)
        }
        .err_msg{color:red;}
    </style>
</head>
<body>
<script type="text/javascript">
    !function(e,t){var n=e.documentElement,i="orientationchange"in window?"orientationchange":"resize",d=(/iPad|iPhone|iPod/.test(navigator.userAgent)&&!window.MSStream,function(){var t=n.clientWidth;t&&(t>=750?(t=750,e.body.style.width="750px"):e.body.style.width=t+"px",n.style.fontSize=100*(t/750)+"px",n.dataset.width=t,n.dataset.percent=100*(t/750))});d(),e.documentElement.classList.add("iosx"+t.devicePixelRatio),e.addEventListener&&t.addEventListener(i,d,!1)}(document,window);
</script>

<div class="banner">
    <img src="img/hl_sc_img1.jpg" width='100%' alt="" />
</div>
<div class="section1">
    <div class="wpr">
        必须承认，我就是这样的女人<br/>“ 我不懂办公室的相处之道 ”<br/>“ 我不想过朝九晚六的忙碌生活 ”<br/>“ 我讨厌循规蹈矩的步调 ”<br/>	可是我想要活的有尊严<br/>
        <br/>
        你们一定会说：痴人说梦呢！<br/>哪有不劳而获的事情<br/>告诉你，真的有<br/>就在“ <span>美人谷</span> ”<br/>
        <br/>
        让我来带你走进美丽代言人的世界，告诉你<br/>这里是“ <span>分享即财富</span> ”的理想国
    </div>
</div>
<div class="helen">
    <div class="section2">
        <h4 class="tit">什么是美丽代言人？</h4>
        <p>
            美丽代言人是美人谷平台的传播使者，她们是一群懂生活、有思想、会理财，爱分享，让身边每一个平凡女性的日子都闪闪发光的精英典范。
        </p>
        <hr>
        <h4 class="tit">什么是海伦计划？</h4>
        <p style="text-align:center;">
            海伦计划是青岛海伦美容医院联合美人谷共同推出的新型医美项目主推的互联网众筹产品“海伦合伙人”<br/>
        </p>
        <p class="p2">
            享有股权分红，预期年化高达 <b>15%</b><br/>股东专享折扣低至 <b>6折！</b><br/>更有现金返现 <b>10% </b>，尊享推荐奖励！<br/>
            以及最高价值 <b>143700</b> 元医美项目免费赠送<b style="color:#333;display:block;margin-top:.1rem;">本金到期返还</b>
        </p>
        <hr>
        <h4 class="tit">联系客服获取海伦合伙计划</h4>
        <img src="img/hl_sc_ecode.jpg" style="width:3.12rem;margin-top:.4rem;" alt="" />
        <p class="p3">
            添加客服微信BeautyValley_00<br/>
            <span>客服电话：<b>400-886-8382</b></span><br/>
        </p>
    </div>

    <div class="section3">
        <h4 class="tit">你需要做什么？</h4>
        <p>
            <b class="b1 ft36" style="color:#333">动动手指，分享链接</b>
        </p>
        <hr>
        <h4 class="tit">你会获得什么？</h4>
        <p>
            投资人通过你分享的链接投资成功<br/>
            你即可获得该投资款金额的 <b>5%</b><br/>
            最低 <b>2500元 — 25000元/人</b><br/>
            <span>另有多场线下活动现场答疑，详询客服。</span>
        </p>
        <hr>
        <p class="p2">
            <var>活动范围：</var>仅限海伦医院项目<br/>
            <var>活动时间：</var>2017.6.1-6.30<br/>
            <var>活动地点：</var>美人谷APP<br/>
            <var>技术支持：</var>海伦医院多场线下路演活动<br/>
            <small>
                <var>代言规则：</var><br/>被邀请人认筹成功后可获得被邀请人认购额 <b>5%</b> 的返现奖励。<br/>
            </small>
            <var>代言收益</var><br/>
        </p>
        <table>
            <tr>
                <th>认筹金额（元）</th><th>奖励百分比</th><th>返现金额（元）</th>
            </tr>
            <tr>
                <td>50000</td><td rowspan="3">5%</td><td>2500</td>
            </tr>
            <tr>
                <td>200000</td><td>10000</td>
            </tr>
            <tr>
                <td>500000</td><td>25000</td>
            </tr>
        </table><br/><br/>
    </div>

    <div class="section4">
        <input type="text" placeholder="请输入手机号" maxlength="11" name="tel" value="">
        <div class="v_code">
            <input type="text" placeholder="请输入验证码" maxlength="6" name="ecode" value="">
            <input type="button" value="获取验证码" class="getEcode">
        </div>
        <div class="err_msg"></div>
    </div>


    <br/><br/><br/><br/><br/>
</div>
<div class="sub_now">立即报名</div>

</body>
<script type="text/javascript" src="js/zepto.js"></script>
<script type="text/javascript">

    function emp_test(a){return 0==a.replace(/^\s+|\s+$/g,"").length?!1:!0};

    var telPhone = '';
    var user_id = '';
    var flag = '';
    var loginFlag = '';

    var title='美丽邀约：加入成为海伦代言人';
    var desc='股权权分红+专享折扣+现金返利+医美项目赠送。享有股权分红，预期年化高达15%。专享折扣低至6折！更有现金返现10%，尊享推荐奖励！以及最高价值143700元医美项目免费赠送。';
    var link='https://api.meirenguvip.com/webview/helen';
    var imgUrl='https://api.meirenguvip.com/webview/img/helen_share.jpg';

    function showInfoFromAppWithMsg(mobile){
        //alert("来自App的消息: "+mobile);
        telPhone = mobile;
        if(telPhone == null || telPhone == '' || telPhone == undefined){
            $(".section4").show();
        }else{
            $(".section4").hide();
        }
    }

    function showInfoFromAppWithUserId(userId){
        user_id = userId;
    }

    function showInfoFromAppWithFlag(msg){
        console.log("来自App的消息: "+msg);
        flag = msg;
        if(flag == 2){
            //1 分享  2 不分享
            window.AndroidWebView.shareAppTag(1, link, title, desc, imgUrl);
            return;
        }else if(flag == 1){
            window.webkit.messageHandlers.shareAppTag.postMessage({"tag":1, "url":link, "title":title, "description":desc, "thumbUrl":imgUrl});
            return;
        }
    }

    //app登录成功通知   1 成功  2 失败
    function appAlreadyLogin(login){
        loginFlag = login;
    }

    function isLogin(){
        if(telPhone == '' || telPhone == undefined || telPhone == null){
            return false;
        }else {
            return true;
        }
    }

    var numa = 60;//倒计时
    // 提交
    $('.sub_now').on('click',function(){
        if(isLogin()){
            window.location.href="helen/invite/record/"+user_id+"?mobile="+telPhone;
            return;
        }
        //window.webkit.messageHandlers.jsLogin.postMessage({"token":"12345"});
        //window.webkit.messageHandlers.Native.postMessage('func=jsLogin&1=12345');
        console.log($('.section4').offset().top);
        window.scrollTo(0,$('.section4').offset().top)
        $('.err_msg').hide();
        var tel = $('.section4 input[name="tel"]').val(),
                ecode = $('.section4 input[name="ecode"]').val();
        if(!emp_test(tel)){
            $('.err_msg').show().text('* 您的手机号码不能为空')
        }else if(!(/^1(3|4|5|7|8)\d{9}$/.test(tel))){
            $('.err_msg').show().text('* 您的手机号码格式不正确')
        }else if(!emp_test(ecode)){
            $('.err_msg').show().text('* 验证码不能为空')
        }else{
            $.ajax({
                url: "helen/register",
                type : "POST",
                async: false,
                data : {"mobile":tel, "checkCode":ecode, "inviteMobile":""},
                success : function(data){
                    if(data.code == 200){
                        //提交成功
                        $('.sub_ok').show();
                        $('.sub_ok em').click(function(){
                            $('.sub_ok').hide();
                        });
                        var userId = data.userId;
                        var token = data.token;
                        if(flag == 2){
                            window.AndroidWebView.jsLogin(token);
                            //alert("loginFlag: "+loginFlag);
                            //isLogin==1 登录成功
                            //if(loginFlag == 1){
                            window.location.href="helen/invite/record/"+userId+"?mobile="+tel;
                            //}
                        }else if(flag == 1){
                            window.webkit.messageHandlers.jsLogin.postMessage({"token":token});
                            //isLogin==1 登录成功
                            //if(loginFlag == 1){
                            window.location.href="helen/invite/record/"+userId+"?mobile="+tel;
                            //}
                        }else{
                            window.location.href="helen/invite/record/"+userId+"?mobile="+tel;
                        }

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
        console.log("_this:"+_this.val());
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

