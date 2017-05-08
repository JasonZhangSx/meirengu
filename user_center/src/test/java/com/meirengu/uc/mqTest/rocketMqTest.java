package com.meirengu.uc.mqTest;

import com.meirengu.rocketmq.Consumer;
import com.meirengu.rocketmq.Producer;
import com.meirengu.utils.JacksonUtil;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by huoyan403 on 5/8/2017.
 */
public class rocketMqTest {

    private static final Logger logger = LoggerFactory.getLogger(rocketMqTest.class);

    @Test
    public void testMq() throws MQClientException, InterruptedException {

        Producer producer = new Producer();
        producer.setProducerGroup("UserProductGroup");
        producer.setNamesrvAddr("192.168.0.135:9876");
        String topic = "user";
        String tag = "createContract";

        SendResult sendResult = null;
        String key = "createContract" + new Random().nextInt(1000);
        try {

            Map<String,String> map = new HashMap<>();
            map.put("itemId","1");
            map.put("levelId","2");
            map.put("userId","1231231223");
            map.put("orderId","1231231224");

            Message msg = new Message(topic,tag, key, JacksonUtil.toJSon(map).getBytes());
            producer.init();
            DefaultMQProducer producer1 = producer.getDefaultMQProducer();
            sendResult = producer1.send(msg);
            logger.info("sendResult: {}, key: {}", sendResult, key);

        Consumer consumer = new Consumer();

        consumer.setNamesrvAddr("192.168.0.135:9876");
        consumer.setConsumerGroup("UserConsumerGroup");
        consumer.setTopic(topic);
        consumer.setTags(tag);
        consumer.init();

        } catch (Exception e) {
            logger.error("发送消息异常：{}", e);
            e.printStackTrace();
        }

        // 当消息发送失败时如何处理
        if (sendResult == null || sendResult.getSendStatus() != SendStatus.SEND_OK) {
            // TODO
            logger.error("发送消息失败 sendResult: {}, key: {} ", sendResult, key);
        }


    }

}
