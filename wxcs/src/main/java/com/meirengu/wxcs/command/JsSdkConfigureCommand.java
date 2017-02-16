package com.meirengu.wxcs.command;

import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meirengu.wxcs.service.JsSdkSignatureService;
import com.meirengu.wxcs.util.StringUtil;
import com.meirengu.wxcs.util.Util;
import com.meirengu.wxcs.vo.JsSdkSignature;

/**
 * 微信JS-SDK签名
 * 通过config接口注入权限验证配置
 */
public class JsSdkConfigureCommand extends Command{
    
    private static final Logger logger = LoggerFactory.getLogger(JsSdkConfigureCommand.class);

    public JsSdkConfigureCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    protected void process() throws Exception {
        //get params
        String url = getParameter("url");
        //verify params
        if (StringUtil.isEmpty(url)) {
            responseMessage = ResponseMessage.client_parameter_error;
            return;
        }
        String shareUrl = URLDecoder.decode(url, "UTF-8");
        //invoke wx signature service
        JsSdkSignature signature = JsSdkSignatureService.signature(shareUrl);
        if (signature != null) {
            result.put("noncestr", Util.nullToEmptyStr(signature.getNoncestr()));
            result.put("timestamp", signature.getTimestamp());
            result.put("signature", Util.nullToEmptyStr(signature.getSignature()));
            contentType = Util.emptyToDefaultStr(getParameter("contentType"), "jsonp");
            logger.info("process result:{}", result);
        }else{
            responseMessage = ResponseMessage.signature_error;
            logger.warn("wxSignature is null");
        }
    }
}
