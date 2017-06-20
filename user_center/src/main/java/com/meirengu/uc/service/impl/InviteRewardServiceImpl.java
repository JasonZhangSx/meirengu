package com.meirengu.uc.service.impl;

import com.meirengu.common.RedisClient;
import com.meirengu.common.StatusCode;
import com.meirengu.service.impl.BaseServiceImpl;
import com.meirengu.uc.dao.InviterDao;
import com.meirengu.uc.model.Inviter;
import com.meirengu.uc.service.InviteRewardService;
import com.meirengu.uc.service.InviterService;
import com.meirengu.uc.utils.ConfigUtil;
import com.meirengu.utils.*;
import com.meirengu.utils.HttpUtil.HttpResult;
import org.apache.commons.io.IOUtils;
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

import java.util.*;

/**
 * Created by huoyan403 on 3/23/2017.
 */
@Service
@EnableRetry
public class InviteRewardServiceImpl extends BaseServiceImpl<Inviter> implements InviteRewardService {

    private static final Logger logger = LoggerFactory.getLogger(InviteRewardServiceImpl.class);

    @Autowired
    private InviterDao inviterDao;
    @Autowired
    private RedisClient redisClient;
    @Autowired
    private InviterService inviterService;

    @Override
    public Boolean inviteReward(String fileName,String filePath) {
        try {
            //oss配置信息  从oss读取文件
            String contractFolderName = ConfigUtil.getConfig("contractFolderName");
            String endpoint = ConfigUtil.getConfig("endpoint");
            String accessKeyId = ConfigUtil.getConfig("accessKeyId");
            String accessKeySecret = ConfigUtil.getConfig("accessKeySecret");
            String bucketName = ConfigUtil.getConfig("bucketName");
            String callback = ConfigUtil.getConfig("callbackUrl");


            List<String[]> list = new ArrayList<>();
            OSSFileUtils fileUtils = new OSSFileUtils(endpoint, accessKeyId, accessKeySecret, bucketName, callback);
            String order = IOUtils.toString(fileUtils.download(filePath, fileName), "UTF-8");
            List<Map<String, String>> investInfo = JacksonUtil.readValue(order, List.class);

            // 根据用户id 获取邀请人信息 判断是否为空
            if (investInfo.size() != 0) {
                for (Map map : investInfo) {
                    String userId = String.valueOf(map.get("userId"));
                    String investMoney = String.valueOf(map.get("orderAmount"));
                    String type = String.valueOf(map.get("itemType"));

                    Inviter inviter = new Inviter();
                    inviter.setInvitedUserId(Integer.parseInt(userId + ""));
                    inviter = inviterService.detail(inviter);
                    if (inviter != null && !StringUtil.isEmpty(inviter.getId())) {
                        // 根据注册时间  当前时间 校验是否有效期
                        Date registerTime = inviter.getRegisterTime();
                        int i = DateAndTime.dateDiff("dd", DateAndTime.convertDateToString(registerTime, "yyyy-MM-dd HH:mm:ss"),
                                DateAndTime.convertDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
                        // 封装入结构 写入文件
                        if (i <= 34) {
                            String[] arr = new String[3];
//                                arr[0] = String.valueOf(userId);//被邀请人id
                            arr[0] = inviter.getUserId() + "";//邀请人id
                            arr[1] = String.valueOf(investMoney);//投资金额
                            arr[2] = String.valueOf(type);
                            list.add(arr);
                        }
                    }
                }
            }
            //如果没有数据直接return
            if (list.size() == 0) {
                return false;
            } else {
                //大阳写的文件格式
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < list.size(); i++) {
                    String[] arr2 = list.get(i);
                    for (int index = 0; index < arr2.length; index++) {
                        sb.append(arr2[index]);
                        if (index < arr2.length - 1) {
                            sb.append(",");
                        }
                    }
                    if (i < list.size() - 1) {
                        sb.append("|");
                    }
                }
                String foldName = "user";
                fileName = "user." + DateUtils.getCurrentDate() + ".txt";
                OSSFileUtils fileUpload = new OSSFileUtils(endpoint, accessKeyId, accessKeySecret, bucketName, callback);
                fileUpload.upload(sb.toString(), fileName, foldName);
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
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
            logger.info("InviteRewardServiceImpl.send get >> uri :{}, params:{}", new Object[]{url});
            hr = HttpUtil.doPostForm(url,params);
            if(hr != null && hr.getStatusCode() == StatusCode.OK){
                redisClient.setObject("noticePayment."+ DateUtils.getCurrentDate(),"SUCCESS",86400);
            }

        } catch (Exception e) {
            logger.info("InviteRewardServiceImpl.send get throws Exception ",e.getMessage());
        }

        if(hr.getStatusCode()!= StatusCode.OK){
            logger.info("InviteRewardServiceImpl.send get throws Exception ",hr.getStatusCode());
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
