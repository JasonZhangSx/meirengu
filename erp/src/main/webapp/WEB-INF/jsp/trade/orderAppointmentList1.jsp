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
    <title>待审核预约订单列表</title>
</head>
<body>
<section class="Hui-article-box" style="left:0;top:0">
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 订单管理 <span class="c-gray en">&gt;</span> 待审核预约订单 <a
            class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"
            href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
    <div class="Hui-article">
        <article class="cl pd-20">
            <form action="delete" id="itemForm" method="post">
                <div class="text-c">
                    订单编号：<input type="text" class="input-text" style="width:120px;" value="${query.orderSn}" name="orderSn">　
                    用户账号：<input type="text" class="input-text" style="width:120px;" value="${query.userPhone}" name="userPhone">　
                    项目名称：<input type="text" class="input-text" style="width:120px;" value="${query.itemName}" name="itemName">　
                    <button name="" id="" class="btn btn-success radius" type="submit"><i class="Hui-iconfont">&#xe665;</i>
                        查 询
                    </button>
                </div>
            </form>

            <div class="cl pd-5 bg-1 bk-gray mt-20">
                <span class="r" style="line-height:30px;">共有数据：<strong></strong> 条</span></div>
            <div class="mt-20">
                <table id="example" class="table table-striped table-bordered">
                    <thead>
                    <tr>
                        <th></th>
                        <th>姓名</th>
                        <th>地点</th>
                        <th>薪水</th>
                        <th>入职时间</th>
                        <th>办公地点</th>
                        <th>编号</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </article>
    </div>
</section>
<input type="hidden" id="errcode">
<input type="hidden" id="errmsg">


<script type="text/javascript">
    var table;
    $(function () {

        table = $('#example').DataTable({
            "serverSide": true,  //启用服务器端分页
            "ajax": {
                url: "/erp/order_appointment",
                "dataSrc": "aaData"
            },
            "order": [[1, 'asc']],// dt默认是第一列升序排列 这里第一列为序号列，所以设置为不排序，并把默认的排序列设置到后面

            "columns": [
                {"data": null}, //因为要加行号，所以要多一列，不然会把第一列覆盖
                {"data": "createTime"},
                {"data": "finishedTime"},
                {"data": "flag"},
                {"data": "investmentPeriod"},
                {"data": "isShareBonus"},
                {"data": "itemId"}
            ],
            "columnDefs": [
                {
                    "searchable": false,
                    "orderable": false,
                    "targets": [0.-1]
                }

            ],
            "language": {
                "lengthMenu": "每页_MENU_ 条记录",
                "zeroRecords": "没有找到记录",
                "info": "第 _PAGE_ 页 ( 总共 _PAGES_ 页 )",
                "infoEmpty": "无记录",
                "search": "搜索：",
                "infoFiltered": "(从 _MAX_ 条记录过滤)",
                "paginate": {
                    "previous": "上一页",
                    "next": "下一页"
                }
            }



        });

        //添加序号
        //不管是排序，还是分页，还是搜索最后都会重画，这里监听draw事件即可
        table.on('draw.dt',function() {
            table.column(0, {
                search: 'applied',
                order: 'applied'
            }).nodes().each(function(cell, i) {
                //i 从0开始，所以这里先加1
                i = i+1;
                //服务器模式下获取分页信息
                var page = table.page.info();
                //当前第几页，从0开始
                var pageno = page.page;
                //每页数据
                var length = page.length;
                //行号等于 页数*每页数据长度+行号
                var columnIndex = (i+pageno*length);
                cell.innerHTML = columnIndex;
            });
        }).draw();
    });
    /*
    $('.table-sort').dataTable({
        "aaSorting": [[1, "desc"]],//默认第几个排序
        "bStateSave": true,//状态保存
        "aoColumnDefs": [
            //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
            {"orderable": false, "aTargets": [0, 8]}// 不参与排序的列
        ]
    });*/

    /**
     *编辑方法
     **/
    function edit(orderId) {
        console.log(orderId);
        layer.confirm('是否通过？', {
                btn: ['通过', '不通过', '取消'],
                shade: false,
                closeBtn: 0
            },
            //order_state 通过是2，不通过是3
            function () {
                if (appointmentAduitAjax(orderId, 2)) {
                    layer.msg('已通过', {icon: 6, time: 1000});
                } else {
                    layer.msg('错误代码: ' + $("#errcode").val() + ", " + $("#errmsg").val(), {icon: 6, time: 5000});
                }
            },
            function () {
                if (appointmentAduitAjax(orderId, 3)) {
                    layer.msg('未通过', {icon: 5, time: 1000});
                } else {
                    layer.msg('错误代码: ' + $("#errcode").val() + ", " + $("#errmsg").val(), {icon: 6, time: 5000});
                }
            });
    }

    /*预约订单审核*/
    function appointment_audit(obj, orderId) {
        layer.confirm('是否通过？', {
                btn: ['通过', '不通过', '取消'],
                shade: false,
                closeBtn: 0
            },
            //order_state 通过是2，不通过是3
            function () {
                if (appointmentAduitAjax(orderId, 2)) {
                    $(obj).parents("tr").find(".td-status").html('<span class="label label-success radius">已通过</span>');
                    $(obj).remove();
                    layer.msg('已通过', {icon: 6, time: 1000});
                } else {
                    layer.msg('错误代码: ' + $("#errcode").val() + ", " + $("#errmsg").val(), {icon: 6, time: 5000});
                }
            },
            function () {
                if (appointmentAduitAjax(orderId, 3)) {
                    $(obj).parents("tr").find(".td-status").html('<span class="label label-danger radius">未通过</span>');
                    $(obj).remove();
                    layer.msg('未通过', {icon: 5, time: 1000});
                } else {
                    layer.msg('错误代码: ' + $("#errcode").val() + ", " + $("#errmsg").val(), {icon: 6, time: 5000});
                }
            });
    }

    function appointmentAduitAjax(orderId, orderState) {
        var url = "<%=basePath %>/order_appointment/"+orderId;
        var flag=false;
        $.ajax({
            type: "post",
            url: url,
            cache:false,
            async:false,
            data:{order_state:orderState},
            dataType:"json",
            success: function(data){
                var code = data.code;//200是成功，其他是失败
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