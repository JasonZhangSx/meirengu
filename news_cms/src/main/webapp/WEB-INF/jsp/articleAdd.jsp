<%--
  Created by IntelliJ IDEA.
  User: 建新
  Date: 2017/1/9
  Time: 19:44
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    System.out.print(request.getRequestURI());
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>推荐位新增</title>
    <meta http-equiv="Content-Type" content="text/html;charset=utf-8"/>

    <link rel="stylesheet" href="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/css/bootstrap.min.css">
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script src="http://cdn.static.runoob.com/libs/bootstrap/3.3.7/js/bootstrap.min.js"></script>

    <script src="../resource/js/jquery-3.1.1.min.js"></script>
    <script type="text/javascript" charset="utf-8" src="../resource/UEditor1.4.3.3/ueditor.config.js"></script>
    <script type="text/javascript" charset="utf-8" src="../resource/UEditor1.4.3.3/ueditor.all.min.js"> </script>
    <!--建议手动加在语言，避免在ie下有时因为加载语言失败导致编辑器加载失败-->
    <!--这里加载的语言文件会覆盖你在配置项目里添加的语言类型，比如你在配置项目里配置的是英文，这里加载的中文，那最后就是中文-->
    <script type="text/javascript" charset="utf-8" src="../resource/UEditor1.4.3.3/lang/zh-cn/zh-cn.js"></script>
    <style type="text/css">
        div{
            width:100%;
        }
    </style>
</head>
<body>
<h2>文章新增</h2>
<div>
    <div class="container-fluid">
        <div class="row-fluid">
            <div class="span12">
                <form class="form-horizontal" id="articleAdd" method="post" accept-charset="utf-8"
                      action="../article/insert" enctype="multipart/form-data">
                    <div class="control-group">
                        <label class="control-label">文章分类</label>
                        <div class="controls">
                            <select id="ac_id" name="ac_id">

                            </select>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">文章头图</label>
                        <div class="controls">
                            <input name="img" type="file" />
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">文章标题</label>
                        <div class="controls">
                            <input name="title" type="text"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">是否展示</label>
                        <div class="controls">
                            <input name="show" type="radio" value="1" checked/>是
                            <input name="show" type="radio" value="0"/>否
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">排序</label>
                        <div class="controls">
                            <input name="sort" type="text" value="1"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">是否为推荐文章</label>
                        <div class="controls">
                            <input name="is_commend" type="radio" value="1"/>是
                            <input name="is_commend" type="radio" value="0" checked/>否
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">是否为轮播图</label>
                        <div class="controls">
                            <input name="is_banner" type="radio" value="1"/>是
                            <input name="is_banner" type="radio" value="0" checked/>否
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">是否发布</label>
                        <div class="controls">
                            <input name="is_publish" type="radio" value="1" checked/>是
                            <input name="is_publish" type="radio" value="0"/>否
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">文章内容</label>
                        <div class="controls">
                            <input type="hidden" name="content" id="content">
                            <script id="editor" type="text/plain" style="width:1024px;height:500px;"></script>
                        </div>
                    </div>
                    <div class="control-group">
                        <div class="controls">
                            <button type="button" id="add" class="btn">新增</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

    <form action="/upload/images" method="post" enctype="multipart/form-data">
        <input type="file" name="upfile">
        <input type="submit" value="上传">
    </form>
</div>
<script type="text/javascript">

    $(document).ready(function(){

        $.ajax({
            type: "POST",
            url:"../class/list",
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            error: function(request) {
                alert("Connection error");
            },
            success: function(data) {
                data = data.data;
                var class_list = data.list;
                for (var i=0;i<class_list.length;i++){
                    $("#ac_id").append("<option value='"+class_list[i].id+"'>"+class_list[i].name+"</option>");
                }
                console.log(data);
            }
        });

        $("#add").click(function(){

            var content = UE.getEditor('editor').getContent();
            $("#content").val(content);
            console.log($("#content").val());
            var formData = new FormData($( "#articleAdd" )[0]);

            $.ajax({
                type: "POST",
                url:"../article/insert",
                data:formData,
                async: false,
                cache: false,
                contentType: false,
                processData: false,
                error: function(request) {
                    alert("Connection error");
                },
                success: function(data) {
                    alert("success");
                    console.log(data);
                }
            });
        });
    });

    //实例化编辑器
    //建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
    var ue = UE.getEditor('editor');
    UE.Editor.prototype._bkGetActionUrl = UE.Editor.prototype.getActionUrl;
    UE.Editor.prototype.getActionUrl = function(action) {
        if (action == 'uploadimage' || action == 'uploadscrawl' || action == 'uploadimage') {
            return '/news_cms/upload/image';
        } else if (action == 'uploadvideo') {
            return '/news_cms/upload/video';
        } else {
            return this._bkGetActionUrl.call(this, action);
        }
    }

</script>
</body>
</html>
