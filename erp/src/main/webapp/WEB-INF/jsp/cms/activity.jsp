<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="../common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset=utf-8>
    <base href="<%=basePath %>">
    <meta name=renderer content=webkit|ie-comp|ie-stand>
    <meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
    <meta name=viewport content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <meta http-equiv=Cache-Control content=no-siteapp/>
    <link rel=Bookmark href=favicon.ico>
    <link rel="Shortcut Icon" href=favicon.ico/>
    <meta name=keywords content=xxxxx>
    <meta name=description content=xxxxx>
    <title>图片列表</title>
    <style type="text/css">
        th,td { white-space: nowrap; }
        .myloading{position: absolute;left:0;top:0;width:100%;bottom:0;background-color:rgba(0,0,0,0.6);display:flex;justify-content: center;align-items: center;z-index:999999;}
    </style>
</head>
<body>
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 运营管理 <span class="c-gray en">&gt;</span> 活动列表 <a
            class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"
            href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
    <div class="Hui-article">
        <article class="cl pd-20">
            <div class="text-c">
                活动编号：<input type="text" id="activity_id" class="input-text" style="width:120px;">　
                活动名称：<input type="text" id="activity_name" class="input-text" style="width:120px;">　
                活动类型：
                <span class="select-box mr-20" style="width:120px">
                    <select id="activity_type" name="activity_type" class="select">
                            <option value="">请选择</option>
                            <option value="1">拉新</option>
                            <option value="2">促活</option>
                            <option value="3">转化</option>
                            <option value="4">传播</option>
                    </select>
                </span>
                状态：
                <span class="select-box mr-20" style="width:120px">
                    <select id="status" name="status" class="select">
                            <option value="">请选择</option>
                            <option value="1">待发布</option>
                            <option value="2">进行中</option>
                            <option value="3">已结束</option>
                            <option value="4">已下架</option>
                    </select>
                </span>

                <button name="" id="" class="btn btn-success radius"  onclick="search()" type="button"><i class="Hui-iconfont">&#xe665;</i>
                    查 询
                </button>
            </div>
            <div class="cl pd-5 bg-1 bk-gray mt-20">
                <span class="l" style="margin-right:20px"><!-- <a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>  -->
                    <a class="btn btn-primary radius"  onClick="project_toadd('添加活动','activity/toadd')" href="javascript:;">
                        <i class="Hui-iconfont">&#xe600;</i> 添加活动</a></span>
                <%--<span class="r" style="line-height:30px;">共有数据：<strong>1</strong> 条</span>--%>
            </div>
            <div class="mt-20">
                <table class="table table-border table-bordered table-bg table-hover table-sort">
                    <thead>
                    <tr class="text-c">
                        <th>编号</th>
                        <th>活动名称</th>
                        <th>活动类型</th>
                        <th>活动链接</th>
                        <th>备注</th>
                        <th>开始时间</th>
                        <th>结束时间</th>
                        <th>状态</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <%--<tbody>--%>
                    <%--<tr class="text-c">--%>
                        <%--<td>1</td>--%>
                        <%--<td>18510162765</td>--%>
                        <%--<td>是</td>--%>
                        <%--<td>魏华鑫</td>--%>
                        <%--<td>5137291990123223232</td>--%>
                        <%--<td>年收入30万</td>--%>
                        <%--<td>招商银行62223423423423</td>--%>
                        <%--<td>5000.00</td>--%>
                        <%--<td class="f-14 td-manage">--%>
                            <%--<a style="text-decoration:none" class="ml-5"--%>
                               <%--onClick="project_edit('运营-活动列表-添加','运营-活动列表-添加.html','10001')" href="javascript:;"--%>
                               <%--title="项目编辑"><i class="Hui-iconfont">&#xe6df;</i></a>--%>
                            <%--<a style="text-decoration:none" onClick="project_stop(this,'10001')" href="javascript:;"--%>
                               <%--title="下架"><i class="Hui-iconfont">&#xe6de;</i></a>--%>
                        <%--</td>--%>
                    <%--</tr>--%>
                    <%--</tbody>--%>
                </table>
            </div>

        </article>
    </div>

<script type="text/javascript" language="javascript" class="init">
    var table;
    $(document).ready(function() {
        table = $('.table-sort').DataTable({
            "pagingType": "simple_numbers",//设置分页控件的模式
            searching: false,//屏蔽datatales的查询框
            ordering:false,
            "scrollX": true, //允许水平滚动
            aLengthMenu:[10],//设置一页展示10条记录
            "bLengthChange": false,//屏蔽tables的一页展示多少条记录的下拉列表
            "oLanguage": {  //对表格国际化
                "sLengthMenu": "每页显示 _MENU_条",
                "sZeroRecords": "没有找到符合条件的数据",
                //  "sProcessing": "&lt;img src=’./loading.gif’ /&gt;",
                "sInfo": "当前第 _START_ - _END_ 条　共计 _TOTAL_ 条",
                "sInfoEmpty": "木有记录",
                "sInfoFiltered": "(从 _MAX_ 条记录中过滤)",
                "sSearch": "搜索：",
                "oPaginate": {
                    "sFirst": "首页",
                    "sPrevious": "前一页",
                    "sNext": "后一页",
                    "sLast": "尾页"
                }
            },
            "aoColumnDefs": [
                {
                    sDefaultContent: '',
                    aTargets: [ '_all' ]
                }
            ],
            "processing": true, //打开数据加载时的等待效果
            "serverSide": true,//打开后台分页
            "ajax": {
                "url": "activity/list",
                "dataSrc": "aaData",
                "data": function ( d ) {
                    var activity_id = $('#activity_id').val();
                    var activity_name = $('#activity_name').val();
                    var activity_type = $('#activity_type').val();
                    var status = $('#status').val();
//                    //添加额外的参数传给服务器
                    d.activity_id = activity_id;
                    d.activity_name = activity_name;
                    d.activity_type = activity_type;
                    d.status = status;
                }
            },
            "columns": [
                { "data": "activityId" },
                { "data": "activityName" },
                { "data": null,
                    render: function(data, type, row, meta) {
                        if(row.activityType=='1'){
                            return '<label> 拉新 </label>';
                        }
                        if(row.activityType=='2'){
                            return '<label>  促活  </label>';
                        }
                        if(row.activityType=='3'){
                            return '<label>  转化  </label>';
                        }
                        if(row.activityType=='4'){
                            return '<label>  传播 </label>';
                        }
                    }
                },
                { "data": "activityLink" },
                { "data": "remarks" },
                { "data": "startTime",
                    render: function(data, type, row, meta) {
                        //先讲 时间格式化
                        //这类问题主要给大家讲逻辑，因为都是类似的问题，类似的解决方案
                        //最基础的解决方案： 一、直接在数据源就格式化为常见的格式（sql或者后台代码格式化）;二、在dt里面格式化;
                        //在js格式化时间的三种方式，我这里示范一种
                        //具体方法的链接：http://www.cnblogs.com/zhangpengshou/archive/2012/07/19/2599053.html
                        return (new Date(data)).Format("yyyy-MM-dd hh:mm:ss"); //date的格式 Thu Apr 26 2016 00:00:00 GMT+0800
                    }
                },
                { "data": "endTime",
                    render: function(data, type, row, meta) {
                        //先讲 时间格式化
                        //这类问题主要给大家讲逻辑，因为都是类似的问题，类似的解决方案
                        //最基础的解决方案： 一、直接在数据源就格式化为常见的格式（sql或者后台代码格式化）;二、在dt里面格式化;
                        //在js格式化时间的三种方式，我这里示范一种
                        //具体方法的链接：http://www.cnblogs.com/zhangpengshou/archive/2012/07/19/2599053.html
                        return (new Date(data)).Format("yyyy-MM-dd hh:mm:ss"); //date的格式 Thu Apr 26 2016 00:00:00 GMT+0800
                    }
                },
                { "data": null,
                    "className":"f-14 td-status",
                    render: function(data, type, row, meta) {
                        if(row.status=='1'){
                            return '<label> 待发布 </label>';
                        }
                        if(row.status=='2'){
                            return '<label>  进行中  </label>';
                        }
                        if(row.status=='3'){
                            return '<label>  已结束  </label>';
                        }
                        if(row.status=='4'){
                            return '<label>  已下架 </label>';
                        }
                    }
                },
                { "data": null,
                    "className":"f-14 td-manage",
                    render: function(data, type, row, meta) {

                        if(row.status=='1'){
                            return '<td class="f-14 td-manage">' +
                                    '<a style="text-decoration:none" id="'+row.activityId+'" onclick="project_start(this,'+row.activityId+')" href="javascript:;" title="发布"><i class="Hui-iconfont">' +
                                    '</i></a><a style="text-decoration:none" class="ml-5"  id="'+row.activityId+row.activityId+'" ' +
                                    'onclick="project_edit(\'运营-活动列表-修改\',\'activity/toedit\','+row.activityId+')" href="javascript:;" title="项目编辑">' +
                                    '<i class="Hui-iconfont"></i></a> </td>'
                        }else if(row.status=='2'){
                            return ' <td class="f-14 td-manage">' +
                                    '<a style="text-decoration:none" id="'+row.activityId+row.activityId+'" onClick="project_stop(this,'+row.activityId+')" href="javascript:;"title="下架"><i class="Hui-iconfont">&#xe6de;</i></a>' +
                                        '<a style="text-decoration:none" id="'+row.activityId+'" class="ml-5"' +
                                        'onClick="project_edit(\'运营-活动列表-修改\',\'activity/toedit\','+row.activityId+')" href="javascript:;"title="项目编辑"><i class="Hui-iconfont">&#xe6df;' +
                                            '</i></a>' +
                                    '</td>';
                        }else if(row.status=='3'){
                            return '<td class="f-14 td-manage">' +
                                    '<a style="text-decoration:none" id="'+row.activityId+'" onclick="project_start(this,'+row.activityId+')" href="javascript:;" title="发布"><i class="Hui-iconfont">' +
                                    '</i></a><a style="text-decoration:none" class="ml-5"  id="'+row.activityId+row.activityId+'" ' +
                                    'onclick="project_edit(\'运营-活动列表-修改\',\'activity/toedit\','+row.activityId+')" href="javascript:;" title="项目编辑">' +
                                    '<i class="Hui-iconfont"></i></a> </td>'
                        }else{
                            return '<label></label>';
                        }
                    }
                }
            ]

        } );
    } );

    function search()
    {
        table.ajax.reload();
    }

</script>
<script>$(function () {
    $(".Hui-aside ul a").on("click", function () {
        console.log($(this).attr("data-href")), $(".content_iframe").attr("src", $(this).attr("data-href"))
    })

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
        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
        return fmt;
    }

})</script>
<script>

    //*项目-编辑*/
    function project_toadd(title, url) {
        var index = layer.open({
            type: 2,
            title: title,
            content: url,
            end: function(){
                table.ajax.reload();
            }
        });
        layer.full(index);
    }

    //*项目-编辑*/
    function project_edit(title, url, id, w, h) {
        var index = layer.open({
            type: 2,
            title: title,
            content: url+'?activity_id='+id,
            end: function(){
                table.ajax.reload();
            }
        });
        layer.full(index);
    }
    /*项目-下架*/
    function project_stop(obj, id) {
        layer.confirm('确认要下架吗？', function (index) {
            $.ajax({
                url:"activity/update",
                data:{
                    "activity_id":id,
                    "status":"3"
                },
                success : function(data) {
                    if(data.code==200){
                        console.log(data);
                        $(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" id="'+id+'" onClick="project_start(this,id)" href="javascript:;" title="发布"><i class="Hui-iconfont">&#xe603;</i></a>');
                        $(obj).parents("tr").find(".td-status").html('<span>已结束</span>');
                        $(obj).remove();
//                        $("#"+id).remove();
//                        $("#"+id+id).remove();
                        layer.msg('已下架!', {icon: 5, time: 1000});
                    }else{
                        alert("操作失败! 请重试");
                    }
                }
            })

        });
    }

    /*项目-发布*/
    function project_start(obj, id) {
        layer.confirm('确认要发布吗？', function (index) {
            $.ajax({
                url:"activity/update",
                data:{
                    "activity_id":id,
                    "status":"2"
                },
                success : function(data) {
                    if(data.code==200){
                        console.log(data);
                        $(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" id="'+id+'" onClick="project_stop(this,id)" href="javascript:;" title="下架"><i class="Hui-iconfont">&#xe6de;</i></a>');
                        $(obj).parents("tr").find(".td-status").html('<span>进行中</span>');
                        $(obj).remove();
                        layer.msg('已发布!', {icon: 6, time: 1000});
                    }else{
                        alert("操作失败! 请重试");
                    }
                }
            })
        });
    }
    /*项目-申请上线*/
    function project_shenqing(obj, id) {
        $(obj).parents("tr").find(".td-status").html('<span class="label label-default radius">待审核</span>');
        $(obj).parents("tr").find(".td-manage").html("");
        layer.msg('已提交申请，耐心等待审核!', {icon: 1, time: 2000});
    }
</script>
</body>
</html>
