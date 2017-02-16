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
    public static String WX_TAIQIKEFU_APPID;
    public static String WX_TAIQIKEFU_SECRET;

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
			MEMCACHED_SERVERS = PropUtil.getInstance().getProperty("memcached.servers").split(",");
			String [] MEMCACHED_WEIGHTS_STR = PropUtil.getInstance().getProperty("memcached.weights").split(",");
			MEMCACHED_WEIGHTS = new Integer[MEMCACHED_WEIGHTS_STR.length];
			for (int i = 0; i < MEMCACHED_WEIGHTS_STR.length; i++) {
				MEMCACHED_WEIGHTS[i] = Integer.valueOf(MEMCACHED_WEIGHTS_STR[i]);
			}
			if (MEMCACHED_SERVERS.length != MEMCACHED_WEIGHTS.length) {
				throw new Exception("memcached servers.length != memcached weights");
			}
			MEMCACHED_CONN_MIN = Integer.valueOf(PropUtil.getInstance().getProperty("memcached.conn.min"));
			MEMCACHED_CONN_MAX = Integer.valueOf(PropUtil.getInstance().getProperty("memcached.conn.max"));
			MEMCACHED_IDEL_MAX = Long.valueOf(PropUtil.getInstance().getProperty("memcached.idel.max"));
			MEMCACHED_MAINTSLEEP = Long.valueOf(PropUtil.getInstance().getProperty("memcached.maintsleep"));
			MEMCACHED_NAGEL = "true".equals(PropUtil.getInstance().getProperty("memcached.nagel")) ? true : false;
			MEMCACHED_SOCKETTO = Integer.valueOf(PropUtil.getInstance().getProperty("memcached.SocketTO"));
			MEMCACHED_SOCKETCONNECTTO = Integer.valueOf(PropUtil.getInstance().getProperty("memcached.SocketConnectTO"));
		} catch (Exception e) {
			logger.warn("the properties memcached configuration init error", e);
		}
		
		try {
		    WX_ACCESS_TOKEN_URL = PropUtil.getInstance().getProperty("wx.access.token.url");
		    WX_JSAPI_TICKET_URL = PropUtil.getInstance().getProperty("wx.jsapi.ticket.url");
		    WX_OAUTH2_AUTHORIZE_URL = PropUtil.getInstance().getProperty("wx.oauth2.authorize.url");
            WX_OAUTH2_ACCESSTOKEN_URL = PropUtil.getInstance().getProperty("wx.oauth2.accesstoken.url");
            WX_OAUTH2_REFRESHTOKEN_URL = PropUtil.getInstance().getProperty("wx.oauth2.refreshtoken.url");
            WX_OAUTH2_USERINFO_URL = PropUtil.getInstance().getProperty("wx.oauth2.userinfo.url");
            WX_OAUTH2_REDIRECT_URL = PropUtil.getInstance().getProperty("wx.oauth2.redirect.url");
            WX_TAIQIKEFU_APPID = PropUtil.getInstance().getProperty("wx.tqkhfw.appid");
            WX_TAIQIKEFU_SECRET = PropUtil.getInstance().getProperty("wx.tqkhfw.secret");
            
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
