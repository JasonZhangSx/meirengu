package com.meirengu.webview.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.utils.HttpUtil;
import com.meirengu.webview.service.UserCenterService;
import com.meirengu.webview.utils.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户注册服务
 * @author 建新
 * @create 2017-05-31 11:25
 */
@Service
public class UserCenterServiceImpl implements UserCenterService{

    private Logger logger = LoggerFactory.getLogger(UserCenterServiceImpl.class);

    @Override
    public JSONObject getCheckCode(String mobile, String ip, String type) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobile);
        params.put("type", type);
        params.put("ip", ip);
        String url = ConfigUtil.getConfig("URI_CHECK_CODE");
        logger.info("UserCenterServiceImpl.getCheckCode http >> url:{}, params:{}", new Object[]{url, params});
        HttpUtil.HttpResult hr = null;
        try {
            hr = HttpUtil.doPostForm(url, params);
        } catch (Exception e) {
            logger.error("UserCenterServiceImpl.getCheckCode http exception << url:{},  params:{}, exception:{}", new Object[]{url, params, e});
        }
        if(hr.getStatusCode() == StatusCode.OK){
            JSONObject jsonObject = JSONObject.parseObject(hr.getContent());
            Integer code = (Integer) jsonObject.get("code");
            if(code == StatusCode.OK){
                JSONObject data = (JSONObject) jsonObject.get("data");
                logger.info("UserCenterServiceImpl.getCheckCode result success << url:{}, params:{}, data:{}", new Object[]{url, params, data});
                return jsonObject;
            }else {
                logger.info("UserCenterServiceImpl.getCheckCode result failure << url:{}, params:{}, code:{}", new Object[]{url, params, code});
                return jsonObject;
            }
        }else{
            logger.error("UserCenterServiceImpl.getCheckCode http failure << url:{},  params:{}, http_code:{}", new Object[]{url, params, hr.getStatusCode()});
        }
        return null;
    }

    @Override
    public JSONObject getInviteRecord(String userId, Integer page, Integer perPage) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("user_id", userId);
        params.put("page", String.valueOf(page));
        params.put("perPage", String.valueOf(perPage));
        String url = ConfigUtil.getConfig("URI_INVITER");
        logger.info("UserCenterServiceImpl.getInviteRecord http >> url:{}, params:{}", new Object[]{url, params});
        HttpUtil.HttpResult hr = null;
        try {
            hr = HttpUtil.doPostForm(url, params);
        } catch (Exception e) {
            logger.error("UserCenterServiceImpl.getInviteRecord http exception << url:{},  params:{}, exception:{}", new Object[]{url, params, e});
        }
        if(hr.getStatusCode() == StatusCode.OK){
            JSONObject jsonObject = JSONObject.parseObject(hr.getContent());
            Integer code = (Integer) jsonObject.get("code");
            if(code == StatusCode.OK){
                JSONObject data = (JSONObject) jsonObject.get("data");
                logger.info("UserCenterServiceImpl.getInviteRecord result success << url:{}, params:{}, data:{}", new Object[]{url, params, data});
                return jsonObject;
            }else {
                logger.info("UserCenterServiceImpl.getInviteRecord result failure << url:{}, params:{}, code:{}", new Object[]{url, params, code});
                return jsonObject;
            }
        }else{
            logger.error("UserCenterServiceImpl.getInviteRecord http failure << url:{},  params:{}, http_code:{}", new Object[]{url, params, hr.getStatusCode()});
        }
        return null;
    }

    @Override
    public JSONObject register(String mobile, String checkCode, String mobileInviter, int from, String ip) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobile);
        params.put("check_code", checkCode);
        params.put("mobile_inviter", mobileInviter);
        params.put("from", String.valueOf(from));
        params.put("ip", ip);
        String url = ConfigUtil.getConfig("URI_LOGIN");
        logger.info("UserCenterServiceImpl.register http >> url:{}, params:{}", new Object[]{url, params});
        HttpUtil.HttpResult hr = null;
        try {
            hr = HttpUtil.doPostForm(url, params);
        } catch (Exception e) {
            logger.error("UserCenterServiceImpl.register http exception << url:{},  params:{}, exception:{}", new Object[]{url, params, e});
        }
        if(hr.getStatusCode() == StatusCode.OK){
            JSONObject jsonObject = JSONObject.parseObject(hr.getContent());
            /*Integer code = (Integer) jsonObject.get("code");
            if(code == StatusCode.OK){
                JSONObject data = (JSONObject) jsonObject.get("data");
                logger.info("UserCenterServiceImpl.register result success << url:{}, params:{}, data:{}", new Object[]{url, params, data});
                return jsonObject;
            }else {
                logger.info("UserCenterServiceImpl.register result failure << url:{}, params:{}, code:{}", new Object[]{url, params, code});
                return jsonObject;
            }*/
            return jsonObject;
        }else{
            logger.error("UserCenterServiceImpl.register http failure << url:{},  params:{}, http_code:{}", new Object[]{url, params, hr.getStatusCode()});
        }
        return null;
    }
}
