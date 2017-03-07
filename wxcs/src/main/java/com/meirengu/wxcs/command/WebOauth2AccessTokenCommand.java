package com.meirengu.wxcs.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meirengu.wxcs.service.WebOauth2UserinfoService;
import com.meirengu.wxcs.util.StringUtil;
import com.meirengu.wxcs.vo.WebOauth2AccessToken;

/**
 *  <p>网页授权access_token</p>
 *  用户同意授权后，通过code换取网页授权的access_token。<br>
 * 
 *  https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
 */
public class WebOauth2AccessTokenCommand extends Command{

    private static final Logger logger = LoggerFactory.getLogger(WebOauth2AccessTokenCommand.class);
    
    public WebOauth2AccessTokenCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    protected void process() throws Exception {
        //get params
        String code = request.getParameter("code");
        if (logger.isDebugEnabled()) {
            logger.debug("WxOauth2AccessTokenCommand params are code:{}", new Object[]{code});
        }
        //verify params
        if (StringUtil.isEmpty(code)) {
            responseMessage = ResponseMessage.client_parameter_error;
            return;
        }
        //invoke accesstoke service
        WebOauth2AccessToken accessToken = WebOauth2UserinfoService.oauth2AccessToken(code);
        if (accessToken != null) {
            result.put("access_token", accessToken.getAccess_token());
            result.put("expires_in", accessToken.getExpires_in());
            result.put("refresh_token", accessToken.getRefresh_token());
            result.put("openid", accessToken.getOpenid());
            result.put("scope", accessToken.getScope());
        }
    }

}
