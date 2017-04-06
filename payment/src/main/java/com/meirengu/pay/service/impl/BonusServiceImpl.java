package com.meirengu.pay.service.impl;

import com.meirengu.common.StatusCode;
import com.meirengu.pay.dao.PaymentAccountDao;
import com.meirengu.pay.model.PaymentAccount;
import com.meirengu.pay.service.BonusService;
import com.meirengu.pay.utils.ResultUtil;
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

    @Transactional
    @Override
    public String invite() {
        List<String[]> list = new ArrayList<>();
        InputStreamReader read = null;
        try {
            if (super.inviteUrl==null){
                projectValue();
            }
            read = new InputStreamReader(
                    new FileInputStream(super.inviteUrl+"user."+ DateUtils.getCurrentDate()+".txt"),"utf-8");
            BufferedReader bufferedReader = new BufferedReader(read);
            for (String string : Arrays.asList(bufferedReader.readLine().split("\\|"))){
                String[] str = string.split(",");
                BigDecimal bigDecimal = new BigDecimal(str[str.length-1]);
                BigDecimal money = bigDecimal.multiply(BigDecimal.valueOf(0.005)).setScale(BigDecimal.ROUND_CEILING,BigDecimal.ROUND_HALF_UP);
                for (int i=0;i<str.length-1;i++){
                    PaymentAccount paymentAccount = paymentAccountDao.selectByUserId(Integer.valueOf(str[i]));
                    paymentAccountDao.updateBalance(paymentAccount.getAccountId(),paymentAccount.getAccountBalance().add(money));
                }
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return ResultUtil.getResult(StatusCode.OK,null);
    }
}
