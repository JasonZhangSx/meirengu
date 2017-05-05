package com.meirengu.uc.service.impl;

import com.alibaba.fastjson.JSON;
import com.meirengu.common.StatusCode;
import com.meirengu.model.Result;
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
        if( hr!=null && hr.getStatusCode() == StatusCode.OK){
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
            logger.info("VerityServiceImpl.selectPayAccountByUserId connected refused :{}");
        }
        return flag;
    }

    @Override
    public Result verityUser(Integer userId, String bankCode, String bankIdcard, String bankPhone,
                             String idcard, String realname, String password, Integer investConditions) {
        HttpResult hr = null;
        Integer result = 0;
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("bankNo",bankIdcard);
        map.put("mobile",bankPhone);
        map.put("identityNumber",idcard);
        map.put("realName",realname);
        Map<String, String> params = new HashMap<String, String>();
        params.put("content", JacksonUtil.toJSon(map));
        String url = ConfigUtil.getConfig("URI_VETIFY_USER_PAYACCOUNT");
        logger.info("VerityServiceImpl.send get >> uri :{}, params:{}", new Object[]{url, params});
        try {
            hr = HttpUtil.doPostForm(url,params);
        } catch (Exception e) {
            logger.error("VerityServiceImpl.send error >> params:{}, exception:{}", new Object[]{params, e});
        }
        if(hr!=null && hr.getStatusCode()==StatusCode.OK){
            Map<String,Object> account = new HashedMap();
            account = JacksonUtil.readValue(hr.getContent(),Map.class);
            if(StatusCode.OK==(Integer) account.get("code")){
                User user = new User();
                user.setUserId(userId);
                user.setRealname(realname);
                user.setIdCard(idcard);
                user.setBankIdCard(bankIdcard);
                user.setBankCode(bankCode);
                user.setBankPhone(bankPhone);
                user.setInvestConditions(investConditions);
                user.setIsAuth(1);
                userDao.update(user);

                Map<String, String> payAccount = new HashMap<String, String>();
                payAccount.put("accountName",realname);
                payAccount.put("userId",userId+"");
                Map<String, String> paramsModify = new HashMap<String, String>();
                paramsModify.put("content", JacksonUtil.toJSon(payAccount));
                String urlModify = ConfigUtil.getConfig("URI_MODIFY_USER_PAYACCOUNT");
                hr = HttpUtil.doPostForm(urlModify,paramsModify);
                if(hr.getStatusCode() != StatusCode.OK){
                    hr = HttpUtil.doPostForm(urlModify,paramsModify);
                }
                return this.setResult((Integer) account.get("code"),null,(String) account.get("msg"));
            }else{
                Map data = (Map) account.get("data");
                return this.setResult((Integer) account.get("code"),null,(String) data.get("ErrorMsg"));
            }
        }else{
            logger.info("VerityServiceImpl.send failed ！ >>", hr.getContent());
        }
        return this.setResult(StatusCode.RETRIEVE_PAYMENT_GET_MESSAGE_FAILED, null, StatusCode.codeMsgMap.get(StatusCode.RETRIEVE_PAYMENT_GET_MESSAGE_FAILED));
    }




    /**
     * 封装返回数据对象
     * @param code 返回状态码
     * @param data 返回数据集合
     * @param msg 返回状态消息
     * @return
     */
    public Result setResult(int code, Object data, String msg){
        Result result = new Result();
        result.setCode(code);
        result.setMsg(msg);
        if (code == StatusCode.OK && (data != null && !"".equals(data))){
            result.setData(data);
        }else {
            //result.setData("");
        }
        logger.info("Request getResponse: {}", JSON.toJSON(result));
        return result;
    }
}
