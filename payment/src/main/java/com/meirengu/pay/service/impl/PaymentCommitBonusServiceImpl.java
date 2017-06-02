package com.meirengu.pay.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.pay.dao.PaymentAccountDao;
import com.meirengu.pay.dao.PaymentCommitBonusDao;
import com.meirengu.pay.model.PaymentAccount;
import com.meirengu.pay.model.PaymentCommitBonus;
import com.meirengu.pay.service.PaymentCommitBonusService;
import com.meirengu.pay.utils.HttpUtil;
import com.meirengu.pay.utils.PaymentTypeUtil;
import com.meirengu.pay.utils.ResultUtil;
import com.meirengu.pay.utils.check.Validator;
import com.meirengu.pay.vo.PaymentCommitBonusVo;
import com.meirengu.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/5/16 10:51.
 */
@Service
public class PaymentCommitBonusServiceImpl extends BaseServiceImpl  implements PaymentCommitBonusService {
    private final static Logger logger = LoggerFactory.getLogger(PaymentCommitBonusServiceImpl.class);
    @Autowired
    private PaymentCommitBonusDao paymentCommitBonusDao;
    @Autowired
    private PaymentAccountDao paymentAccountDao;
    @Transactional
    @Override
    public String bonus(String content) {
        PaymentCommitBonus paymentCommit = new PaymentCommitBonus();
        try {
            paymentCommit = (PaymentCommitBonus) super.execute(content,paymentCommit);
            logger.info("Request bonus parameter:{}",paymentCommit.toString());
            logger.info("bonus Parameter check Start========>");
            Validator.getInstance().validate(paymentCommit);
            logger.info("bonus Parameter check End<========");
            if (url==null){
                projectValue();
            }
            String itemDetail = HttpUtil.httpPostForm(super.url+"/cf/item/bank_commission?item_id="+paymentCommit.getItemId(),new HashMap<>(),"UTF-8");
            logger.info("Request ItemDetail result :{}",itemDetail);
            JSONObject item = (JSONObject) JSONObject.parseObject(itemDetail).get("data");
            JSONArray levelList = (JSONArray) item.get("levelList");
            for(int i=0;i<levelList.size();i++){
                BigDecimal month = levelList.getJSONObject(i).getBigDecimal("investmentPeriod").divide(levelList.getJSONObject(i).getBigDecimal("shareBonusPeriod"));
                BigDecimal yearRate = levelList.getJSONObject(i).getBigDecimal("yearRate").divide(new BigDecimal(100)).setScale(BigDecimal.ROUND_CEILING,BigDecimal.ROUND_HALF_UP);
                JSONArray levelOrder = (JSONArray) JSONObject.parseObject(HttpUtil.httpGet(super.url+"/trade/order/getOrderInfoList?order_state=6&item_level_id="+levelList.getJSONObject(i).getString("levelId"),"UTF-8")).get("data");
                for (int j=0;j<levelOrder.size();j++){
                    JSONObject orderJson = levelOrder.getJSONObject(j);
                    BigDecimal money = orderJson.getBigDecimal("orderAmount").multiply(yearRate).setScale(BigDecimal.ROUND_CEILING,BigDecimal.ROUND_HALF_UP).divide(month,BigDecimal.ROUND_CEILING, BigDecimal.ROUND_HALF_EVEN);
                    List<PaymentCommitBonus> paymentCommitBonusList = new ArrayList<>();
                    for (int f=1;f<=month.intValue();f++){
                        PaymentCommitBonus paymentCommitBonus = new PaymentCommitBonus();
                        paymentCommitBonus.setItemId(paymentCommit.getItemId());
                        paymentCommitBonus.setItemName(paymentCommit.getItemName());
                        paymentCommitBonus.setUserId(orderJson.getInteger("userId"));
                        paymentCommitBonus.setUserName(orderJson.getString("userName"));
                        paymentCommitBonus.setUserPhone(orderJson.getString("userPhone"));
                        paymentCommitBonus.setItemLevelId(orderJson.getInteger("itemLevelId"));
                        paymentCommitBonus.setItemLevelName(orderJson.getString("itemLevelName"));
                        paymentCommitBonus.setNumber(orderJson.getIntValue("itemNum"));
                        paymentCommitBonus.setInvestPrincipal(orderJson.getBigDecimal("orderAmount"));
                        paymentCommitBonus.setYearRate(levelList.getJSONObject(i).getInteger("yearRate"));
                        paymentCommitBonus.setPeriod(f);
                        paymentCommitBonus.setPrincipal(orderJson.getBigDecimal("costAmount"));
                        paymentCommitBonus.setAllowance(orderJson.getBigDecimal("rebateAmount"));
                        paymentCommitBonus.setIncome(money);
                        if (f==month.intValue()){
                            paymentCommitBonus.setTotalAmount(money.add(orderJson.getBigDecimal("costAmount")));
                        }else {
                            paymentCommitBonus.setTotalAmount(money);
                        }
                        Calendar c = Calendar.getInstance();
                        c.add(Calendar.MONTH,f*levelList.getJSONObject(i).getIntValue("shareBonusPeriod"));
                        paymentCommitBonus.setShouldTime(c.getTime());
                        paymentCommitBonusList.add(paymentCommitBonus);
                    }
                    paymentCommitBonusDao.insertList(paymentCommitBonusList);
                }
            }
            logger.info("bonus prompt message:{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COMMIT_BONUS_SUCCESS_INSERT));
            return ResultUtil.getResult(StatusCode.OK,null);
        } catch (Exception e) {
            logger.error("Capture getChannelBank ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COMMIT_BONUS_ERROR_INSERT), e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_COMMIT_BONUS_ERROR_INSERT,null);
        }

    }

    @Override
    public String query(PaymentCommitBonusVo paymentCommitBonusVo) {
        try {
            if (paymentCommitBonusVo.getStartDate()==null){
                paymentCommitBonusVo.setStartDate(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
            }
            Map<String,Object> map = new HashMap<>();
            if (paymentCommitBonusVo.getStartDate()!=null&&!paymentCommitBonusVo.getStartDate().isEmpty()){
                paymentCommitBonusVo.setStartDate(paymentCommitBonusVo.getStartDate()+" 00:00:00");
            }
            if (paymentCommitBonusVo.getEndDate()!=null&&!paymentCommitBonusVo.getEndDate().isEmpty()){
                paymentCommitBonusVo.setEndDate(paymentCommitBonusVo.getEndDate()+" 23:59:59");
            }
            if (paymentCommitBonusVo.getShouldTimeStart()!=null&&!paymentCommitBonusVo.getShouldTimeStart().isEmpty()){
                paymentCommitBonusVo.setShouldTimeStart(paymentCommitBonusVo.getShouldTimeStart()+" 00:00:00");
            }
            if (paymentCommitBonusVo.getShouldTimeEnd()!=null&&!paymentCommitBonusVo.getShouldTimeEnd().isEmpty()){
                paymentCommitBonusVo.setShouldTimeEnd(paymentCommitBonusVo.getShouldTimeEnd()+" 23:59:59");
            }
            logger.info("Request query parameter:{}",paymentCommitBonusVo.toString());
            List<PaymentCommitBonus> paymentCommitBonusList = paymentCommitBonusDao.select(paymentCommitBonusVo);
            map.put("paymentCommitBonusList",paymentCommitBonusList);
            logger.info("Capture query message:{}",StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COMMIT_BONUS_SUCCESS_SELECT));
            return ResultUtil.getResult(StatusCode.OK,map);
        }catch (Exception e){
            logger.error("Capture query ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_COMMIT_BONUS_ERROR_SELECT), e.getMessage());
            return ResultUtil.getResult(StatusCode.PAYMENT_COMMIT_BONUS_ERROR_SELECT,null);
        }
    }
    @Transactional
    @Override
    public void startDistribute() {
        String today = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        PaymentCommitBonusVo paymentCommitBonusVo = new PaymentCommitBonusVo();
        paymentCommitBonusVo.setShouldTimeStart(today+" 00:00:00");
        paymentCommitBonusVo.setShouldTimeEnd(today+" 23:59:59");
        List<PaymentCommitBonus> paymentCommitBonusList = paymentCommitBonusDao.select(paymentCommitBonusVo);
        if (paymentCommitBonusList==null){
            return;
        }
        logger.info("{}总应派息条数{}",today,paymentCommitBonusList.size());
        logger.info("{}派息开始--->", DateUtils.dateToStr(new Date()));
        for (PaymentCommitBonus paymentCommitBonus : paymentCommitBonusList){
            PaymentAccount paymentAccount = paymentAccountDao.selectByUserId(paymentCommitBonus.getUserId());
            paymentAccountDao.updateBalance(paymentAccount.getAccountId(),paymentAccount.getAccountBalance().add(paymentCommitBonus.getTotalAmount()));
            paymentCommitBonus.setStatus(PaymentTypeUtil.PaymentBonus_Success);
            paymentCommitBonus.setBonusTime(new Date());
            paymentCommitBonusDao.updateStatus(paymentCommitBonus);
        }
        logger.info("{}派息结束--->", DateUtils.dateToStr(new Date()));
    }
}
