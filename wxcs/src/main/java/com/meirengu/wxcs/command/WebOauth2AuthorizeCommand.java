package com.meirengu.wxcs.command;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meirengu.wxcs.service.WebOauth2UserinfoService;
import com.meirengu.wxcs.util.StringUtil;

/**
 * <p>用户同意授权，获取code</p>
 * 如果用户同意授权，页面将跳转至 redirect_uri/?code=CODE&state=STATE。
 * 若用户禁止授权，则重定向后不会带上code参数，仅会带上state参数redirect_uri?state=STATE 
 */
public class WebOauth2AuthorizeCommand extends Command{

    private static final Logger logger = LoggerFactory.getLogger(WebOauth2AuthorizeCommand.class);
    
    public WebOauth2AuthorizeCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    protected void process() throws Exception {
        //get params
        String redirect_uri = request.getParameter("redirect_uri");
        String response_type = request.getParameter("response_type");
        String scope = request.getParameter("scope");
        String state = request.getParameter("state");
        if (logger.isDebugEnabled()) {
            logger.debug("request params are redirect_uri:{}, response_type:{}, scope:{}, state:{}", new Object[]{redirect_uri, response_type, scope, state});
        }
        //verify params
        if (StringUtil.isEmpty(redirect_uri) || StringUtil.isEmpty(response_type) || StringUtil.isEmpty(scope)) {
            responseMessage = ResponseMessage.client_parameter_error;
            return;
        }
        //invoke authorize service
        WebOauth2UserinfoService.oauth2Authorize(redirect_uri, response_type, scope, state);
    }

}
