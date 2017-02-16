package com.meirengu.wxcs.command;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.wxcs.util.PropUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Command父类
 */
public abstract class Command {

    private static final Logger commandLogger = LoggerFactory.getLogger(Command.class);
    protected static enum ResponseMessage{
    	ok,
    	client_parameter_error,
    	contentType_error,
    	server_exception,
    	signature_error,
    	unknown_error, 
    	frequent_query,
    	wx_system_busy,
    	wx_appSecret_error,
    	wx_ticket_invalid,
    	wx_openId_invalid, 
    	wx_messageType_invalid,
    	wx_appId_invalid, 
    	wx_access_token_invalid, 
    	wx_oauthCode_invalid, 
    	wx_refreshToken_invalid, 
    	wx_openidList_invalid,
    	wx_openidListLength_invalid,
    	wx_requestCharacter_invalid,
    	wx_requestParam_invalid,
    	wx_requestFormat_invalid,
    	wx_urlLength_invalid,
    	wx_accessToken_lack,
    	wx_appid_lack,
    	wx_refreshToken_lack, 
    	wx_secret_lack,
    	wx_oauthCode_lack, 
    	wx_accessToken_timeout, 
    	wx_refreshToken_timeout,
    	wx_oauthCode_timeout,
    	wx_methodGet_required, 
    	wx_methodPost_required,
    	wx_https_required, 
    	wx_user_not_exist, 
    	wx_json_xml_parse_error,
    	wx_api_unauthorized,
    	wx_system_error, 
    	wx_dateFormat_error, 
    	wx_dateAange_error 
    };
    protected static EnumMap<ResponseMessage, Integer> responseCodes = new EnumMap<ResponseMessage, Integer>(ResponseMessage.class);
    static{
    	responseCodes.put(ResponseMessage.ok, 200);
    	responseCodes.put(ResponseMessage.client_parameter_error, 400);
    	responseCodes.put(ResponseMessage.contentType_error, 401);
    	responseCodes.put(ResponseMessage.server_exception, 500);
    	responseCodes.put(ResponseMessage.signature_error, 501);
    	responseCodes.put(ResponseMessage.unknown_error, 502);
    	responseCodes.put(ResponseMessage.frequent_query, 900);
    	//wx response
    	responseCodes.put(ResponseMessage.wx_system_busy, -1);
    	responseCodes.put(ResponseMessage.wx_appSecret_error, 40001);
    	responseCodes.put(ResponseMessage.wx_ticket_invalid, 40002);
    	responseCodes.put(ResponseMessage.wx_openId_invalid, 40003);
    	responseCodes.put(ResponseMessage.wx_appId_invalid, 40013);
    	responseCodes.put(ResponseMessage.wx_access_token_invalid, 40014);
    	responseCodes.put(ResponseMessage.wx_oauthCode_invalid, 40029);
    	responseCodes.put(ResponseMessage.wx_refreshToken_invalid, 40030);
    	responseCodes.put(ResponseMessage.wx_openidList_invalid, 40031);
    	responseCodes.put(ResponseMessage.wx_openidListLength_invalid, 40032);
    	responseCodes.put(ResponseMessage.wx_requestCharacter_invalid, 40033);
    	responseCodes.put(ResponseMessage.wx_requestParam_invalid, 40035);
    	responseCodes.put(ResponseMessage.wx_requestFormat_invalid, 40038);
    	responseCodes.put(ResponseMessage.wx_urlLength_invalid, 40039);
    	responseCodes.put(ResponseMessage.wx_accessToken_lack, 41001);
    	responseCodes.put(ResponseMessage.wx_appid_lack, 41002);
    	responseCodes.put(ResponseMessage.wx_refreshToken_lack, 41003);
    	responseCodes.put(ResponseMessage.wx_secret_lack, 41004);
    	responseCodes.put(ResponseMessage.wx_oauthCode_lack, 41008);
    	responseCodes.put(ResponseMessage.wx_accessToken_timeout, 42001);
    	responseCodes.put(ResponseMessage.wx_refreshToken_timeout, 42002);
    	responseCodes.put(ResponseMessage.wx_oauthCode_timeout, 42003);
    	responseCodes.put(ResponseMessage.wx_methodGet_required, 43001);
    	responseCodes.put(ResponseMessage.wx_methodPost_required, 43002);
    	responseCodes.put(ResponseMessage.wx_https_required, 43003);
    	responseCodes.put(ResponseMessage.wx_user_not_exist, 46004);
    	responseCodes.put(ResponseMessage.wx_json_xml_parse_error, 47001);
    	responseCodes.put(ResponseMessage.wx_api_unauthorized, 48001);
    	responseCodes.put(ResponseMessage.wx_system_error, 61450);
    	responseCodes.put(ResponseMessage.wx_dateFormat_error, 61500);
    	responseCodes.put(ResponseMessage.wx_dateAange_error, 61501);
    }
    
    protected HttpServletRequest request;
    protected HttpServletResponse response;
    
    protected ResponseMessage responseMessage = ResponseMessage.ok;//default
    protected Map<String, Object> result = Collections.synchronizedMap(new LinkedHashMap<String, Object>());
    
    protected static Map<String, String> responseContentTypes = new HashMap<String, String>();
    static{
        responseContentTypes.put("plain", "text/plain");
        responseContentTypes.put("html", "text/html");
        responseContentTypes.put("xml", "text/xml");
        responseContentTypes.put("json", "application/json");
        responseContentTypes.put("jsonp", "text/html");
    }
    protected static String contentType = "json";//default
    
    public Command(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
        try {
            this.request.setCharacterEncoding(PropUtil.getInstance().getProperty("serviceCharacterEncoding"));
        } catch (UnsupportedEncodingException e) {
            commandLogger.warn("the property named 'serviceCharacterEncoding' has not been set, use default");
            e.printStackTrace();
        }
    }
    

    /**
     * 取HttpServletRequest请求参数
     * 
     * @param parameterName
     * @return
     */
    protected String getParameter(String parameterName) {
        return request.getParameter(parameterName);
    }

    /**
     * 写响应结果
     *
     */
    private void doResponse() {
        response.setContentType(responseContentTypes.get(contentType));
        response.setCharacterEncoding("utf-8");
        if (responseMessage == ResponseMessage.ok) {
            if (result != null && result.size() > 0) {
                PrintWriter out = null;
                try {
                    commandLogger.debug("result:{}", result);
                    JSONObject jsonResult = JSONObject.parseObject(JSON.toJSONString(result));
                    commandLogger.debug("write to client({}, {}):\n{}",  new Object[]{request.getRemoteAddr(), request.getRequestURL(), jsonResult});
                    out = response.getWriter();
                    if ("jsonp".equals(contentType)) {
                        String jsonpCallback = request.getParameter("jsonpCallback");
                        out.write(jsonpCallback+"("+jsonResult.toString()+")");
                    }else {
                        out.print(jsonResult.toString());
                    }
                    out.flush();
                } catch (Exception e) {
                    commandLogger.error("exception occur: {}", e.toString());
                    e.printStackTrace();
                } finally {
                    if (out != null) {
                        out.close();
                        out = null;
                    }
                }
            }
        } else {
        	int responseCode = responseCodes.get(responseMessage);
            commandLogger.debug("send error to client({}, {}), code: {}, message: {}", new Object[]{request.getRemoteAddr(), request.getRequestURL(), responseCode, responseMessage});
            try {
                response.sendError(responseCode, responseMessage.toString());
            } catch (IOException e) {
                commandLogger.error("exception occur: {}", e.toString());
                e.printStackTrace();
            }
        }
    }

    /**
     * 具体接口逻辑处理方法
     * 在此方法中可以直接使用sqlMapClient(com.ibatis.sqlmap.client.SqlMapClient)执行增删改查操作
     * ，但是不需直接操作事物
     * 处理结束后将需要返回的处理状态码赋值给responseMessage,将需要返回的处理结果放入result(java.util.Map)中
     * 当responseMessage为ok时,result将会返回给客户端,当responseMessage为其他值时,忽略result直接发送错误代码到客户端
     */
    protected abstract void process() throws Exception;

    public final void doCommand() {
        contentType = getParameter("contentType");
        if (!responseContentTypes.containsKey(contentType)) {
            contentType = "json";
        }
        try {
            process();
        } catch (Exception e) {
            if(responseMessage==ResponseMessage.ok){
                responseMessage = ResponseMessage.server_exception;
            }
            commandLogger.error("exception occur: {}", e.toString());
            e.printStackTrace();
        }
        doResponse();
    }
    
    protected String getContextPath(){
    	return request.getContextPath();
    }
    
    /**
     * @param resultCode wx rpc协议中定义的状态码
     * @return true - wx rpc调用返回正常<br/>
     * false - wx rpc调用返回异常
     */
    protected boolean isWxNormalResponse(int resultCode) throws Exception{
		switch(resultCode){
		case 200:
		case -1:
		    responseMessage = ResponseMessage.wx_system_busy;
			break;
		case 40001:
			responseMessage = ResponseMessage.wx_appSecret_error;
			break;
        case 40002:
            responseMessage = ResponseMessage.wx_ticket_invalid;
            break;
        case 40003:
            responseMessage = ResponseMessage.wx_openId_invalid;
            break;  
        case 40013:
            responseMessage = ResponseMessage.wx_appId_invalid;
            break;     
        case 40014:
            responseMessage = ResponseMessage.wx_access_token_invalid;
            break;           
        case 40029:
            responseMessage = ResponseMessage.wx_oauthCode_invalid;
            break;     
        case 40030:
            responseMessage = ResponseMessage.wx_refreshToken_invalid;
            break;   
        case 40031:
            responseMessage = ResponseMessage.wx_openidList_invalid;
            break; 
        case 40032:
            responseMessage = ResponseMessage.wx_openidListLength_invalid;
            break;   
        case 40033:
            responseMessage = ResponseMessage.wx_requestCharacter_invalid;
            break;      
        case 40035:
            responseMessage = ResponseMessage.wx_requestParam_invalid;
            break;   
        case 40038:
            responseMessage = ResponseMessage.wx_requestFormat_invalid;
            break; 
        case 40039:
            responseMessage = ResponseMessage.wx_urlLength_invalid;
            break;      
        case 41001:
            responseMessage = ResponseMessage.wx_accessToken_lack;
            break;   
        case 41002:
            responseMessage = ResponseMessage.wx_appid_lack;
            break;    
        case 41003:
            responseMessage = ResponseMessage.wx_refreshToken_lack;
            break;   
        case 41004:
            responseMessage = ResponseMessage.wx_secret_lack;
            break;     
        case 41008:
            responseMessage = ResponseMessage.wx_oauthCode_lack;
            break;     
        case 42001:
            responseMessage = ResponseMessage.wx_accessToken_timeout;
            break;   
        case 42002:
            responseMessage = ResponseMessage.wx_refreshToken_timeout;
            break;     
        case 42003:
            responseMessage = ResponseMessage.wx_oauthCode_timeout;
            break;    
        case 43001:
            responseMessage = ResponseMessage.wx_methodGet_required;
            break;   
        case 43002:
            responseMessage = ResponseMessage.wx_methodPost_required;
            break;     
        case 43003:
            responseMessage = ResponseMessage.wx_https_required;
            break;  
        case 46004:
            responseMessage = ResponseMessage.wx_user_not_exist;
            break;   
        case 47001:
            responseMessage = ResponseMessage.wx_json_xml_parse_error;
            break;     
        case 48001:
            responseMessage = ResponseMessage.wx_api_unauthorized;
            break;  
        case 61450:
            responseMessage = ResponseMessage.wx_dateFormat_error;
            break;   
        case 61500:
            responseMessage = ResponseMessage.wx_dateFormat_error;
            break;     
        case 61501:
            responseMessage = ResponseMessage.wx_dateAange_error;
            break;            
		default:
			responseMessage = ResponseMessage.unknown_error;
		}
		return false;
    }
}
