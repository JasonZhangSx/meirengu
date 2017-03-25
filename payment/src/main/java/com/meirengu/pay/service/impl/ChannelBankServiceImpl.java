package com.meirengu.pay.service.impl;

import com.meirengu.common.StatusCode;
import com.meirengu.pay.dao.ChannelBankDao;
import com.meirengu.pay.model.ChannelBank;
import com.meirengu.pay.service.ChannelBankService;
import com.meirengu.pay.utils.PaymentException;
import com.meirengu.pay.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 渠道支持银行管理Service
 * Author: haoyang.Yu
 * Create Date: 2017/3/24 19:24.
 */
@Service
public class ChannelBankServiceImpl extends BaseServiceImpl implements ChannelBankService {
    private final static Logger logger = LoggerFactory.getLogger(ChannelBankServiceImpl.class);
    @Autowired
    private ChannelBankDao paymentDao;
    @Override
    public String getChannelBank(String content) {
        Map<String,Object> map = new HashMap<>();
        ChannelBank channelBank = new ChannelBank();
        try {
            channelBank = (ChannelBank) super.execute(content,channelBank);
            logger.info("Request getChannelBank parameter:{}",channelBank.toString());
            List<ChannelBank> list = paymentDao.getChannelBank(channelBank);
            if (list==null){
                throw new PaymentException(StatusCode.CHANNEL_BANK_ERROR_SELECT_IS_NULL);
            }
            map.put("channelBank",channelBank);
            logger.info("getChannelBank prompt message:{}",StatusCode.codeMsgMap.get(StatusCode.CHANNEL_BANK_SUCCESS_SELECT));
            return ResultUtil.getResult(StatusCode.OK,map);
        } catch (Exception e) {
            logger.error("Capture getChannelBank ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.CHANNEL_BANK_ERROR_SELECT), e.getMessage());
            return ResultUtil.getResult(StatusCode.CHANNEL_BANK_ERROR_SELECT,null);
        }
    }

    @Override
    public String delete(String content) {
        return null;
    }

    @Override
    public String insert(String content) {
        return null;
    }

    @Override
    public String update(String content) {
        return null;
    }
}
