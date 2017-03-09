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
    <script src="/js/bootstrap.min.js"></script>
    <!--必要样式-->
    <link href="http://hovertree.com/ziyuan/bootstrap/3.3.6/css/bootstrap.min.css" rel="stylesheet" type="text/css" />
    <link href="http://hovertree.com/texiao/bootstrap/4/css/city-picker.css" rel="stylesheet" type="text/css" />
    <link rel="stylesheet" type="text/css" href="<%=path%>/view/editor/dist/css/wangEditor.min.css">
</head>
<script type="text/javascript">
    function saveReport() {
        // 获取编辑器区域完整html代码
        var va=getEditHtml();
//        var html = editor.$txt.html();
        $("#doctorDetail").val(va);

// jquery 表单提交
        $("#doctorDataForm").ajaxSubmit(function(message) {
            var map = eval(message);
            if(confirm(map.msg+",是否进入医生列表"))
            {
                //如果是true ，那么就把页面转向thcjp.cnblogs.com
                location.href="/dotor/show";
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
        <li>医生管理</li>
        <li>添加医生</li>
    </ul>
</div>

<form id="doctorDataForm"  enctype="multipart/form-data" action="<%=path%>/doctor" class="form-horizontal" method="post" onsubmit="return saveReport();">
    <h5 class="page-header alert-info" style="padding:10px; margin:0px; margin-bottom:5px;">基本信息</h5>
    <input type="text" id="doctorDetail" name="doctorDetail" hidden="hidden" value=""/>
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">医生名称</label>
                <div class="col-sm-9">
                    <input type="text" name="doctorName"  class="form-control input-sm" placeholder="请输入医生名称"/>
                </div>
            </div>
        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">所属医院</label>
                <div class="col-sm-9">
                    <select class="form-control input-sm" name="hospitalId">
                        <c:forEach items="${map.data.list}" var="h">
                            <option value="${h.hospitalId}">${h.hospitalName}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>

    </div>
    <!-- 开始2 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">医生职称</label>
                <div class="col-sm-9">
                    <input type="text" name="doctorTitle"  class="form-control input-sm" placeholder="请输入医生职称"/>
                </div>
            </div>
        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">医生照片</label>
                <div class="col-sm-9">
                    <tr>
                        <%--<input type="file" name="hospitalLogoImg_200_200" multiple="multiple"  class="form-control input-sm"/>--%>
                        <input type="file" name="doctorPicImg_200_200" accept="image/*"  class="form-control input-sm"/>
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
                <label class="col-sm-3 control-label">副标题</label>
                <div class="col-sm-9">
                    <input type="text" name="doctorViceTitle" class="form-control input-sm" placeholder="请输入医生副标题"/>
                </div>
            </div>
        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">医生标签</label>
                <div class="col-sm-9">
                    <input type="text" name="doctorLabel" class="form-control input-sm" placeholder="医生标签,使用“,”分割"/>
                </div>
            </div>
        </div>
    </div>
    <!-- 结束3 -->
    <!-- 开始4 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">性别</label>
                <div class="col-sm-9">
                    <select class="form-control input-sm" name="sex">
                        <option selected="selected" value="0">男</option>
                        <option value="1">女</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">医生电话</label>
                <div class="col-sm-9">
                    <input type="text" name="mobile" class="form-control input-sm" placeholder="请输入医生电话"/>
                </div>
            </div>
        </div>
    </div>
    <!-- 结束4 -->
    <!-- 开始4 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">最高学历</label>
                <div class="col-sm-9">
                    <select class="form-control input-sm" name="culturalTop">
                        <option selected="selected" value="硕士">硕士</option>
                        <option value="博士">博士</option>
                        <option value="本科">本科</option>
                        <option value="专科">专科</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">学习经历</label>
                <div class="col-sm-9">
                    <input type="text" name="culturalExperience" class="form-control input-sm" placeholder="请输入学习经历"/>
                </div>
            </div>
        </div>
    </div>
    <!-- 结束4 -->
    <!-- 开始5 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">擅长医术</label>
                <div class="col-sm-9">
                    <input type="text" name="goodat" class="form-control input-sm" placeholder="请输入擅长医术 "/>
                </div>
            </div>
        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">职业经历</label>
                <div class="col-sm-9">
                    <input type="text" name="professionExperience" class="form-control input-sm" placeholder="请输入职业经历 "/>
                </div>
            </div>
        </div>
    </div>
    <!-- 结束5 -->
    <!-- 开始6 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">资格证书号</label>
                <div class="col-sm-9">
                    <input type="text" name="certificateNo" class="form-control input-sm" placeholder="请输入医师资格证书号 "/>
                </div>
            </div>
        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">执业证书照</label>
                <div class="col-sm-9">
                    <input type="file" name="certificatePicOne_750_480" multiple="multiple"  accept="image/*"  class="form-control input-sm"/>
                </div>
            </div>
        </div>
    </div>
    <!-- 结束6 -->
    <h5 class="page-header alert-info" style="padding:10px; margin:0px; margin-bottom:5px;">医生简介</h5>
    <div class="row">
        <div class="col-sm-10">
            <div class="form-group">
                <label class="col-sm-3 control-label">医生简介</label>
                <div class="col-sm-9">
                    <textarea class="form-control" name="doctorProfile"></textarea>
                </div>
            </div>
        </div>
    </div>

    <!-- 结束6 -->
    <h5 class="page-header alert-info" style="padding:10px; margin:0px; margin-bottom:5px;">医生详情</h5>
    <%--<iframe id="mainframe" name="mainframe"style="width: 100%;border: 0px;" src="/view/editor/html/index.jsp"> </iframe>--%>
    <div id="div1" style="height:400px;">
        <p>请输入内容。。。。。..</p>
    </div>
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
