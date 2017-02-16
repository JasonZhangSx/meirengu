package com.meirengu.wxcs.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meirengu.wxcs.service.WebOauth2UserinfoService;
import com.meirengu.wxcs.util.StringUtil;
import com.meirengu.wxcs.vo.WebOauth2UserInfo;

/**
 * 拉取用户信息(需scope为 snsapi_userinfo)
 */
public class WebOauth2UserInfoCommand extends Command{

    private static final Logger logger = LoggerFactory.getLogger(WebOauth2UserInfoCommand.class);
    
    public WebOauth2UserInfoCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    protected void process() throws Exception {
        //get params
        String access_token = request.getParameter("access_token ");
        String openid = request.getParameter("openid");
        if (logger.isDebugEnabled()) {
            logger.debug("WxOauth2UserInfoCommand params are access_token:{}, openid:{}", new Object[]{access_token, openid});
        }
        //verify params
        if (StringUtil.isEmpty(access_token) || StringUtil.isEmpty(openid)) {
            responseMessage = ResponseMessage.client_parameter_error;
            return;
        }
        //invoke oauth2UserInfo service
        WebOauth2UserInfo userInfo = WebOauth2UserinfoService.oauth2UserInfo(access_token, openid);
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
        }
    }
}
