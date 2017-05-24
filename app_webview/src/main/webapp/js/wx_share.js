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
		    appId: 'wx8dc681561f2e64a5', // 必填，公众号的唯一标识
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
		      title: '《青龙偃月刀》08月20日开启AppStore免费下载，众将云集今の觉醒',
		      link: 'http://test.api.meirenguvip.com/activity/game_ts/wap/index.html',
		      imgUrl: 'http://ql.taiqigame.com/wap/images/share_icon.jpg'			     
		    });
		    
		  // “分享给朋友”		
	    wx.onMenuShareAppMessage({
	      title: '《青龙偃月刀》官方网站-革命性无双RPG手游 ',
	      desc: '《青龙偃月刀》08月20日开启AppStore免费下载，众将云集今の觉醒',
	      link: 'http://ql.taiqigame.com/activity/game_ts/wap/index.html',
	      imgUrl: 'http://ql.taiqigame.com/wap/images/share_icon.jpg'		    
	    });
	    
	    //“分享到QQ”
	    wx.onMenuShareQQ({
	      title: '《青龙偃月刀》官方网站-革命性无双RPG手游',
	      desc: '《青龙偃月刀》官方网站-革命性无双RPG手游',
  		  link: 'http://ql.taiqigame.com/activity/game_ts/wap/index.html',
	      imgUrl: 'http://ql.taiqigame.com/wap/images/share_icon.jpg'		
	    }); 
		//“分享到微博”
	    wx.onMenuShareWeibo({
	      title: '《青龙偃月刀》官方网站-革命性无双RPG手游',
	      desc: '《青龙偃月刀》08月20日开启AppStore免费下载，众将云集今の觉醒',
	      link: 'http://ql.taiqigame.com/activity/game_ts/wap/index.html',
	      imgUrl: 'http://ql.taiqigame.com/wap/images/share_icon.jpg'		
	    });
	    
	});































