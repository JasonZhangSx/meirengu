<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="../common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset=utf-8>
    <meta name=renderer content=webkit|ie-comp|ie-stand>
    <meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
    <meta name=viewport content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <meta http-equiv=Cache-Control content=no-siteapp/>
    <link rel=Bookmark href=favicon.ico>
    <link rel="Shortcut Icon" href=favicon.ico/>
    <meta name=keywords content=xxxxx>
    <meta name=description content=xxxxx>
   </head>
</html>

<title>图片列表</title>
</head>
<body>
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> CMS管理系统 <span class="c-gray en">&gt;</span> 常见问题分类列表 <a
            class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"
            href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
    <div class="Hui-article">
        <article class="cl pd-20">
           <%-- <div class="text-c">
                编号：<input type="text" class="input-text" style="width:120px;">　
                <button name="" id="" class="btn btn-success radius" onclick="search()" type="button"><i class="Hui-iconfont">&#xe665;</i>
                    查 询
                </button>
            </div>--%>
            <div class="cl pd-5 bg-1 bk-gray mt-20">
  				<span class="l">
            <a class="btn btn-primary radius" onClick="project_edit('CMS-常见问题分类-添加分类','/erp/faqclass/toadd','10001')"
               href="javascript:;"><i class="Hui-iconfont">&#xe600;</i> 添 加 分 类</a>
          </span>
                <%--<span class="r" style="line-height:30px;">共有数据：<strong>1</strong> 条</span>--%>
            </div>

            <div class="mt-20">
                <table class="table table-border table-bordered table-bg table-hover table-sort">
                    <thead>
                    <tr class="text-c">
                        <th width="40">序号</th>
                        <th width="100">分类名称</th>
                        <th width="70">添加时间</th>
                        <th width="70">状态</th>
                        <th width="100">操作</th>
                    </tr>
                    </thead>
                </table>
            </div>


        </article>
    </div>

<script>$(function () {
    $(".Hui-aside ul a").on("click", function () {
        console.log($(this).attr("data-href")), $(".content_iframe").attr("src", $(this).attr("data-href"))
    })
})</script>
<!-- 时间插件 -->
<link href="/erp/lib/datetimepicker/datetimepicker.css" rel="stylesheet" type="text/css"/>
<script type="text/javascript" src="/erp/lib/datetimepicker/datetimepicker.js"></script>
<script>

    var table;
    $(function () {

        table = $('.table-sort').DataTable({
            "pagingType": "simple_numbers",//设置分页控件的模式
            searching: false,//屏蔽datatales的查询框
            aLengthMenu:[10],//设置一页展示10条记录
            ordering:false,
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
            "processing": true, //打开数据加载时的等待效果
            "serverSide": true,//打开后台分页
            "ajax": {
                url: "/erp/faqclass/list",
                "dataSrc": "aaData"
            },
//            "order": [[1, 'asc']],// dt默认是第一列升序排列 这里第一列为序号列，所以设置为不排序，并把默认的排序列设置到后面

            "columns": [
                {"data": "classId"}, //因为要加行号，所以要多一列，不然会把第一列覆盖
                {"data": "className"},
                { "data": "createTime",
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
                        if(row.status==1){
                            return '<label>已发布</lable>';
                        }
                        if(row.status==0){
                            return '<label>已下架</lable>';
                        }
                    }
                },
                { "data": null,
                    "className":"f-14 td-manage",
                    render: function(data, type, row, meta) {

                        if(row.status=='0'){
                            return '<td class="f-14 td-manage">' +
                                    '<a style="text-decoration:none" id="'+row.classId+'" onclick="project_start(this,'+row.classId+')" href="javascript:;" title="发布"><i class="Hui-iconfont"></i></a>' +
                                    '<a style="text-decoration:none" class="ml-5" ' +
                                    'onclick="project_edit(\'CMS-常见问题分类-修改\',\'/erp/faqclass/toedit\','+row.classId+')" href="javascript:;" title="项目编辑">' +
                                    '<i class="Hui-iconfont"></i></a> </td>'
                        }
                        if(row.status=='1'){
                            return ' <td class="f-14 td-manage">' +
                                    '<a style="text-decoration:none" id="'+row.classId+'" onClick="project_stop(this,'+row.classId+')" href="javascript:;"title="下架"><i class="Hui-iconfont">&#xe6de;</i></a>' +
                                    '<a style="text-decoration:none" class="ml-5"' +
                                    'onClick="project_edit(\'CMS-常见问题分类-修改\',\'/erp/faqclass/toedit\','+row.classId+')" href="javascript:;"title="项目编辑"><i class="Hui-iconfont">&#xe6df;' +
                                    '</i></a>' +
                                    '</td>';
                        }else{
                            return ' <td class="f-14 td-manage"></td>';
                        }
                    }
                }
            ]
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

    function search()
    {
        table.ajax.reload();
    }

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

    //*项目-编辑*/
    function project_edit(title, url, id, w, h) {
        var index = layer.open({
            type: 2,
            title: title,
            content: url+"?class_id="+id,
            end: function(){
                table.ajax.reload();
            }
        });
        layer.full(index);
    }
    // 时间
    $('#datetimepicker2,#datetimepicker3').datetimepicker({
        yearOffset: 0,
        lang: $.datetimepicker.setLocale('ch'),
        timepicker: false,
        format: 'Y-m-d',
        formatDate: 'Y/m/d',
    });
    /*资讯-下架*/
    function project_stop(obj, id) {
        layer.confirm('确认要下架吗？', function (index) {
            $.ajax({
                url:"/erp/faqclass/update",
                data:{
                    "class_id":id,
                    "status":"0"
                },
                success : function(data) {
                    if(data.code==200){
                        console.log(data);
                        $(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" id="'+id+'" onclick="project_start(this,'+id+')" href="javascript:;" title="发布"><i class="Hui-iconfont"></i></a>');
                        $(obj).parents("tr").find(".td-status").html('<span>已下架</span>');
                        $(obj).remove();
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
                url:"/erp/faqclass/update",
                data:{
                    "class_id":id,
                    "status":"1"
                },
                success : function(data) {
                    if(data.code==200){
                        console.log(data);
                        $(obj).parents("tr").find(".td-manage").prepend('<a style="text-decoration:none" id="'+id+'" onClick="project_stop(this,id)" href="javascript:;" title="下架"><i class="Hui-iconfont">&#xe6de;</i></a>');
                        $(obj).parents("tr").find(".td-status").html('<span>已发布</span>');
                        $(obj).remove();
                        layer.msg('已发布!', {icon: 6, time: 1000});
                    }else{
                        alert("操作失败! 请重试");
                    }
                }
            })
        });
    }
</script>
</body>
</html>
