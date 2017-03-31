<%@ page language="java" contentType="text/html;charset=UTF-8" pageEncoding="utf-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
      <c:if test="${not empty faqClasses}">
        <ul>
          <c:forEach items="${faqClasses}" var="class" varStatus="stat">
            <c:if test="${stat.count - 1 == index}"><li class="active"><a href="<%=request.getContextPath()%>/faqs/${class.classId}/${stat.count - 1}">${class.className}</a></li></c:if>
            <c:if test="${stat.count - 1 != index}"><li><a href="<%=request.getContextPath()%>/faqs/${class.classId}/${stat.count - 1}">${class.className}</a></li></c:if>
          </c:forEach>
        </ul>
      </c:if>
    </div>
    <div class="wrapper">
      <ul>
       <c:if test="${not empty classFaqs.list}">
         <c:forEach items="${classFaqs.list}" var="faq" varStatus="stat">
           <c:if test="${stat.first}">
             <li class="active">
               <span class="border_b1"><em>${faq.faqQuestion}</em><var><img src="<%=request.getContextPath()%>/img/arr_right.png" style="width:0.15rem" /></var></span>
               <div class="wrapper">
                 <p>
                    ${faq.faqAnswer}
                 </p>
               </div>
             </li>
           </c:if>
           <c:if test="${not stat.first}">
             <li>
               <span class="border_b1"><em>${faq.faqQuestion}</em><var><img src="<%=request.getContextPath()%>/img/arr_right.png" style="width:0.15rem" /></var></span>
               <div class="wrapper">
                 <p>
                     ${faq.faqAnswer}
                 </p>
               </div>
             </li>
           </c:if>
         </c:forEach>
       </c:if>
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
