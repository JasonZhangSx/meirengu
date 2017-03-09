<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <base href="<%=basePath%>">
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
        $("#itemDetails").val(va);
// jquery 表单提交
        $("#itemDataForm").ajaxSubmit(function(message) {
            var map = eval(message);
            if(confirm(map.msg+",是否进入项目列表"))
            {
                //如果是true ，那么就把页面转向thcjp.cnblogs.com
                location.href="<%=path%>/itemPro/show";
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
        <li>项目管理</li>
        <li>添加项目</li>

    </ul>
</div>

<form id="itemDataForm"  enctype="multipart/form-data" action="<%=path%>/itemPro" class="form-horizontal" method="post" onsubmit="return saveReport();">
    <h5 class="page-header alert-info" style="padding:10px; margin:0px; margin-bottom:5px;">基本信息</h5>
    <input type="text" id="itemDetails" name="itemDetails" hidden="hidden" value=""/>
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">项目名称</label>
                <div class="col-sm-9">
                    <input type="text" name="itemName"  class="form-control input-sm" placeholder="请输入项目名称"/>
                </div>
            </div>
        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">项目副标题</label>
                <div class="col-sm-9">
                    <input type="text" name="subtitle" class="form-control input-sm" placeholder="请输入项目副标题"/>
                </div>
            </div>
        </div>

    </div>
    <!-- 开始2 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">项目品牌</label>
                <div class="col-sm-9">
                    <select class="form-control input-sm" name="brandId">
                        <c:forEach items="${map.brand}" var="b">
                            <option value="${b.brandId}">${b.brandName}</option>
                            <%--<option value="${h.icId}">${h.icName}</option>--%>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">项目分类</label>
                <div class="col-sm-9">
                    <select class="form-control input-sm" name="icId">
                        <c:forEach items="${map.itemClass}" var="c">
                            <optgroup label="${c.icName}">
                                <c:forEach items="${c.list}" var="i">
                                    <option value="${i.icId}">${i.icName}</option>
                                </c:forEach>
                            </optgroup>
                            <%--<option value="${h.icId}">${h.icName}</option>--%>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
    </div>
    <!-- 结束2 -->
    <!-- 开始3 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">项目类型</label>
                <div class="col-sm-9">
                    <select class="form-control input-sm" name="typeId">
                        <c:forEach items="${map.type}" var="t">
                            <option value="${t.typeId}">${t.typeName}</option>
                            <%--<option value="${h.icId}">${h.icName}</option>--%>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">所属医院</label>
                <div class="col-sm-9">
                    <select class="form-control input-sm" name="hospitalId">
                        <c:forEach items="${map.hospital}" var="h">
                            <option value="${h.hospitalId}">${h.hospitalName}</option>
                            <%--<option value="${h.icId}">${h.icName}</option>--%>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
    </div>
    <!-- 结束3 -->
    <!-- 开始4 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">所属医生</label>
                <div class="col-sm-9">
                    <select class="form-control input-sm" name="doctorId">
                        <c:forEach items="${map.doctor}" var="d">
                            <option value="${d.doctorId}">${d.doctorName}</option>
                            <%--<option value="${h.icId}">${h.icName}</option>--%>
                        </c:forEach>
                    </select>
                </div>
            </div>
        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">是否组合</label>
                <div class="col-sm-9">
                    <select class="form-control input-sm" name="itemFlag">
                        <option value="0" selected="selected">否</option>
                        <option value="1" >是</option>
                    </select>
                </div>
            </div>
        </div>
    </div>
    <!-- 结束4 -->
    <!-- 开始4 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">原价</label>
                <div class="col-sm-9">
                    <input type="text" name="originalPrice" class="form-control input-sm" placeholder="请输入原价"/>
                </div>
            </div>

        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">售价</label>
                <div class="col-sm-9">
                    <input type="text" name="sellingPrice" class="form-control input-sm" placeholder="请输入售价"/>
                </div>
            </div>
        </div>
    </div>
    <!-- 结束4 -->
    <!-- 开始5 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">计价单位</label>
                <div class="col-sm-9">
                    <select class="form-control input-sm" name="chargeUnit">
                        <option value="0" selected="selected">单次</option>
                        <option value="1" >一个疗程</option>
                        <option value="2" >包干价</option>
                        <option value="3" >一个单位</option>
                    </select>
                </div>
            </div>
        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">其他说明</label>
                <div class="col-sm-9">
                    <input type="text" name="chargeRemarks" class="form-control input-sm" placeholder="请输入收费标准其他说明"/>
                </div>
            </div>

        </div>
    </div>
    <!-- 结束5 -->
    <!-- 开始6 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">封面图片</label>
                <div class="col-sm-9">
                    <input type="file" name="itemImageOne_750_480" multiple="multiple"  accept="image/*"  class="form-control input-sm"/>
                </div>
            </div>
        </div>

        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">其他图</label>
                <div class="col-sm-9">
                    <input type="file" name="itemImageMoreOne_750_480" multiple="multiple"  accept="image/*"  class="form-control input-sm"/>
                </div>
            </div>
        </div>

    </div>
    <!-- 结束6 -->

    <!-- 开始7 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">有效期(天)</label>
                <div class="col-sm-9">
                    <input type="text" name="validityPeriods" class="form-control input-sm" placeholder="请输入收费标准其他说明"/>
                </div>
            </div>
        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">特殊说明</label>
                <div class="col-sm-9">
                    <input type="text" name="specialRemarks" class="form-control input-sm" placeholder="请输入有效期特殊说明"/>
                </div>
            </div>
        </div>
    </div>
    <!-- 结束7 -->
    <!-- 开始8 -->
    <div class="row">
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">售卖数量</label>
                <div class="col-sm-9">
                    <input type="text" name="limitNum" value="0" class="form-control input-sm" placeholder="请输入售卖数量"/>
                </div>
            </div>
        </div>
        <div class="col-sm-5">
            <div class="form-group">
                <label class="col-sm-3 control-label">下线时间</label>
                <div class="col-sm-9">
                    <input type="date" name="endDate" class="form-control input-sm" placeholder="请输入下线时间"/>
                </div>
            </div>
        </div>
    </div>
    <!-- 结束8 -->
    <h5 class="page-header alert-info" style="padding:10px; margin:0px; margin-bottom:5px;">项目详情内容</h5>
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
