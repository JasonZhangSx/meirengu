
var contentIndex = 0;
var levelIndex = 0;

function typeChange(){
    var typeId = $("#typeId").val();
    if(typeId == 1){

    }

    if(typeId == 2){

    }

    if(typeId == 3){
        $("#extentionDiv").show();
        $("#shareholderDiv").show();
        $("div[name=shareHoldRateDiv]").show();
        $("span[name=yearRateMaxDiv]").show();
    }else{
        $("#extentionDiv").hide();
        $("#shareholderDiv").hide();
        $("div[name=shareHoldRateDiv]").hide();
        $("span[name=yearRateMaxDiv]").hide();
    }
}
/**
 * 是否分红
 */
$("#isShareBonus"+levelIndex).change(function(){
    if($("#isShareBonus"+levelIndex).val() == 1){
        $("#shareBonusDiv"+levelIndex).show();
    }else if($("#isShareBonus"+levelIndex).val() == 0){
        $("#shareBonusDiv"+levelIndex).hide();
    }
});

$("#revenueModel"+levelIndex).change(function () {
    alert(levelIndex);
    if($("#revenueModel"+levelIndex).val() == 1){
        $("#shareBonusPeriodDiv"+levelIndex).hide();
    }else if($("#revenueModel"+levelIndex).val() == 2){
        $("#shareBonusPeriodDiv"+levelIndex).show();
    }
});

function getCity(){
    var pid = $("#provinceSelect").val();
    $.ajax({
        type: "POST",
        url: "address/city",
        data: {"pid": pid},
        dataType: "json",
        success: function(data){
            data = data.data;
            console.log(data);
            if(data != null){
                var option = "";
                for(var i = 0; i<data.length; i++){
                    if(i==0){
                        option += '<option value="'+data[i].areaId+'" selected>'+data[i].areaName+'</option>';
                    }else {
                        option += '<option value="'+data[i].areaId+'">'+data[i].areaName+'</option>';
                    }
                    
                }
                $("#citySelect").html("");
                $("#citySelect").append(option);
                getArea();
            }
        }
    });
}

function getArea(){
    var cid = $("#citySelect").val();
    console.log("cid:"+cid);
    $.ajax({
        type: "POST",
        url: "address/area",
        data: {"cid": cid},
        dataType: "json",
        success: function(data){
            console.log(data);
            data = data.data;
            if(data != null){
                var option = "";
                for(var i = 0; i<data.length; i++){
                    if(i==0){
                        option += '<option value="'+data[i].areaId+'" selected>'+data[i].areaName+'</option>';
                    }else {
                        option += '<option value="'+data[i].areaId+'">'+data[i].areaName+'</option>';
                    }
                }
                $("#areaSelect").html("");
                $("#areaSelect").append(option);
            }
        }
    });
}

function contentAdd(){
    var itemId = $("#itemId"+contentIndex).val();
    if(itemId == null || itemId == '' || itemId == 0){
        alert("请先添加项目基本信息");
        return;
    }
    $.ajax({
        type: "POST",
        url: "item/content/save",
        data: $("#contentAddForm"+contentIndex).serialize(),
        dataType: "json",
        success: function(data){
            console.log(data);
            if(data.code == '200'){
                var content = data.data;
                console.log(content);
                console.log(contentIndex);
                $("#contentId"+contentIndex).val(content.contentId);
                $('.content_tab_menu span[cnum='+contentIndex+']' ).html(content.contentTitle+"<var></var>");
                alert("内容添加成功");
            }
        }
    });
}

function levelAdd(){
    //$('.huibao_set .huibao_tab_menu span[cnum='+levelIndex+']').text("内容"+levelIndex+"<var></var>");
    var itemId = $("#itemId1"+levelIndex).val();
    if(itemId == null || itemId == '' || itemId == 0){
        alert("请先添加项目基本信息");
        return;
    }
    $.ajax({
        type: "POST",
        url: "item/level/save",
        data: $("#levelAddForm"+levelIndex).serialize(),
        dataType: "json",
        success: function(data){
            console.log(data);
            if(data.code == '200'){
                var content = data.data;
                console.log(content);
                $("#levelId"+levelIndex).val(content.levelId);
                $('.huibao_set .huibao_tab_menu span[cnum='+levelIndex+']' ).html(content.levelName+"<var></var>");
                alert("回报档位添加成功");
            }
        }
    });
}

function itemAdd(){

    if(!itemValidate()){
        return;
    }
    $.ajax({
        type: "POST",
        url: "item/save",
        data: $("#itemAddForm").serialize(),
        dataType: "json",
        success: function(data){
            console.log(data);
            if(data.code == '200'){
                var content = data.data;
                console.log(content.itemId);
                $("input[name='itemId']").val(content.itemId);
                alert("项目添加成功");
            }
        }
    });
}

function itemValidate(){

    var r = /^\+?[1-9][0-9]*$/;　　//正整数

    var itemName = $("#itemName").val();
    var itemProfile = $("#itemProfile").val();
    var typeId = $("#typeId").val();
    var classId = $("#classId").val();
    var targetAmount = $("#targetAmount").val();
    var preheatingDays = $("#preheatingDays").val();
    var partnerId = $("#partnerId").val();
    var crowdDays = $("#crowdDays").val();
    var areaId = $("#areaId").val();
    var headerImage = $("#headerImage").val();

    if(itemName.length > 30){
        alert("项目名称不能超过30字");
        return false;
    }

    if(itemProfile.length > 200){
        alert("项目简介不能超过200字");
        return false;
    }

    if(!r.test(targetAmount)){
        alert("目标金额必须为整数");
        return false;
    }

    if(!r.test(preheatingDays)){
        alert("预热天数必须为整数且小于5天");
        return false;
    }

    if(preheatingDays > 5){
        alert("预热天数不能超过5天");
        return false;
    }

    if(!r.test(crowdDays)){
        alert("众筹天数必须为整数");
        return false;
    }

    if(headerImage.length <= 0){
        alert("项目头像不能为空");
        return false;
    }

    return true;
}

//提交股权项目扩展信息
function extentionSave(){
    var itemId = $("#itemId30").val();
    if(itemId == null || itemId == '' || itemId == 0){
        alert("请先添加项目基本信息");
        return;
    }
    $.ajax({
        type: "POST",
        url: "item/extention/save",
        data: $("#itemExtentionForm").serialize(),
        dataType: "json",
        success: function(data){
            console.log(data);
            if(data.code == '200'){
                var content = data.data;
                console.log(content);
                alert("融资信息添加成功");
            }
        }
    });
}

//股东信息提交
function shareHolderSave(){
    var itemId = $("#itemId31").val();
    if(itemId == null || itemId == '' || itemId == 0){
        alert("请先添加项目基本信息");
        return;
    }
    $.ajax({
        type: "POST",
        url: "item/shareholder/save",
        data: $("#itemShareHolderForm").serialize(),
        dataType: "json",
        success: function(data){
            console.log(data);
            if(data.code == '200'){
                var content = data.data;
                console.log(content);
                alert("股东信息添加成功");
            }
        }
    });
}

//提交审核
function verify() {
    var itemId = $("#itemId").val();
    if(itemId == null || itemId == '' || itemId == 0){
        alert("请先添加项目基本信息");
        return;
    }
    $.ajax({
        type: "POST",
        url: "item/submit_verify",
        data: {"itemId": itemId},
        dataType: "json",
        success: function(data){
            console.log(data);
            if(data.code == '200'){
                layer_close();
            }else {
                alert("提交审核失败")
            }
        }
    });
}

$(function () {
    $(".Hui-aside ul a").on("click", function () {
        console.log($(this).attr("data-href")), $(".content_iframe").attr("src", $(this).attr("data-href"))
    })
}), $(".toTop").show();

$(function () {
    getCity();
    //回报tab切换
    $('body').on('click', '.huibao_set .huibao_tab_menu span', function (event) {
        console.log($(event.target));
        $('.huibao_set .huibao_tab_menu span').removeClass('current');
        levelIndex = $(event.target).addClass('current').attr("cnum");
        var index = $(event.target).index();
        $('.huibao_set .huibao_tab').hide();
        $('.huibao_set .huibao_tab').eq(index).show();
        console.log(levelIndex);
    });
    //内容tab切换
    $('body').on('click', '.content_tab_menu span', function (event) {
        console.log($(event.target));
        $('.content_tab_menu span').removeClass('current');
        $(event.target).addClass('current');
        var index = $(event.target).index();
        contentIndex = index;
        $('.content_set .item').hide();
        $('.content_set .item').eq(index).show();
        console.log(contentIndex);
    });
    function sortName(menu) {
        // for (var i = 0; i < menu.length; i++) {
        // 	$(menu[i]).html('名称' + (i+1)+'<var></var>');
        // }
    }

    $('.huibao_wrapper').find('.huibao_tab').eq(0).show();
    $('.content_set').find('.item').eq(0).show();
    $('.huibao_tab_menu em').on('click', function () {
        /*var aObj = $('.hide_huibao_tab').find('.huibao_tab').eq(0).clone();
         var s = String(aObj).replace("#1","2");
         console.log(String(s));*/
        var itemId = $("#itemId").val();
        var typeId = $("#typeId").val();
        var shareHolderDiv = '';
        var yearRateMaxDiv = '';
        if(typeId == 3){
            shareHolderDiv = '<div name="shareHoldRateDiv">'+
                '<label class="form-label col-xs-4 col-sm-2">持股比例：</label>'+
                '<div class="formControls col-xs-8 col-sm-3">'+
                '<input type="text" class="input-text" value="" maxlength="30" id="shareHoldRate#1" name="shareHoldRate" style="width: 272px;">&nbsp;&nbsp;%'+
                '</div>'+
                '</div>';
            yearRateMaxDiv = '<span name="yearRateMaxDiv">'+
                            '&nbsp;&nbsp; - &nbsp;&nbsp;'+
                            '<input type="text" class="input-text" value="" placeholder="%" maxlength="30" id="yearRateMax#1"'+
                            'name="yearRateMax" style="width: 100px;">&nbsp;&nbsp;%'+
                            '</span>';
        }else{
            shareHolderDiv = '<div name="shareHoldRateDiv" style="display: none;">'+
                '<label class="form-label col-xs-4 col-sm-2">持股比例：</label>'+
                '<div class="formControls col-xs-8 col-sm-3">'+
                '<input type="text" class="input-text" value="" maxlength="30" id="shareHoldRate#1" name="shareHoldRate" style="width: 272px;">&nbsp;&nbsp;%'+
                '</div>'+
                '</div>';
            yearRateMaxDiv = '<span name="yearRateMaxDiv" style="display: none;">'+
                '&nbsp;&nbsp; - &nbsp;&nbsp;'+
                '<input type="text" class="input-text" value="" placeholder="%" maxlength="30" id="yearRateMax#1"'+
                'name="yearRateMax" style="width: 100px;">&nbsp;&nbsp;%'+
                '</span>';
        }
        var aObj = '<div class="huibao_tab" style="display: block;"><form id="levelAddForm#1" >' +
            '<input type="hidden" id="itemId1#1" name="itemId" value="'+itemId+'">' +
            '<input type="hidden" id="levelId#1" name="levelId" value="0">' +
            '<div class="row cl" style="display:block">' +
            '<label class="form-label col-xs-4 col-sm-2">档位名称：</label>' +
            '<div class="formControls col-xs-8 col-sm-8">' +
            '   <input type="text" class="input-text" value="" maxlength="30" id="levelName#1" name="levelName">' +
            '   </div>' +
            '   </div>' +
            '   <div class="row cl">' +
            '   <label class="form-label col-xs-4 col-sm-2">支持金额：</label>' +
            '<div class="formControls col-xs-8 col-sm-3">' +
            '   <input type="text" class="input-text" value="" maxlength="30" id="levelAmount#1" name="levelAmount" style="width: 272px;">&nbsp;&nbsp;元' +
            '   </div>' +
            shareHolderDiv +
            '   </div>' +
            '   <div class="row cl">' +
            '    <label class="form-label col-xs-4 col-sm-2">回报描述：</label>' +
            '<div class="formControls col-xs-8 col-sm-8">' +
            '    <textarea name="levelDesc" id="levelDesc#1" cols="" rows="" class="textarea" placeholder="..." datatype="*10-100"' +
            'dragonfly="true" nullmsg="备注不能为空！"' +
            'onKeyUp="$.Huitextarealength(this,200)"></textarea>' +
            '    <p class="textarea-numberbar"><em class="textarea-length">0</em>/200</p>' +
            '   </div>' +
            '   </div>' +
            '   <div class="row cl">' +
            '   <label class="form-label col-xs-4 col-sm-2">总份数：</label>' +
            '<div class="formControls col-xs-8 col-sm-3">' +
            '   <input type="text" class="input-text" value="" placeholder="0即为无限制" maxlength="30" id="totalNumber#1"' +
            'name="totalNumber" style="width: 272px;">&nbsp;&nbsp;份' +
            '   </div>' +
            '   <label class="form-label col-xs-4 col-sm-2">单人限额：</label>' +
            '<div class="formControls col-xs-8 col-sm-3">' +
            '   <input type="text" class="input-text" value="" placeholder="0即为无限制" maxlength="30" id="singleLimitNumber#1"' +
            'name="singleLimitNumber" style="width: 272px;">&nbsp;&nbsp;份' +
            '   </div>' +
            '   </div>' +
            '   <div class="row cl">' +
            '   <label class="form-label col-xs-4 col-sm-2">回报时间：</label>' +
            '<div class="formControls col-xs-8 col-sm-3">' +
            '   <input type="text" class="input-text" value="" placeholder="100 天" maxlength="30" id="paybackDays#1"' +
            'name="paybackDays" style="width: 272px;">&nbsp;&nbsp;天' +
            '   </div>' +
            '   <label class="form-label col-xs-4 col-sm-2">是否分红：</label>' +
            '<div class="formControls col-xs-8 col-sm-3"> <span class="select-box">' +
            '   <select id="isShareBonus#1" name="isShareBonus" class="select" onchange="shareBonusChange()">' +
            '   <option value="1">是</option>' +
            '   <option value="0">否</option>' +
            '   </select>' +
            '   </span>' +
            '   </div>' +
            '</div>' +
            '<div id="shareBonusDiv#1">' +
            '   <div class="row cl">' +
            '   <label class="form-label col-xs-4 col-sm-2">年化利率：</label>' +
            '<div class="formControls col-xs-8 col-sm-3">' +
            '   <input type="text" class="input-text" value="" placeholder="%" maxlength="30" id="yearRate#1"' +
            'name="yearRate" style="width: 100px;">&nbsp;&nbsp;%' +
            yearRateMaxDiv +
            '   </div>' +
            '   <label class="form-label col-xs-4 col-sm-2">投资期限：</label>' +
            '<div class="formControls col-xs-8 col-sm-3">' +
            '   <input type="text" class="input-text" value="" placeholder=" 月" maxlength="30" id="investmentPeriod#1"' +
            'name="investmentPeriod">' +
            '   </div>' +
            '   </div>' +
            '   <div class="row cl">' +
            '   <label class="form-label col-xs-4 col-sm-2">收益方式：</label>' +
            '<div class="formControls col-xs-8 col-sm-3"> <span class="select-box">' +
            '   <select name="revenueModel" id="revenueModel#1" class="select">' +
            '   <option value="1">一次性还款</option>' +
            '   <option value="2">按月还息到期还本</option>' +
            '   </select>' +
            '   </span>' +
            '   </div>' +
            '<div id="shareBonusPeriodDiv#1" style="display: none;">' +
            '   <label class="form-label col-xs-4 col-sm-2">分红周期：</label>' +
            '<div class="formControls col-xs-8 col-sm-3"> <span class="select-box">' +
            '   <select name="shareBonusPeriod" id="shareBonusPeriod#1" class="select">' +
            '   <option value="1">1月</option>' +
            '<option value="3">3月</option>' +
            '<option value="6">6月</option>' +
            '<option value="12">12月</option>' +
            '</select>' +
            '</span>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '</div>' +
            '<div class="row cl">' +
            '   <label class="form-label col-xs-4 col-sm-2">是否需要地址：</label>' +
            '<div class="formControls col-xs-8 col-sm-3"> <span class="select-box">' +
            '   <select name="isNeedAddress" id="isNeedAddress#1" class="select">' +
            '   <option value="1">是</option>' +
            '   <option value="0">否</option>' +
            '   </select>' +
            '   </span>' +
            '   </div>' +
            '   <label class="form-label col-xs-4 col-sm-2">是否需要协议：</label>' +
            '<div class="formControls col-xs-8 col-sm-3"> <span class="select-box">' +
            '   <select name="isNeedAgreement" id="isNeedAgreement#1" class="select">' +
            '   <option value="1">是</option>' +
            '   <option value="0">否</option>' +
            '   </select>' +
            '   </span>' +
            '   </div>' +
            '   </div>' +
            '   <div class="row cl">' +
            '   <div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2 mt-30 mb-20">' +
            '   <button class="btn btn-secondary radius" type="button" id="levelAddBtn#1"  onclick="levelAdd()"><i' +
            '   class="Hui-iconfont">&#xe632;</i> 保存' +
            '</button>' +
            '</div>' +
            '</div>' +
            '</form></div>';

        var cnum = $('.huibao_tab_menu .wrapper span:last-child').attr("cnum");
        cnum = parseInt(cnum)+1;
        aObj = aObj.replace(/#1/g,cnum);
        console.log(cnum);
        var a = confirm('要新增项目档位吗'),
            menu = $('.huibao_tab_menu .wrapper span');
        if (a) {
            $('.huibao_tab_menu span').removeClass('current');
            $('.huibao_tab_menu .wrapper').append('<span cnum="'+cnum+'" class="current">档位'+cnum+'<var></var></span>');
            sortName($('.huibao_tab_menu .wrapper span'));
            $('.huibao_wrapper').children().hide();
            $('.huibao_wrapper').append(aObj);
            levelIndex = cnum;
        }
    });
    $('.content_tab_menu em').on('click', function () {
        //var aObj = $('.hide_content_item').find('.item').eq(0).clone();
        var itemId = $("#itemId").val();

        var aObj = '<div class="item" style="display: block;">'+
                        '<form action="content/add" method="post" id="contentAddForm#1">'+
                            '<input type="hidden" name="itemId" id="itemId#1" value="'+itemId+'">'+
                            '<input type="hidden" name="contentType" value="1">'+
                            '<input type="hidden" id="contentId#1" name="contentId" value="0">'+
                            '<div class="row cl">'+
                                '<label class="form-label col-xs-4 col-sm-2">主标题：</label>'+
                                '<div class="formControls col-xs-8 col-sm-8">'+
                                    '<input type="text" class="input-text" value="" maxlength="30" placeholder="项目标题最多30字" id="contentTitle#1" name="contentTitle">'+
                                '</div>'+
                            '</div>'+
                            '<div class="row cl">'+
                                '<label class="form-label col-xs-4 col-sm-2">内容：</label>'+
                                '<div class="formControls col-xs-8 col-sm-8">'+
                                    '<input type="hidden" id="contentInfo#1" name="contentInfo">'+
                                    '<div class="img-box full">'+
                                        '<section class=" img-section">'+
                                            '<div class="z_photo upimg-div clearfix">'+
                                                '<div id="imgParent1#1" class="clearfix"></div>'+
                                                '<section class="z_file fl">'+
                                                    '<img src="static/upload-file/a11.png" class="add-img">'+
                                                    '<input type="file" name="file" id="file1#1" class="file" value="" accept="image/jpg,image/jpeg,image/png,image/bmp" multiple="" onchange="uploadFile(\'file1#1\',\'item\',\'imgParent1#1\',\'contentInfo#1\')">'+
                                                '</section>'+
                                            '</div>'+
                                        '</section>'+
                                    '</div>'+
                                '</div>'+
                            '</div>'+
                            '<div class="col-xs-8 col-sm-9 col-xs-offset-4 col-sm-offset-2 mt-30 mb-20">'+
                                '<button class="btn btn-primary radius" id="contentAddBtn0" type="button"  onclick="contentAdd()">'+
                                '<i class="Hui-iconfont">&#xe632;</i> 保存</button>'+
                            '</div>'+
                        '</form>'+
                    '</div>';

        var cnum = $('.content_tab_menu .wrapper span:last-child').attr("cnum");
        cnum = parseInt(cnum)+1;
        aObj = aObj.replace(/#1/g,cnum);
        console.log("cnum:"+cnum);
        var a = confirm('要新增内容页吗'),
            menu = $('.content_tab_menu .wrapper span');
        if (a) {
            $('.content_tab_menu .wrapper span').removeClass("current");
            $('.content_tab_menu .wrapper').append('<span cnum="'+cnum+'" class="current">内容'+cnum+'<var></var></span>');
            sortName($('.content_tab_menu .wrapper span'));
            $('.content_set').children().hide();
            $('.content_set').append(aObj);
            contentIndex = cnum;
        }
    })
    $('.huibao_tab_menu').on('click', 'span var', function (event) {
        event.stopPropagation();
        var index = $(event.target).parent().index();
        if(index == 0){
            alert("档位至少有一个");
            return;
        }
        var a = confirm('要删除此项吗');
        if (a) {
            $(event.target).parent().remove();
            $('.huibao_set .huibao_tab').eq(index).remove();
            $('.huibao_set .huibao_tab').eq(index-1).attr("style","display: block;");
            // sortName($('.huibao_tab_menu .wrapper span'));
        }
    })
    $('.content_tab_menu').on('click', 'span var', function (event) {
        event.stopPropagation();
        var index = $(event.target).parent().index();
        /*if(index == 0){
            alert("内容至少有一个");
            return;
        }*/

        var a = confirm('要删除此项吗');
        if (a) {
            /*$.ajax({
                type: "POST",
                url: "item/content/del",
                data: {"contentId": contentId},
                dataType: "json",
                success: function(data){
                    console.log(data);
                    if(data.code == '200'){
                        layer_close();
                    }else {
                        alert("提交审核失败")
                    }
                }
            });*/
            $(event.target).parent().remove();
            $('.content_set .item').eq(index).remove();
            // sortName($('.huibao_tab_menu .wrapper span'));
        }
    })

    $.Huitab(".content_tab_menu span", ".content_set .item", "current", "click", "0");
    $.Huitab(".tabBar span", ".tabCon", "current", "click", "0");

    $('.skin-minimal input').iCheck({
        checkboxClass: 'icheckbox-blue',
        radioClass: 'iradio-blue',
        increaseArea: '20%'
    });

    
});