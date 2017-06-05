<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath()+"/";
%>
<!doctype html>
<html lang="en">
<head>
    <base href="<%=path%>">
    <meta charset="UTF-8">
    <title>美丽0元购，即刻变女神</title>
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

        .helen{background-color: #dbcefe;color:#333;padding:0 .3rem;box-sizing: border-box;font-size: .3rem;overflow: hidden;
            background-image:url(img/ml_0_shop.jpg);background-position: center 0;background-repeat: no-repeat;background-size: 100%;
        }

        .section1{
            margin: 5.46rem auto 0;width: 6.46rem;background-color: #fbfaff;text-align: center;position: relative;
            padding: .16rem;box-sizing: border-box;border-top-left-radius: .4rem;border-bottom-right-radius: .4rem;
        }
        .section1 .wrapper{padding:.3rem .21rem;border:.03rem #a18fd2 solid;line-height: .52rem;font-size: .28rem;color:#6c6c6e;}
        .section1 .wrapper span{line-height: .54rem;color:#333;display: block;padding-top:.12rem;}

        .section1 var{display: block;position: absolute;overflow: hidden;}
        .section1 .var1{left:-.45rem;top:-.42rem;}
        .section1 .var2{right:-.48rem;bottom:-.45rem;transform: rotateZ(180deg);-webkit-transform: rotateZ(180deg)}
        /*.section1 .var2{right:0rem;bottom:0rem;}*/
        .tit{font-size: .32rem;font-weight: normal;line-height: .50rem;margin-top:.3rem;}
        .tit var{color:#fe6b8a;font-weight: bold;}
        .section6,.section1,.section2,.bot{box-shadow: 0 .05rem .04rem .02rem rgba(185, 166, 237, 0.5);}

        .section6{text-align: center;background-color: #fbfaff;margin-top: .72rem;overflow: hidden;
            padding:.17rem .18rem;box-sizing: border-box;}
        .section6 .wrapper{border:.03rem #aa99d6 solid;padding:.42rem .2rem;}
        .section6 p.p1{text-align: left;line-height: .52rem;}
        .section6 p.p3{text-align: center;margin-top: .38rem;}
        .section6 p.p3 img{display: block;margin:auto;}
        .section6 p.p3 b{font-size: .36rem;}
        .section6 p.p3 span{display: block;margin:.46rem auto;}
        .section6 .t1{text-align: left;margin:.5rem 0 .3rem;}
        .section6 .t2{text-align: left;line-height: .48rem;margin-top:.5rem;}
        .section6 var{color:#ae91fa}
        hr{border:none;height:0;line-height: 0;border-top:1px #bfbfbf dashed;margin-top: .36rem;}

        .section6 table{font-size: .28rem;text-align: center;width:5.85rem;margin:0 auto;line-height: .78rem;}
        .section6 table tr td:nth-child(1){text-align: left;}
        .section6 table b{color:#fe6b8a;}
        .section6 table b.b2{font-size: .32rem;}

        .sub_now{position: fixed;font-weight: bold;font-family: 'Microsoft Yahei';letter-spacing: .04rem;bottom:0;width:100%;max-width:750px;color:#fff;font-size: .42rem;text-align: center;line-height: 1rem;background-color: #f071c8;
            text-shadow: 0.03rem 0.03rem 0.03rem rgba(0,0,0,0.2)
        }

        .section2{background-color: #fbfaff;padding:.7rem .38rem;margin-top: .45rem;line-height: .52rem;}

        .bot{text-align: center;margin-top:.46rem;}
        .bot .wrapper{background-color: #fbfaff;padding:.39rem 0;border-radius: .1rem;}
        .bot .wrapper .top{padding: 0 .38rem;}
        .bot input{width:100%;height:.98rem;padding:0 .23rem;-webkit-appearance:none;box-shadow: none;font-size: .3rem;box-sizing: border-box;border:none;border:1px #e5e5e5 solid;margin-bottom: .34rem;border-radius: .1rem;background-color: #fbfaff;}

        .err_msg{color:red;margin:0 0 .3rem;display: none;}

        .sub_ok{position: fixed;top:0;width:100%;left:0;bottom:0;background-color: rgba(0,0,0,0.4);display: none;border-radius: .12rem;
        }
        .sub_ok .wrapper{width:6.08rem;text-align: center;line-height: .4rem;padding: 1.1rem 0;position: relative;left:50%;top:1.42rem;transform:translate(-50%,0);-webkit-transform:translate(-50%,0);
            background-color: #e4d9fb;
            background-size:1.4rem 1.4rem;
            background-image: -webkit-linear-gradient(135deg,rgba(255,255,255,0.25) 25%,transparent 25%,transparent 50%,rgba(255,255,255,0.25) 50%,rgba(255,255,255,0.25) 75%,transparent 75%,transparent);
            background-image: linear-gradient(135deg,rgba(255,255,255,0.25) 25%,transparent 25%,transparent 50%,rgba(255,255,255,0.25) 50%,rgba(255,255,255,0.25) 75%,transparent 75%,transparent);

        }
        .sub_ok .wrapper h4{font-size: .42rem;margin:0 auto 1.4rem; }
        .sub_ok button{font-size:.46rem;color:#fff;-webkit-appearance:none;background:none;border:none;
            width:5.3rem;line-height: 1rem;border-radius: .2rem;font-weight: bold;letter-spacing: .02rem;
            background-color: #a588f5;background: -webkit-linear-gradient(top,#b095f9,#9a7af1);
            box-shadow: 0 0.06rem .22rem .03rem rgba(174,145,20,0.4);
            text-shadow: 0.03rem 0.03rem 0.03rem rgba(0,0,0,0.2)
        }
        .sub_ok em{position: absolute;font-size:.4rem;font-weight: bold;display: block;width:.6rem;height:.6rem;left:50%;margin-left:-0.3rem;bottom:-.8rem;background-color: #5c5c5c;color:#fff;text-align: center;line-height: .6rem;border-radius: 50%;}
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
        <var class="var1">
            <img src="img/ml_0_shop_icon2.png" style="width:1.08rem;" alt="" />
        </var>
        <var class="var2">
            <img src="img/ml_0_shop_icon2.png" style="width:1.08rem;" alt="" />
        </var>
        <div class="wrapper">
            洗完澡，看看镜中的自己<br/>
            不化妆、无修饰，你喜欢镜中的自己吗<br/>
            你是否也会羡慕那些被称为“整形脸”的女人<br/>
            大眼睛，尖下巴，高鼻梁，多少男人为其倾倒<br/>
            昂贵的手术费，你有点承受不起<br/>
            每个女人都有对自己好的权利<br/>
				<span>
				你知道吗？<br/>
				你和女神之间，只差一个“海伦”
				</span>
        </div>
    </div>
    <div class="section6">
        <div class="wrapper">


            <p class="p1">
                《美丽0元购，即刻变女神》活动的开展，是为了让越来越多的爱美人士集合到海伦计划之中，把美丽带到你身边，0门槛，0消费，只为给你一个最美的自己。
            </p>
            <img src="img/hl_sc_ecode.png" style="width:3.4rem;margin-top:.45rem;" alt="" />
            <p class="p3">
                添加客服微信，码上兑豪礼<br/><br/>
                客服电话：<b>400-886-8382</b>
            </p>
            <hr>
            <div class="t1">
                <var>活动项目:</var>（以下项目可全部体验）
            </div>
            <table>
                <tr>
                    <td>线雕</td><td><s>原价 <b>15800</b> 元</s></td><td><b class="b2"><var>免费!</var></b></td>
                </tr>
                <tr>
                    <td>乌金翘睫美眼术</td><td><s>原价 <b>13800</b> 元</s></td><td><b class="b2"><var>免费!</var></b></td>
                </tr>
                <tr>
                    <td>美版超声刀</td><td><s>原价 <b>12800</b> 元</s></td><td><b class="b2"><var>免费!</var></b></td>
                </tr>
                <tr>
                    <td>自体活细胞填充</td><td><s>原价 <b>12800</b> 元</s></td><td><b class="b2"><var>免费!</var></b></td>
                </tr>
            </table>
            <div class="t2">
                <var>活动范围：</var>仅限海伦医院项目<br/><br/>
                <var>活动时间：</var>2017.6.1-6.30<br/><br/>
                <var>活动地点：</var>美人谷APP线上报名<br/><br/>
                <var>技术支持：</var>海伦医院（详询客服）
            </div>
            <br/><br/>
        </div>
    </div>

    <div class="section2">
        <img src="img/ml_0_shop_img1.jpg" style="height:4.73rem;" alt="" /><br/><br/>
        青岛海伦美容医院于2017年元旦开始试营业，位于青岛市崂山区香港中路160号，建筑面积5000余平。<br/><br/>
        开设整形外科、皮肤美容、微整注射、抗衰老四大医美服务中心，汇聚眼部整形、鼻部整形、胸部整形、 形体雕塑、祛斑美白、祛痘嫩肤、紧肤除皱、无创微整等经典项目。<br/><br/>
        全面满足不同需求爱美人士的塑美心愿，完美实现爱美人士蝶变梦想。<br/>
    </div>

    <div class="bot">
        <div class="wrapper">
            <div class="top">
                <input type="text" placeholder="姓名" maxlength='10' name="name" value="">
                <input type="text" placeholder="电话" maxlength='11' name="tel" value="">
                <input type="text" placeholder="城市" maxlength='10' name="city" value="" style="margin-bottom:0">
                <div class="err_msg"></div>
            </div>

        </div>
    </div>

    <br/><br/><br/><br/><br/>
</div>

<div class="sub_now">立即变美</div>

<div class="sub_ok">
    <div class="wrapper">
        <em>×</em>
        <img src="img/ml_0_shop_icon1.png" style="width:2.7rem;" alt="" /><br/><br/>
        <h4>恭喜你，报名成功！</h4>
        <button type="button" name="button" id="shareBtn">分享给好友</button>
    </div>
</div>
</body>
<script type="text/javascript" src="js/zepto.js"></script>
<script src="https://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
<script src="js/jquery.min.js"></script>
<script type="text/javascript">

    var title = '美丽0元购，即刻变女神',
        desc = '即刻变女神，美丽到身边，美版超声刀、自体活细胞填充、线雕、乌金翘睫双眼皮，只要0元。',
        link = 'https://api.meirenguvip.com/webview/activity/signup/ml0shop',
        imgUrl = 'https://api.meirenguvip.com/webview/img/share_0_shop.png';
    function emp_test(a){return 0==a.replace(/^\s+|\s+$/g,"").length?!1:!0};

    $('.share').click(function(){
        $(this).hide();
    });

    $('#shareBtn').click(function () {
        $(".share").show();
    });

    var numa = 60;//倒计时
    // 提交
    $('.sub_now').on('click',function(){

        console.log($('.bot').offset().top);
        window.scrollTo(0,$('.bot').offset().top)
        $('.err_msg').hide();
        var name = $('.bot input[name="name"]').val(),
                tel = $('.bot input[name="tel"]').val(),
                city = $('.bot input[name="city"]').val();
        if(!emp_test(tel)){
            $('.bot .err_msg').show().text('* 手机号码不能为空')
        }else if(!(/^1(3|4|5|7|8)\d{9}$/.test(tel))){
            $('.bot .err_msg').show().text('* 手机号码格式不正确')
        }else{
            $.ajax({
                url:"activity/signup/submit",
                type:"POST",
                data:{"telphone":tel, "name":name, "city":city, "type":3},
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
<script type="text/javascript" src="js/wx_share_common.js"></script>
</html>

