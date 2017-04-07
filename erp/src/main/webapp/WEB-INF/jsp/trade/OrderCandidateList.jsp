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
    <title>候补预约列表</title>
</head>
<body>
<section class="Hui-article-box" style="left:0;top:0">
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 订单管理 <span class="c-gray en">&gt;</span> 候补预约列表 <a
            class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"
            href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
    <div class="Hui-article">
        <article class="cl pd-20">
            <form action="delete" id="itemForm" method="post">
                <div class="text-c">
                    用户账号：<input type="text" class="input-text" style="width:120px;" value="${query.userPhone}" name="userPhone">　
                    项目名称：<input type="text" class="input-text" style="width:120px;" value="${query.itemName}" name="itemName">　
                    <button name="" id="" class="btn btn-success radius" type="submit"><i class="Hui-iconfont">&#xe665;</i>
                        查 询
                    </button>
                </div>
            </form>

            <div class="cl pd-5 bg-1 bk-gray mt-20">
                <span class="r" style="line-height:30px;">共有数据：<strong>${page.totalCount}</strong> 条</span></div>
            <div class="mt-20">
                <table class="table table-border table-bordered table-bg table-hover table-sort">
                    <thead>
                    <tr class="text-c">
                        <th>账号</th>
                        <th>项目名称</th>
                        <th>回报档位</th>
                        <th>数量</th>
                        <th>订单总额</th>
                        <th>微信号</th>
                        <th>预约时间</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${page.list}" var="item">
                        <tr class="text-c">
                            <td>${item.userPhone}</td>
                            <td>${item.itemName}</td>
                            <td>${item.itemLevelName}</td>
                            <td>${item.itemNum}</td>
                            <td>${item.orderAmount}</td>
                            <td>${item.userWeixin}</td>
                            <td>
                                <jsp:useBean id="dateObject" class="java.util.Date" scope="page"></jsp:useBean>
                                <jsp:setProperty property="time" name="dateObject" value="${item.createTime}"/>
                                <fmt:formatDate value="${dateObject}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>
                            <td class="td-status">
                                <c:if test="${item.status == 0}">未处理</c:if>
                                <c:if test="${item.status == 1}">已处理</c:if>
                            </td>
                            <td class="f-14 td-manage">
                                <a style="text-decoration:none" class="ml-5"
                                   onClick="candidate_handle(this,${item.id})"
                                   href="javascript:;" title="处理"><i class="Hui-iconfont">&#xe6df;</i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>
        </article>
    </div>
</section>
<input type="hidden" id="errcode">
<input type="hidden" id="errmsg">
<script type="text/javascript">

    $('.table-sort').dataTable({
        "aaSorting": [[1, "desc"]],//默认第几个排序
        "bStateSave": true,//状态保存
        "aoColumnDefs": [
            //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
            {"orderable": false, "aTargets": [0, 8]}// 不参与排序的列
        ]
    });

    /*预约订单审核*/
    function candidate_handle(obj, id) {
        layer.confirm('是否处理完成？', {
                btn: ['完成', '取消'],
                shade: false,
                closeBtn: 0
            },
            //state 处理完成为1
            function () {
                if (candidateHandleAjax(id, 1)) {
                    $(obj).parents("tr").find(".td-status").html('<span class="label label-danger radius">已处理</span>');
                    $(obj).remove();
                    layer.msg('已处理', {icon: 5, time: 1000});
                } else {
                    layer.msg('错误代码: ' + $("#errcode").val() + ", " + $("#errmsg").val(), {icon: 6, time: 5000});
                }
            });
    }

    function candidateHandleAjax(id, status) {
        var url = "<%=basePath %>/order_candidate/handle/"+id;
        var flag=false;
        $.ajax({
            type: "post",
            url: url,
            cache:false,
            async:false,
            data:{status:status},
            dataType:"json",
            success: function(data){
                var code = data.code;//200 is success，other is fail
                if(code=="200"){
                    flag=true;
                }else{
                    $("#errcode").val(data.code);
                    $("#errmsg").val(data.msg);
                    flag=false;
                }
            }
        });
        return flag;
    }
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>