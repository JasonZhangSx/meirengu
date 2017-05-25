jQuery(document).ready(function(){	
	var url_=encodeURIComponent(window.location.href);
  $.ajax({
      type : "get",
  	  async: false,
  	  url:"https://api.meirenguvip.com/wxcs/service/dtwxconfig?url="+url_,
  	  dataType: "jsonp",
  	  jsonp: "jsonpCallback",
  	  jsonpCallback:"success_jsonpCallback",
      success : function (json){
		wx.config({
		    debug: false, // 开启调试模式,调用的所有api的返回值会在客户端alert出来，若要查看传入的参数，可以在pc端打开，参数信息会通过log打出，仅在pc端时才会打印。
		    appId: 'wx22ae3075d64a4f81', // 必填，公众号的唯一标识
		    timestamp: json.timestamp, // 必填，生成签名的时间戳
		    nonceStr: json.noncestr, // 必填，生成签名的随机串
		    signature: json.signature,// 必填，签名
		    jsApiList: ["onMenuShareTimeline","onMenuShareAppMessage","onMenuShareQQ","onMenuShareWeibo"] // 必填，需要使用的JS接口列表			
		});
		
  		}
  	  
  });
});

//通过ready接口处理成功验证
	wx.ready(function(){
		 // “分享到朋友圈”
		 wx.onMenuShareTimeline({
		      title: '美丽邀约-加入2017海伦合伙人计划',
		      link: 'https://api.meirenguvip.com/webview/activity/signup',
		      imgUrl: 'https://api.meirenguvip.com/webview/img/signup_share.jpg'
		    });
		    
		  // “分享给朋友”		
	    wx.onMenuShareAppMessage({
	      	title: '美丽邀约-加入2017海伦合伙人计划',
	      	desc: '股权分红+专享折扣+现金返利+医美项目赠送。享有股权分红，预期年化高达15%,股东专享折扣低至6折！更有现金返现10%，尊享推荐奖励！以及最高价值143700元医美项目免费赠送。',
			link: 'https://api.meirenguvip.com/webview/activity/signup',
			imgUrl: 'https://api.meirenguvip.com/webview/img/signup_share.jpg'
	    });
	    
	    //“分享到QQ”
	    wx.onMenuShareQQ({
			title: '美丽邀约-加入2017海伦合伙人计划',
			desc: '股权分红+专享折扣+现金返利+医美项目赠送。享有股权分红，预期年化高达15%,股东专享折扣低至6折！更有现金返现10%，尊享推荐奖励！以及最高价值143700元医美项目免费赠送。',
			link: 'https://api.meirenguvip.com/webview/activity/signup',
			imgUrl: 'https://api.meirenguvip.com/webview/img/signup_share.jpg'
		});
		//“分享到微博”
	    wx.onMenuShareWeibo({
			title: '美丽邀约-加入2017海伦合伙人计划',
			desc: '股权分红+专享折扣+现金返利+医美项目赠送。享有股权分红，预期年化高达15%,股东专享折扣低至6折！更有现金返现10%，尊享推荐奖励！以及最高价值143700元医美项目免费赠送。',
			link: 'https://api.meirenguvip.com/webview/activity/signup',
			imgUrl: 'https://api.meirenguvip.com/webview/img/signup_share.jpg'
		});
	    
	});































