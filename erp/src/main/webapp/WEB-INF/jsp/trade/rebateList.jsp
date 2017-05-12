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
    <title>抵扣券列表</title>
    <style type="text/css">
        th,td { white-space: nowrap; }
        .myloading{position: absolute;left:0;top:0;width:100%;bottom:0;background-color:rgba(0,0,0,0.6);display:flex;justify-content: center;align-items: center;z-index:999999;}
    </style>
</head>
<body>
<section class="Hui-article-box" style="left:0;top:0">
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 运营管理 <span class="c-gray en">&gt;</span> 抵扣券批次列表 <a
            class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"
            href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
    <div class="Hui-article">
        <article class="cl pd-20">
            <div class="text-c">
                抵扣券编号：<input type="text" class="input-text" style="width:120px;" id="rebateSn">　
                抵扣券批次号：<input type="text" class="input-text" style="width:120px;" id="rebateBatchId">
                用户手机号：<input type="text" class="input-text" style="width:120px;" id="userPhone">　
                抵扣券状态状态：
                        <span class="select-box mr-20" style="width:120px" >
                            <select id="receiveStatus" class="select">
                                <option value="">请选择</option>
                                <option value="1">未使用</option>
                                <option value="2">已使用</option>
                                <option value="3">已失效</option>
                            </select>
                        </span>

                <button name="" id="" onclick="search()" class="btn btn-success radius"><i class="Hui-iconfont">&#xe665;</i>
                    查 询
                </button>
            </div>

            <div class="cl pd-5 bg-1 bk-gray mt-20">
                <span class="l"><a class="btn btn-primary radius" onClick="rebateBatchInsert('添加抵扣券批次','/erp/rebate_batch/toAdd')"
                                   href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添加抵扣券批次</a></span>
                <span class="r" style="line-height:30px;">共有数据：<strong><span id="totalCount"></span></strong> 条</span></div>
            <div class="mt-20">
                <table id="dt" class="table table-border table-bordered table-bg table-hover table-sort">
                    <thead>
                    <tr class="text-c">
                        <th></th>
                        <th>抵扣券ID</th>
                        <th>抵扣券编号</th>
                        <th>有效期开始时间</th>
                        <th>有效期截止时间</th>
                        <th>抵扣券批次ID</th>
                        <th>抵扣券适用范围</th>
                        <th>抵扣券金额</th>
                        <th>用户手机号</th>
                        <th>用户领取时间</th>
                        <th>订单编号</th>
                        <th>使用时间</th>
                        <th>状态</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </article>
    </div>
</section>
<input type="hidden" id="errcode">
<input type="hidden" id="errmsg">

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
                'url': '<%=basePath %>rebate'
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
                {"data": "rebateId"},
                {"data": "rebateSn"},
                {
                    "data": "validStartTime",
                    defaultContent:""
                },
                {
                    "data": "validEndTime",
                    defaultContent:""
                },
                {"data": "rebateBatchId"},
                {"data": "rebateScope"},
                {"data": "rebateAmount"},
                {
                    "data": "userPhone",
                    defaultContent:""
                },
                {
                    "data": "receiveTime",
                    defaultContent:""
                },
                {
                    "data": "orderSn",
                    defaultContent:""
                },
                {
                    "data": "usedTime",
                    defaultContent:""
                },
                {
                    "data": "receiveStatus",
                    "render": function (data, type, row, meta) {
                        if (data == 1) {
                            return "未使用";
                        } else if (data == 2) {
                            return "已使用";
                        } else if (data == 3) {
                            return "已失效";
                        } else {
                            return "";
                        }
                    }
                },
            ],
            "columnDefs": [
                {
                    "searchable": false,
                    "orderable": false,
                    "targets": [0.-1]
                }
//                { "name": "batchId",  "targets": 1 },
//                { "name": "batchStatue", "targets": 12 }
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
        var rebateSn = $("#rebateSn").val();
        var rebateBatchId = $("#rebateBatchId").val();
        var userPhone = $("#userPhone").val();
        var receiveStatus = $("#receiveStatus").val();
        table.column(2).search(rebateSn).column(5).search(rebateBatchId).column(8).search(userPhone).column(12).search(receiveStatus).draw();
    }
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>