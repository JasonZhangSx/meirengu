<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="../common/common.jsp" %>
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
    <title>行业添加</title>
    <link href="lib/lightbox2/2.8.1/css/lightbox.css" rel="stylesheet" type="text/css">
    <link href="lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css"/>
    <link href="lib/datetimepicker/datetimepicker.css" rel="stylesheet" type="text/css"/>
    <%--<script src="static/js/ajaxfileupload.js"/>--%>

</head>
<body>
<div class="page-container">
    <form action="add" method="post" class="form form-horizontal" id="form-article-add">
        <style>
            .select-box1 {
                padding-left: 0;
            }

            .select-box1 select {
                font-size: 14px;
                height: 31px;
                line-height: 31px;
                padding: 0 4px;
                border: 1px #ddd solid;
            }

            .edit_h31 {
                border-bottom: 1px #ddd solid;
                overflow: hidden;
            }
        </style>

        <!-- 合作方 添加 -->
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">合作方名称：</label>
            <div class="formControls col-xs-8 col-sm-3">
                <input type="text" class="input-text" value="" id="partnerName" name="partnerName" maxlength="30"
                       placeholder="项目标题最多30字">
            </div>
            <label class="form-label col-xs-4 col-sm-2">公司成立日：</label>
            <div class="formControls col-xs-8 col-sm-3">
                <input type="text" class="input-text" value="" placeholder="" id="partnerCreateDay" name="partnerCreateDay">
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">注册资金：</label>
            <div class="formControls col-xs-8 col-sm-3">
                <input type="text" class="input-text" value="" placeholder="" id="partnerRegistCapital" name="partnerRegistCapital">
            </div>
            <label class="form-label col-xs-4 col-sm-2">公司估值：</label>
            <div class="formControls col-xs-8 col-sm-3">
                <input type="text" class="input-text" value="" placeholder="" id="partnerValuation" name="partnerValuation">
            </div>
        </div>

        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">行业类型：</label>
            <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
				<select name="typeId" id="typeId" class="select">
                    <c:forEach items="${classList}" var="classList">
                        <option value="${classList.classId}">${classList.className}</option>
                    </c:forEach>
				</select>
				</span>
            </div>
            <label class="form-label col-xs-4 col-sm-2">服务专员：</label>
            <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
				<select name="accountId" id="accountId" class="select">
					<option value="0">专员a</option>
					<option value="1">专员b</option>
				</select>
				</span>
            </div>
        </div>

        <div class="row cl">
            <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">企业信息</h3>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">企业名称：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <input type="text" class="input-text" value="" id="enterpriseName" name="enterpriseName" maxlength="30"
                       placeholder="项目标题最多30字">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">证件号：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <input type="text" class="input-text" value="" id="idNumber" name="idNumber" maxlength="30"
                       placeholder="项目标题最多30字">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2" style="line-height:30px;">企业地址：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <label data-toggle="distpicker" style="display:block;width:100%">
					<span class="select-box select-box1" style="border:none;">
                        <input type="text" class="input-text" name="enterpriseAddress" id="enterpriseAddress">
					</span>
                </label>
            </div>
        </div>

        <div class="row cl">
            <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">法人信息</h3>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">负责人姓名：</label>
            <div class="formControls col-xs-8 col-sm-3">
                <input type="text" class="input-text" value="" placeholder="" id="principalName" name="principalName">
            </div>
            <label class="form-label col-xs-4 col-sm-2">身份证号：</label>
            <div class="formControls col-xs-8 col-sm-3">
                <input type="text" class="input-text" value="" placeholder="" id="principalIdcard"
                       name="principalIdcard">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">联系方式：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <input type="text" class="input-text" value="" id="principalTelephone" name="principalTelephone"
                       maxlength="30"
                       placeholder="项目标题最多30字">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">传 真：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <input type="text" class="input-text" value="" id="principalFax" name="principalFax" maxlength="30"
                       placeholder="项目标题最多30字">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2" style="line-height:30px;">联系地址：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <label data-toggle="distpicker" style="display:block;width:100%">
					<span class="select-box select-box1" style="border:none;">
                        <input type="text" class="input-text" name="principalAddress" id="principalAddress">
					</span>
                </label>
            </div>
        </div>

        <div class="row cl">
            <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">联系人信息</h3>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">联系人姓名：</label>
            <div class="formControls col-xs-8 col-sm-3">
                <input type="text" class="input-text" value="" placeholder="" id="contactsName" name="contactsName">
            </div>
            <label class="form-label col-xs-4 col-sm-2">身份证号：</label>
            <div class="formControls col-xs-8 col-sm-3">
                <input type="text" class="input-text" value="" placeholder="" id="contactsIdcard" name="contactsIdcard">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">联系方式：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <input type="text" class="input-text" value="" id="contactsTelephone" name="contactsTelephone"
                       maxlength="30"
                       placeholder="项目标题最多30字">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">传 真：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <input type="text" class="input-text" value="" id="contactsax" name="contacts_fax" maxlength="30"
                       placeholder="项目标题最多30字">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2" style="line-height:30px;">联系地址：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <label data-toggle="distpicker" style="display:block;width:100%">
					<span class="select-box select-box1" style="border:none;">
                        <input type="text" class="input-text" name="contactsAddress" id="contactsAddress">
					</span>
                </label>
            </div>
        </div>

        <div class="row cl">
            <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">收款账户信息</h3>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">开户行名称：</label>
            <div class="formControls col-xs-8 col-sm-3">
                <input type="text" class="input-text" value="" placeholder="" id="bankName" name="bankName">
            </div>
            <label class="form-label col-xs-4 col-sm-2">账户名：</label>
            <div class="formControls col-xs-8 col-sm-3">
                <input type="text" class="input-text" value="" placeholder="" id="bankAccount" name="bankAccount">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">银行账号：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <input type="text" class="input-text" value="" id="bankCard" name="bankCard" maxlength="30"
                       placeholder="项目标题最多30字">
            </div>
        </div>

        <div class="row cl">
            <h3 class="edit_h31 col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10">资质文件</h3>
        </div>
        <div class="col-sm-9 col-sm-offset-1 col-xs-offset-0 mb-10 pb-10 mt-20">
            <table class="table table-border table-bordered table-bg table-hover table-sort">
                <thead>
                <tr class="text-c">
                    <th width="240">资料类型</th>
                    <th>合同文件</th>
                </tr>
                </thead>
                <tbody>
                <tr class="text-c">
                    <td>发起人身份证正反面
                        <button class="btn btn-link radius ml-10" type="button">下载</button>
                    </td>
                    <td class="text-l">
                        <div class="uploader-thum-container">
                            <div id="fileList" class="uploader-list"></div>
                            <div id="filePicker">选择图片</div>
                            <button id="btn-star" class="btn btn-default btn-uploadstar radius ml-10" type="button">
                                开始上传
                            </button>
                        </div>
                    </td>
                </tr>
                <tr class="text-c">
                    <td>营业执照
                        <button class="btn btn-link radius ml-10" type="button">下载</button>
                    </td>
                    <td class="text-l"><!-- upload?foldName=item-->
                        <div class="portfoliobox">
                            <div class="picbox">
                                <a href="" data-lightbox="gallery" data-title="项目头图"><img
                                        src="?x-oss-process=image/resize,m_lfit,h_200,w_200"></a>
                            </div>
                        </div>
                        <input type="hidden" id="imageBusinessLicence" name="imageBusinessLicence">
                        <input type="file" id="file1" name="file">
                        <button class="btn r va-m btn-secondary radius ml-10" id="btn1" type="button">上传</button>
                        <p><img id="img1" alt="上传成功啦" src="" /></p>
                    </td>
                </tr>
                <tr class="text-c">
                    <td>开户行
                        <button class="btn btn-link radius ml-10" type="button">下载</button>
                    </td>
                    <td class="text-l">
                        <button class="btn r va-m btn-secondary radius ml-10" type="button">上传</button>
                    </td>
                </tr>
                <tr class="text-c">
                    <td>医疗机构执业许可证
                        <button class="btn btn-link radius ml-10" type="button">下载</button>
                    </td>
                    <td class="text-l">
                        <button class="btn r va-m btn-secondary radius ml-10" type="button">上传</button>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2"></label>
            <div class="formControls col-xs-8 col-sm-8 text-c">
                <button class="btn btn-primary radius size-L mt-20 mb-30" style="padding:0 30px" type="submit">添 加
                </button>
            </div>
        </div>
        <br/><br/><br/>
    </form>
</div>

<script>

    $(".Hui-aside ul a").on("click", function () {
        console.log($(this).attr("data-href")), $(".content_iframe").attr("src", $(this).attr("data-href"))
    });

</script>
</body>
</html>
