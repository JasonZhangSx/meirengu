package com.meirengu.pay.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.pay.dao.PaymentCollectionListDao;
import com.meirengu.pay.dao.PaymentCollectionRecordDao;
import com.meirengu.pay.dao.PaymentCommitBonusDao;
import com.meirengu.pay.model.PaymentCollectionList;
import com.meirengu.pay.model.PaymentCollectionRecord;
import com.meirengu.pay.model.PaymentCommitBonus;
import com.meirengu.pay.service.PaymentCollectionListService;
import com.meirengu.pay.utils.HttpUtil;
import com.meirengu.pay.utils.ResultUtil;
import com.meirengu.pay.utils.check.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/5/15 15:20.
 */
@Service
public class PaymentCollectionListServiceImpl extends BaseServiceImpl implements PaymentCollectionListService {
    private final static Logger logger = LoggerFactory.getLogger(PaymentCollectionListServiceImpl.class);
    @Autowired
    private PaymentCommitBonusDao paymentCommitBonusDao;
    @Autowired
    private PaymentCollectionRecordDao paymentCollectionRecordDao;
    @Autowired
    private PaymentCollectionListDao paymentCollectionListDao;
    @Transactional
    @Override
    public String insert(String content) {
        PaymentCollectionList paymentCollectionList = new PaymentCollectionList();
        try {
            paymentCollectionList = (PaymentCollectionList) super.execute(content,paymentCollectionList);
            logger.info("Request insert parameter:{}",paymentCollectionList.toString());
            logger.info("insert Parameter check Start========>");
            Validator.getInstance().validate(paymentCollectionList);
            logger.info("insert Parameter check End<========");
            if (super.url==null){
                projectValue();
            }
            JSONArray itemSumMoney = (JSONArray) JSONObject.parseObject(HttpUtil.httpGet(url+tradeUrl+"/get_sum_amount_by_level_ids"+"?item_ids="+paymentCollectionList.getItemId(),null)).get("data");
            paymentCollectionList.setCompletedAmount(itemSumMoney.getJSONObject(0).getBigDecimal("sumOrderAmount"));
            paymentCollectionList.setPrincipal(itemSumMoney.getJSONObject(0).getBigDecimal("sumCostAmount"));
            paymentCollectionList.setInterest(paymentCommitBonusDao.itemSumMoney(paymentCollectionList.getItemId()));
            String itemDetail = HttpUtil.httpPostForm(super.url+"/cf/item/bank_commission?item_id="+paymentCollectionList.getItemId(),new HashMap<>(),"UTF-8");
            logger.info("Request ItemDetail result :{}",itemDetail);
            JSONObject item = (JSONObject) JSONObject.parseObject(itemDetail).get("data");
            Integer prepaidBonus = item.getInteger("prepaidBonus");
            JSONArray levelList = mySort((JSONArray) item.get("levelList"));
            JSONObject jsonObject = levelList.getJSONObject(0);
            BigDecimal sumStage = jsonObject.getBigDecimal("investmentPeriod").divide(jsonObject.getBigDecimal("shareBonusPeriod"));
            paymentCollectionList.setCollectionPeriod(sumStage.intValue());
            paymentCollectionList.setReceivedPeriod(prepaidBonus);
            paymentCollectionList.setSurplusPeriod(paymentCollectionList.getCollectionPeriod()-paymentCollectionList.getReceivedPeriod());
            paymentCollectionListDao.insert(paymentCollectionList);
            List<PaymentCollectionRecord> list = new ArrayList<>();
            for (int i=1;i<=sumStage.intValue();i++){
                PaymentCollectionRecord paymentCollectionRecord = new PaymentCollectionRecord();
                paymentCollectionRecord.setPaymentCollectionId(paymentCollectionList.getId());
                paymentCollectionRecord.setPartnerId(paymentCollectionList.getPartnerId());
                paymentCollectionRecord.setPartnerName(paymentCollectionList.getPartnerName());
                paymentCollectionRecord.setItemId(paymentCollectionList.getItemId());
                paymentCollectionRecord.setItemName(paymentCollectionList.getItemName());
                paymentCollectionRecord.setCollectionPeriod(i);
                BigDecimal sumMoney = paymentCommitBonusDao.sumMoney(paymentCollectionList.getItemId(),i);
                if (i>prepaidBonus){
                    paymentCollectionRecord.setCollectionType(2);
                    paymentCollectionRecord.setStatus(0);
                }else {
                    paymentCollectionRecord.setCollectionType(1);
                    paymentCollectionRecord.setActualAmount(sumMoney);
                    paymentCollectionRecord.setStatus(1);
                    paymentCollectionRecord.setCollectionTime(new Date());
                }
                paymentCollectionRecord.setShouldAmount(sumMoney);
                list.add(paymentCollectionRecord);
            }
            paymentCollectionRecordDao.insertList(list);
            logger.info("insert prompt message:{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COLLECTION_SUCCESS_INSERT));
            return ResultUtil.getResult(StatusCode.OK,null);
        } catch (Exception e) {
            logger.error("Capture getChannelBank ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COLLECTION_ERROR_INSERT), e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_COLLECTION_ERROR_INSERT,null);
        }
    }

    @Override
    public String select(PaymentCollectionList paymentCollectionList) {
        Map<String,Object> map = new HashMap<>();
        List<PaymentCollectionList> paymentCollectionLists = new ArrayList<PaymentCollectionList>();
        try {
            logger.info("Request select parameter:{}",paymentCollectionList.toString());
            paymentCollectionLists = paymentCollectionListDao.select(paymentCollectionList);
            map.put("paymentCollectionList",paymentCollectionLists);
            logger.info("select prompt message:{}",StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COLLECTION_SUCCESS_SELECT));
            return ResultUtil.getResult(StatusCode.OK,map);
        } catch (Exception e) {
            logger.error("Capture select ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COLLECTION_ERROR_SELECT), e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_COLLECTION_ERROR_SELECT,null);
        }
    }

    @Override
    public String selectRecord(PaymentCollectionRecord paymentCollectionRecord) {
        Map<String,Object> map = new HashMap<>();
        List<PaymentCollectionRecord> paymentCollectionRecords = new ArrayList<PaymentCollectionRecord>();
        try {
            logger.info("Request select parameter:{}",paymentCollectionRecord.toString());
            paymentCollectionRecords = paymentCollectionRecordDao.select(paymentCollectionRecord);
            map.put("paymentCollectionRecord",paymentCollectionRecords);
            logger.info("select prompt message:{}",StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COLLECTION_SUCCESS_SELECT));
            return ResultUtil.getResult(StatusCode.OK,map);
        } catch (Exception e) {
            logger.error("Capture select ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COLLECTION_ERROR_SELECT), e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_COLLECTION_ERROR_SELECT,null);
        }
    }

    @Override
    public String addRecord(String content) {
        PaymentCollectionRecord paymentCollectionRecord = new PaymentCollectionRecord();
        try {
            paymentCollectionRecord = (PaymentCollectionRecord) super.execute(content, paymentCollectionRecord);
            logger.info("Request addRecord parameter:{}", paymentCollectionRecord.toString());
            logger.info("addRecord Parameter check Start========>");
            Validator.getInstance().validate(paymentCollectionRecord);
            logger.info("addRecord Parameter check End<========");
            PaymentCollectionList paymentCollectionList = paymentCollectionListDao.selectById(paymentCollectionRecord.getPaymentCollectionId());
            if (url==null){
                projectValue();
            }
            if (paymentCollectionRecord.getInterestRate()!=null){
                String itemDetail = HttpUtil.httpPostForm(super.url+"/cf/item/bank_commission?item_id="+paymentCollectionList.getItemId(),new HashMap<>(),"UTF-8");
                logger.info("Request ItemDetail result :{}",itemDetail);
                JSONObject item = (JSONObject) JSONObject.parseObject(itemDetail).get("data");
                JSONArray levelList = (JSONArray) item.get("levelList");
                JSONObject levelDetail = levelList.getJSONObject(0);
                BigDecimal month =levelDetail.getBigDecimal("investmentPeriod").divide(levelDetail.getBigDecimal("shareBonusPeriod"));
                BigDecimal yearRate = new BigDecimal(paymentCollectionRecord.getInterestRate()).divide(new BigDecimal(100)).setScale(BigDecimal.ROUND_CEILING, BigDecimal.ROUND_HALF_UP);
                List<PaymentCommitBonus> paymentCommitBonusList = paymentCommitBonusDao.selectMoney(paymentCollectionList.getItemId(),paymentCollectionRecord.getCollectionPeriod());
                for (PaymentCommitBonus paymentCommitBonus : paymentCommitBonusList){
                    BigDecimal income = paymentCommitBonus.getIncome();
                    paymentCommitBonus.setIncome(paymentCommitBonus.getPrincipal().multiply(yearRate).setScale(BigDecimal.ROUND_CEILING,BigDecimal.ROUND_HALF_UP).divide(month,BigDecimal.ROUND_CEILING, BigDecimal.ROUND_HALF_EVEN));
                    paymentCommitBonus.setTotalAmount(paymentCommitBonus.getTotalAmount().subtract(income).add(paymentCommitBonus.getIncome()));
                    paymentCommitBonus.setYearRate(Integer.valueOf(paymentCollectionRecord.getInterestRate()));
                    paymentCommitBonusDao.updateIncome(paymentCommitBonus);
                }
                paymentCollectionRecord.setShouldAmount(paymentCommitBonusDao.sumMoney(paymentCollectionList.getItemId(),paymentCollectionRecord.getCollectionPeriod()));
            }
            paymentCollectionRecord.setItemId(paymentCollectionList.getItemId());
            paymentCollectionRecord.setStatus(1);
            paymentCollectionRecord.setCollectionTime(new Date());
            paymentCollectionRecordDao.updateStatus(paymentCollectionRecord);
            PaymentCollectionList paymentCollection = new PaymentCollectionList();
            paymentCollection.setId(paymentCollectionList.getId());
            paymentCollection.setInterest(paymentCommitBonusDao.itemSumMoney(paymentCollectionList.getItemId()));
            paymentCollectionListDao.update(paymentCollection);
            logger.info("addRecord prompt message:{}",StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COLLECTION_RECORD_SUCCESS_INSERT));
            return ResultUtil.getResult(StatusCode.OK,null);
        }catch (Exception e){
            logger.error("Capture addRecord ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COLLECTION_RECORD_ERROR_INSERT), e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_COLLECTION_RECORD_ERROR_INSERT,null);
        }
    }

    private JSONArray mySort(JSONArray arr) {
        JSONObject jsonObject = new JSONObject();
        boolean exchange = false;
        for (int i = 0; i < arr.size(); i++) {
            exchange = false;
            for (int j = arr.size() - 2; j >= i; j--) {
                if (arr.getJSONObject(j + 1).getInteger("investmentPeriod").compareTo(
                        arr.getJSONObject(j).getInteger("investmentPeriod")) >= 0) {
                    jsonObject =  arr.getJSONObject(j + 1);
                    arr.set(j + 1, arr.get(j));
                    arr.set(j, jsonObject);
                    exchange = true;
                }
            }
            if (!exchange)
                break;
        }
        return arr;
    }
}
