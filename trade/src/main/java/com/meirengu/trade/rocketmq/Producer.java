package com.meirengu.trade.rocketmq;


import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;

public class Producer {

    private final Logger logger = LoggerFactory.getLogger(Producer.class);

    private DefaultMQProducer defaultMQProducer;
    private String producerGroup;
    private String namesrvAddr;

    /**
     * Spring bean init-method
     */
    public void init() throws MQClientException {
        // 参数信息
        logger.info("DefaultMQProducer initialize!");
        logger.info(producerGroup);
        logger.info(namesrvAddr);

        // 初始化
        defaultMQProducer = new DefaultMQProducer(producerGroup);
        defaultMQProducer.setNamesrvAddr(namesrvAddr);
        defaultMQProducer.setInstanceName(String.valueOf(System.currentTimeMillis()));

        defaultMQProducer.start();
        logger.info("DefaultMQProudcer start success!");

    }

    /**
     * Spring bean destroy-method
     */
    public void destroy() {
        defaultMQProducer.shutdown();
    }

    public DefaultMQProducer getDefaultMQProducer() {
        return defaultMQProducer;
    }

    // ---------------setter -----------------

    public void setProducerGroup(String producerGroup) {
        this.producerGroup = producerGroup;
    }

    public void setNamesrvAddr(String namesrvAddr) {
        this.namesrvAddr = namesrvAddr;
    }

}
