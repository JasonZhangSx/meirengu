package com.meirengu.wxcs.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meirengu.wxcs.service.WebOauth2UserinfoService;
import com.meirengu.wxcs.util.StringUtil;
import com.meirengu.wxcs.vo.WebOauth2AccessToken;
import com.meirengu.wxcs.vo.WebOauth2UserInfo;

/**
 * 拉取用户信息Token
 */
public class WebOauth2UserTokenCommand extends Command{

    private static final Logger logger = LoggerFactory.getLogger(WebOauth2UserTokenCommand.class);
    
    public WebOauth2UserTokenCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    protected void process() throws Exception {
        //get params
        String code = request.getParameter("code");
        String state = request.getParameter("state");
        if (logger.isDebugEnabled()) {
            logger.debug("WxOauth2UserTokenCommand params are code:{}, state:{}", new Object[]{code, state});
        }
        //verify params
        if (StringUtil.isEmpty(code)) {
            responseMessage = ResponseMessage.client_parameter_error;
            return;
        }
        //invoke service
        WebOauth2AccessToken wxOauth2AccessToken = WebOauth2UserinfoService.oauth2AccessToken(code);
        if (wxOauth2AccessToken != null) {
            //refresh accessToken
            wxOauth2AccessToken = WebOauth2UserinfoService.oauth2RefreshToken(wxOauth2AccessToken.getRefresh_token());
            if (wxOauth2AccessToken != null) {
                //get userinfo
                WebOauth2UserInfo userInfo = WebOauth2UserinfoService.oauth2UserInfo(wxOauth2AccessToken.getAccess_token(), wxOauth2AccessToken.getOpenid());
                if (userInfo != null) {
                    //get userinfo success
                    result.put("openid", userInfo.getOpenid());
                    result.put("nickname", userInfo.getNickname());
                    result.put("sex", userInfo.getSex());
                    result.put("province", userInfo.getProvince());
                    result.put("city", userInfo.getCity());
                    result.put("country", userInfo.getCountry());
                    result.put("headimgurl", userInfo.getHeadimgurl());
                    result.put("privilege", userInfo.getPrivilege());
                    result.put("unionid", userInfo.getUnionid());
                    result.put("access_token", wxOauth2AccessToken.getAccess_token());
                    result.put("expires_in", wxOauth2AccessToken.getExpires_in());
                }
            }
        }
        responseMessage = ResponseMessage.unknown_error;
    }
}
