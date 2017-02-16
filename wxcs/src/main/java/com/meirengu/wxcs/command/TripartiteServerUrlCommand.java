package com.meirengu.wxcs.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.meirengu.wxcs.util.PropUtil;
import com.meirengu.wxcs.util.SHA1;
import com.meirengu.wxcs.util.Util;

/**
 * <p>微信公众平台接入(服务器暂时未启用)</p>
 * 验证服务器URL有效性成功后即接入生效，成为开发者<br>
 * 启用并设置服务器配置后，用户发给公众号的消息以及开发者需要的事件推送，将被微信转发到该URL中
 */
public class TripartiteServerUrlCommand extends Command{
    
    private static final Logger logger = LoggerFactory.getLogger(TripartiteServerUrlCommand.class);

    public TripartiteServerUrlCommand(HttpServletRequest request, HttpServletResponse response) {
        super(request, response);
    }

    @Override
    protected void process() throws Exception {
        String token = "";//公众号基本配置，服务器配置启用的Token令牌
        try {
            token = PropUtil.getInstance().getProperty("wx.meirengu.token");
        } catch (Exception e) {
            ;
        }
        if (Util.isEmptyStr(token)) {
            responseMessage = ResponseMessage.client_parameter_error;
            return;
        }
        // 微信服务器将发送GET请求到填写的URL上,这里需要判定是否为GET请求
        boolean isGet = request.getMethod().toLowerCase().equals("get");
        logger.info("wx meirengu url verify, http method is:", request.getMethod());
        if (isGet) {
            // 验证URL真实性
            String signature = request.getParameter("signature");// 微信加密签名
            String timestamp = request.getParameter("timestamp");// 时间戳
            String nonce = request.getParameter("nonce");// 随机数
            String echostr = request.getParameter("echostr");// 随机字符串
            List<String> params = new ArrayList<String>();
            params.add(token);
            params.add(timestamp);
            params.add(nonce);
            // 1. 将token、timestamp、nonce三个参数进行字典序排序
            Collections.sort(params, new Comparator<String>() {
                public int compare(String o1, String o2) {
                    return o1.compareTo(o2);
                }
            });
            // 2. 将三个参数字符串拼接成一个字符串进行sha1加密
            String temp = SHA1.encode(params.get(0) + params.get(1) + params.get(2));
            if (temp.equals(signature)) {
                logger.info("signature success,temp={},signatrue={}", new Object[]{temp, signature});
                response.getWriter().write(echostr);
            }else {
                logger.info("signature failure,temp={},signatrue={}", new Object[]{temp, signature});
            }
        } else {
            // 处理接收消息
        }
    }

}
