package com.meirengu.uc.mqTest;

import com.meirengu.model.Result;
import com.meirengu.rocketmq.Producer;
import com.meirengu.utils.JacksonUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;
import java.util.Map;

/**
 * Created by huoyan403 on 5/8/2017.
 */
public class inviterTest {


    private static final Logger logger = LoggerFactory.getLogger(inviterTest.class);

    @Autowired
    private Producer producer;
    //测试方式 把下面方法 随便复制一个controller  用http去访问
    @RequestMapping(value = "/sendMessage", method = RequestMethod.GET)
    public Result sendMessage() throws Exception{
        Map<String,Object> map = new HashedMap();

        map.put("invitedUserId","123123123");
        map.put("invitedUserPhone","13207603761");
        map.put("investTime",new Date());
        Message msg = new Message("user", "editInviter", JacksonUtil.toJSon(map).getBytes());
        SendResult sendResult = null;
        try {
            sendResult = producer.getDefaultMQProducer().send(msg);
            logger.info(sendResult+"");
        } catch (MQClientException e) {
            logger.error(e.getMessage() + String.valueOf(sendResult));
        } catch (Exception e) {
            e.printStackTrace();
        }

        // 当消息发送失败时如何处理
        if (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK) {
            // TODO
        }
        return new Result();
    }
}
