package com.meirengu.wxcs.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meirengu.wxcs.service.WebOauth2UserinfoService;
import com.meirengu.wxcs.util.StringUtil;
import com.meirengu.wxcs.vo.WebOauth2AccessToken;

/**
 * 刷新access_token（如果需要）
 * 由于access_token拥有较短的有效期，当access_token超时后，可以使用refresh_token进行刷新，
 * refresh_token拥有较长的有效期（7天、30天、60天、90天），当refresh_token失效的后，需要用户重新授权。
 */
public class WebOauth2RefreshTokenCommand extends Command{

    private static final Logger logger = LoggerFactory.getLogger(WebOauth2RefreshTokenCommand.class);
    
    public WebOauth2RefreshTokenCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    protected void process() throws Exception {
        //get params
        String refresh_token = request.getParameter("refresh_token");
        if (logger.isDebugEnabled()) {
            logger.debug("WxOauth2RefreshTokenCommand params are refresh_token:{}", new Object[]{refresh_token});
        }
        //verify params
        if (StringUtil.isEmpty(refresh_token)) {
            responseMessage = ResponseMessage.client_parameter_error;
            return;
        }
        //invoke refreshtoke service
        WebOauth2AccessToken accessToken = WebOauth2UserinfoService.oauth2RefreshToken(refresh_token);
        if (accessToken != null) {
            result.put("access_token", accessToken.getAccess_token());
            result.put("expires_in", accessToken.getExpires_in());
            result.put("refresh_token", accessToken.getRefresh_token());
            result.put("openid", accessToken.getOpenid());
            result.put("scope", accessToken.getScope());
        }
    }

}
