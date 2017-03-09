<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=path%>">
    <title>首页</title>
    <!-- 新 Bootstrap 核心 CSS 文件 -->
    <link rel="stylesheet" href="<%=path%>/css/bootstrap.min.css">
    <!-- jQuery文件。务必在bootstrap.min.js 之前引入 -->
    <script src="<%=path%>/js/jquery.min.js"></script>
    <script src="<%=path%>/js/jquery-form.js"></script>
    <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
    <script src="<%=path%>/js/bootstrap.min.js"></script>
    <!--必要样式-->
    <link href="http://hovertree.com/ziyuan/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="http://hovertree.com/texiao/bootstrap/4/css/city-picker.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="<%=path%>/view/editor/dist/css/wangEditor.min.css">
</head>
<script>
    function saveReport() {
        // 获取编辑器区域完整html代码
        var va=getEditHtml();
//        var html = editor.$txt.html();
        $("#hospitalRemark").val(va);
// jquery 表单提交
        $("#hospitalDataForm").ajaxSubmit(function(message) {
            var map = eval(message);
            if(confirm(map.msg+",是否进入医院列表"))
            {
                //如果是true ，那么就把页面转向thcjp.cnblogs.com
                location.href="<%=path%>/hospital/show";
            }
// 对于表单提交成功后处理，message为提交页面saveReport.htm的返回内容
        });

        return false; // 必须返回false，否则表单会自己再做一次提交操作，并且页面跳转
    }
</script>
<body>
<div style="padding:0px; margin:0px;">
    <ul class="breadcrumb" style="  margin:0px; " >
        <li><a href="#">系统管理</a></li>
        <li>医院管理</li>
        <li>添加医院</li>

    </ul>
</div>

<form id="hospitalDataForm"  enctype="multipart/form-data" action="<%=path%>/hospital" class="form-horizontal" method="post" onsubmit="return saveReport();"> >
    <h5 class="page-header alert-info" style="padding:10px; margin:0px; margin-bottom:5px;">基本信息</h5>
    <input type="text" id="hospitalRemark" name="hospitalRemark" hidden="hidden" value=""/>
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">医院名称</label>
                <div class="col-sm-9">
                    <input type="text" name="hospitalName"  class="form-control input-sm" placeholder="请输入医院名称"/>
                </div>
            </div>
        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">类型</label>
                <div class="col-sm-9">
                    <select class="form-control input-sm" name="hospitalType">
                        <option value="1">公立</option>
                        <option value="2">民营</option>
                    </select>
                </div>
            </div>
        </div>

    </div>
    <!-- 开始2 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">医院类型</label>
                <div class="col-sm-9">
                    <select class="form-control input-sm" name="hospitalClass">
                        <option value="1">公立三级综合医院</option>
                        <option value="2">整形外科医院</option>
                        <option value="3">民营医疗美容医院</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">logo图片</label>
                <div class="col-sm-9">
                    <tr>
                        <%--<input type="file" name="hospitalLogoImg_200_200" multiple="multiple"  class="form-control input-sm"/>--%>
                        <input type="file" name="hospitalLogoImg_200_200" accept="image/*" class="form-control input-sm"/>
                    </tr>
                </div>
            </div>
        </div>
    </div>
    <!-- 结束2 -->
    <!-- 开始3 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">医院网站</label>
                <div class="col-sm-9">
                    <input type="text" name="hospitalWebsite" class="form-control input-sm" placeholder="请输入医院网站"/>
                </div>
            </div>

        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">医院地址</label>
                <div class="col-sm-9">
                    <input type="text" name="hospitalAddress" class="form-control input-sm" placeholder="请输入医院地址"/>
                </div>
            </div>
        </div>
    </div>
    <!-- 结束3 -->
    <!-- 开始4 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">医院电话</label>
                <div class="col-sm-9">
                    <input type="text" name="hospitalTel" class="form-control input-sm" placeholder="请输入医院电话"/>
                </div>
            </div>

        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">资格证书</label>
                <div class="col-sm-9">
                    <input type="file" name="certificatePicOne_700_700" accept="image/*" multiple="multiple"  class="form-control input-sm"/>
                </div>
            </div>
        </div>
    </div>
    <!-- 结束4 -->
    <!-- 开始4 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">联系人</label>
                <div class="col-sm-9">
                    <input type="text" name="contacts" class="form-control input-sm" placeholder="请输入联系人名称"/>
                </div>
            </div>

        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">联系人电话</label>
                <div class="col-sm-9">
                    <input type="text" name="contactsTel" class="form-control input-sm" placeholder="请输入联系人电话"/>
                </div>
            </div>
        </div>
    </div>
    <!-- 结束4 -->
    <!-- 开始5 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">联系邮箱</label>
                <div class="col-sm-9">
                    <input type="text" name="email" class="form-control input-sm" placeholder="请输入联系邮箱 "/>
                </div>
            </div>

        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">成立时间</label>
                <div class="col-sm-9">
                    <input type="date" name="hiredate" class="form-control input-sm" placeholder="请输入成立时间"/>
                </div>
            </div>

        </div>
    </div>
    <!-- 结束5 -->
    <!-- 开始6 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">医院图片</label>
                <div class="col-sm-9">
                    <input type="file" name="hospitalPic_200_200" accept="image/*" multiple="multiple"  class="form-control input-sm"/>
                </div>
            </div>

        </div>
    </div>
    <!-- 结束6 -->
    <h5 class="page-header alert-info" style="padding:10px; margin:0px; margin-bottom:5px;">医院简介</h5>
    <div id="div1" style="height:400px;">
        <p>请输入内容。。。。。..</p>
    </div>
    <%--<div class="row">--%>
    <%--<div class="col-sm-10">--%>
    <%--<div class="form-group">--%>
    <%--<label class="col-sm-3 control-label">医院简介</label>--%>
    <%--<div class="col-sm-9">--%>
    <%--<textarea class="form-control" name="hospitalRemark"></textarea>--%>
    <%--</div>--%>
    <%--</div>--%>

    <%--</div>--%>

    <%--</div>--%>
    <%--<h5 class="page-header alert-info" style="padding:10px; margin:0px; margin-bottom:5px;">账号信息</h5>--%>
    <%--<div class="row">--%>
    <%--<div class="col-sm-5">--%>
    <%--<div class="form-group">--%>
    <%--<label class="col-sm-3 control-label">账号</label>--%>
    <%--<div class="col-sm-9">--%>
    <%--<input type="text" name="number" class="form-control input-sm" placeholder="请输入账号 "/>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%----%>
    <%--</div>--%>
    <%--<div class="col-sm-5">--%>
    <%--<div class="form-group">--%>
    <%--<label class="col-sm-3 control-label">密码</label>--%>
    <%--<div class="col-sm-9">--%>
    <%--<input type="password" name="password" class="form-control input-sm" placeholder="请输入密码"/>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</div>--%>
    <%--</div>--%>
    <div class="row">
        <div class="col-sm-3 col-sm-offset-4">
            <input  type="submit" class="btn btn-success" value="保存"/>
            <input  type="reset" class="btn  btn-danger" value="取消"/>
        </div>
    </div>
</form>

</body>
<script type="text/javascript" src="<%=path%>/view/editor/dist/js/lib/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="<%=path%>/view/editor/dist/js/wangEditor.min.js"></script>
<script type="text/javascript">
    var div = document.getElementById('div1');
    var editor = new wangEditor(div);
    editor.config.uploadImgUrl = '<%=path%>/service/saveEditImage';
    editor.create();
    function getEditHtml() {
        var htmlValue = editor.$txt.html();
        return htmlValue;
    }
</script>
</html>
