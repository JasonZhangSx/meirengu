package com.meirengu.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.Constants;
import com.meirengu.common.RedisClient;
import com.meirengu.common.StatusCode;
import com.meirengu.utils.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * API验签
 * @author 建新
 * @create 2017-02-07 13:59
 */
public class ValidateSignFilter extends OncePerRequestFilter{

    private static final Logger LOGGER = LoggerFactory.getLogger(ValidateSignFilter.class);

    RedisClient redisClient;

    @Override
    protected synchronized void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        //获取请求的URL
        String requestURL = httpServletRequest.getRequestURI();
        //获取请求的ip地址
        String ip = RequestUtil.getIpAddr(httpServletRequest);

        String ip1 = RequestUtil.getClientIp(httpServletRequest);
        LOGGER.info("ip: {}, ip1: {}", ip, ip1);

        LOGGER.info("request api filter >> ip: {}, url: {}, params: {}", new Object[]{ip, requestURL, JSON.toJSON(httpServletRequest.getParameterMap())});
        //白名单部分的全部过滤
        if(requestURL.startsWith(ConfigUtil.getConfig("ip.white.url"))){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        String whiteUrl = redisClient.get(Constants.IP_WHITE_PREFIX+ip);
        if(!StringUtil.isEmpty(whiteUrl)){
            //*是所有url免验签
            if("*".equals(whiteUrl)){
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
            if(whiteUrl.contains(requestURL)){
                filterChain.doFilter(httpServletRequest, httpServletResponse);
                return;
            }
        }

        Map<String , Object> map = new HashMap<>();
        map.put("code", StatusCode.SIGN_NOT_VALID);
        map.put("msg", StatusCode.codeMsgMap.get(StatusCode.SIGN_NOT_VALID));

        String tsp = httpServletRequest.getParameter("timestamp");
        String appKey = httpServletRequest.getParameter("key");
        String sign = httpServletRequest.getParameter("sign");

        //timestamp, key, sign 为验签必传参数
        if(StringUtil.isEmpty(appKey) || StringUtil.isEmpty(sign)){
            PrintWriter out = httpServletResponse.getWriter();
            LOGGER.warn(">> sign is {} and appKey is {}",sign, appKey);
            map.put("code", StatusCode.SIGN_MISSING_ARGUMENT);
            map.put("msg", StatusCode.codeMsgMap.get(StatusCode.SIGN_MISSING_ARGUMENT));
            out.print(JSON.toJSON(map));
            out.flush();
            out.close();
            return;
        }

        Map<String, String[]> paramsMap = httpServletRequest.getParameterMap();

        Map<String, String> params = new HashMap<>();
        for (String key: paramsMap.keySet()) {
            params.put(key, paramsMap.get(key)[0]);
        }

        //是否开启防重放
        if(ConfigUtil.getConfig("api.repeat.action").equalsIgnoreCase("true")){

            if(StringUtil.isEmpty(tsp)){
                PrintWriter out = httpServletResponse.getWriter();
                LOGGER.warn(">> timestamp is {}",tsp);
                map.put("code", StatusCode.TIMESTAMP_MISSING);
                map.put("msg", StatusCode.codeMsgMap.get(StatusCode.TIMESTAMP_MISSING));
                out.print(JSON.toJSON(map));
                out.flush();
                out.close();
                return;
            }

            Long timestamp = Long.valueOf(tsp);
            Long interval = (System.currentTimeMillis() - timestamp)/1000;
            Long apiInterval = Long.parseLong(ConfigUtil.getConfig("api.interval.time"));

            //当前请求时间大于api限制的请求时间
            if(interval > apiInterval){
                PrintWriter out = httpServletResponse.getWriter();
                LOGGER.warn(">> request timeout....");
                map.put("code", StatusCode.REQUEST_TIMEOUT);
                map.put("msg", StatusCode.codeMsgMap.get(StatusCode.REQUEST_TIMEOUT));
                out.print(JSON.toJSON(map));
                out.flush();
                out.close();
                return;
            }
        }

        String androidAppKey = ConfigUtil.getConfig("api.adroid.appKey");
        String iosAppKey = ConfigUtil.getConfig("api.ios.appKey");
        String wxAppKey = ConfigUtil.getConfig("api.wx.appKey");
        String wapAppKey = ConfigUtil.getConfig("api.wap.appKey");
        String appSecret = null;

        if(androidAppKey.equals(appKey)){
            appSecret = ConfigUtil.getConfig("api.adroid.appSecret");
            LOGGER.info("requet api platform is android.");
        }else if(iosAppKey.equals(appKey)){
            appSecret = ConfigUtil.getConfig("api.ios.appSecret");
            LOGGER.info("requet api platform is ios.");
        }else if(wapAppKey.equals(appKey)){
            appSecret = ConfigUtil.getConfig("api.wap.appSecret");
            LOGGER.info("requet api platform is wap.");
        }else if(wxAppKey.equals(appKey)){
            appSecret = ConfigUtil.getConfig("api.wx.appSecret");
            LOGGER.info("requet api platform is weixin.");
        }else {
            PrintWriter out = httpServletResponse.getWriter();
            LOGGER.warn(">> invalid appkey....");
            map.put("code", StatusCode.BAD_API_KEY);
            map.put("msg", StatusCode.codeMsgMap.get(StatusCode.BAD_API_KEY));
            out.print(JSON.toJSON(map));
            out.flush();
            out.close();
            return;
        }

        if(SignParamsUtils.verify(params, appSecret)){
            LOGGER.warn(">> validate sign is success");
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }else{
            PrintWriter out = httpServletResponse.getWriter();
            LOGGER.warn(">> validate sign is fail");
            out.print(JSON.toJSON(map));
            out.flush();
            out.close();
        }



    }

    public RedisClient getRedisClient() {
        return redisClient;
    }

    public void setRedisClient(RedisClient redisClient) {
        this.redisClient = redisClient;
    }
}
