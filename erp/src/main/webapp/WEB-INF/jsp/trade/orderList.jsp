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
    <title>订单列表</title>
</head>
<body>
<section class="Hui-article-box" style="left:0;top:0">
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 订单管理 <span class="c-gray en">&gt;</span> 订单列表 <a
            class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"
            href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
    <div class="Hui-article">
        <article class="cl pd-20">
            <form action="delete" id="itemForm" method="post">
                <div class="text-c">
                    订单编号：<input type="text" class="input-text" style="width:120px;" value="${query.orderSn}" name="orderSn">　
                    用户账号：<input type="text" class="input-text" style="width:120px;" value="${query.userPhone}" name="userPhone">　
                    项目名称：<input type="text" class="input-text" style="width:120px;" value="${query.itemName}" name="itemName">
                    订单状态：　　
                            <span class="select-box mr-20" style="width:120px" >
                                <select name="orderState" class="select">
                                    <option value="4" <c:if test='${query.orderState == 4}'> selected </c:if>>待支付</option>
                                    <option value="6" <c:if test='${query.orderState == 6}'> selected </c:if>>已支付</option>
                                </select>
                            </span>
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
                        <th>订单编号</th>
                        <th>账号</th>
                        <th>项目名称</th>
                        <th>回报档位</th>
                        <th>单价</th>
                        <th>数量</th>
                        <th>订单总额</th>
                        <th>本金</th>
                        <th>抵扣券金额</th>
                        <th>年化收益率</th>
                        <th>期限</th>
                        <th>分红方式</th>
                        <th>收货人</th>
                        <th>收货地址</th>
                        <th>状态</th>
                        <th>时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${page.list}" var="item">
                        <tr class="text-c">
                            <td>${item.orderSn}</td>
                            <td>${item.userPhone}</td>
                            <td>${item.itemName}</td>
                            <td>${item.itemLevelName}</td>
                            <td>${item.itemLevelAmount}</td>
                            <td>${item.itemNum}</td>
                            <td>${item.orderAmount}</td>
                            <td>${item.costAmount}</td>
                            <td>${item.rebateAmount}</td>
                            <td>${item.yearRate}%</td>
                            <td>${item.investmentPeriod}个月</td>
                            <td>${item.shareBonusPeriod}月/次</td>
                            <td>${item.userName}(${item.userPhone})</td>
                            <td>${item.province}${item.city}${item.areas}${item.userAddress}</td>
                            <td>
                                <c:if test="${item.orderState == 4}">待支付</c:if>
                                <c:if test="${item.orderState == 6}">已支付</c:if>
                            </td>
                            <td>
                                <jsp:useBean id="dateObject" class="java.util.Date" scope="page"></jsp:useBean>
                                <jsp:setProperty property="time" name="dateObject" value="${item.createTime}"/>
                                <fmt:formatDate value="${dateObject}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </td>

                            <td class="f-14 td-manage">
                                <a style="text-decoration:none" class="ml-5"
                                   onClick="project_edit('众筹-新建项目列表-添加基本信息','/erp/item/to_edit?itemId=${item.itemId}','10001')"
                                   href="javascript:;" title="项目编辑"><i class="Hui-iconfont">&#xe6df;</i></a>
                                <a style="text-decoration:none" class="ml-5" onClick="project_del(this,'10001')"
                                   href="javascript:;" title="处理"><i class="Hui-iconfont">&#xe6e2;</i></a>
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