package com.meirengu.uc.service.impl;

import com.meirengu.uc.dao.UserDao;
import com.meirengu.uc.dao.VerityDao;
import com.meirengu.uc.model.User;
import com.meirengu.uc.service.VerityService;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.HttpUtil.HttpResult;
import com.meirengu.utils.JacksonUtil;
import com.meirengu.utils.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by huoyan403 on 3/24/2017.
 */
@Service
public class VerityServiceImpl implements VerityService{

    @Autowired
    private VerityDao verityDao;
    @Autowired
    private UserDao userDao;
    private static final Logger logger = LoggerFactory.getLogger(VerityServiceImpl.class);

    @Override
    public Boolean selectPayAccountByUserId(Integer userId) {
        Boolean flag = false;
        HttpResult hr = null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId",userId+"");
        Map<String, String> params = new HashMap<String, String>();
        params.put("content", JacksonUtil.toJSon(map));
        String url = ConfigUtil.getConfig("URI_GET_USER_PAYACCOUNT");
        String urlAppend = url+"?content="+ URLEncoder.encode(JacksonUtil.toJSon(map));
        logger.info("VerityServiceImpl.send get >> uri :{}, params:{}", new Object[]{url, params});
        try {
            hr = HttpUtil.doGet(urlAppend);
        } catch (Exception e) {
            logger.error("VerityServiceImpl.send error >> params:{}, exception:{}", new Object[]{params, e});
        }
        if(hr.getStatusCode()==200){
            Map<String,Object> account = new HashedMap();
            account = JacksonUtil.readValue(hr.getContent(),Map.class);
            if(account!=null){
                Map mapData = (Map)account.get("data");
                if(mapData!=null){
                    Map accountUser = (Map) mapData.get("account");
                    if(accountUser!=null){
                        String password = (String) accountUser.get("password");
                        if(!StringUtil.isEmpty(password)){
                            flag = true;
                        }
                    }
                }
            }
        }else{
            logger.error("VerityServiceImpl.back code >> params:{}, exception:{}", hr.getStatusCode(),hr.getContent());
        }
        return flag;
    }

    @Override
    public Integer verityUser(Integer userId, String bankCode, String bankIdcard, String bankPhone,
                              String idcard, String realname, String password) {

        HttpResult hr = null;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId",userId);
        map.put("bankCode",bankCode);
        map.put("bankIdcard",bankIdcard);
        map.put("bankPhone",bankPhone);
        map.put("idcard",idcard);
        map.put("realname",realname);
        map.put("password",password);
        Map<String, String> params = new HashMap<String, String>();
        params.put("content", JacksonUtil.toJSon(map));
        String url = ConfigUtil.getConfig("URI_VETIFY_USER_PAYACCOUNT");
        logger.info("VerityServiceImpl.send get >> uri :{}, params:{}", new Object[]{url, params});
        try {
            hr = HttpUtil.doPostForm(url,params);
        } catch (Exception e) {
            logger.error("VerityServiceImpl.send error >> params:{}, exception:{}", new Object[]{params, e});
        }
        if(hr.getStatusCode()==200){
            Map<String,Object> account = new HashedMap();
            account = JacksonUtil.readValue(hr.getContent(),Map.class);
            if("".equals(map.get(4000))){
                User user = new User();
                user.setUserId(userId);
                user.setRealname(realname);
                user.setIdCard(idcard);
                user.setBankIdCard(bankIdcard);
                user.setBankCode(bankCode);
                user.setBankPhone(bankPhone);
                user.setIsAuth(1);
                userDao.update(user);
            }
        }else{
            logger.error("VerityServiceImpl.back code >> params:{}, exception:{}", hr.getStatusCode(),hr.getContent());
        }
        return null;
    }
}
