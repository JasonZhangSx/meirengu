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

    <title>合作方列表</title>
</head>
<body>
<section class="Hui-article-box" style="left: 0; top: 0;">
    <nav class="breadcrumb"><i class="Hui-iconfont">&#xe67f;</i> 众筹项目 <span class="c-gray en">&gt;</span> 合作方列表 <a
            class="btn btn-success radius r" style="line-height:1.6em;margin-top:3px"
            href="javascript:location.replace(location.href);" title="刷新"><i class="Hui-iconfont">&#xe68f;</i></a></nav>
    <div class="Hui-article">
        <article class="cl pd-20">
            <div class="text-c">
                合作方名称：<input type="text" class="input-text" style="width:120px;">　
                分类：<span class="select-box mr-20" style="width:120px">
      					<select name="" class="select">
      						<option value="0">公司</option>
      						<option value="13">个人</option>
      					</select>
      					</span>
                <button name="" id="" class="btn btn-success radius" type="submit"><i class="Hui-iconfont">&#xe665;</i>
                    查 询
                </button>
            </div>
            <div class="cl pd-5 bg-1 bk-gray mt-20">
  				<span class="l">
            <a class="btn btn-primary radius" href="javascript:;"><i class="Hui-iconfont">&#xe634;</i> 导出</a>
            <a class="btn btn-primary radius" href="javascript:;"
               onClick="project_edit('合作方-合作方列表-添加','to_add','10001')" href="javascript:;" title="添加合作方"><i
                    class="Hui-iconfont">&#xe600;</i> 添加合作方</a>
          </span>
                <span class="r" style="line-height:30px;">共有数据：<strong>1</strong> 条</span></div>

            <div class="mt-20">
                <table class="table table-border table-bordered table-bg table-hover table-sort">
                    <thead>
                    <tr class="text-c">
                        <th>序号</th>
                        <th>合作方名称</th>
                        <th>分类</th>
                        <th>联系人姓名</th>
                        <th>联系电话</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${list}" var="partner">
                        <tr class="text-c">
                            <td>${partner.partnerId}</td>
                            <td>${partner.partnerName}</td>
                            <td>${partner.className}</td>
                            <td>${partner.contactsame}</td>
                            <td>${partner.contactsTelephone}</td>
                            <td>
                                <a style="text-decoration:none" class="ml-5"
                                   onClick="project_edit('合作方-合作方列表-详情','合作方-合作方列表-详情.html','10001')" href="javascript:;"
                                   title="查看"><i class="Hui-iconfont">&#xe725;</i></a>
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
    })
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