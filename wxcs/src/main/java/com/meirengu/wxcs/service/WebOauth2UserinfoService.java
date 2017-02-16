package com.meirengu.wxcs.service;

import java.net.SocketTimeoutException;
import java.util.Date;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.utils.HttpUtil;
import org.apache.http.conn.ConnectTimeoutException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meirengu.wxcs.util.ConstUtil;
import com.meirengu.wxcs.vo.WebOauth2AccessToken;
import com.meirengu.wxcs.vo.WebOauth2UserInfo;

/**
 * 网页授权获取用户基本信息服务
 *
 * @author Maxzh
 * @since 2015年1月27日
 *
 */
public class WebOauth2UserinfoService {
    
    private static final Logger logger = LoggerFactory.getLogger(WebOauth2UserinfoService.class);
    
    /**
     * <p>用户同意授权，获取code </p>
     * 
     * 在确保微信公众账号拥有授权作用域（scope参数）的权限的前提下（服务号获得高级接口后，默认拥有scope参数中的snsapi_base和snsapi_userinfo），引导关注者打开如下页面<br>
     * https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect<br>
     *
     * @param redirect_uri 授权后重定向的回调链接地址，请使用urlencode对链接进行处理
     * @param response_type 返回类型，请填写code
     * @param scope 应用授权作用域，snsapi_base （不弹出授权页面，直接跳转，只能获取用户openid），snsapi_userinfo （弹出授权页面，可通过openid拿到昵称、性别、所在地。并且，即使在未关注的情况下，只要用户授权，也能获取其信息
     * @param state 重定向后会带上state参数，开发者可以填写a-zA-Z0-9的参数值，最多128字节
     */
    public static void oauth2Authorize(String redirect_uri, String response_type, String scope, String state){
        StringBuffer oauth2AuthorizeUrl = new StringBuffer(ConstUtil.WX_OAUTH2_AUTHORIZE_URL);
        oauth2AuthorizeUrl.append("?appid=").append(ConstUtil.WX_TAIQIKEFU_APPID)
        .append("&redirect_uri=").append(redirect_uri)
        .append("&response_type=").append(response_type)
        .append("&scope=").append(scope)
        .append("&state=").append(state)
        .append("#wechat_redirect");
        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(oauth2AuthorizeUrl.toString());
            if (logger.isDebugEnabled()) {
                logger.debug("oauth2Authorize Result >>> code:{}, content:{}" , new Object[]{hr.getStatusCode(), hr.getContent()});
            }
            if (hr.getStatusCode() == 200) {
                logger.info("oauth2Authorize response >> {}", hr.getContent());
            }
        } catch (ConnectTimeoutException cte) {
            logger.warn("oauth2Authorize connect timeout to server" + cte);
        } catch (SocketTimeoutException e) {
            logger.warn("oauth2Authorize socket timeout to server" + e);
        } catch (Exception e) {
            logger.error("oauth2Authorize Error " + e);
        }
    }
    
    /**
     * 通过code换取网页授权access_token
     * 
     * @param code
     * @return
     */
    public static WebOauth2AccessToken oauth2AccessToken(String code){
        StringBuffer oauth2AccessTokenUrl = new StringBuffer(ConstUtil.WX_OAUTH2_ACCESSTOKEN_URL);
        oauth2AccessTokenUrl.append("?appid=").append(ConstUtil.WX_TAIQIKEFU_APPID)
        .append("&secret=").append(ConstUtil.WX_TAIQIKEFU_SECRET)
        .append("&code=").append(code)
        .append("&grant_type=authorization_code");
        
        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(oauth2AccessTokenUrl.toString());
            if (logger.isDebugEnabled()) {
                logger.debug("oauth2AccessToken Result >>> code:{}, content:{}" , new Object[]{hr.getStatusCode(), hr.getContent()});
            }
            if (hr.getStatusCode() == 200) {
                logger.info("oauth2AccessToken response >>{}", hr.getContent());
                WebOauth2AccessToken webOauth2AccessToken = JSONObject.parseObject(hr.getContent(), WebOauth2AccessToken.class);
                if (webOauth2AccessToken != null) {
                    //MemcachedHandler.store(webOauth2AccessToken.getOpenid()+"_access_token", webOauth2AccessToken, new Date(1000*(webOauth2AccessToken.getExpires_in() - 100)));
                }
                return webOauth2AccessToken;
            }
        } catch (ConnectTimeoutException cte) {
            logger.warn("oauth2AccessToken connect timeout to server" + cte);
        } catch (SocketTimeoutException e) {
            logger.warn("oauth2AccessToken socket timeout to server" + e);
        } catch (Exception e) {
            logger.error("oauth2AccessToken Error " + e);
        }
        return null;
    }

    /**
     * 刷新access_token（如果需要）
     * 
     * @param refreshToken
     * @return
     */
    public static WebOauth2AccessToken oauth2RefreshToken(String refreshToken){
        StringBuffer oauth2RefreshTokenUrl = new StringBuffer(ConstUtil.WX_OAUTH2_REFRESHTOKEN_URL);
        oauth2RefreshTokenUrl.append("?appid=").append(ConstUtil.WX_TAIQIKEFU_APPID)
        .append("&grant_type=").append("refresh_token")
        .append("&refresh_token=").append(refreshToken);
        
        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(oauth2RefreshTokenUrl.toString());
            if (logger.isDebugEnabled()) {
                logger.debug("oauth2RefreshToken Result >>> code:{}, content:{}" , new Object[]{hr.getStatusCode(), hr.getContent()});
            }
            if (hr.getStatusCode() == 200) {
                logger.info("oauth2RefreshToken response >> {}", hr.getContent());
                WebOauth2AccessToken webOauth2AccessToken = JSONObject.parseObject(hr.getContent(), WebOauth2AccessToken.class);
                return webOauth2AccessToken;
            }
        } catch (ConnectTimeoutException cte) {
            logger.warn("oauth2RefreshToken connect timeout to server" + cte);
        } catch (SocketTimeoutException e) {
            logger.warn("oauth2RefreshToken socket timeout to server" + e);
        } catch (Exception e) {
            logger.error("oauth2RefreshToken Error " + e);
        }
        return null;
    }    
    
    
    /**
     * 拉取用户信息(需scope为 snsapi_userinfo)
     * 
     * @param oauth2AccessToken
     * @param openId
     * @return
     */
    public static WebOauth2UserInfo oauth2UserInfo(String oauth2AccessToken, String openId){
        StringBuffer wxOauth2UserInfoUrl = new StringBuffer(ConstUtil.WX_OAUTH2_USERINFO_URL);
        wxOauth2UserInfoUrl.append("?access_token=").append(oauth2AccessToken)
        .append("&openid=").append(openId)
        .append("&lang=zh_CN");
        
        try {
            HttpUtil.HttpResult hr = HttpUtil.doGet(wxOauth2UserInfoUrl.toString());
            if (logger.isDebugEnabled()) {
                logger.debug("wx oauth2UserInfo Result >>> code:{}, content:{}" , new Object[]{hr.getStatusCode(), hr.getContent()});
            }
            if (hr.getStatusCode() == 200) {
                logger.info("wx oauth2UserInfo response >> {}", hr.getContent());
                WebOauth2UserInfo webOauth2UserInfo = JSONObject.parseObject(hr.getContent(), WebOauth2UserInfo.class);
                return webOauth2UserInfo;
            }
        } catch (ConnectTimeoutException cte) {
            logger.warn("oauth2UserInfo connect timeout to server" + cte);
        } catch (SocketTimeoutException e) {
            logger.warn("oauth2UserInfo socket timeout to server" + e);
        } catch (Exception e) {
            logger.error("oauth2UserInfo Error " + e);
        }
        return null;
    }    
}
