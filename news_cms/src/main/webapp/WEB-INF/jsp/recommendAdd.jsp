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
                <form class="form-horizontal" id="recommendAdd" method="post">
                    <div class="control-group">
                        <label class="control-label">推荐位名称</label>
                        <div class="controls">
                            <input name="name" type="text" />
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">推荐位简介</label>
                        <div class="controls">
                            <input name="intro" type="text" />
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">推荐位类别</label>
                        <div class="controls">
                            <input name="class" type="radio" value="0"/>图片
                            <input name="class" type="radio" value="1"/>文字
                            <input name="class" type="radio" value="2"/>幻灯片
                            <input name="class" type="radio" value="3"/>Flash
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">展示方式</label>
                        <div class="controls">
                            <input name="display" type="radio" value="0"/>幻灯片
                            <input name="display" type="radio" value="1"/>多条推荐展示
                            <input name="display" type="radio" value="2"/>单条推荐展示
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">是否启用</label>
                        <div class="controls">
                            <input name="is_use" type="radio" value="0"/>不启用
                            <input name="is_use" type="radio" value="1"/>启用
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">宽度</label>
                        <div class="controls">
                            <input name="width" type="text"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">高度</label>
                        <div class="controls">
                            <input name="height" type="text"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">推荐位点击率</label>
                        <div class="controls">
                            <input name="recomment_num" type="text"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">推荐位点击率</label>
                        <div class="controls">
                            <input name="click_num" type="text"/>
                        </div>
                    </div>
                    <div class="control-group">
                        <label class="control-label">默认内容</label>
                        <div class="controls">
                            <input type="hidden" name="default_content" id="default_content">
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
<%--<div id="btns">
    <div>
        <button onclick="getAllHtml()">获得整个html的内容</button>
        <button onclick="getContent()">获得内容</button>
        <button onclick="setContent()">写入内容</button>
        <button onclick="setContent(true)">追加内容</button>
        <button onclick="getContentTxt()">获得纯文本</button>
        <button onclick="getPlainTxt()">获得带格式的纯文本</button>
        <button onclick="hasContent()">判断是否有内容</button>
        <button onclick="setFocus()">使编辑器获得焦点</button>
        <button onmousedown="isFocus(event)">编辑器是否获得焦点</button>
        <button onmousedown="setblur(event)" >编辑器失去焦点</button>

    </div>
    <div>
        <button onclick="getText()">获得当前选中的文本</button>
        <button onclick="insertHtml()">插入给定的内容</button>
        <button id="enable" onclick="setEnabled()">可以编辑</button>
        <button onclick="setDisabled()">不可编辑</button>
        <button onclick=" UE.getEditor('editor').setHide()">隐藏编辑器</button>
        <button onclick=" UE.getEditor('editor').setShow()">显示编辑器</button>
        <button onclick=" UE.getEditor('editor').setHeight(300)">设置高度为300默认关闭了自动长高</button>
    </div>

    <div>
        <button onclick="getLocalData()" >获取草稿箱内容</button>
        <button onclick="clearLocalData()" >清空草稿箱</button>
    </div>

</div>
<div>
    <button onclick="createEditor()">
        创建编辑器</button>
    <button onclick="deleteEditor()">
        删除编辑器</button>
</div>--%>

<script type="text/javascript">

    $(document).ready(function(){
        $("#add").click(function(){
            var default_content = UE.getEditor('editor').getContent();
            $("#default_content").val(default_content);
            console.log($("#default_content").val());

            $.ajax({
                type: "POST",
                url:"../rp",
                data:$('#recommendAdd').serialize(),// 你的formid
                async: false,
                error: function(request) {
                    alert("Connection error");
                },
                success: function(data) {
                    alert("success");
                    console.log(data)
                    /*var json = JSON.parse(data);
                    alert(json);*/
                    //$("#commonLayout_appcreshi").parent().html(data);
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
            return '/upload/image';
        } else if (action == 'uploadvideo') {
            return '/upload/video';
        } else {
            return this._bkGetActionUrl.call(this, action);
        }
    }


    /*function isFocus(e){
        alert(UE.getEditor('editor').isFocus());
        UE.dom.domUtils.preventDefault(e)
    }
    function setblur(e){
        UE.getEditor('editor').blur();
        UE.dom.domUtils.preventDefault(e)
    }
    function insertHtml() {
        var value = prompt('插入html代码', '');
        UE.getEditor('editor').execCommand('insertHtml', value)
    }
    function createEditor() {
        enableBtn();
        UE.getEditor('editor');
    }
    function getAllHtml() {
        alert(UE.getEditor('editor').getAllHtml())
    }
    function getContent() {
        var arr = [];
        arr.push("使用editor.getContent()方法可以获得编辑器的内容");
        arr.push("内容为：");
        arr.push(UE.getEditor('editor').getContent());
        alert(arr.join("\n"));
    }
    function getPlainTxt() {
        var arr = [];
        arr.push("使用editor.getPlainTxt()方法可以获得编辑器的带格式的纯文本内容");
        arr.push("内容为：");
        arr.push(UE.getEditor('editor').getPlainTxt());
        alert(arr.join('\n'))
    }
    function setContent(isAppendTo) {
        var arr = [];
        arr.push("使用editor.setContent('欢迎使用ueditor')方法可以设置编辑器的内容");
        UE.getEditor('editor').setContent('欢迎使用ueditor', isAppendTo);
        alert(arr.join("\n"));
    }
    function setDisabled() {
        UE.getEditor('editor').setDisabled('fullscreen');
        disableBtn("enable");
    }

    function setEnabled() {
        UE.getEditor('editor').setEnabled();
        enableBtn();
    }

    function getText() {
        //当你点击按钮时编辑区域已经失去了焦点，如果直接用getText将不会得到内容，所以要在选回来，然后取得内容
        var range = UE.getEditor('editor').selection.getRange();
        range.select();
        var txt = UE.getEditor('editor').selection.getText();
        alert(txt)
    }

    function getContentTxt() {
        var arr = [];
        arr.push("使用editor.getContentTxt()方法可以获得编辑器的纯文本内容");
        arr.push("编辑器的纯文本内容为：");
        arr.push(UE.getEditor('editor').getContentTxt());
        alert(arr.join("\n"));
    }
    function hasContent() {
        var arr = [];
        arr.push("使用editor.hasContents()方法判断编辑器里是否有内容");
        arr.push("判断结果为：");
        arr.push(UE.getEditor('editor').hasContents());
        alert(arr.join("\n"));
    }
    function setFocus() {
        UE.getEditor('editor').focus();
    }
    function deleteEditor() {
        disableBtn();
        UE.getEditor('editor').destroy();
    }
    function disableBtn(str) {
        var div = document.getElementById('btns');
        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            if (btn.id == str) {
                UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
            } else {
                btn.setAttribute("disabled", "true");
            }
        }
    }
    function enableBtn() {
        var div = document.getElementById('btns');
        var btns = UE.dom.domUtils.getElementsByTagName(div, "button");
        for (var i = 0, btn; btn = btns[i++];) {
            UE.dom.domUtils.removeAttributes(btn, ["disabled"]);
        }
    }

    function getLocalData () {
        alert(UE.getEditor('editor').execCommand( "getlocaldata" ));
    }

    function clearLocalData () {
        UE.getEditor('editor').execCommand( "clearlocaldata" );
        alert("已清空草稿箱")
    }*/
</script>
</body>
</html>
