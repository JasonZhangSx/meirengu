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
    <title>操作日志详细列表</title>
    <style type="text/css">
        th,td { white-space: nowrap; }
        .myloading{position: absolute;left:0;top:0;width:100%;bottom:0;background-color:rgba(0,0,0,0.6);display:flex;justify-content: center;align-items: center;z-index:999999;}
    </style>
</head>
<body>
<section class="Hui-article-box" style="left:0;top:0">
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 操作日志 <span class="c-gray en">&gt;</span> 操作日志详细 </nav>
    <div class="Hui-article">
        <article class="cl pd-20">

                <input type="hidden" id="logOperationId" value="${logOperationId}">

                <table id="dt" class="table table-border table-bordered table-bg table-hover table-sort">
                    <thead>
                    <tr class="text-c">
                        <th>字段名</th>
                        <th>字段旧值</th>
                        <th>字段新值</th>
                    </tr>
                    </thead>
                </table>
        </article>
    </div>
</section>

<script type="text/javascript">

    var table;
    $(function () {
        table = $('#dt').DataTable({

            'ajax': {
                'url': '<%=basePath %>logOperationDetail',
                'data': {
                    logOperationId : $('#logOperationId').val()
                }
            },
            "serverSide": true,  //启用服务器端分页
            "paging": false,
            "processing": true,
            "info": false,
            "searching": false,
            "columns": [
                {"data": "columnName"}, //因为要加行号，所以要多一列，不然会把第一列覆盖
                {"data": "columnOldValue"},
                {"data": "columnNewValue"}
            ],
            "language": {
                "processing": "<div class='myloading'><img src='<%=basePath %>static/h-ui/images/loading/loading-b.gif'> <br/>&nbsp&nbsp&nbsp&nbsp&nbsp努力加载数据中.</div>",
                "lengthMenu": "",
                "zeroRecords": "没有找到记录",
                "info": "",
                "infoEmpty": "无记录",
                "search": "",
                "infoFiltered": "(从 _MAX_ 条记录过滤)",
                "paginate": null
            }

        });

        //添加序号
        //不管是排序，还是分页，还是搜索最后都会重画，这里监听draw事件即可
        table.on('draw.dt',function() {
            table.each(function(cell, i) {
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


</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>