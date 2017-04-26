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
    <style type="text/css">
        th,td { white-space: nowrap; }
        .myloading{position: absolute;left:0;top:0;width:100%;bottom:0;background-color:rgba(0,0,0,0.6);display:flex;justify-content: center;align-items: center;z-index:999999;}
    </style>
</head>
<body>
<section class="Hui-article-box" style="left:0;top:0">
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 订单管理 <span class="c-gray en">&gt;</span> 待审核订单列表 <a
            class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"
            href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
    <div class="Hui-article">
        <article class="cl pd-20">
            <div class="text-c">
                订单编号：<input type="text" class="input-text" style="width:120px;" id="orderSn">　
                用户账号：<input type="text" class="input-text" style="width:120px;" id="userPhone">　
                项目名称：<input type="text" class="input-text" style="width:120px;" id="itemName">

                <button name="" id="" onclick="search()" class="btn btn-success radius"><i class="Hui-iconfont">&#xe665;</i>
                    查 询
                </button>
            </div>

            <div class="cl pd-5 bg-1 bk-gray mt-20">
                <span class="r" style="line-height:30px;">共有数据：<strong><span id="totalCount"></span></strong> 条</span></div>
            <div class="mt-20">
                <table id="dt" class="table table-border table-bordered table-bg table-hover table-sort">
                    <thead>
                    <tr class="text-c">
                        <th></th>
                        <th>退款单号</th>
                        <th>原订单号</th>
                        <th>账号</th>
                        <th>项目名称</th>
                        <th>回报档位</th>
                        <th>数量</th>
                        <th>订单总额</th>
                        <th>退款金额</th>
                        <th>退款原因</th>
                        <th>备注</th>
                        <th>状态</th>
                        <th>申请时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </article>
    </div>
</section>
<input type="hidden" id="errcode">
<input type="hidden" id="errmsg">
<input type="hidden" id="orderId">
<input type="hidden" id="refundId">
<div class="zd_div" style="display:none">
    <article class="cl pd-20">
        <div class="row cl">
            <label class="form-label col-xs-2 col-sm-2">审核：</label>
            <div class="formControls col-xs-8 col-sm-8">
                  <span class="select-box">
                      <select id="refundState" class="select">
                            <option value="2">同意</option>
                            <option value="3">拒绝</option>
                      </select>
                  </span>
            </div>
        </div>
        <div class="row cl pt-10">
            <label class="form-label col-xs-2 col-sm-2">备注：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <textarea id="adminMessage" cols="" rows="" class="textarea"  placeholder="请输入处理原因" datatype="*10-100" dragonfly="true" nullmsg="备注不能为空！" onKeyUp="$.Huitextarealength(this,200)"></textarea>
                <p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>
            </div>
        </div>

        <div class="row cl pt-10">
            <div class="text-c">
                <input ONCLICK="refundAuditAjax()" class="btn btn-primary radius" type="submit" value="&nbsp;&nbsp;确定&nbsp;&nbsp;">
            </div>
        </div>
    </article>
</div>

<style media="screen">
    .zd_div{position: fixed;z-index:9999;left:0;bottom:0;right:0;top:0;background-color: rgba(0,0,0,0.4);}
    .zd_div article{position: absolute;z-index: 19891015;width:600px;top:50%;left:50%;transform: translate3D(-300px,-50%,0);background-color: #fff;}

</style>

<!--定义操作列按钮模板-->
<!--说下这里使用模板的作用，除了显示和数据分离好维护以外，绑定事件和传值也比较方便，希望大家能不拼接html则不拼接-->
<script id="tpl" type="text/x-handlebars-template">
    {{#each func}}
    <button type="button" class="btn btn-{{this.type}} btn-sm" onclick="{{this.fn}}">{{this.name}}</button>
    {{/each}}
</script>

<script type="text/javascript">

    var table;

    $(function () {
        var tpl = $("#tpl").html();
        //预编译模板
        var template = Handlebars.compile(tpl);
        table = $('#dt').DataTable({

            'ajax': {
                'url': '<%=basePath %>refund'
            },
            "rowCallback": function( row, data, index ) {
                // 加载总记录数
                loadTotalCount();
            },
            "order": [[1, 'asc']],// dt默认是第一列升序排列 这里第一列为序号列，所以设置为不排序，并把默认的排序列设置到后面
            "serverSide": true,  //启用服务器端分页
            "processing": true,
            "scrollX": true, //允许水平滚动
            "columns": [
                {"data": null}, //因为要加行号，所以要多一列，不然会把第一列覆盖
                {"data": "refundSn"},
                {"data": "orderSn"},
                {"data": "userPhone"},
                {"data": "itemName"},
                {"data": "itemLevelName"},
                {"data": "itemNum"},
                {"data": "orderAmount"},
                {"data": "orderRefund"},
                {"data": "userMessage"},
                {"data": "refundMessage"},
                {
                    "data": "refundState",
                    "render": function (data, type, row, meta) {
                        if (data == 1) {
                            return "待审核";
                        } else if (data == 2){
                            return "已通过";
                        }
                    }
                },
                {
                    "data": "createTime",
                    "render": function (data, type, row, meta) {
                        return new Date(data).Format("yyyy-MM-dd HH:mm:ss");
                    }
                },
                {"data": null}
            ],
            "columnDefs": [
                {
                    "searchable": false,
                    "orderable": false,
                    "targets": [0.-1]
                },
                { "name": "orderSn",   "targets": 2 },
                { "name": "userPhone",  "targets": 3 },
                { "name": "itemName", "targets": 4 },
                {
                    "targets": 13,
                    "render": function (data, type, row, meta) {
                        var context =
                            {
                                func: [
                                    {"name": "查看", "fn": "detail(\'" + row.refundId + "\')", "type": "default"},
                                    {"name": "审核", "fn": "edit(\'" + row.refundId + "\',\'" + row.orderId + "\')", "type": "primary"}
                                ]
                            };
                        var html = template(context);
                        return html;
                    }
                }

            ],
            "language": {
                "processing": "<div class='myloading'><img src='<%=basePath %>static/h-ui/images/loading/loading-b.gif'> <br/>&nbsp&nbsp&nbsp&nbsp&nbsp努力加载数据中.</div>",
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

    function loadTotalCount(){
        var info = table.page.info();
        var totalCount = info.recordsTotal;
        $("#totalCount").html(totalCount);
    }

    /**
     * 检索
     **/
    function search(){
        var orderSn = $("#orderSn").val();
        var userPhone = $("#userPhone").val();
        var itemName = $("#itemName").val();
        table.column(2).search(orderSn).column(3).search(userPhone).column(4).search(itemName).draw();
    }

    function zd_alert(){
        $('.zd_div').show();
    }
    $('.zd_div').on('click',function(){
        $('.zd_div').fadeOut();
    })
    $('.zd_div article').on('click',function(event){
        event.stopPropagation();
    })


    /**
     * 编辑方法
     **/
    function edit(refundId, orderId) {
        $("#refundId").val(refundId);
        $("#orderId").val(orderId);
        zd_alert();
    }
    function refundAuditAjax() {
        var refundId = $("#refundId").val();
        var orderId = $("#orderId").val();
        var refundState = $("#refundState").val();
        var adminMessage = $("#adminMessage").val();
        var url = "<%=basePath %>refund/audit";
        $.ajax({
            type: "post",
            url: url,
            cache: false,
            async: false,
            data:{refundId:refundId,orderId:orderId,refundState:refundState,adminMessage:adminMessage},
            dataType: "json",
            success: function (data) {
                var code = data.code;//200 is success，other is fail
                if (code == "200") {
                    $('.zd_div').fadeOut();
                    layer.msg('已处理', {icon: 5, time: 1000});
                    table.ajax.reload();
                } else {
                    $('.zd_div').fadeOut();
                    $("#errcode").val(data.code);
                    $("#errmsg").val(data.msg);
                    layer.msg('错误代码: ' + $("#errcode").val() + ", " + $("#errmsg").val(), {icon: 6, time: 5000});
                }
                //清空退款表单提交
                $("#refundId").val("");
                $("#orderId").val("");
                $("#refundState").val("");
                $("#adminMessage").val("");
            }
        });
    }
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>