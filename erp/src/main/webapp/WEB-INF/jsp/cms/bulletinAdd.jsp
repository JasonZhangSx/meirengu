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
    <title>公告添加</title>
<style>
    .select-box1 {
        padding-left: 0;
    }

    .select-box1 select {
        font-size: 14px;
        height: 31px;
        line-height: 1.42857;
        padding: 4px;
        border: 1px #ddd solid;
    }

    .edit_h31 {
        border-bottom: 1px #ddd solid;
        overflow: hidden;
    }

    .tabCon {
        width: 100%;
    }

    .tabCon {
        display: none;
        overflow: hidden;
        width：100%;
    }

    .tabBar {
        border: none;
        position: fixed;
        top: 30px;
        right: 20px;
        z-index: 9999;
    }

    .tabBar span {
        display: block;
        float: none;
        font-size: 20px;
        line-height: 30px;
        padding: 5px 15px;
        font-weight: normal;
        color: #a7a5a5
    }

    .tabBar span.current {
        background-color: #5a98de;
    }

    .tabCon .form-label {
        margin-top: 3px;
        cursor: text;
        text-align: right;
    }

    .tabCon .row {
        margin-top: 15px;
    }
</style>
</head>
<body>
<div class="page-container">
    <!-- 选项卡 -->

    <span class="form form-horizontal" id="form-article-add">
        <!-- 内容设置 -->
        <div class="row cl">
            <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">公告添加</h3>
        </div>
        <div class="content_set">

            <form action="content/add" method="post" id="contentAddForm1">
                <div class="item">
                    <div class="row cl">
                        <label class="form-label col-xs-4 col-sm-2">公告名称：</label>
                        <div class="formControls col-xs-8 col-sm-8">
                            <input type="text" class="input-text" value="" maxlength="30" placeholder="项目标题最多30字" id="contentTitle1"
                                   name="contentTitle">
                        </div>
                    </div>
                    <div class="row cl">
                        <label class="form-label col-xs-4 col-sm-2">内容：</label>
                        <div class="formControls col-xs-8 col-sm-8">
                            <textarea name="contentInfo" id="contentInfo1" cols="" rows="" class="textarea" placeholder="..." datatype="*10-100"
                                      dragonfly="true" nullmsg="备注不能为空！"
                                      onKeyUp="$.Huitextarealength(this,500)"></textarea>
                            <p class="textarea-numberbar"><em class="textarea-length">0</em>/500</p>
                        </div>
                    </div>

                    <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2 mt-30 mb-20">
                        <button class="btn btn-primary radius" id="contentAddBtn1" type="button" onclick="contentAdd()"><i
                                class="Hui-iconfont">&#xe632;</i> 保存</button>
                    </div>
                </div>
            </form>


        </div>

    </span>
</div>

<script type="text/javascript">

</script>
</body>
</html>
