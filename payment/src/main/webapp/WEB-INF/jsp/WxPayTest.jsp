<%--
  Created by IntelliJ IDEA.
  User: 建新
  Date: 2017/2/18
  Time: 18:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>微信支付测试</title>
    <script src="http://cdn.static.runoob.com/libs/jquery/2.1.1/jquery.min.js"></script>
    <script type="text/javascript" src="http://res.wx.qq.com/open/js/jweixin-1.0.0.js"></script>
</head>
<body>
    <h1>微信扫码支付：</h1>
    www.meirenguvip.com/payment/wx/unifiedorder?body=保妥适肉毒素（30单位/次）&out_trade_no=0214874107310427&total_fee=1&trade_type=NATIVE&device_info=WEB&product_id=12
    <h2>公众号支付：</h2>
    <script>

        $(function(){
            var wxopenid = getcookie('wxopenid');
            var access_code = GetQueryString('code');
            alert(access_code);
            if(wxopenid == ""){
                if(access_code==null){
                    alert(2);
                    var fromurl = encodeURIComponent(location.href);
                    var url='https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx22ae3075d64a4f81&redirect_uri='+fromurl+'&response_type=code&scope=snsapi_base&state=STATE%23wechat_redirect&connect_redirect=1#wechat_redirect';
                    window.location.href=url;
                }else {
                    $.ajax({
                        type:'get',
                        url:'http://www.meirenguvip.com/wxcs/service/auth2_access_token',
                        async:false,
                        cache:false,
                        data:{code:access_code},
                        dataType:'jsonp',
                        success:function(result){
                            if (result!=null){
                                alert(result);
                                addcookie('wxopenid',result.openid,360000);
                            }else {
                                alert('微信身份识别失败 \n '+result);
                                location.href=fromurl;
                            }
                        }
                    });
                }
            }
        });

        function GetQueryString(name) {
            var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
            var r = window.location.search.substr(1).match(reg);
            if (r != null)
                return unescape(r[2]);
            return null;
        }

        function addcookie(name,value,expireHours){
            var cookieString=name+"="+escape(value)+"; path=/";
            //判断是否设置过期时间
            if(expireHours>0){
                var date=new Date();
                date.setTime(date.getTime+expireHours*3600*1000);
                cookieString=cookieString+"; expire="+date.toGMTString();
            }
            document.cookie=cookieString;
        }

        function getcookie(name){
            var strcookie=document.cookie;
            var arrcookie=strcookie.split("; ");
            for(var i=0;i<arrcookie.length;i++){
                var arr=arrcookie[i].split("=");
                if(arr[0]==name)return decodeURIComponent(arr[1]); //增加对特殊字符的解析
            }
            return "";
        }

        function delCookie(name){//删除cookie
            var exp = new Date();
            exp.setTime(exp.getTime() - 1);
            var cval=getcookie(name);
            if(cval!=null) document.cookie= name + "="+cval+"; path=/;expires="+exp.toGMTString();
        }
        /*function getOpein(){
        }

        function onBridgeReady(){
            WeixinJSBridge.invoke('getBrandWCPayRequest', {
                "appId" ： "wx2421b1c4370ec43b",     //公众号名称，由商户传入
                "timeStamp"：" 1395712654",         //时间戳，自1970年以来的秒数
                "nonceStr" ： "e61463f8efa94090b1f366cccfbbb444", //随机串
                "package" ： "prepay_id=u802345jgfjsdfgsdg888",
                "signType" ： "MD5",         //微信签名方式：
                "paySign" ： "70EA570631E4BB79628FBCA90534C63FF7FADD89" //微信签名
            },
            function(res){
                if(res.err_msg == "get_brand_wcpay_request：ok" ) {}     // 使用以上方式判断前端返回,微信团队郑重提示：res.err_msg将在用户支付成功后返回    ok，但并不保证它绝对可靠。
            }
        );
        }
        if (typeof WeixinJSBridge == "undefined"){
            if( document.addEventListener ){
                document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
            }else if (document.attachEvent){
                document.attachEvent('WeixinJSBridgeReady', onBridgeReady);
                document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
            }
        }else{
            onBridgeReady();
        }
*/
    </script>
</body>
</html>
