package com.meirengu.rocketmq;


import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.List;
@Component
public class Consumer {

    private final Logger logger = LoggerFactory.getLogger(Consumer.class);

    private DefaultMQPushConsumer defaultMQPushConsumer;
    @Value("${rocketmq.consumer.group}")
    private String consumerGroup;
    @Value("${rocketmq.namesrv.addr}")
    private String namesrvAddr;
    @Value("${rocketmq.subscribe.topic}")
    private String topic;
    @Value("${rocketmq.subscribe.tags}")
    private String tags;

    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Spring bean init-method
     */
    @PostConstruct
    public void init() throws InterruptedException, MQClientException {

        // 参数信息
        logger.info("DefaultMQPushConsumer initialize!");
        logger.info(consumerGroup);
        logger.info(namesrvAddr);

        // 一个应用创建一个Consumer，由应用来维护此对象，可以设置为全局对象或者单例<br>
        // 注意：ConsumerGroupName需要由应用来保证唯一
        defaultMQPushConsumer = new DefaultMQPushConsumer(consumerGroup);
        defaultMQPushConsumer.setNamesrvAddr(namesrvAddr);

        // 订阅指定MyTopic下tags等于MyTag
        defaultMQPushConsumer.subscribe(topic, tags);

        // 设置Consumer第一次启动是从队列头部开始消费还是队列尾部开始消费<br>
        // 如果非第一次启动，那么按照上次消费的位置继续消费
        defaultMQPushConsumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);

        // 设置为集群消费(区别于广播消费)
        defaultMQPushConsumer.setMessageModel(MessageModel.CLUSTERING);

        defaultMQPushConsumer.registerMessageListener(new MessageListenerConcurrently() {

            // 默认msgs里只有一条消息，可以通过设置consumeMessageBatchMaxSize参数来批量接收消息
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {

                MessageExt msg = msgs.get(0);
                logger.info(msg.toString());
                // TODO 执行Topic的消费逻辑
                try {
                    applicationContext.publishEvent(new RocketmqEvent(msg, defaultMQPushConsumer));
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("comsumer消费异常：{}", e.getMessage());
                    if(msg.getReconsumeTimes()<=3){//重复消费3次
                        //TODO 进行日志记录
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    } else {
                        //TODO 消息消费失败，进行日志记录
                        logger.error("comsumer消费失败：{}", msg.toString());
                    }
                }
                // 如果没有return success ，consumer会重新消费该消息，直到return success
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(5000);//延迟5秒再启动，主要是等待spring事件监听相关程序初始化完成，否则，回出现对RocketMQ的消息进行消费后立即发布消息到达的事件，然而此事件的监听程序还未初始化，从而造成消息的丢失
                    /**
                     * Consumer对象在使用之前必须要调用start初始化，初始化一次即可<br>
                     */
                    try {
                        defaultMQPushConsumer.start();
                    } catch (Exception e) {
                        logger.info("DefaultMQPushConsumer start failure!");
                        e.printStackTrace();
                    }

                    logger.info("DefaultMQPushConsumer start success!");

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        }).start();


    }

    /**
     * Spring bean destroy-method
     */
    @PreDestroy
    public void destroy() {
        defaultMQPushConsumer.shutdown();
    }

    // ----------------- setter --------------------

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

    public void setConsumerGroup(String consumerGroup) {
        this.consumerGroup = consumerGroup;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
