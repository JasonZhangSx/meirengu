<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    /*String basePath = "http://192.168.0.135/erp/";*/
    String imgPath = "https://test.img.meirenguvip.com/";
%>
<html>
<head>
    <base href="<%=basePath %>">
    <title>图片上传</title>
    <script type="text/javascript" src="lib/jquery/1.9.1/jquery.min.js"></script>
    <script type="text/javascript" src="static/upload-file/upload.css"></script>
</head>
<body>
    <%--<form action="upload" method="post" enctype="multipart/form-data">
        <input type="text" name="foldName">
        <input type="file" name="file">
        <input type="submit" value="上传">
    </form>--%>

    <%--<img id="imgshow" width="400" height="250" /><br />
    <input type="file" id="pic" name="pic" onchange="printFileInfo()" accept="image/*" multiple="multiple"/>
    <input type="button" value="上传图片" onclick="uploadFile()" /><br />
    <div id="parent">
        　　<div id="son"></div>
    </div>--%>
    <section class=" img-section">
        <p class="up-p">作品图片：<span class="up-span">最多可以上传5张图片，马上上传</span></p>
        <div class="z_photo upimg-div clear">
            <section class="up-section fl">
                <span class="up-span"></span>
                <img class="close-upimg" src="img/a7.png">
                <img class="up-img" src="blob:http://www.17sucai.com/4dad8d2a-652b-4236-8f3d-dbd63931f154">
                <p class="img-name-p">1492596784927.png</p>
                <input id="taglocation" name="taglocation" value="" type="hidden">
                <input id="tags" name="tags" value="" type="hidden">
            </section>
            <!--<section class="up-section fl">
                 <span class="up-span"></span>
                 <img src="/img/buyerCenter/a7.png" class="close-upimg">
                 <img src="/img/buyerCenter/3c.png" class="type-upimg" alt="添加标签">
                 <img src="/img/test2.jpg" class="up-img">
                 <p class="img-namep"></p>
                 <input id="taglocation" name="taglocation" value="" type="hidden">
                 <input id="tags" name="tags" value="" type="hidden">
             </section>-->
            <section class="z_file fl">
                <img src="static/upload-file/a11.png" class="add-img">
                <input type="file" name="file" id="file" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" multiple="">
            </section>
        </div>
    </section>
    <script type="text/javascript">

        var xhr;
        if(window.XMLHttpRequest){
            xhr = new XMLHttpRequest();
        }else{
            xhr = new ActiveXObject('Microsoft.XMLHTTP');
        }

        function printFileInfo(){

            var picFile = document.getElementById("pic");
            var files = picFile.files;
            for(var i=0; i<files.length; i++){
                var file = files[i];
                var div = document.createElement("div")
                div.innerHTML = "第("+ (i+1) +") 个文件的名字："+ file.name +
                        " , 文件类型："+ file.type +" , 文件大小:"+ file.size
                document.body.appendChild( div)
            }
        }

        //上传失败
        function uploadFailed(evt) {
            alert("上传失败");
        }

        /**
         * 侦查附件上传情况 ,这个方法大概0.05-0.1秒执行一次
         */
        function onprogress(evt){
            var loaded = evt.loaded;       //已经上传大小情况
            var tot = evt.total;       //附件总大小
            var per = Math.floor(100*loaded/tot);   //已经上传的百分比
            $("#son").html( per +"%" );
            $("#son").css("width" , per +"%");
        }

        //上传文件
        function uploadFile(foldName) {
            //将上传的多个文件放入formData中
            var picFileList = $("#pic").get(0).files;
            var formData = new FormData();
            for(var i=0; i< picFileList.length; i++){
                formData.append("file" , picFileList[i] );
            }
            formData.append("foldName", foldName);

            //监听事件
            xhr.upload.addEventListener("progress", onprogress, false);
            xhr.addEventListener("error", uploadFailed, false);//发送文件和表单自定义参数
            xhr.addEventListener("success", uploadSuccess, false);
            xhr.open("POST", "uploadMultiple");
            //记得加入上传数据formData
            xhr.send(formData);
            //异步接受响应
            xhr.onreadystatechange = function(){
                if(xhr.readyState == 4){
                    if(xhr.status == 200){
                        var responseData = eval('(' + xhr.responseText + ')');
                        if(responseData.code != 200){
                            alert("上传失败");
                            return;
                        }
                        console.log(responseData);
                        var data = responseData.data;
                        for(var i = 0; i < data.length; i++){
                            $("#imgshow").attr("src","http://test.img.meirenguvip.com/"+data[i]);
                        }
                    }
                }
            }

        }
    </script>
</body>
</html>
