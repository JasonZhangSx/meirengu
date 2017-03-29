<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="utf-8" %>
<%@ include file="../common/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset=utf-8>
    <meta name=renderer content=webkit|ie-comp|ie-stand>
    <meta http-equiv=X-UA-Compatible content="IE=edge,chrome=1">
    <meta name=viewport content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=no"/>
    <meta http-equiv=Cache-Control content=no-siteapp/>
    <link rel=Bookmark href=favicon.ico>
    <link rel="Shortcut Icon" href=favicon.ico/>
    <meta name=keywords content=xxxxx>
    <meta name=description content=xxxxx>
    <title>行业分类添加</title>
    <link href="lib/lightbox2/2.8.1/css/lightbox.css" rel="stylesheet" type="text/css">
    <link href="lib/webuploader/0.1.5/webuploader.css" rel="stylesheet" type="text/css"/>
    <link href="lib/datetimepicker/datetimepicker.css" rel="stylesheet" type="text/css"/>

</head>
<body>
<div class="page-container">
    <form action="" method="post" class="form form-horizontal" id="form-article-add">
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
            <div class="formControls col-xs-8 col-sm-8">
                <input type="text" class="input-text" value="" id="articletitle" name="articletitle" maxlength="30"
                       placeholder="项目标题最多30字" id="" name="">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">行业类型：</label>
            <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
				<select name="" class="select">
					<option value="0">公司</option>
					<option value="1">个人</option>
				</select>
				</span>
            </div>
            <label class="form-label col-xs-4 col-sm-2">服务专员：</label>
            <div class="formControls col-xs-8 col-sm-3"> <span class="select-box">
				<select name="" class="select">
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
                <input type="text" class="input-text" value="" id="articletitle" name="articletitle" maxlength="30"
                       placeholder="项目标题最多30字" id="" name="">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">证件号：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <input type="text" class="input-text" value="" id="articletitle" name="articletitle" maxlength="30"
                       placeholder="项目标题最多30字" id="" name="">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2" style="line-height:30px;">企业地址：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <label data-toggle="distpicker" style="display:block;width:100%">
					<span class="select-box select-box1" style="border:none;">
						<select name="" class="col-sm-1 col-xs-8" data-province="---- 选择省 ----"></select>
						<select name="" class="col-sm-2 col-xs-8 ml-10" data-city="---- 选择市 ----"></select>
						<select name="" class="col-sm-2 col-xs-8 ml-10" data-district="---- 选择区 ----"></select>
						<input type="text" class=" input-text ml-20" style="width:40%" value="" id="articletitle"
                               name="articletitle" maxlength="30" placeholder="项目标题最多30字" id="" name="">
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
                <input type="text" class="input-text" value="" placeholder="" id="" name="">
            </div>
            <label class="form-label col-xs-4 col-sm-2">身份证号：</label>
            <div class="formControls col-xs-8 col-sm-3">
                <input type="text" class="input-text" value="" placeholder="" id="" name="">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">联系方式：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <input type="text" class="input-text" value="" id="articletitle" name="articletitle" maxlength="30"
                       placeholder="项目标题最多30字" id="" name="">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">传 真：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <input type="text" class="input-text" value="" id="articletitle" name="articletitle" maxlength="30"
                       placeholder="项目标题最多30字" id="" name="">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2" style="line-height:30px;">联系地址：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <label data-toggle="distpicker" style="display:block;width:100%">
					<span class="select-box select-box1" style="border:none;">
						<select name="" class="col-sm-1 col-xs-8" data-province="---- 选择省 ----"></select>
						<select name="" class="col-sm-2 col-xs-8 ml-10" data-city="---- 选择市 ----"></select>
						<select name="" class="col-sm-2 col-xs-8 ml-10" data-district="---- 选择区 ----"></select>
						<input type="text" class=" input-text ml-20" style="width:40%" value="" id="articletitle"
                               name="articletitle" maxlength="30" placeholder="项目标题最多30字" id="" name="">
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
                <input type="text" class="input-text" value="" placeholder="" id="" name="">
            </div>
            <label class="form-label col-xs-4 col-sm-2">身份证号：</label>
            <div class="formControls col-xs-8 col-sm-3">
                <input type="text" class="input-text" value="" placeholder="" id="" name="">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">联系方式：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <input type="text" class="input-text" value="" id="articletitle" name="articletitle" maxlength="30"
                       placeholder="项目标题最多30字" id="" name="">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">传 真：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <input type="text" class="input-text" value="" id="articletitle" name="articletitle" maxlength="30"
                       placeholder="项目标题最多30字" id="" name="">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2" style="line-height:30px;">联系地址：</label>
            <div class="formControls col-xs-8 col-sm-9">
                <label data-toggle="distpicker" style="display:block;width:100%">
					<span class="select-box select-box1" style="border:none;">
						<select name="" class="col-sm-1 col-xs-8" data-province="---- 选择省 ----"></select>
						<select name="" class="col-sm-2 col-xs-8 ml-10" data-city="---- 选择市 ----"></select>
						<select name="" class="col-sm-2 col-xs-8 ml-10" data-district="---- 选择区 ----"></select>
						<input type="text" class=" input-text ml-20" style="width:40%" value="" id="articletitle"
                               name="articletitle" maxlength="30" placeholder="项目标题最多30字" id="" name="">
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
                <input type="text" class="input-text" value="" placeholder="" id="" name="">
            </div>
            <label class="form-label col-xs-4 col-sm-2">账户名：</label>
            <div class="formControls col-xs-8 col-sm-3">
                <input type="text" class="input-text" value="" placeholder="" id="" name="">
            </div>
        </div>
        <div class="row cl">
            <label class="form-label col-xs-4 col-sm-2">银行账号：</label>
            <div class="formControls col-xs-8 col-sm-8">
                <input type="text" class="input-text" value="" id="articletitle" name="articletitle" maxlength="30"
                       placeholder="项目标题最多30字" id="" name="">
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
                    <td class="text-l">北京玉之光众筹
                        <button class="btn r va-m btn-secondary radius ml-10" type="button">上传</button>
                    </td>
                </tr>
                <tr class="text-c">
                    <td>开户行
                        <button class="btn btn-link radius ml-10" type="button">下载</button>
                    </td>
                    <td class="text-l">北京玉之光众筹
                        <button class="btn r va-m btn-secondary radius ml-10" type="button">上传</button>
                    </td>
                </tr>
                <tr class="text-c">
                    <td>医疗机构执业许可证
                        <button class="btn btn-link radius ml-10" type="button">下载</button>
                    </td>
                    <td class="text-l">北京玉之光众筹
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
<!-- 省市区 -->
<script type="text/javascript" src="lib/distpicker/distpicker.data.js"></script>
<script type="text/javascript" src="lib/distpicker/distpicker.js"></script>

<script type="text/javascript" src="lib/webuploader/0.1.5/webuploader.min.js"></script>

<script>

    $(".Hui-aside ul a").on("click", function () {
        console.log($(this).attr("data-href")), $(".content_iframe").attr("src", $(this).attr("data-href"))
    });

    //补充资料-收益权股权类  图片上传demo
    $(function () {
        $list = $("#fileList"),
                $btn = $("#btn-star"),
                state = "pending",
                uploader;

        var uploader = WebUploader.create({
            auto: true,
            swf: 'lib/webuploader/0.1.5/Uploader.swf',

            // 文件接收服务端。
            server: 'fileupload.php',

            // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素，也可能是flash.
            pick: '#filePicker',

            // 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            resize: false,
            // 只允许选择图片文件。
            accept: {
                title: 'Images',
                extensions: 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/*'
            }
        });
        uploader.on('fileQueued', function (file) {
            var $li = $(
                            '<div id="' + file.id + '" class="item">' +
                            '<div class="pic-box"><img></div>' +
                            '<div class="info">' + file.name + '</div>' +
                            '<p class="state">等待上传...</p>' +
                            '</div>'
                    ),
                    $img = $li.find('img');
            $list.append($li);

            // 创建缩略图
            // 如果为非图片文件，可以不用调用此方法。
            // thumbnailWidth x thumbnailHeight 为 100 x 100
            uploader.makeThumb(file, function (error, src) {
                if (error) {
                    $img.replaceWith('<span>不能预览</span>');
                    return;
                }

                $img.attr('src', src);
            }, thumbnailWidth, thumbnailHeight);
        });
        // 文件上传过程中创建进度条实时显示。
        uploader.on('uploadProgress', function (file, percentage) {
            var $li = $('#' + file.id),
                    $percent = $li.find('.progress-box .sr-only');

            // 避免重复创建
            if (!$percent.length) {
                $percent = $('<div class="progress-box"><span class="progress-bar radius"><span class="sr-only" style="width:0%"></span></span></div>').appendTo($li).find('.sr-only');
            }
            $li.find(".state").text("上传中");
            $percent.css('width', percentage * 100 + '%');
        });

        // 文件上传成功，给item添加成功class, 用样式标记上传成功。
        uploader.on('uploadSuccess', function (file) {
            $('#' + file.id).addClass('upload-state-success').find(".state").text("已上传");
        });

        // 文件上传失败，显示上传出错。
        uploader.on('uploadError', function (file) {
            $('#' + file.id).addClass('upload-state-error').find(".state").text("上传出错");
        });

        // 完成上传完了，成功或者失败，先删除进度条。
        uploader.on('uploadComplete', function (file) {
            $('#' + file.id).find('.progress-box').fadeOut();
        });
        uploader.on('all', function (type) {
            if (type === 'startUpload') {
                state = 'uploading';
            } else if (type === 'stopUpload') {
                state = 'paused';
            } else if (type === 'uploadFinished') {
                state = 'done';
            }

            if (state === 'uploading') {
                $btn.text('暂停上传');
            } else {
                $btn.text('开始上传');
            }
        });

        $btn.on('click', function () {
            if (state === 'uploading') {
                uploader.stop();
            } else {
                uploader.upload();
            }
        });

    });


</script>
</body>
</html>
