<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="../common/common.jsp"%>
<html>
<head>
    <base href="<%=basePath %>">
    <meta charset=utf-8>
    <meta name=renderer content=webkit|ie-comp|ie-stand>
    <meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
    <meta name=viewport content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <meta http-equiv=Cache-Control content=no-siteapp/>
    <link rel=Bookmark href=favicon.ico>
    <link rel="Shortcut Icon" href=favicon.ico/>
    <meta name=keywords content=xxxxx>
    <meta name=description content=xxxxx>
    <title>待合作项目列表</title>
</head>
<body>
<section class="Hui-article-box" style="top: 0; left: 0;">
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 众筹项目 <span class="c-gray en">&gt;</span> 待合作项目 <a
            class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"
            href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
    <div class="Hui-article">
        <article class="cl pd-20">
            <div class="text-c">
                项目编号：<input type="text" class="input-text" style="width:120px;">　
                项目名称：<input type="text" class="input-text" style="width:120px;">　
                <button name="" id="" class="btn btn-success radius" type="submit"><i class="Hui-iconfont">&#xe665;</i>
                    查 询
                </button>
            </div>
            <div class="cl pd-5 bg-1 bk-gray mt-20">
                <span class="l"></span>
                <span class="r" style="line-height:30px;">共有数据：<strong>1</strong> 条</span></div>
            <div class="mt-20">
                <table class="table table-border table-bordered table-bg table-hover table-sort">
                    <thead>
                    <tr class="text-c">
                        <th width="40">编号</th>
                        <th width="100">项目名称</th>
                        <th width="100">众筹类型</th>
                        <th>项目分类</th>
                        <th width="80">众筹金额</th>
                        <th width="70">预热天数</th>
                        <th width="60">众筹天数</th>
                        <th>提交时间</th>
                        <th width="100">操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="item">
                        <tr class="text-c">
                            <td>${item.itemId}</td>
                            <td>${item.itemName}</td>
                            <td>${item.typeName}</td>
                            <td></td>
                            <td>${item.targetAmount}</td>
                            <td>${item.preheatingDays}</td>
                            <td>${item.crowdDays}</td>
                            <td><jsp:useBean id="dateValue" class="java.util.Date"/>
                                <jsp:setProperty name="dateValue" property="time" value="${item.updateTime}"/>
                                <fmt:formatDate value="${dateValue}" pattern="yyyy/MM/dd HH:mm:ss"/>
                            </td>
                            <td class="f-14 td-manage">
                                <a style="text-decoration:none" class="ml-5"
                                   onClick="project_edit('众筹-待合作项目-设置','众筹-待合作项目-设置.html','10001')" href="javascript:;"
                                   title="设置">设置</a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </article>
    </div>
</section>
<script type="text/javascript">
    $('.table-sort').dataTable({
        "aaSorting": [[1, "desc"]],//默认第几个排序
        "bStateSave": true,//状态保存
        "aoColumnDefs": [
            //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
            {"orderable": false, "aTargets": [0, 8]}// 不参与排序的列
        ]
    });

    $(function () {
        $(".Hui-aside ul a").on("click", function () {
            console.log($(this).attr("data-href")), $(".content_iframe").attr("src", $(this).attr("data-href"))
        })
    })
    //*项目-编辑*/
    function project_edit(title, url, id, w, h) {
        var index = layer.open({
            type: 2,
            title: title,
            content: url
        });
        layer.full(index);
    }
    /*项目-删除*/
    function project_del(obj, id) {
        layer.confirm('确认要删除吗？', function (index) {
            $.ajax({
                type: 'POST',
                url: '',
                dataType: 'json',
                success: function (data) {
                    $(obj).parents("tr").remove();
                    layer.msg('已删除!', {icon: 1, time: 1000});
                },
                error: function (data) {
                    console.log(data.msg);
                },
            });
        });
    }
    /*项目-下架*/
    function project_stop(obj, id) {
        layer.confirm('确认要下架吗？', function (index) {
            $(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" onClick="project_start(this,id)" href="javascript:;" title="发布"><i class="Hui-iconfont">&#xe603;</i></a>');
            $(obj).parents("tr").find(".td-status").html('<span class="label label-defaunt radius">已下架</span>');
            $(obj).remove();
            layer.msg('已下架!', {icon: 5, time: 1000});
        });
    }

    /*项目-发布*/
    function project_start(obj, id) {
        layer.confirm('确认要发布吗？', function (index) {
            $(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" onClick="picture_stop(this,id)" href="javascript:;" title="下架"><i class="Hui-iconfont">&#xe6de;</i></a>');
            $(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">已发布</span>');
            $(obj).remove();
            layer.msg('已发布!', {icon: 6, time: 1000});
        });
    }
    /*项目-申请上线*/
    function project_shenqing(obj, id) {
        $(obj).parents("tr").find(".td-status").html('<span class="label label-default radius">待审核</span>');
        $(obj).parents("tr").find(".td-manage").html("");
        layer.msg('已提交申请，耐心等待审核!', {icon: 1, time: 2000});
    }

    /*项目-删除*/
    function project_del(obj, id) {
        layer.confirm('确认要删除吗？', function (index) {
            $(obj).parents("tr").remove();
            layer.msg('已删除!', {icon: 1, time: 1000});
        });
    }
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>
