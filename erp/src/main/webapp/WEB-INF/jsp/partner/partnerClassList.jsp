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
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />

    <title>项目分类列表</title>
</head>
<body>
<section class="Hui-article-box" style="left:0; top: 0;">
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 众筹项目 <span class="c-gray en">&gt;</span> 行业分类列表 <a
            class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"
            href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
    <div class="Hui-article">
        <article class="cl pd-20">
            <div class="text-c">
                <form action="list" method="post" accept-charset="utf-8" onclick="document.charset='utf-8';">
                    分类名称：<input type="text" class="input-text" style="width:120px;" name="className" value="<c:if test="${className != null}">${className}</c:if>">　
                    <button name="" id="" class="btn btn-success radius" type="submit"><i class="Hui-iconfont">&#xe665;</i>
                        查 询
                    </button>
                </form>
            </div>
            <div class="cl pd-5 bg-1 bk-gray mt-20">
  				<span class="l">
            <a class="btn btn-primary radius" href="javascript:;"><i class="Hui-iconfont">&#xe634;</i> 导出</a>
            <a class="btn btn-primary radius" href="javascript:;"
               onClick="project_edit('合作方-行业分类-添加','to_add','10001')" href="javascript:;" title="添加行业分类"><i
                    class="Hui-iconfont">&#xe600;</i> 添加行业分类</a>
          </span>
                <span class="r" style="line-height:30px;">共有数据：<strong>1</strong> 条</span></div>
            <div class="mt-20">
                <table class="table table-border table-bordered table-bg table-hover table-sort">
                    <thead>
                    <tr class="text-c">
                        <th>序号</th>
                        <th>行业分类</th>
                        <th>描述</th>
                        <th>已关联合作方</th>
                        <th>添加时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="c">
                        <tr class="text-c">
                            <td>${c.classId}</td>
                            <td>${c.className}</td>
                            <td>${c.classDescription}</td>
                            <td>${c.partnerNum}</td>
                            <td>
                                ${c.createTime}
                                <%--<fmt:parseDate value="${partner.createTime}" var="date" pattern="yyyy-MM-dd HH:mm:ss"/>--%>
                                <%--<fmt:formatDate value="${date}" pattern="yyyy-MM-dd hh:mm:ss" type="date"/>--%>
                            </td>
                            <td>
                                <a style="text-decoration:none" class="ml-5" onClick="project_del(this,'10001')"
                                   href="javascript:;" title="删除"><i class="Hui-iconfont">&#xe6e2;</i></a>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </div>

        </article>
    </div>
</section>
<script>

    $(".Hui-aside ul a").on("click", function () {
        console.log($(this).attr("data-href")), $(".content_iframe").attr("src", $(this).attr("data-href"))
    });

    $('.table-sort').dataTable({
        "aaSorting": [[1, "desc"]],//默认第几个排序
        "bStateSave": true,//状态保存
        "aoColumnDefs": [
            //{"bVisible": false, "aTargets": [ 3 ]} //控制列的隐藏显示
            {"orderable": false, "aTargets": [0, 8]}// 不参与排序的列
        ]
    });

    //*项目-编辑*/
    function project_edit(title, url, id, w, h) {
        var index = layer.open({
            type: 2,
            title: title,
            content: url
        });
        layer.full(index);
    }
    /*项目-删除*/
    function project_del(obj, id) {
        layer.confirm('确认要删除吗？', function (index) {
            $.ajax({
                type: 'POST',
                url: '',
                dataType: 'json',
                success: function (data) {
                    $(obj).parents("tr").remove();
                    layer.msg('已删除!', {icon: 1, time: 1000});
                },
                error: function (data) {
                    console.log(data.msg);
                },
            });
        });
    }
</script>
</body>
</html>
