<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ include file="../common/common.jsp"%>
<!DOCTYPE html>
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
    <title>已发布项目列表</title>
    <style type="text/css">
        .table tbody tr td{
            text-align:center;
        }
    </style>
</head>
<body>
<section class="Hui-article-box" style="left:0;top:0">
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 众筹项目管理 <span class="c-gray en">&gt;</span>已完成项目列表 <a
            class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"
            href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
    <div class="Hui-article">
        <article class="cl pd-20">

            <div class="cl pd-5 bg-1 bk-gray mt-20">
                <%--<span class="l"><a class="btn btn-primary radius" onClick="project_edit('添加版本信息','investor/detail')"
                                   href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添加领投人</a></span>--%>
                <span class="r" style="line-height:30px;">共有数据：<strong><span id="totalCount"></span></strong> 条</span></div>
            <div class="mt-20">
                <table id="dt" class="table table-border table-bordered table-bg table-hover table-sort">
                    <thead>
                    <tr class="text-c">
                        <th>序号</th>
                        <th>项目名称</th>
                        <th>众筹类型</th>
                        <th>项目分类</th>
                        <th>众筹金额</th>
                        <th>预热天数</th>
                        <th>众筹天数</th>
                        <th>提交时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                </table>
            </div>
        </article>
    </div>
</section>

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
                'url': '<%=basePath %>item/query?status=12'
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
                {"data": "itemName"},
                {"data": "className"},
                {"data": "typeName"},
                {"data": "targetAmount"},
                {"data": "preheatingDays"},
                {"data": "crowdDays"},
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

                    "defaultContent":'',
                    "searchable": false,
                    "orderable": false,
                    "targets": [0.-1]
                },
                {
                    "targets": 8,
                    "render": function (data, type, row, meta) {
                        var context =
                        {
                            func: [
                                {"name": "查看", "fn": "detail(\'" + row.itemId + "\')", "type": "primary"},
                                {"name": "分红", "fn": "project_confirm( this,\'" + row.itemId + "\')", "type": "primary"}
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
     *项目-编辑/新增
     */
    function project_edit(title, url, id, w, h) {
        var index = layer.open({
            type: 2,
            title: title,
            content: url
        });
        layer.full(index);
    }

    /*删除*/
    function project_confirm(obj, id) {
        layer.confirm('确认要下架吗？', function (index) {
            $.ajax({
                url:"item/off",
                data:{
                    "id":id
                },
                success : function(data) {
                    if(data.code==200){
                        console.log(data);
                        $(obj).parents("tr").remove();
                        layer.msg('已下架!', {icon: 1, time: 1000});
                    }else{
                        alert("操作失败! 请重试");
                    }
                }
            })

        });
    }

    /**
     * 跳转到修改页面
     * @param id
     */
    function detail(id){
        var index = layer.open({
            type: 2,
            title: "已发布项目详情",
            content: "item/to_published?itemId="+id
        });
        layer.full(index);
    }
</script>
<!--/请在上方写此页面业务相关的脚本-->
</body>
</html>

