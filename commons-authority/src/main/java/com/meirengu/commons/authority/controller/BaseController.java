package com.meirengu.commons.authority.controller;

import com.uqsoft.common.util.JsonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.Charset;

public class BaseController {

    private static Log logger = LogFactory.getLog(BaseController.class);

    final int EXPRIED_TIME = 60 * 60 * 5;



   

    protected void doResponseByJson(HttpServletResponse response, Object o) {
        doResponse(response, "json", o);
    }

    void doResponse(HttpServletResponse response, String format, Object o) {
        String result = null;
        if (format != null && format.toLowerCase().equals("json")) {
            response.setContentType("text/json;charset=utf-8");
            result = JsonUtil.toJson(o);
        } 
        logger.info("json result:" + result);
        response.setCharacterEncoding("utf-8");

        try {
            PrintWriter out = response.getWriter();
            out.println(result);
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error("[BaseController response]", e);
        }
    }

    void doResponse(HttpServletResponse response, Object o) {
        response.setContentType("text/html;charset=utf-8");

        response.setCharacterEncoding("utf-8");
        if (logger.isDebugEnabled()) {
            logger.debug("doResponse_send: " + o);
        }
        try {
            PrintWriter out = response.getWriter();
            out.println(o);
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error("[BaseController response]", e);
        }
    }
    
    protected ResponseEntity<String> doResponse(String result, String fomart, String charset){
    	try{
    		HttpHeaders headers = new HttpHeaders();  
            MediaType mt=new MediaType("text",fomart,Charset.forName(charset));  
            headers.setContentType(mt); 
            if (logger.isDebugEnabled()) {
                logger.debug("doResponse_send: " + result);
            }
            return new ResponseEntity<String>(result,headers, HttpStatus.OK);
    	}catch(Exception e){
    		logger.error("[BaseController response]", e);
    	}
    	return null;
    }
  
}
