package com.meirengu.mall.service.impl;

import com.meirengu.mall.dao.PaymentDao;
import com.meirengu.mall.model.Payment;
import com.meirengu.mall.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * 支付服务实现层
 * @author 建新
 * @create 2017-02-20 11:28
 */
@Service
@Transactional(readOnly = true)
public class PaymentServiceImpl implements PaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentServiceImpl.class);

    @Autowired
    private PaymentDao paymentDao;

    @Override
    @Transactional(readOnly = false)
    public int insert(Payment payment) {
        try {
            int result = paymentDao.insert(payment);
            if(result > 0){
                return 1;
            }else {
                return 0;
            }
        }catch (RuntimeException e){
            LOGGER.error(">> insert payment throw error. it's {}", e);
            return 2;
        }
    }

    @Override
    @Transactional(readOnly = false)
    public int update(Payment payment) {
        try {
            int result = paymentDao.update(payment);
            if(result > 0){
                return 1;
            }else {
                return 0;
            }
        }catch (RuntimeException e){
            LOGGER.error(">> update payment throw error. it's {}", e);
            return 2;
        }
    }
}
