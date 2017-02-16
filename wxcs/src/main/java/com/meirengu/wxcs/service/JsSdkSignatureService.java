package com.meirengu.wxcs.service;

import java.net.SocketTimeoutException;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.StringUtil;
import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meirengu.wxcs.util.ConstUtil;
import com.meirengu.wxcs.util.SHA1;
import com.meirengu.wxcs.vo.AccessToken;
import com.meirengu.wxcs.vo.JsapiTicket;
import com.meirengu.wxcs.vo.JsSdkSignature;

/**
 * 微信签名服务
 *
 * @author Maxzh
 * @since 2015年1月27日
 *
 */
public class JsSdkSignatureService {

    private static final Logger logger = LoggerFactory.getLogger(JsSdkSignatureService.class);
    private static final String KEY_WX_ACCESS_TOKEN = "wx_access_token";
    private static final String KEY_WX_JSAPI_TICKET = "wx_jsapi_ticket";

    /**
     * <p>获取access token</p>
     * access_token是公众号的全局唯一票据，公众号调用各接口时都需使用access_token。<br>
     * 
     * http请求方式: GET
     * https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET
     *
     * @return {"access_token":"ACCESS_TOKEN","expires_in":7200}
     */
    public static String getAccessToken() {
        //Object memObject = MemcachedHandler.get(KEY_WX_ACCESS_TOKEN);
        Object memObject = null;
        if (memObject != null) {
            return memObject.toString();
        }
        StringBuffer accessTokenUrl = new StringBuffer(ConstUtil.WX_ACCESS_TOKEN_URL);
        accessTokenUrl.append("?grant_type=client_credential");
        accessTokenUrl.append("&appid=").append(ConstUtil.WX_TAIQIKEFU_APPID);
        accessTokenUrl.append("&secret=").append(ConstUtil.WX_TAIQIKEFU_SECRET);
        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(accessTokenUrl.toString());
            if (logger.isDebugEnabled()) {
                logger.debug("getAccessToken Result >>> code:" + hr.getStatusCode() + " content:" + hr.getContent());
            }
            if (hr.getStatusCode() == 200) {
                logger.info("getAccessToken response >>", hr.getContent());
                AccessToken accessToken = JSONObject.parseObject(hr.getContent(), AccessToken.class);
                if (accessToken != null) {
                    //MemcachedHandler.store(KEY_WX_ACCESS_TOKEN, accessToken.getAccess_token(), new Date(1000*(accessToken.getExpires_in() - 100)));
                }
                return accessToken.getAccess_token();
            }
        } catch (ConnectTimeoutException cte) {
            logger.warn("getAccessToken connect timeout to server" + cte);
        } catch (SocketTimeoutException e) {
            logger.warn("getAccessToken socket timeout to server" + e);
        } catch (Exception e) {
            logger.error("getAccessToken Error " + e);
        }
        
        return "";
    }

    /**
     * <p>获取jsapi_ticket</p>
     * 
     * jsapi_ticket是公众号用于调用微信JS接口的临时票据，通过access_token来获取。<br>
     * 
     * 采用http GET方式请求获得jsapi_ticket:
     * https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=ACCESS_TOKEN&type=jsapi
     *
     * @return jsapi_ticket
     */
    public static String getJsapiTicket() {
        //Object memObject = MemcachedHandler.get(KEY_WX_JSAPI_TICKET);
        Object memObject = null;
        if (memObject != null) {
            return memObject.toString();
        }
        String accessToken = getAccessToken();
        if (StringUtil.isEmpty(accessToken)) {
            return "";
        }
        StringBuffer jsapiTicketUrl = new StringBuffer(ConstUtil.WX_JSAPI_TICKET_URL);
        jsapiTicketUrl.append("?access_token=").append(accessToken);
        jsapiTicketUrl.append("&type=jsapi");
        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(jsapiTicketUrl.toString());
            if (logger.isDebugEnabled()) {
                logger.debug("getJsapiTicket Result >>> code:" + hr.getStatusCode() + " content:" + hr.getContent());
            }
            if (hr.getStatusCode() == 200) {
                logger.info("getJsapiTicket response >>", hr.getContent());
                JsapiTicket jsapiTicket = JSONObject.parseObject(hr.getContent(), JsapiTicket.class);
                if (jsapiTicket != null) {
                    //MemcachedHandler.store(KEY_WX_JSAPI_TICKET, jsapiTicket.getTicket(), new Date(1000*(jsapiTicket.getExpires_in() - 100)));
                }
                return jsapiTicket.getTicket();
            }
        } catch (ConnectTimeoutException cte) {
            logger.warn("getJsapiTicket connect timeout to server" + cte);
        } catch (SocketTimeoutException e) {
            logger.warn("getJsapiTicket socket timeout to server" + e);
        } catch (Exception e) {
            logger.error("getJsapiTicket Error " + e);
        }

        return "";
    }

    /**
     * <p>签名算法</p>
     * 签名生成规则如下：参与签名的字段包括noncestr（随机字符串）, 有效的jsapi_ticket, timestamp（时间戳）, url（当前网页的URL，不包含#及其后面部分） 。
     * 对所有待签名参数按照字段名的ASCII 码从小到大排序（字典序）后，使用URL键值对的格式（即key1=value1&key2=value2…）拼接成字符串string1。
     * 这里需要注意的是所有参数名均为小写字符。对string1作sha1加密，字段名和字段值都采用原始值，不进行URL 转义。
     * 
     * @param url 当前网页的URL，不包含#及其后面部分
     * @return JS-SDK权限验证的签名
     */
    public static JsSdkSignature signature(String url) {
        String jsapi_ticket = getJsapiTicket();
        if (StringUtil.isEmpty(jsapi_ticket)) {
            return null;
        }
        String noncestr = StringUtil.createNonceStr(16);//签名用随机字符串
        Long timestamp = System.currentTimeMillis()/1000;//签名用时间戳

        StringBuffer signatureStr = new StringBuffer();
        signatureStr.append("jsapi_ticket=").append(jsapi_ticket).append("&noncestr=").append(noncestr).append("&timestamp=").append(timestamp).append("&url=")
                .append(url);

        String signature = SHA1.encode(signatureStr.toString());//生成签名
        logger.info("signatureStr:{} >> signature:{}", new Object[]{ signatureStr, signature});
        
        JsSdkSignature jsSdkSignature = new JsSdkSignature();
        jsSdkSignature.setNoncestr(noncestr);
        jsSdkSignature.setTimestamp(timestamp);
        jsSdkSignature.setSignature(signature);

        return jsSdkSignature;
    }

}
