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
    <base href="<%=path%>">
    <meta charset="UTF-8">
    <c:if test="${acId == 8}">
        <title>媒体报道</title>
    </c:if>
    <c:if test="${acId == 9}">
        <title>最新资讯</title>
    </c:if>
    <meta id="viewport" name="viewport" content="width=device-width,initial-scale=1,maximum-scale=1,user-scalable=0" />
    <meta name="format-detection" content="telephone=no" />
    <link rel="stylesheet" href="css/index.css" media="screen" charset="utf-8">
</head>
<body style='background-color:#fff;'>
<script type="text/javascript">
    !function(e,t){var n=e.documentElement,i="orientationchange"in window?"orientationchange":"resize",d=(/iPad|iPhone|iPod/.test(navigator.userAgent)&&!window.MSStream,function(){var t=n.clientWidth;t&&(t>=750?(t=750,e.body.style.width="750px"):e.body.style.width=t+"px",n.style.fontSize=100*(t/750)+"px",n.dataset.width=t,n.dataset.percent=100*(t/750))});d(),e.documentElement.classList.add("iosx"+t.devicePixelRatio),e.addEventListener&&t.addEventListener(i,d,!1)}(document,window)
</script>

<div class="meit_bd">
    <div class="wrapper">
        <!-- <a href="javascript:void(0)" class="item clearfix">
            <div class="left" style="background-image:url(https://dummyimage.com/234x150/b293a4/fff&text=someImg)"></div>
            <div class="right">
                <h4>5月PMI为51.2连续10个月在荣枯线上方</h4>
                <var>2017-05-31</var>
            </div>
        </a> -->

    </div>
    <!--加载更多按钮-->
    <div class="js-load-more">点击加载更多</div>
</div>
</body>
<script type="text/javascript" src="js/zepto.js?v=20170621"></script>
<script type="text/javascript">

    // 对Date的扩展，将 Date 转化为指定格式的String
    // 月(M)、日(d)、小时(h)、分(m)、秒(s)、季度(q) 可以用 1-2 个占位符，
    // 年(y)可以用 1-4 个占位符，毫秒(S)只能用 1 个占位符(是 1-3 位的数字)
    // 例子：
    // (new Date()).Format("yyyy-MM-dd hh:mm:ss.S") ==> 2006-07-02 08:09:04.423
    // (new Date()).Format("yyyy-M-d h:m:s.S")      ==> 2006-7-2 8:9:4.18
    Date.prototype.Format = function (fmt) { //author: meizz
        var o = {
            "M+": this.getMonth() + 1, //月份
            "d+": this.getDate(), //日
            "h+": this.getHours(), //小时
            "m+": this.getMinutes(), //分
            "s+": this.getSeconds(), //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds() //毫秒
        };
        if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        for (var k in o)
            if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        return fmt;
    }

    $(function(){

        /*初始化*/
        var counter = 1; /*计数器*/

        /*首次加载*/
        getData(counter);

        /*监听加载更多*/
        $('.js-load-more').on('click', function(){
            counter ++;
            getData(counter);
        });

    });
    function getData(counter){
        $.ajax({
            type: 'get',
            url: 'article/query',
            dataType: 'json',
            data:{'acId':${acId},'page':counter,'perPage':6},
            beforeSend:function(){
                isEnd =false;
                $(".js-load-more").text('加载中...');
            },
            success: function(reponse){
                console.log(reponse);
                var data = reponse.list;
                var sum = reponse.list.length;

                var result = '';

                if(data.length == 0 ){
                    $(".js-load-more").text('没有更多了...');
                }else{
                    $(".js-load-more").text('点击加载更多...');
                }

                for(var i=0; i< sum; i++){
                    if(data[i].articleUrl == ''){
                        //result += '<a href="'+data[i].articleUrl+'" class="item clearfix">';
                    }else {
                        result += '<a href="'+data[i].articleUrl+'" class="item clearfix">';
                    }
                    result += '<div class="left" style="background-image:url(<%=imgURI%>'+data[i].articleImg+')"></div>' +
                            '<div class="right">'+
                            '<h4>' + data[i].articleTitle + '</h4>'+
                            '<var>' + new Date(data[i].createTime).Format("yyyy-MM-dd hh:mm:ss") + '</var>' +
                            '</div>' +
                            '</a>'

                }

                $('.meit_bd .wrapper').append(result);

            },
            error: function(xhr, type){
                alert('Ajax error!');
            },
            complete:function(){
            }
        });
    }
</script>
</html>


