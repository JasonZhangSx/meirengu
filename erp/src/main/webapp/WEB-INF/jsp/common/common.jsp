<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!--[if lt IE 9]>
<script type="text/javascript" src="/erp/lib/html5.js"></script>
<script type="text/javascript" src="/erp/lib/respond.min.js"></script>
<![endif]-->
<link rel=stylesheet type=text/css href="/erp/static/h-ui/css/H-ui.min.css" />
<link rel=stylesheet type=text/css href="/erp/static/h-ui.admin/css/H-ui.admin.css" />
<link rel=stylesheet type=text/css href="/erp/lib/Hui-iconfont/1.0.8/iconfont.css"/>
<link rel=stylesheet type=text/css href="/erp/static/h-ui.admin/skin/default/skin.css" id=skin/>
<link rel=stylesheet type=text/css href="/erp/static/h-ui.admin/css/style.css"/>
<!--[if IE 6]>
<script type="text/javascript" src="http://lib.h-ui.net/DD_belatedPNG_0.0.8a-min.js"></script>
<script>DD_belatedPNG.fix('*');</script><![endif]-->

<script src="/erp/lib/jquery/1.9.1/jquery.min.js"></script>
<script src="/erp/lib/layer/2.4/layer.js"></script>
<script src="/erp/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
<script src="/erp/lib/jquery.validation/1.14.0/validate-methods.js"></script>
<script src="/erp/lib/jquery.validation/1.14.0/messages_zh.js"></script>
<script src="/erp/static/h-ui/js/H-ui.js"></script>
<script src="/erp/static/h-ui.admin/js/H-ui.admin.page.js"></script>
<!--请在下方写此页面业务相关的脚本-->
<script type="text/javascript" src="/erp/lib/My97DatePicker/4.8/WdatePicker.js"></script>
<script type="text/javascript" src="/erp/lib/datatables/1.10.0/jquery.dataTables.min.js"></script>
<script type="text/javascript" src="/erp/lib/laypage/1.2/laypage.js"></script>
