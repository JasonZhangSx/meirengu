<%--
  Created by IntelliJ IDEA.
  User: xiaoyang
  Date: 2017/3/1
  Time: 20:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="Generator" content="EditPlus®">
    <meta name="Author" content="">
    <meta name="Keywords" content="">
    <meta name="Description" content="">
    <!--引入wangEditor.css-->
    <link rel="stylesheet" type="text/css" href="../dist/css/wangEditor.min.css">
    <title>Document</title>
</head>
<body>
<%--<input type="button" id="btn1" value="点击隐藏编辑器"/>--%>
<%--<input type="button" id="btn2" value="点击显示编辑器"/>--%>
<input type="button" id="btn3" value="点击获取编辑器内容"/>
<div id="div1" style="height:400px;">
    <p>请输入内容。。。。。..</p>
</div>
</body>

<!--引入jquery和wangEditor.js-->   <!--注意：javascript必须放在body最后，否则可能会出现问题-->
<script type="text/javascript" src="../dist/js/lib/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="../dist/js/wangEditor.min.js"></script>
<!--这里引用jquery和wangEditor.js-->
<script type="text/javascript">
    // 获取元素
    var div = document.getElementById('div1');
    // 生成编辑器
    var editor = new wangEditor(div);
    // 上传图片（举例）
    editor.config.uploadImgUrl = 'http://localhost:8081/testImg/';
    editor.create();
    $('#btn1').click(function () {
        // 销毁编辑器
        editor.destroy();
    });

    $('#btn2').click(function () {
        // 恢复编辑器
        editor.undestroy();
    });
    $('#btn3').click(function () {
        // 获取编辑器区域完整html代码
        var html = editor.$txt.html();

        // 获取编辑器纯文本内容
        var text = editor.$txt.text();

        // 获取格式化后的纯文本
        var formatText = editor.$txt.formatText();
    })
    function getEditHtml() {
        // 获取编辑器区域完整html代码
        var htmlValue = editor.$txt.html();
        return htmlValue;
    }
</script>
</html>

