package com.meirengu.pay.dao;


import com.meirengu.pay.model.PaymentCommitBonus;
import com.meirengu.pay.vo.PaymentCommitBonusVo;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

public interface PaymentCommitBonusDao {
    int deleteByPrimaryKey(Integer id);

    int insert(PaymentCommitBonus record);

    void insertList(List<PaymentCommitBonus> list);

    List<PaymentCommitBonus> select(PaymentCommitBonusVo paymentCommitBonusVo);

    int update(PaymentCommitBonus record);

    int updateStatus(PaymentCommitBonus record);

    int updateIncome(PaymentCommitBonus record);

    BigDecimal sumMoney(@Param("itemId")Integer itemId,@Param("period")Integer period);

    BigDecimal itemSumMoney(@Param("itemId")Integer itemId);

    List<PaymentCommitBonus> selectMoney(@Param("itemId")Integer itemId,@Param("period")Integer period);

}