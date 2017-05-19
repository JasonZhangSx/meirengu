package com.meirengu.pay.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.pay.model.PaymentCollectionList;
import com.meirengu.pay.service.PaymentCollectionListService;
import com.meirengu.pay.utils.HttpUtil;
import com.meirengu.pay.utils.ResultUtil;
import com.meirengu.pay.utils.check.Validator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.*;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/5/15 15:20.
 */
public class PaymentCollectionListServiceImpl extends BaseServiceImpl implements PaymentCollectionListService {
    private final static Logger logger = LoggerFactory.getLogger(PaymentCollectionListServiceImpl.class);
    @Override
    public String insert(String content) {
        PaymentCollectionList paymentCollectionList = new PaymentCollectionList();
        try {
            paymentCollectionList = (PaymentCollectionList) super.execute(content,paymentCollectionList);
            logger.info("Request insert parameter:{}",paymentCollectionList.toString());
            logger.info("insert Parameter check Start========>");
            Validator.getInstance().validate(paymentCollectionList);
            logger.info("insert Parameter check End<========");
            String itemDetail = HttpUtil.httpPostForm(super.url+"/cf/item/bank_commission?item_id="+paymentCollectionList.getItemId(),new HashMap<>(),"UTF-8");
            logger.info("Request ItemDetail result :{}",itemDetail);
            JSONObject item = (JSONObject) JSONObject.parseObject(itemDetail).get("data");
            JSONArray levelList = (JSONArray) item.get("levelList");
            StringBuffer levelId = new StringBuffer();
            String level;
            Map<String,String> interestRate = new HashMap<>();
            List<BigDecimal> stage = new ArrayList<>();
            for(int i=0;i<levelList.size();i++){
                level = String.valueOf(levelList.getJSONObject(i).get("levelId"));
                //0->分红利率，1->投资期限，以月为单位，2->分红周期（多少月回款一次）
//                interestRate.put(level,String.valueOf(levelList.getJSONObject(i).get("yearRate"))+","+String.valueOf(levelList.getJSONObject(i).get("investmentPeriod"))+","+String.valueOf(levelList.getJSONObject(i).get("shareBonusPeriod")));
                stage.add(new BigDecimal(levelList.getJSONObject(i).get("investmentPeriod").toString()).divideToIntegralValue(new BigDecimal(levelList.getJSONObject(i).get("shareBonusPeriod").toString())));
                if (i==levelList.size()-1){
                    levelId.append(level);
                }else {
                    levelId.append(level+",");
                }
            }
            String levelSumAmount = HttpUtil.httpGet(super.url+"/trade/order/get_sum_amount_by_level_ids?level_ids="+levelId.toString(),"UTF-8");
            logger.info("Request levelSumAmount result :{}",levelSumAmount);
            JSONArray sumAmount = (JSONArray) JSONObject.parseObject(itemDetail).get("data");
            Map<String,String> amount = new HashMap<>();
            for (int i=0;i<sumAmount.size();i++){
                JSONObject levelAmount = sumAmount.getJSONObject(i);
                amount.put(levelAmount.getString("itemLevelId"),levelAmount.getString("sumOrderAmount"));
            }
            for (int i=0;i<mySort(stage).get(0).intValue();i++){

            }
            BigDecimal prepaidBonus = new BigDecimal(JSONObject.parseObject(itemDetail).get("prepaidBonus").toString());
//            for (String key : interestRate.keySet()){
//                String[] rate = String.valueOf(interestRate.get(key)).split(",");
//                BigDecimal yearRate = new BigDecimal(rate[0]).divide(new BigDecimal(100)).setScale(BigDecimal.ROUND_CEILING,BigDecimal.ROUND_HALF_UP);
//                BigDecimal month = new BigDecimal(rate[1]).divide(new BigDecimal(rate[2]),BigDecimal.ROUND_CEILING, BigDecimal.ROUND_HALF_EVEN).setScale(BigDecimal.ROUND_CEILING,BigDecimal.ROUND_HALF_UP);
//
//
//                if (month.compareTo(prepaidBonus)<0){
//                    BigDecimal m = month.multiply(new BigDecimal(amount.get(key)).multiply(yearRate).divide(new BigDecimal(12),BigDecimal.ROUND_CEILING, BigDecimal.ROUND_HALF_EVEN)).setScale(BigDecimal.ROUND_CEILING,BigDecimal.ROUND_HALF_UP);
//                    amount.put(key,String.valueOf(m));
//                }else {
//                    BigDecimal m = prepaidBonus.multiply(new BigDecimal(amount.get(key)).multiply(yearRate).setScale(BigDecimal.ROUND_CEILING,BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(12),BigDecimal.ROUND_CEILING, BigDecimal.ROUND_HALF_EVEN));
//                    m = m.add(new BigDecimal(amount.get(key)));
//                    amount.put(key,String.valueOf(m));
//                }
//            }
            logger.info("insert prompt message:{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COMMIT_RECORD_SUCCESS_INSERT));
            return ResultUtil.getResult(StatusCode.OK,null);
        } catch (Exception e) {
            logger.error("Capture getChannelBank ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COMMIT_RECORD_ERROR_INSERT), e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_COMMIT_RECORD_ERROR_INSERT,null);
        }
    }

    private List<BigDecimal> mySort(List<BigDecimal> arr) { // 交换排序->冒泡排序
        BigDecimal bigDecimal = null;
        boolean exchange = false;
        for (int i = 0; i < arr.size(); i++) {
            exchange = false;
            for (int j = arr.size() - 2; j >= i; j--) {
                if ((arr.get(j + 1)).compareTo(
                        (arr.get(j))) >= 0) {
                    bigDecimal =  arr.get(j + 1);
                    arr.set(j + 1, arr.get(j));
                    arr.set(j, bigDecimal);
                    exchange = true;
                }
            }
            if (!exchange)
                break;
        }
        return arr;
    }
}
