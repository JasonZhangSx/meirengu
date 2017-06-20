package com.meirengu.uc.controller;

import com.meirengu.common.RedisClient;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.model.Result;
import com.meirengu.rocketmq.RocketmqEvent;
import com.meirengu.uc.service.InviteRewardService;
import com.meirengu.utils.DateUtils;
import com.meirengu.utils.JacksonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Created by huoyan403 on 4/1/2017.
 */
@RestController
@RequestMapping("inviteReward")
public class InviteRewardController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(InviteRewardController.class);


    @Autowired
    private InviteRewardService inviterRewardService;
    @Autowired
    private RedisClient redisClient;


    @RequestMapping(value = "notify",method = RequestMethod.GET)
    public Result notify(@RequestParam(value="file_name", required = true) String fileName,
                         @RequestParam(value="file_path", required = true) String filePath) {
        try {

            if(redisClient.existsObject("noticePayment."+ DateUtils.getCurrentDate())){
                logger.info("InviteRewardController.notify 重复调用！");
                return setResult(StatusCode.OK,null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }
            if(inviterRewardService.inviteReward(fileName,filePath)){
                // 去通知支付系统  异步 重试
                inviterRewardService.noticePayment();
            }
            return setResult(StatusCode.OK,null, StatusCode.codeMsgMap.get(StatusCode.OK));
        }catch (Exception e){
            logger.info("AddressController.redis get token result:{}",e.getMessage());
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /** rocketMQ接收消息  */
    @EventListener(condition = "#event.topic=='user' && #event.tag=='inviteRewardNotify'")
    public void inviteRewardNotify(RocketmqEvent event) throws Exception {
        logger.info("从rocketMQ 接收批处理分红消息 inviteRewardNotify event :{} ",event.getMsg());

        String message = event.getMsg();
        Map<String,Object> map = (Map<String,Object>) JacksonUtil.readValue(message,Map.class);

        String fileName = String.valueOf(map.get("file_name"));
        String filePath = String.valueOf(map.get("file_path"));

        Result result = this.notify(fileName,filePath);
        logger.info("rocketMQ 接收批处理分红消息 inviteRewardNotify result :{} ",result);
        if(result.getCode() != StatusCode.OK){
            throw new Exception("邀请分红批处理 处理失败! ");
        }
    }
}
