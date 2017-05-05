package com.meirengu.uc.service.impl;

import com.meirengu.common.StatusCode;
import com.meirengu.service.impl.BaseServiceImpl;
import com.meirengu.uc.dao.InviterDao;
import com.meirengu.uc.model.Inviter;
import com.meirengu.uc.service.InviterService;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.HttpUtil.HttpResult;
import com.meirengu.utils.JacksonUtil;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.remoting.RemoteAccessException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huoyan403 on 3/23/2017.
 */
@Service
@EnableRetry
public class InviterServiceimpl extends BaseServiceImpl<Inviter> implements InviterService{

    private static final Logger logger = LoggerFactory.getLogger(InviterServiceimpl.class);

    @Autowired
    private InviterDao inviterDao;

    @Override
    public Inviter detail(Inviter inviter) {
        return inviterDao.detail(inviter);
    }

    @Override
    public void getReward(List<Map<String, Object>> list) {
        //拼接被邀请人id
        StringBuffer sb = new StringBuffer();
        for(int i = 0; i<list.size();i++){

            sb.append(String.valueOf(list.get(i).get("invitedUserId")));
            if(i<list.size()-1){
                sb.append(",");
            }
        }

        HttpResult hr = null;
        String url = ConfigUtil.getConfig("URI_GET_USER_REWARD");
        String urlAppend = url+"?userId="+ sb.toString();
        logger.info("InviterServiceimpl.send get >> uri :{} ", urlAppend);
        try {
            hr = HttpUtil.doGet(urlAppend);
        } catch (Exception e) {
            logger.error("InviterServiceimpl.send error >> uri:{}, exception:{}", new Object[]{urlAppend, e});
        }
        if(hr.getStatusCode() == StatusCode.OK){
            Map<String,Object> message = new HashedMap();
            message = JacksonUtil.readValue(hr.getContent(),Map.class);
            if(message!=null){
                Map invitationAmount = (Map)message.get("data");
                if(invitationAmount!=null){
                    List<Map<String,String>> invitationAmountList = (List<Map<String,String>>)invitationAmount.get("invitationAmount");
                   for (Map<String,String> map:invitationAmountList){
                       for(Map userInvite:list){
                            if(String.valueOf(map.get("userId")).equals(String.valueOf(userInvite.get("invitedUserId")))){
                                userInvite.put("reward",String.valueOf(map.get("principal")));
                            }
                       }
                   }
                }
            }
        }else{
            logger.error("InviterServiceimpl.back code >> params:{}, exception:{}");
        }
    }

    /**
     * delay 第一次重试时间
     * multiplier 重试累乘倍数  延时
     * maxAttempts 重试次数
     */
    @Override
    @Async
    @Retryable(value=RemoteAccessException.class,maxAttempts=3,backoff = @Backoff(delay = 5000,multiplier=1.5))
    public void noticePayment() {

        HttpResult hr = null;
        try {
            Map<String, String> params = new HashMap<String, String>();
            String url = ConfigUtil.getConfig("INVITE.INVESTMONEY.NOTICE");
            logger.info("InviterServiceimpl.send get >> uri :{}, params:{}", new Object[]{url});
            hr = HttpUtil.doPostForm(url,params);
        } catch (Exception e) {
            logger.info("InviterServiceimpl.send get throws Exception ",e.getMessage());
        }

        if(hr.getStatusCode()!= StatusCode.OK){
            logger.info("InviterServiceimpl.send get throws Exception ",hr.getStatusCode());
            throw new RemoteAccessException("通知支付系统批处理邀请分红异常");
        }
    }
    @Recover
    public void recover(RemoteAccessException e) {

        //此处可用来通知开发者 处理异常
        logger.info("通知支付系统批处理邀请分红异常  回调执行");
        logger.info(e.getMessage());
    }
}
