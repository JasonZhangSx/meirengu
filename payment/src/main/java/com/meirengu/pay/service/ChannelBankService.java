package com.meirengu.pay.service;

import com.meirengu.pay.model.ChannelBank;

import java.util.List;

/**
 * 渠道支持银行管理Service
 * Author: haoyang.Yu
 * Create Date: 2017/3/24 19:21.
 */
public interface ChannelBankService {
    String getChannelBank(String bankCode);

    String delete(String content);

    String insert(String content);

    String update(String content);
}
