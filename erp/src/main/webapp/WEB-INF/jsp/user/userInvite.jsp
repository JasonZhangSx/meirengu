<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="../common/common.jsp"%>
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
<header class=navbar-wrapper>
    <div class="navbar navbar-fixed-top" style=background-color:#19a97b>
        <div class="container-fluid cl"><a class="logo navbar-logo f-l mr-10 hidden-xs"
                                           href=/aboutHui.shtml>Beauty-ERP</a> <a
                class="logo navbar-logo-m f-l mr-10 visible-xs" href=/aboutHui.shtml>H-ui</a> <span
                class="logo navbar-slogan f-l mr-10 hidden-xs">v1.0.0</span> <a aria-hidden=false
                                                                                class="nav-toggle Hui-iconfont visible-xs"
                                                                                href=javascript:;>&#xe667;</a>
            <nav id=Hui-userbar class="nav navbar-nav navbar-userbar hidden-xs">
                <ul class=cl>
                    <li>管理员</li>
                    <li class="dropDown dropDown_hover"><a href=# class=dropDown_A>admin <i class=Hui-iconfont>
                        &#xe6d5;</i></a>
                        <ul class="dropDown-menu menu radius box-shadow">
                            <li><a href=#>个人信息</a></li>
                            <li><a href=#>切换账户</a></li>
                            <li><a href=#>退出</a></li>
                        </ul>
                    </li>
                </ul>
            </nav>
        </div>
    </div>
</header>
<aside class=Hui-aside><input runat=server id=divScrollValue type=hidden value=""/>
    <div class="menu_dropdown bk_2">
        <dl id=menu-article>
            <dt><i class=Hui-iconfont style=font-size:20px>&#xe63a;</i> 众筹项目<i class="Hui-iconfont menu_dropdown-arrow">
                &#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a href=javascript:void(0) data-href=众筹-新建项目列表.html title=新建项目列表>新建项目列表</a></li>
                    <li><a href=javascript:void(0) data-href=众筹-待初审项目.html title=待初审项目>待初审项目</a></li>
                    <li><a href=javascript:void(0) data-href=众筹-待合作项目.html title=待合作项目>待合作项目</a></li>
                    <li><a href=javascript:void(0) data-href=众筹-待复审项目.html title=待复审项目>待复审项目</a></li>
                    <li><a href=javascript:void(0) data-href=众筹-待发布项目列表.html title=待发布项目列表>待发布项目列表</a></li>
                    <li><a href=javascript:void(0) data-href=众筹-已发布项目列表.html title=已发布项目列表>已发布项目列表</a></li>
                    <li><a href=javascript:void(0) data-href=众筹-已完成项目.html title=已完成项目>已完成项目</a></li>
                </ul>
            </dd>
        </dl>
        <dl id=menu-article>
            <dt><i class=Hui-iconfont style=font-size:20px>&#xe687;</i> 订单管理<i class="Hui-iconfont menu_dropdown-arrow">
                &#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a href=javascript:void(0) data-href=订单-待审核预约订单.html title=待审核预约订单>待审核预约订单</a></li>
                    <li><a href=javascript:void(0) data-href=订单-候补预约列表.html title=候补预约列表>候补预约列表</a></li>
                    <li><a href=javascript:void(0) data-href=订单-订单列表.html title=订单列表>订单列表</a></li>
                    <li><a href=javascript:void(0) data-href=订单-待审核退款订单.html title=待审核退款订单>待审核退款订单</a></li>
                    <li><a href=javascript:void(0) data-href=订单-退款订单列表.html title=退款订单列表>退款订单列表</a></li>
                </ul>
            </dd>
        </dl>
        <dl id=menu-article>
            <dt><i class=Hui-iconfont style=font-size:20px>&#xe607;</i> 用户管理<i class="Hui-iconfont menu_dropdown-arrow">
                &#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a href=javascript:void(0) data-href=用户-用户列表.html title=项目列表>用户列表</a></li>
                    <li><a href=javascript:void(0) data-href=用户-邀请记录.html title=项目列表>邀请记录</a></li>
                </ul>
            </dd>
        </dl>
        <dl id=menu-article>
            <dt><i class=Hui-iconfont style=font-size:20px>&#xe607;</i> 运营管理<i class="Hui-iconfont menu_dropdown-arrow">
                &#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a href=javascript:void(0) data-href=运营-活动列表.html title=活动列表>活动列表</a></li>
                    <li><a href=javascript:void(0) data-href=运营-抵扣券查询.html title=抵扣券查询>抵扣券查询</a></li>
                </ul>
            </dd>
        </dl>
        <dl id=menu-article>
            <dt><i class=Hui-iconfont style=font-size:20px>&#xe62b;</i> 合作方管理<i
                    class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a href=javascript:void(0) data-href=合作方-行业分类.html title=项目列表>行业分类管理</a></li>
                    <li><a href=javascript:void(0) data-href=合作方-合作方列表.html title=项目列表>合作方列表</a></li>
                </ul>
            </dd>
        </dl>
        <dl id=menu-article>
            <dt><i class=Hui-iconfont style=font-size:20px>&#xe6b5;</i> 财务管理<i class="Hui-iconfont menu_dropdown-arrow">
                &#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a href=javascript:void(0) data-href=财务-待打款列表.html title=待打款列表>待打款列表</a></li>
                    <li><a href=javascript:void(0) data-href=财务-打款记录.html title=打款记录>打款记录</a></li>
                    <li><a href=javascript:void(0) data-href=财务-待收款项列表.html title=代收款项列表>待收款项列表</a></li>
                    <li><a href=javascript:void(0) data-href=财务-收款记录.html title=收款记录>收款记录</a></li>
                    <li><a href=javascript:void(0) data-href=财务-放款分红记录.html title=放款分红记录>放款分红记录</a></li>
                    <li><a href=javascript:void(0) data-href=财务-支付通道管理.html title=放款分红记录>支付通道管理</a></li>
                    <li><a href=javascript:void(0) data-href=财务-用户充值记录.html title=放款分红记录>用户充值记录</a></li>
                    <li><a href=javascript:void(0) data-href=财务-用户提现记录.html title=放款分红记录>用户提现记录</a></li>
                    <li><a href=javascript:void(0) data-href=财务-抵扣券核销.html title=放款分红记录>抵扣券核销</a></li>
                </ul>
            </dd>
        </dl>
        <dl id=menu-article>
            <dt><i class=Hui-iconfont style=font-size:20px>&#xe60c;</i> CMS管理系统<i
                    class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a href=javascript:void(0) data-href=CMS-轮播图管理.html title=轮播图管理>CMS-轮播图管理</a></li>
                    <li><a href=javascript:void(0) data-href=CMS-公告列表.html title=公告列表>CMS-公告列表</a></li>
                    <li><a href=javascript:void(0) data-href=CMS-常见问题分类.html title=常见问题分类>CMS-常见问题分类</a></li>
                    <li><a href=javascript:void(0) data-href=CMS-常见问题列表.html title=常见问题列表>CMS-常见问题列表</a></li>
                    <li><a href=javascript:void(0) data-href=CMS-意见反馈列表.html title=意见反馈列表>CMS-意见反馈列表</a></li>
                </ul>
            </dd>
        </dl>
        <dl id=menu-article>
            <dt><i class=Hui-iconfont style=font-size:20px>&#xe61d;</i> 系统设置<i class="Hui-iconfont menu_dropdown-arrow">
                &#xe6d5;</i></dt>
            <dd>
                <ul>
                    <li><a href=javascript:void(0) data-href=系统-操作员管理.html title=操作员管理>操作员管理</a></li>
                    <li><a href=javascript:void(0) data-href=系统-角色管理.html title=角色管理>角色管理</a></li>
                    <li><a href=javascript:void(0) data-href=系统-部门管理.html title=部门管理>部门管理</a></li>
                </ul>
            </dd>
        </dl>
    </div>
</aside>
<div class="dislpayArrow hidden-xs"><a class=pngfix href=javascript:void(0) data-href=javascript:void(0);
                                       onclick=displaynavbar(this)></a></div>
<section class="Hui-article-box">
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 众筹项目 <span class="c-gray en">&gt;</span> 新建项目列表 <a
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
                <span class="l"><!-- <a href="javascript:;" onclick="datadel()" class="btn btn-danger radius"><i class="Hui-iconfont">&#xe6e2;</i> 批量删除</a>  --><a
                        class="btn btn-primary radius" href="javascript:;"><i
                        class="Hui-iconfont">&#xe634;</i> 导出</a></span>
                <span class="r" style="line-height:30px;">共有数据：<strong>1</strong> 条</span></div>
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
                        <th>累计投资额</th>
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
</section>
<script>$(function () {
    $(".Hui-aside ul a").on("click", function () {
        console.log($(this).attr("data-href")), $(".content_iframe").attr("src", $(this).attr("data-href"))
    })
})</script>
<script type="text/javascript" src="/erp/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="/erp/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="/erp/lib/laypage/1.2/laypage.js"></script>
<script type="text/javascript" language="javascript" class="init">
    var table;
    $(document).ready(function() {
        table = $('.table-sort').DataTable({
            "pagingType": "simple_numbers",//设置分页控件的模式
            searching: false,//屏蔽datatales的查询框
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
                "url": "/erp/inviter/list",
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
                { "data": "totalInvestMoney" },
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
</script>
</body>
</html>
