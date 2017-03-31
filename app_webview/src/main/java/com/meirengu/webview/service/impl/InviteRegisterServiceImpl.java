package com.meirengu.webview.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.HttpUtil.HttpResult;
import com.meirengu.webview.service.InviteRegisterService;
import com.meirengu.webview.utils.ConfigUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 邀请注册接口实现类
 *
 * @author Marvin
 * @create 2017-03-31 下午5:49
 */
@Service
public class InviteRegisterServiceImpl implements InviteRegisterService {

    private static final Logger logger = LoggerFactory.getLogger(InviteRegisterServiceImpl.class);

    @Override
    public JSONObject inviteRegister(String registerPhone, String inviterPhone, String from, String ip) {
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", registerPhone);
        params.put("mobile_inviter", inviterPhone);
        params.put("from", from);
        params.put("ip", ip);
        String url = ConfigUtil.getConfig("URI_REGISTER");
        logger.info("inviteRegister http >> url:{}, params:{}", new Object[]{url, params});
        HttpResult hr = null;
        try {
            hr = HttpUtil.doPostForm(url, params);
        } catch (Exception e) {
            logger.error("inviteRegister http exception << url:{},  params:{}, exception:{}", new Object[]{url, params, e});
        }
        if(hr.getStatusCode() == StatusCode.OK){
            JSONObject jsonObject = JSONObject.parseObject(hr.getContent());
            Integer code = (Integer) jsonObject.get("code");
            if(code == StatusCode.OK){
                JSONObject data = (JSONObject) jsonObject.get("data");
                logger.info("inviteRegister result success << url:{}, params:{}, data:{}", new Object[]{url, params, data});
                return jsonObject;
            }else {
                logger.info("inviteRegister result failure << url:{}, params:{}, code:{}", new Object[]{url, params, code});
                return jsonObject;
            }
        }else{
            logger.error("inviteRegister http failure << url:{},  params:{}, http_code:{}", new Object[]{url, params, hr.getStatusCode()});
        }
        return null;
    }
}
