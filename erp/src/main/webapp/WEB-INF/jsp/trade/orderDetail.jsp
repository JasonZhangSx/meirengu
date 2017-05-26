<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="../common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <base href="<%=basePath %>">
    <meta charset=utf-8>
    <meta name=renderer content=webkit|ie-comp|ie-stand>
    <meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
    <meta name=viewport content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <meta http-equiv=Cache-Control content=no-siteapp/>
    <link rel=Bookmark href=favicon.ico>
    <link rel="Shortcut Icon" href=favicon.ico/>
    <meta name=keywords content=xxxxx>
    <meta name=description content=xxxxx>
    <title>订单详情</title>
<style>
    .edit_h31 {
        border-bottom: 1px #ddd solid;
        overflow: hidden;
    }
</style>
</head>
<body>
<div class="page-container">
    <!-- 选项卡 -->

    <span class="form form-horizontal" id="form-article-add">
        <div class="row cl">
            <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">订单信息</h3>
        </div>
        <div class="content_set">
            <div class="item">
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">订单状态：</label>
                    <div class="formControls col-xs-8 col-sm-8">
                        <c:choose>
                            <c:when test="${order.orderState eq 1}">
                                待审核
                            </c:when>
                            <c:when test="${order.orderState eq 2}">
                                预约审核通过--待支付
                            </c:when>
                            <c:when test="${order.orderState eq 3}">
                                预约审核不通过--已失效
                            </c:when>
                            <c:when test="${order.orderState eq 4}">
                                待支付
                            </c:when>
                            <c:when test="${order.orderState eq 5}">
                                已失效
                            </c:when>
                            <c:when test="${order.orderState eq 6}">
                                已支付
                            </c:when>
                            <c:when test="${order.orderState eq 9}">
                                退款待审核
                            </c:when>
                            <c:when test="${order.orderState eq 10}">
                                确认退款
                            </c:when>
                            <c:when test="${order.orderState eq 11}">
                                拒绝退款
                            </c:when>
                            <c:when test="${order.orderState eq 11}">
                                退款成功
                            </c:when>
                        </c:choose>

                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">订单编号：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${order.orderSn}
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">创建时间：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <jsp:useBean id="dateValue" class="java.util.Date"/>
                        <jsp:setProperty name="dateValue" property="time" value="${order.createTime}"/>
                        <%--<fmt:parseDate value="${order.createTime}" pattern="yyyy-MM-dd HH:mm" var="createTime"/>--%>
                        <fmt:formatDate value="${dateValue}" pattern="yyyy-MM-dd HH:mm:ss"/>
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">用户姓名：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${order.userName}
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">用户电话：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${order.userPhone}
                    </div>
                </div>
                <c:if test="${order.userAddressId ne 0}">
                    <div class="row cl">
                        <label class="form-label col-xs-4 col-sm-2">收件人：</label>
                        <div class="formControls col-xs-8 col-sm-3">
                                ${order.addUserName}
                        </div>
                        <label class="form-label col-xs-4 col-sm-2">电话：</label>
                        <div class="formControls col-xs-8 col-sm-3">
                                ${order.addUserPhone}
                        </div>
                    </div>
                    <div class="row cl">
                        <label class="form-label col-xs-4 col-sm-2">地址：</label>
                        <div class="formControls col-xs-8 col-sm-8">
                                ${order.addProvince + order.addCity + order.addArea + order.addUserAddress}
                        </div>
                    </div>
                </c:if>

            </div>
        </div>
        <div class="row cl">
            <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">购买信息</h3>
        </div>
        <div class="content_set">
            <div class="item">
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">项目名称：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${order.itemName}
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">众筹类型：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <c:choose>
                            <c:when test="${order.itemType eq 1}">产品众筹</c:when>
                            <c:when test="${order.itemType eq 2}">收益权众筹</c:when>
                            <c:when test="${order.itemType eq 3}">股权众筹</c:when>
                        </c:choose>

                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">回报档位：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${order.itemLevelName}
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">是否分红档：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <c:choose>
                            <c:when test="${order.isShareBonus eq 1}">是</c:when>
                            <c:when test="${order.isShareBonus eq 0}">否</c:when>
                        </c:choose>
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">持股比例：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${order.shareHoldRate}
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">总金额：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${order.orderAmount}
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">回报内容：</label>
                    <div class="formControls col-xs-8 col-sm-8">
                        ${order.levelDesc}
                    </div>
                </div>
            </div>
        </div>
        <c:if test="${order.orderState ge 6}">
        <div class="row cl">
            <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">支付信息</h3>
        </div>
        <div class="content_set">
            <div class="item">
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">合计金额：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${order.orderAmount}
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">实付金额：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${order.costAmount}
                    </div>
                </div>
                <c:if test="${!empty order.rebateSn }">
                    <div class="row cl">
                        <label class="form-label col-xs-4 col-sm-2">抵扣券金额：</label>
                        <div class="formControls col-xs-8 col-sm-3">
                                ${order.rebateAmount}
                        </div>
                        <label class="form-label col-xs-4 col-sm-2">抵扣券编号：</label>
                        <div class="formControls col-xs-8 col-sm-3">
                                ${order.rebateSn}
                        </div>
                    </div>
                    <div class="row cl">
                        <label class="form-label col-xs-4 col-sm-2">抵扣券名称：</label>
                        <div class="formControls col-xs-8 col-sm-3">
                                ${order.rebateName}
                        </div>
                        <label class="form-label col-xs-4 col-sm-2">抵扣券适用范围：</label>
                        <div class="formControls col-xs-8 col-sm-3">
                                ${order.rebateScope}
                        </div>
                    </div>
                </c:if>

                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">付款方式：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        <c:choose>
                            <c:when test="${order.paymentMethod eq 0}">余额支付</c:when>
                            <c:when test="${order.paymentMethod eq 1}">第三方支付</c:when>
                        </c:choose>
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">支付单号：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${order.outSn}
                    </div>
                </div>
            </div>
        </div>
        </c:if>
        <c:if test="${order.orderState eq 12}">
        <div class="row cl">
            <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">退款信息</h3>
        </div>
            <div class="content_set">
            <div class="item">
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">申请退款时间：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                            ${order.refundCreateTime}
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">后台申请人员：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                            ${order.refundSponsor}
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">申请退款原因：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                            ${order.userMessage}
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">备注：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                            ${order.refundMessage}
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">审核退款时间：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                            ${order.adminTime}
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">审核退款人员：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                            ${order.refundOperateAccount}
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">审核：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                            审核通过
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">备注：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                            ${order.adminMessage}
                    </div>
                </div>
            </div>
        </div>
        </c:if>
    </span>
</div>
</body>
</html>
