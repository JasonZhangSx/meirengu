package com.meirengu.rocketmq;


import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component(value = "Producer")
@PropertySource("classpath:rocketmq.properties")
public class Producer {

    private final Logger logger = LoggerFactory.getLogger(Producer.class);

    private DefaultMQProducer defaultMQProducer;
    @Value("${rocketmq.producer.group}")
    private String producerGroup;
    @Value("${rocketmq.namesrv.addr}")
    private String namesrvAddr;

    /**
     * Spring bean init-method
     */
    @PostConstruct
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
    @PreDestroy
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
