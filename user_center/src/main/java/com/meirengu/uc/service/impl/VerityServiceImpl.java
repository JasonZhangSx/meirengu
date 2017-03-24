package com.meirengu.uc.service.impl;

import com.meirengu.uc.dao.VerityDao;
import com.meirengu.uc.service.VerityService;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.HttpUtil.HttpResult;
import com.meirengu.utils.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by huoyan403 on 3/24/2017.
 */
@Service
public class VerityServiceImpl implements VerityService{

    @Autowired
    private VerityDao verityDao;
    private static final Logger logger = LoggerFactory.getLogger(VerityServiceImpl.class);

    @Override
    public Map<String, Object> selectPayAccountByUserId(Integer userId) {

        HttpResult hr = null;
        Map<String, String> map = new HashMap<String, String>();
        map.put("userId",userId+"");
        Map<String, String> params = new HashMap<String, String>();
        params.put("content", JacksonUtil.toJSon(map));
        String url = ConfigUtil.getConfig("URI_GET_USER_PAYACCOUNT");
        logger.info("VerityServiceImpl.send get >> uri :{}, params:{}", new Object[]{url, params});
        try {
            hr = HttpUtil.doGet(url,params);
        } catch (Exception e) {
            logger.error("CheckCodeServiceImpl.send error >> params:{}, exception:{}", new Object[]{params, e});
        }
        //return hr.getContent();

        return null;
    }
}
