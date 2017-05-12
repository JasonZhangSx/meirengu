<%@ page import="java.text.SimpleDateFormat" %>
<%@ page import="java.util.Date" %><%--
  Created by IntelliJ IDEA.
  User: xiaoyang
  Date: 2017/4/19
  Time: 16:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html> <head>
    <meta charset=utf-8>
    <meta name=renderer content=webkit|ie-comp|ie-stand>
    <meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
    <meta name=viewport content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <meta http-equiv=Cache-Control content=no-siteapp />
    <link rel=Bookmark href=favicon.ico>
    <link rel="Shortcut Icon" href=favicon.ico />
    <meta name=keywords content=xxxxx>
    <meta name=description content=xxxxx> <!--[if lt IE 9]>
    <script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js" ></script>
    <script>DD_belatedPNG.fix('*');</script><![endif]--> </head></html>

<title>图片列表</title>
</head>
<body>
<section class="Hui-article-box" style="top: 0; left: 0">
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 众筹项目
        <span class="c-gray en">&gt;</span> 轮播图列表
        <a class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px" href="javascript:location.replace(location.href);" title="刷新" >
            <i class="Hui-iconfont">&#xe68f;</i></a>
    </nav>
    <div class="Hui-article">
        <article class="cl pd-20">
            <form action="slideshow" method="get">
                <div class="text-c">
                    状态：<span class="select-box mr-20" style="width:120px">
                            <select id="status" name="status" class="select">
                                <option value="">请选择</option>
                                <option value="0">已下架</option>
                                <option value="1">已上架</option>
                            </select>
      					</span>　
                    位置：<span class="select-box mr-20" style="width:120px">
                            <select id="slideshowPosition" name="slideshowPosition" class="select">
                                <option value="">请选择</option>
                                <option value="1">PC</option>
                                <option value="2">APP</option>
                                <option value="3">H5</option>
                            </select>
      					</span>　
                    <button name="" id="" class="btn btn-success radius" type="submit"><i class="Hui-iconfont">&#xe665;</i> 查 询</button>
                </div>
            </form>
            <div class="cl pd-5 bg-1 bk-gray mt-20">
  				<span class="l">
            <a class="btn btn-primary radius" onClick="slideshow_add('CMS-轮播图管理-添加','slideshow/add','10001')"  href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添 加</a>
          </span></div>

            <div class="mt-20">
                <table class="table table-border table-bordered table-bg table-hover table-sort">
                    <thead>
                    <tr class="text-c">
                        <th width="40">序号</th>
                        <th width="100">位置</th>
                        <th width="100">轮播图名称</th>
                        <th>链接</th>
                        <th width="80">状态</th>
                        <th width="70">添加时间</th>
                        <th width="100">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="slideshow">
                        <tr class="text-c">
                            <td>${slideshow.id}</td>
                            <c:choose>
                                <c:when test="${slideshow.slideshowPosition==1}">
                                    <td>PC</td>
                                </c:when>
                                <c:when test="${slideshow.slideshowPosition==2}">
                                    <td>APP</td>
                                </c:when>
                                <c:when test="${slideshow.slideshowPosition==3}">
                                    <td>H5</td>
                                </c:when>
                            </c:choose>
                            <td>${slideshow.slideshowName}</td>
                            <td>${slideshow.slideshowLink}</td>
                            <c:choose>
                                <c:when test="${slideshow.status==1}">
                                    <td>已上架</td>
                                </c:when>
                                <c:when test="${slideshow.status==0}">
                                    <td>已下架</td>
                                </c:when>
                            </c:choose>
                            <td>
                                <c:set var="createTime" value="${slideshow.createTime}" scope="session"/>
                                <%=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(String.valueOf(session.getAttribute("createTime"))))%>
                            </td>
                            <td class="f-14 td-manage">
                                <c:choose>
                                    <c:when test="${slideshow.status==1}">
                                        <a style="text-decoration:none" onClick="project_stop(this,${slideshow.id})" href="javascript:;" title="下架"><i class="Hui-iconfont">&#xe6de;</i></a>
                                    </c:when>
                                    <c:when test="${slideshow.status==0}">
                                        <a style="text-decoration:none" onClick="project_start(this,${slideshow.id})" href="javascript:;" title="发布"><i class="Hui-iconfont">&#xe603;</i></a>
                                    </c:when>
                                </c:choose>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </article>
    </div>
</section>

<script>$(function(){$(".Hui-aside ul a").on("click",function(){console.log($(this).attr("data-href")),$(".content_iframe").attr("src",$(this).attr("data-href"))})}),$(".toTop").show()</script>
<!-- 时间插件 -->
<link href="lib/datetimepicker/datetimepicker.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="lib/datetimepicker/datetimepicker.js"></script>
<script>
    var table = $('.table-sort').dataTable({
//        "aaSorting": [[0, "desc"]],
        "bSort" : false,
        "bStateSave": true,
    });
    function slideshow_add(title,url,id,w,h){
        var index = layer.open({
            type: 2,
            title: title,
            content: url
        });
        layer.full(index);
    }
    /*轮播图-下架*/
    function project_stop(obj,id){
        layer.confirm('确认要下架吗？',function(index){
            $.ajax({
                url:"slideshow/update",
                type:"POST",
                data:{
                    "id":id,
                    "status":"0"
                },
                success : function(data) {
                    var map = eval("("+data+")");
                    if(map.code==200){
                        layer.msg('已下架!', {icon: 5, time: 1000});
                        location.href="slideshow";
                    }else{
                        layer.msg('操作失败! 请重试!', {icon: 5, time: 1000});
                    }
                }
            })
        });
    }
    /*轮播图-上架*/
    function project_start(obj,id){
        layer.confirm('确认要发布吗？',function(index){
            $.ajax({
                url:"slideshow/update",
                type:"POST",
                data:{
                    "id":id,
                    "status":"1"
                },
                success : function(data) {
                    var map = eval("("+data+")");
                    if(map.code==200){
                        layer.msg('已发布!',{icon: 6,time:1000});
                        location.href="slideshow";
                    }else{
                        layer.msg('操作失败! 请重试!', {icon: 5, time: 1000});
                    }
                }
            })
        });
    }
</script>
</body>
</html>