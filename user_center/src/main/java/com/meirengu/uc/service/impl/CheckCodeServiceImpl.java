package com.meirengu.uc.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.uc.common.Constants;
import com.meirengu.uc.dao.CheckCodeDao;
import com.meirengu.uc.model.CheckCode;
import com.meirengu.uc.service.CheckCodeService;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.HttpUtil.HttpResult;
import org.apache.commons.lang3.RandomUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证码服务实现类
 *
 * @author Marvin
 * @create 2017-01-12 下午7:24
 */
@Service
public class CheckCodeServiceImpl implements CheckCodeService {

    private static final Logger logger = LoggerFactory.getLogger(CheckCodeServiceImpl.class);

    @Autowired
    CheckCodeDao checkCodeDao;

    @Override
    public int generate() {
        return RandomUtils.nextInt(100000,999999);
    }

    @Override
    public HttpResult send(String mobile, int code, String ip) {
        HttpResult hr = null;
        Map<String, String> params = new HashMap<String, String>();
        params.put("mobile", mobile);
        params.put("text", Constants.SMS_TEMPLATE_CHECKCODE.replace("#code#", String.valueOf(code)));
        params.put("uid", String.valueOf(code));
        params.put("ip", ip);
        String url = ConfigUtil.getConfig("URI_CHECKCODE_SEND");
        logger.info("CheckCodeServiceImpl.send post >> uri :{}, params:{}", new Object[]{url, params});
        try {
            hr = HttpUtil.doPost(url,params);
        } catch (Exception e) {
            logger.error("CheckCodeServiceImpl.send error >> params:{}, exception:{}", new Object[]{params, e});
        }
        return hr;
    }

    @Override
    public int create(CheckCode checkCode) {
        return checkCodeDao.create(checkCode);
    }

    @Override
    public int update(CheckCode checkCode) {
        return checkCodeDao.update(checkCode);
    }

    @Override
    public CheckCode retrieve(String mobile, int code) {
        Map params = new HashMap();
        params.put("mobile", mobile);
        params.put("code", code);
        return checkCodeDao.retrieve(params);
    }
}
