package com.meirengu.pay.dao;


import com.meirengu.pay.model.ChannelBank;

import java.util.List;

public interface ChannelBankDao {
    int deleteByPrimaryKey(Integer bankId);

    int insertSelective(ChannelBank record);

    List<ChannelBank> getChannelBank(ChannelBank channelBank);

    int updateByPrimaryKeySelective(ChannelBank record);
}