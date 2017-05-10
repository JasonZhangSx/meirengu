<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!doctype html>
<html lang="en">
<head>
    <base href="<%=basePath %>">
    <meta charset="UTF-8">
    <title>Document</title>
    <meta id="viewport" name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=0" />
    <meta name="format-detection" content="telephone=no" />
    <link rel="stylesheet" href="css/index.css" media="screen" charset="utf-8">
</head>
<body>
<script type="text/javascript">
    !function(e,t){var n=e.documentElement,i="orientationchange"in window?"orientationchange":"resize",d=(/iPad|iPhone|iPod/.test(navigator.userAgent)&&!window.MSStream,function(){var t=n.clientWidth;t&&(t>=750?(t=750,e.body.style.width="750px"):e.body.style.width=t+"px",n.style.fontSize=100*(t/750)+"px",n.dataset.width=t,n.dataset.percent=100*(t/750))});d(),e.documentElement.classList.add("iosx"+t.devicePixelRatio),e.addEventListener&&t.addEventListener(i,d,!1)}(document,window)
</script>

<div class="pro_det">
    <header class="bg_fff">
        <img src="https://dummyimage.com/750x480/b293a4/fff&text=someImg" width="100%" alt="" />
        <div class="wrapper">
            <h4>预热中 | 青岛海伦美容医院</h4>
            <span style="margin-right:1.2rem;"><img src="img/pro_det_icon1.jpg" style="width:.3rem" alt="" />股权众筹</span>
            <span><img src="img/pro_det_icon2.jpg" style="width:.3rem" alt="" />山东青岛</span>
            <div class="clear"></div>
            <p>
                海伦美容医院，是国内早家致力于将抗衰老技术融合于医美领域的医学美容机构之一。 <br/>
                海伦整形坐落于美丽的岛城青岛，拥有岛城医美领域细胞抗衰老实验室、超洁净层流手术室群、五星级豪华病房等一流硬件设施。
            </p>
        </div>
    </header>
    <div class="section1 bg_fff">
        <div class="process">
            <div class="pointer" style="left:10%">
                <em></em>
                <div class="top">
                    146%
                </div>
            </div>
        </div>
        <div class="tab">
            <span><b>500万元</b>目标金额</span>
            <span><b>500万元</b>已筹金额</span>
            <span><b>114%</b>完成度</span>
            <span><b>11天</b>剩余时间</span>
        </div>
    </div>

    <div class="section2 bg_fff">
        <span>项目发起人</span><var>青丘白浅</var>
    </div>

    <div class="section3 bg_fff">
        <div class="leader">
            <span>领投人</span>
            <div class="">
                <em><img src="https://dummyimage.com/80x80/b293a4/fff&text=someImg" alt="" /> Robo</em>
                <em class="">
                    &yen; 1000万
                </em>
            </div>
        </div>
        <div class="tit">
            已有999人跟投此项目
        </div>
        <div class="supporter">
            <img src="https://dummyimage.com/80x80/b293a4/fff&text=someImg" alt="" />
            <img src="https://dummyimage.com/80x80/b293a4/fff&text=someImg" alt="" />
            <img src="https://dummyimage.com/80x80/b293a4/fff&text=someImg" alt="" />
            <img src="https://dummyimage.com/80x80/b293a4/fff&text=someImg" alt="" />
            <img src="https://dummyimage.com/80x80/b293a4/fff&text=someImg" alt="" />
            <img src="https://dummyimage.com/80x80/b293a4/fff&text=someImg" alt="" />
            <span>·····</span>
        </div>
    </div>
    <div class="bg_fff">
        <div class="menu">
            <span class="active"><a href="javascript:void(0)">投资回报</a></span>
            <span><a href="javascript:void(0)">项目介绍</a></span>
            <span><a href="javascript:void(0)">投资方案</a></span>
        </div>
    </div>

    <div class="section4">
        <div class="wrapper huibao" style="display:block">
            <div class="item">
                <div class="top">
                    <span>NO.1 档位名称</span><em>¥ 10,000.00/份</em>
                </div>
                <p>
                    自体活性脂肪面部填充，自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充。
                </p>
                <div class="bot">
                    <h5>剩余45/200份（每人限购5份）<small>项目结束50天后发送</small> </h5>
                    <a href="javascript:void(0)">我要支持</a>
                </div>
            </div>
            <div class="item">
                <div class="top">
                    <span>NO.1 档位名称</span><em>¥ 10,000.00/份</em>
                </div>
                <p>
                    自体活性脂肪面部填充，自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充。
                </p>
                <div class="bot">
                    <h5>剩余45/200份（每人限购5份）<small>项目结束50天后发送</small> </h5>
                    <a href="javascript:void(0)">我要支持</a>
                </div>
            </div>
            <div class="item">
                <div class="top">
                    <span>NO.1 档位名称</span><em>¥ 10,000.00/份</em>
                </div>
                <p>
                    自体活性脂肪面部填充，自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充。
                </p>
                <div class="bot">
                    <h5>剩余45/200份（每人限购5份）<small>项目结束50天后发送</small> </h5>
                    <a href="javascript:void(0)">我要支持</a>
                </div>
            </div>
        </div>

        <div class="wrapper intro">
            <div class="txt">
                <b>项目介绍</b>
                <p>
                    自体活性脂肪面部填充，自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充。
                </p>
                <img src="https://dummyimage.com/750x480/b293a4/fff&text=someImg" alt="" />
                <b>项目介绍</b>
                <p>
                    自体活性脂肪面部填充，自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充。
                </p>
                <b>项目介绍</b>
                <p>
                    自体活性脂肪面部填充，自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充。
                </p>
                <img src="https://dummyimage.com/750x480/b293a4/fff&text=someImg" alt="" />
                <p>
                    自体活性脂肪面部填充，自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充。
                </p>
            </div>
        </div>

        <div class="wrapper intro">
            <div class="txt">
                <b>项目介绍222</b>
                <p>
                    自体活性脂肪面部填充，自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充。
                </p>
                <img src="https://dummyimage.com/750x480/b293a4/fff&text=someImg" alt="" />
                <b>项目介绍</b>
                <p>
                    自体活性脂肪面部填充，自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充。
                </p>
                <b>项目介绍</b>
                <p>
                    自体活性脂肪面部填充，自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充。
                </p>
                <img src="https://dummyimage.com/750x480/b293a4/fff&text=someImg" alt="" />
                <p>
                    自体活性脂肪面部填充，自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充自体活性脂肪面部填充。
                </p>
            </div>
        </div>
    </div>

    <div style="height:1.5rem"></div>
    <div class="fixed">
        <span>下载美人谷app更多精彩等着你</span>
        <a href="javascript:void(0)">立即下载</a>
    </div>
</div>


</body>
<script type="text/javascript" src="js/zepto.js"></script>
<script type="text/javascript">
    $(document).ready(function(){

        $('.menu span').on('click',function(){
            var index = $(this).index();
            $('.menu span').removeClass('active');
            $('.section4').find('.wrapper').hide();
            $(this).addClass('active');
            $('.section4').find('.wrapper').eq(index).show();
        })

    })
</script>
</html>

