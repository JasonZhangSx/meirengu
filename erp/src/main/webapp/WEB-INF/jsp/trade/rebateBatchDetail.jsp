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
    <title>抵扣券批次详情</title>
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
            <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">抵扣券批次统计信息</h3>
        </div>
        <div class="content_set">
            <div class="item">
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">该批次抵扣券总数：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${rebateBatch.batchCount}
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">已领取总数：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${receiveCount+useCount+expiredCount}
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">已领取未使用：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${receiveCount}
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">已领取已使用：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${useCount}
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-4 col-sm-2">已领取已失效：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${expiredCount}
                    </div>
                    <label class="form-label col-xs-4 col-sm-2">关联订单失效：</label>
                    <div class="formControls col-xs-8 col-sm-3">
                        ${failureCount}
                    </div>
                </div>
            </div>
        </div>
    </span>
</div>
</body>
</html>
