package com.meirengu.wxcs.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 常量工具类
 * 
 * @author Maxzh
 * @since 2010-11-18
 */
public class ConstUtil {
	
    private static final Logger logger = LoggerFactory.getLogger(ConstUtil.class);

    public static String WX_ACCESS_TOKEN_URL;
    public static String WX_JSAPI_TICKET_URL;
    public static String WX_OAUTH2_AUTHORIZE_URL;
    public static String WX_OAUTH2_ACCESSTOKEN_URL;
    public static String WX_OAUTH2_REFRESHTOKEN_URL;
    public static String WX_OAUTH2_USERINFO_URL;
    public static String WX_OAUTH2_REDIRECT_URL;
    
    //TAIQIKEFU
    public static String WX_MEIRENGU_APPID;
    public static String WX_MEIRENGU_SECRET;

    //memcached configuration const
	public static String [] MEMCACHED_SERVERS;
	public static Integer[] MEMCACHED_WEIGHTS;
	public static Integer MEMCACHED_CONN_INIT = 10;
	public static Integer MEMCACHED_CONN_MIN = 10;
	public static Integer MEMCACHED_CONN_MAX = 250;
	public static Long MEMCACHED_IDEL_MAX = 1000*60*60L;
	public static Long MEMCACHED_MAINTSLEEP = 1000L;
	public static Boolean MEMCACHED_NAGEL = false;
	public static Integer MEMCACHED_SOCKETTO = 3000;
	public static Integer MEMCACHED_SOCKETCONNECTTO = 3000;
	
	public static void initParas() throws Exception {
		
		try {
		    WX_ACCESS_TOKEN_URL = PropUtil.getInstance().getProperty("wx.access.token.url");
		    WX_JSAPI_TICKET_URL = PropUtil.getInstance().getProperty("wx.jsapi.ticket.url");
		    WX_OAUTH2_AUTHORIZE_URL = PropUtil.getInstance().getProperty("wx.oauth2.authorize.url");
            WX_OAUTH2_ACCESSTOKEN_URL = PropUtil.getInstance().getProperty("wx.oauth2.accesstoken.url");
            WX_OAUTH2_REFRESHTOKEN_URL = PropUtil.getInstance().getProperty("wx.oauth2.refreshtoken.url");
            WX_OAUTH2_USERINFO_URL = PropUtil.getInstance().getProperty("wx.oauth2.userinfo.url");
            WX_OAUTH2_REDIRECT_URL = PropUtil.getInstance().getProperty("wx.oauth2.redirect.url");
            WX_MEIRENGU_APPID = PropUtil.getInstance().getProperty("wx.meirengu.appid");
            WX_MEIRENGU_SECRET = PropUtil.getInstance().getProperty("wx.meirengu.secret");
            
		} catch (Exception e) {
		    logger.warn("the properties wx configuration init error", e);
        }
		
		logger.error("ConstUtil has be inited");
	}
	
	public static void main(String[] args) {

        System.out.println("============memcached configuration const==============");
        for (String server : ConstUtil.MEMCACHED_SERVERS) {
            System.out.println(server);
        }
        for (Integer weight : ConstUtil.MEMCACHED_WEIGHTS) {
            System.out.println(weight);
        }
        System.out.println(ConstUtil.MEMCACHED_CONN_INIT);
        System.out.println(ConstUtil.MEMCACHED_CONN_MIN);
        System.out.println(ConstUtil.MEMCACHED_CONN_MAX);
        System.out.println(ConstUtil.MEMCACHED_IDEL_MAX);
        System.out.println(ConstUtil.MEMCACHED_MAINTSLEEP);
        System.out.println(ConstUtil.MEMCACHED_NAGEL);
        System.out.println(ConstUtil.MEMCACHED_SOCKETTO);
        System.out.println(ConstUtil.MEMCACHED_SOCKETCONNECTTO);
        System.out.println("============api configuration const==============");
    
    }
	
}
