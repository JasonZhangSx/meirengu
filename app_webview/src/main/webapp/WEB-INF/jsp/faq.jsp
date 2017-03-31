<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<!doctype html>
<html>
<head>
	<meta charset="UTF-8">
	<title>Document</title>
	<meta id="viewport" name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=0" />
	<meta name="format-detection" content="telephone=no" />
	<link rel="stylesheet" href="<%=request.getContextPath()%>/css/index.css" media="screen" charset="utf-8">
  <style media="screen">
  ::-webkit-scrollbar {
    width:0;height:0;
  }
  </style>
</head>
<body>
	<script type="text/javascript">
	!function(e,t){var n=e.documentElement,i="orientationchange"in window?"orientationchange":"resize",d=(/iPad|iPhone|iPod/.test(navigator.userAgent)&&!window.MSStream,function(){var t=n.clientWidth;t&&(t>=750?(t=750,e.body.style.width="750px"):e.body.style.width=t+"px",n.style.fontSize=100*(t/750)+"px",n.dataset.width=t,n.dataset.percent=100*(t/750))});d(),e.documentElement.classList.add("iosx"+t.devicePixelRatio),e.addEventListener&&t.addEventListener(i,d,!1)}(document,window)
	</script>

	<div class="findProblems_cont">
    <div class="menu border_b1 box">
      <ul>
        <li class="active"><a href="javascript:void(0)">问题分类</a></li><li><a href="javascript:void(0)">问题分类</a></li>
        <li><a href="javascript:void(0)">问题分类</a></li><li><a href="javascript:void(0)">问题分类</a></li>
        <li><a href="javascript:void(0)">问题分类</a></li><li><a href="javascript:void(0)">问题分类</a></li>
        <li><a href="javascript:void(0)">问题分类</a></li><li><a href="javascript:void(0)">问题分类</a></li>
      </ul>
    </div>
    <div class="wrapper">
      <ul>
        <li class="active">
          <span class="border_b1"><em>如何联系医生？</em><var><img src="<%=request.getContextPath()%>/img/arr_right.png" style="width:0.15rem" /></var></span>
          <div class="wrapper">
            <p>
              您可以在医生详情页中点击“电话咨询”联系医生，如遇联系无人回复建议您耐心等待，医生可能在手术不能及时回复您，希望您谅解。
            </p>
          </div>
        </li>
        <li>
          <span class="border_b1"><em>如何联系院生？</em><var><img src="<%=request.getContextPath()%>/img/arr_right.png" style="width:0.15rem" /></var></span>
          <div class="wrapper">
            <p>
              您可以在医院详情页中点击“电话咨询”联系医院。
            </p>
          </div>
        </li>
        <li>
          <span class="border_b1"><em>右功下单后，订单有效期是多久？</em><var><img src="<%=request.getContextPath()%>/img/arr_right.png" style="width:0.15rem" /></var></span>
          <div class="wrapper">
            <p>
              如无特殊说明的，订单需在下单后3个月内使用。希望您尽快去医院消费，以避免您最终忘记造成不必要的损失。
            </p>
          </div>
        </li>
      </ul>
    </div>
  </div>


</body>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/zepto.js"></script>
<script>
$(function(){
  var arr_li = $('.findProblems_cont .wrapper ul li');
  var child1 = arr_li.eq(0).find('p'),
      child1_h = parseInt(child1.css('margin-top'))*2 + child1.height();
  arr_li.eq(0).find('.wrapper').css('height',child1_h + 'px');
  arr_li.on('click',function(){
    arr_li.removeClass('active');
    arr_li.find('.wrapper').height(0);
    var this_p = $(this).find('p'),
        o_h = this_p.height() + parseInt(this_p.css('margin-top')) + parseInt(this_p.css('margin-bottom'));
    $(this).addClass('active');
    $(this).find('.wrapper').css('height',o_h + 'px')
  })
});
$('.menu li').on('click',function(){
  $('.menu li').removeClass('active')
  $(this).addClass('active')
})
</script>
</html>
