<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="../common/common.jsp"%>
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
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 用户管理 <span class="c-gray en">&gt;</span> 邀请记录 <a
            class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"
            href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
    <div class="Hui-article">
        <article class="cl pd-20">
            <div class="text-c">
                邀请人：<input type="text" id="realname" class="input-text" style="width:120px;">　
                被邀请人：<input type="text" id="invite_realname" class="input-text" style="width:120px;">　
                身份证号：<input type="text" id="invite_idcard" class="input-text" style="width:120px;">　
                <button name="" id="" class="btn btn-success radius" onclick="search()" type="submit"><i class="Hui-iconfont">&#xe665;</i>
                    查 询
                </button>
            </div>
            <div class="cl pd-5 bg-1 bk-gray mt-20">
                <span class="l"  onclick="export1()" ><!-- <a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>  --><a
                        class="btn btn-primary radius" href="javascript:;"><i
                        class="Hui-iconfont">&#xe634;</i> 导出</a></span>
                <%--<span class="r" style="line-height:30px;">共有数据：<strong>1</strong> 条</span>--%>
            </div>
            <div class="mt-20">
                <table class="table table-border table-bordered table-bg table-hover table-sort">
                    <thead>
                    <tr class="text-c">
                        <th>邀请人</th>
                        <th>邀请人姓名</th>
                        <th>被邀请人</th>
                        <th>被邀请人姓名</th>
                        <th>身份证号</th>
                        <th>投资人认证</th>
                        <th>注册时间</th>
                    </tr>
                    </thead>
                    <%--<tbody>
                    <tr class="text-c">
                        <td>1</td>
                        <td>18510162765</td>
                        <td>是</td>
                        <td>魏华鑫</td>
                        <td>5137291990123223232</td>
                        <td>年收入30万</td>
                        <td>招商银行62223423423423</td>
                        <td>5000.00</td>
                    </tr>
                    </tbody>--%>
                </table>
            </div>
        </article>
    </div>
<script>$(function () {
    $(".Hui-aside ul a").on("click", function () {
        console.log($(this).attr("data-href")), $(".content_iframe").attr("src", $(this).attr("data-href"))
    })
})</script>
<script type="text/javascript" src="<%=basePath %>lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="<%=basePath %>lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="<%=basePath %>lib/laypage/1.2/laypage.js"></script>
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
            "processing": true, //打开数据加载时的等待效果
            "serverSide": true,//打开后台分页
            "ajax": {
                "url": "inviter/list",
                "dataSrc": "aaData",
                "data": function ( d ) {
                    var realname = $('#realname').val();
                    var invite_realname = $ ('#invite_realname').val();
                    var invite_idcard = $('#invite_idcard').val();
                    //添加额外的参数传给服务器
                    d.realname = realname;
                    d.invite_realname = invite_realname;
                    d.invite_idcard = invite_idcard;
                }
            },
            "columns": [
                { "data": "phone" },
                { "data": "realname" },
                { "data": "invitedUserPhone" },
                { "data": "invitedRealName" },
                { "data": "invitedIdCard" },
                { "data": null,
                    render: function(data, type, row, meta) {
                        if(row.invitedInvestConditions=='0'){
                            return '<label> 未选择 </label>';
                        }
                        if(row.invitedInvestConditions=='1'){
                            return '<label>  专业投资人  </label>';
                        }
                        if(row.invitedInvestConditions=='2'){
                            return '<label>  投资金额30万  </label>';
                        }
                        if(row.invitedInvestConditions=='3'){
                            return '<label>  投资金额100万 </label>';
                        }
                    }
                },
//                { "data": null,
//                    render: function(data, type, row, meta) {
//                        return '<label>' + row.bankName + '</label>  <label>' + row.bankIdCard + '</label>';
//                    }
//                },
//                { "data": "totalInvestMoney" },
                { "data": "invitedRegisterTime",
                    render: function(data, type, row, meta) {
                        //先讲 时间格式化
                        //这类问题主要给大家讲逻辑，因为都是类似的问题，类似的解决方案
                        //最基础的解决方案： 一、直接在数据源就格式化为常见的格式（sql或者后台代码格式化）;二、在dt里面格式化;
                        //在js格式化时间的三种方式，我这里示范一种
                        //具体方法的链接：http://www.cnblogs.com/zhangpengshou/archive/2012/07/19/2599053.html
                        return (new Date(data)).Format("yyyy-MM-dd hh:mm:ss"); //date的格式 Thu Apr 26 2016 00:00:00 GMT+0800
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
<script>
    //*项目-编辑*/
    function userList_detail(title, url, id, w, h) {
        var index = layer.open({
            type: 2,
            title: title,
            content: url
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
    function export1()
    {
        var count =
        window.location.href="inviter/export";
    }
</script>
</body>
</html>
