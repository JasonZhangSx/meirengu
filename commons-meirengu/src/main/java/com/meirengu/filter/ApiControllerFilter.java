package com.meirengu.filter;

import com.alibaba.fastjson.JSON;
import com.meirengu.common.StatusCode;
import com.meirengu.utils.ConfigUtil;
import com.meirengu.utils.SignParamsUtils;
import com.meirengu.utils.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

/**
 * API验签
 * @author 建新
 * @create 2017-02-07 13:59
 */
public class ApiControllerFilter extends OncePerRequestFilter{

    private static final Logger LOGGER = LoggerFactory.getLogger(ApiControllerFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

        //获取请求的ip地址
        String ip = RequestUtil.getIpAddr(httpServletRequest);
        //ip过滤
        if("120.27.37.54".equals(ip)){
            filterChain.doFilter(httpServletRequest, httpServletResponse);
        }else {
            Map<String, String[]> paramsMap = httpServletRequest.getParameterMap();

            Map<String, String> params = new HashMap<>();
            for (String key: paramsMap.keySet()) {
                params.put(key, paramsMap.get(key)[0]);
            }

            Map<String , Object> map = new HashMap<>();
            map.put("code", StatusCode.SIGN_NOT_VALID);
            map.put("message", StatusCode.codeMsgMap.get(StatusCode.SIGN_NOT_VALID));

            Long timestamp = Long.valueOf(httpServletRequest.getParameter("timestamp"));
            Long interval = (System.currentTimeMillis() - timestamp)/1000;
            Long apiInterval = Long.parseLong(ConfigUtil.getConfig("api.interval.time"));

            //当前请求时间大于api限制的请求时间
            if(interval > apiInterval){
                PrintWriter out = httpServletResponse.getWriter();
                map.put("code", StatusCode.REQUEST_TIMEOUT);
                map.put("message", StatusCode.codeMsgMap.get(StatusCode.REQUEST_TIMEOUT));
                out.print(JSON.toJSON(map));
                out.flush();
                out.close();
            }else {
                if(SignParamsUtils.verify(params)){
                    filterChain.doFilter(httpServletRequest, httpServletResponse);
                }else{
                    PrintWriter out = httpServletResponse.getWriter();
                    out.print(JSON.toJSON(map));
                    out.flush();
                    out.close();
                }
            }
        }

    }

}
