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
</head>
<body>
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 用户管理 <span class="c-gray en">&gt;</span> 用户列表 <a
            class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"
            href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
    <div class="Hui-article">
        <article class="cl pd-20">
            <div class="text-c">
                用户账号：<input type="text" id="phone" minlength="1" class="input-text" style="width:120px;">　
                姓名：<input type="text" id="realname" minlength="1" class="input-text" style="width:120px;">　
                身份证号：<input type="text" id="idcard" minlength="1" class="input-text" style="width:120px;">　
                是否认证：
                <span class="select-box mr-20" style="width:120px">
                    <select id="is_auth" name="is_auth" class="select">
                        <option value="1" selected="">是</option>
                        <option value="0" >否</option>
                    </select>
                </span>
                <button name="" id="" onclick="search1()" class="btn btn-success radius" type="submit"><i class="Hui-iconfont">&#xe665;</i>
                    查 询
                </button>
            </div>
            <div class="cl pd-5 bg-1 bk-gray mt-20">
                <%--<span class="l" onclick="export1()"><!-- <a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>  --><a--%>
                        <%--class="btn btn-primary radius" href="javascript:;"><i--%>
                        <%--class="Hui-iconfont">&#xe634;</i>导出</a></span>--%>
                <%--<span class="r" style="line-height:30px;">共有数据：<strong>1</strong> 条</span>--%>
            </div>
            <div class="mt-20">
                <table id="example" class="table table-border table-bordered table-bg table-hover table-sort">
                    <thead>
                    <tr class="text-c">
                        <th>编号</th>
                        <th>账号</th>
                        <th>是否实名</th>
                        <th>姓名</th>
                        <th>身份证号</th>
                        <th>投资人认证</th>
                        <th>银行卡</th>
                        <th>注册时间</th>
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
                    <%--<td>100000.00</td>--%>
                    <%--<td>2017-02-14 15:33:33</td>--%>
                    <td>
                    <a style="text-decoration:none" class="ml-5"
                    onClick="userList_detail('用户-用户列表-详情','用户-用户列表-详情.html','10001')" href="javascript:;"
                    title="查看"><i class="Hui-iconfont">&#xe725;</i></a>
                    冻结/解绑银行卡
                    </td>
                    <%--</tr>--%>
                    <%--</tbody>--%>
                </table>
            </div>
        </article>
    </div>
<script>$(function () {
    $(".Hui-aside ul a").on("click", function () {
        console.log($(this).attr("data-href")), $(".content_iframe").attr("src", $(this).attr("data-href"))
    })
})</script>
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="<%=basePath %>lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath %>lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=basePath %>lib/laypage/1.2/laypage.js"></script>

<script type="text/javascript" language="javascript" class="init">
    var table;
    var times = 0;
    $(document).ready(function() {
        times = times + 1;
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
                "sInfoEmpty": "本页仅限使用条件查询",
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
                "url": "user/list",
                "dataSrc": "aaData",
                "data": function ( d ) {
                    var phone = $('#phone').val();
                    var realname = $('#realname').val();
                    var idcard = $('#idcard').val();
                    var is_auth = $('#is_auth').val();
                    //添加额外的参数传给服务器
                    d.phone = phone;
                    d.realname = realname;
                    d.idcard = idcard;
                    d.is_auth = is_auth;
                }
            },
            "columns": [
                { "data": "id" },
//                { "data": "phone" },
                { "data": null,
                    render: function(data, type, row, meta) {
                        return '<td>' +
                                    '<a style="text-decoration:none" class="ml-5"onClick="userList_detail(\'用户-用户列表-详情\',\'user/detail\','+row.phone+')" href="javascript:;"title="查看详情">' +
                                    '<i class="Hui-iconfont">'+row.phone+'</i></a>' +
                                '</td>';
                    }
                },
                { "data": null,
                    render: function(data, type, row, meta) {
                        if(row.isAuth=='0'){
                            return '<label> 否 </label>';
                        }
                        if(row.isAuth=='1'){
                            return '<label> 是 </label>';
                        }
                    }
                },
                { "data": "realname" },
                { "data": "idCard" },
                { "data": null,
                    render: function(data, type, row, meta) {
                        if(row.investConditions=='0'){
                            return '<label> 用户未认证 </label>';
                        }
                        if(row.investConditions=='1'){
                            return '<label>  专业投资人  </label>';
                        }
                        if(row.investConditions=='2'){
                            return '<label>  近三年年收入不低于30万元  </label>';
                        }
                        if(row.investConditions=='3'){
                            return '<label>  金融资产不低于100万元 </label>';
                        }
                    }
                },
                { "data": null,
                    render: function(data, type, row, meta) {
                        return '<label>' + row.bankName + '</label>  <label>' + row.bankIdCard + '</label>';
                    }
                },
                { "data": "registerTime",
                    render: function(data, type, row, meta) {
                        //先讲 时间格式化
                        //这类问题主要给大家讲逻辑，因为都是类似的问题，类似的解决方案
                        //最基础的解决方案： 一、直接在数据源就格式化为常见的格式（sql或者后台代码格式化）;二、在dt里面格式化;
                        //在js格式化时间的三种方式，我这里示范一种
                        //具体方法的链接：http://www.cnblogs.com/zhangpengshou/archive/2012/07/19/2599053.html
                        return (new Date(data)).Format("yyyy-MM-dd hh:mm:ss"); //date的格式 Thu Apr 26 2016 00:00:00 GMT+0800
                    }
                },
               /* { "data": null,
                    render: function(data, type, row, meta) {
                        return '<td>' +
//                                    '<a style="text-decoration:none" class="ml-5"onClick="userList_detail(\'用户-用户列表-详情\',\'user/detail\','+row.phone+')" href="javascript:;"title="查看">' +
//                                    '<i class="Hui-iconfont">&#xe725;</i></a>' +
                                '冻结/解绑银行卡<br/>' +
                                '冻结/解绑账户' +
                                '</td>';
                    }
                },*/
                { "data": null,
                    "className":"f-14 td-manage",
                    render: function(data, type, row, meta) {
//                        return '<td class="f-14 td-manage">'
                        if(row.state=='0') {
                            return '<td class="f-14 td-manage">' +
//                                    '<a style="text-decoration:none" id="' + row.userId + '" /*onclick="project_start(this,' + row.userId + ')"*/ href="javascript:;" title="冻结银行卡"><i class="Hui-iconfont">&#xe60e;</i>冻结银行卡</a><br/>'+
//                                    '<a style="text-decoration:none" id="'+row.userId+row.userId+'"/* onClick="project_stop(this,'+row.userId+')"*/ href="javascript:;"title="解绑银行卡"><i class="Hui-iconfont">&#xe60e;</i>解绑银行卡</a><br/>' +
                                    '<a style="text-decoration:none" id="'+row.userId+row.userId+row.userId+'" onclick="user_unlock(this,'+row.userId+')" href="javascript:;" title="解锁账户"><i class="Hui-iconfont">&#xe605;</i>解锁账户</a>' +
                                  '</td>'
                        }
                        if(row.state=='1'){
                            return ' <td class="f-14 td-manage">' +
//                                    '<a style="text-decoration:none" id="' + row.userId + '" /*onclick="project_start(this,' + row.userId + ')"*/ href="javascript:;" title="冻结银行卡"><i class="Hui-iconfont">&#xe60e;</i>冻结银行卡</a><br/>'+
//                                    '<a style="text-decoration:none" id="'+row.userId+row.userId+'"/* onClick="project_stop(this,'+row.userId+')"*/ href="javascript:;"title="解绑银行卡"><i class="Hui-iconfont">&#xe60e;</i>解绑银行卡</a><br/>' +
                                    '<a style="text-decoration:none" id="'+row.userId+row.userId+row.userId+'" onClick="user_lock(this,'+row.userId+')" href="javascript:;"title="锁定账户"><i class="Hui-iconfont">&#xe60e;</i>锁定账户</a>' +
                                    '</td>';
                        }else{
                            return ' <td class="f-14 td-manage"></td>';
                        }
                    }
                }
            ]

        } );
    } );


    function search1()
    {
        var phone = $('#phone').val();
        var realname = $('#realname').val();
        var idcard = $('#idcard').val();
        var is_auth = $('#is_auth').val();
        //添加额外的参数传给服务器
        if( times != 0 && phone == "" && realname == "" && idcard == ""){
            alert("查询信息不能为空！");
            return false;
        }else{
            table.ajax.reload();
        }
    }
    function export1()
        {
           window.location.href="user/export";
        }

</script>
<script type="text/javascript">

    //    $('.table-sort').dataTable({
    //        "aaSorting": [[1, "desc"]],//默认第几个排序
    //        "bStateSave": true,//状态保存
    //        "aoColumnDefs": [
    //            //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
    //            {"orderable": false, "aTargets": [0, 8]}// 不参与排序的列
    //        ]
    //    });
    //*项目-编辑*/
    function userList_detail(title, url, phone, w, h) {
        var index = layer.open({
            type: 2,
            title: title,
            content: url+"?phone="+phone
        });
        layer.full(index);
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

    /*项目-下架*/
    function user_lock(obj, id) {
        layer.confirm('确定要锁定该账户吗？', function (index) {
            $.ajax({
                url:"user/state/update",
                data:{
                    "user_id":id,
                    "state":"0"
                },
                success : function(data) {
                    if(data.code==200){
                        console.log(data);
//                        $(obj).parents("tr").find(".td-status").html('<span>已结束</span>');
//                        $(obj).remove();
//                        $("#"+id).remove();
                        $("#"+id+id+id).remove();
                        table.ajax.reload();
                        layer.msg('已锁定!', {icon: 6, time: 1000});
                    }else{
                        alert("操作失败! 请重试");
                    }
                }
            })

        });
    }
    function user_unlock(obj, id) {
        layer.confirm('确定要解锁该账户吗？', function (index) {
            $.ajax({
                url:"user/state/update",
                data:{
                    "user_id":id,
                    "state":"1"
                },
                success : function(data) {
                    if(data.code==200){
                        console.log(data);
//                        $(obj).parents("tr").find(".td-status").html('<span>已结束</span>');
//                        $(obj).remove();
//                        $("#"+id).remove();
                        $("#"+id+id+id).remove();
                        table.ajax.reload();
                        layer.msg('已解锁!', {icon: 6, time: 1000});
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
