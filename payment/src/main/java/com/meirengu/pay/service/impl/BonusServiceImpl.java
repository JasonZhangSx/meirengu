package com.meirengu.pay.service.impl;

import com.meirengu.common.StatusCode;
import com.meirengu.pay.dao.PaymentAccountDao;
import com.meirengu.pay.dao.PaymentInvitationBonusDao;
import com.meirengu.pay.model.PaymentAccount;
import com.meirengu.pay.model.PaymentInvitationBonus;
import com.meirengu.pay.service.BonusService;
import com.meirengu.pay.utils.HttpUtil;
import com.meirengu.pay.utils.ResultUtil;
import com.meirengu.utils.DateAndTime;
import com.meirengu.utils.DateUtils;
import org.dom4j.DocumentException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/4/5 18:34.
 */
@Service
public class BonusServiceImpl extends BaseServiceImpl implements BonusService {
    @Autowired
    private PaymentAccountDao paymentAccountDao;
    @Autowired
    private PaymentInvitationBonusDao paymentInvitationBonusDao;
    @Transactional
    @Override
    public String invite() {
        List<String[]> list = new ArrayList<>();
        InputStreamReader read = null;
        try {
            if (super.inviteUrl==null){
                projectValue();
            }
//            read = new InputStreamReader(
//                    new FileInputStream(super.inviteUrl+"user."+ DateUtils.getCurrentDate()+".txt"),"utf-8");
//            BufferedReader bufferedReader = new BufferedReader(read);
//            for (String string : Arrays.asList(bufferedReader.readLine().split("\\|"))){
            String content = HttpUtil.httpGet(super.inviteUrl+"user."+DateUtils.getCurrentDate()+".txt","utf-8");
            for (String string : content.split("\\|")){
                String[] str = string.split(",");
                if (!str[2].equals("1")){
                    BigDecimal bigDecimal = new BigDecimal(str[1]);
                    BigDecimal money = bigDecimal.multiply(BigDecimal.valueOf(0.05)).setScale(BigDecimal.ROUND_CEILING,BigDecimal.ROUND_HALF_UP);
                    for (int i=0;i<str.length-2;i++){
                        Integer userId = Integer.valueOf(str[i]);
                        PaymentAccount paymentAccount = paymentAccountDao.selectByUserId(userId);
                        paymentAccountDao.updateBalance(paymentAccount.getAccountId(),paymentAccount.getAccountBalance().add(money));
                        PaymentInvitationBonus paymentInvitationBonus = new PaymentInvitationBonus();
                        paymentInvitationBonus.setUserId(userId);
                        paymentInvitationBonus.setPrincipal(money);
                        paymentInvitationBonus.setInvestPrincipal(bigDecimal);
                        paymentInvitationBonus.setInvestmentTime(DateAndTime.addDay(-4));
                        paymentInvitationBonusDao.insertSelective(paymentInvitationBonus);
                    }
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return ResultUtil.getResult(StatusCode.OK,null);
    }
}
