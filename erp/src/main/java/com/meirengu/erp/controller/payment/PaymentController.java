package com.meirengu.erp.controller.payment;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * 充值提现记录
 * Author: haoyang.Yu
 * Create Date: 2017/3/31 16:01.
 */
@RestController
@RequestMapping("record")
public class PaymentController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(PaymentController.class);
    protected static StringBuffer url;

    @RequestMapping("rechargeRecord")
    public ModelAndView rechargeRecord(String orderSn,String userPhone,String startDate,String endDate){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("3");
        if (orderSn!=null&&!orderSn.isEmpty()){
            if (stringBuffer.length()>=0){
                stringBuffer.append("&orderSn="+orderSn);
            }else {
                stringBuffer.append("orderSn="+orderSn);
            }
        }
        if (userPhone!=null&&!userPhone.isEmpty()){
            if (stringBuffer.length()>=0){
                stringBuffer.append("&userPhone="+userPhone);
            }else {
                stringBuffer.append("userPhone="+userPhone);
            }
        }
        if (startDate!=null&&!startDate.isEmpty()){
            if (stringBuffer.length()>=0){
                stringBuffer.append("&startDate="+startDate);
            }else {
                stringBuffer.append("startDate="+startDate);
            }
        }
        if (endDate!=null&&!endDate.isEmpty()){
            if (stringBuffer.length()>=0){
                stringBuffer.append("&endDate="+endDate);
            }else {
                stringBuffer.append("endDate="+endDate);
            }
        }
        return new ModelAndView("/payment/userRechargeRecord", recordList(stringBuffer.toString()));
    }
    @RequestMapping("withdrawalsRecord")
    public ModelAndView withdrawalsRecord(String orderSn,String userPhone,String startDate,String endDate){
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("4");
        if (orderSn!=null&&!orderSn.isEmpty()){
            if (stringBuffer.length()>=0){
                stringBuffer.append("&orderSn="+orderSn);
            }else {
                stringBuffer.append("orderSn="+orderSn);
            }
        }
        if (userPhone!=null&&!userPhone.isEmpty()){
            if (stringBuffer.length()>=0){
                stringBuffer.append("&userPhone="+userPhone);
            }else {
                stringBuffer.append("userPhone="+userPhone);
            }
        }
        if (startDate!=null&&!startDate.isEmpty()){
            if (stringBuffer.length()>=0){
                stringBuffer.append("&startDate="+startDate);
            }else {
                stringBuffer.append("startDate="+startDate);
            }
        }
        if (endDate!=null&&!endDate.isEmpty()){
            if (stringBuffer.length()>=0){
                stringBuffer.append("&endDate="+endDate);
            }else {
                stringBuffer.append("endDate="+endDate);
            }
        }
        return new ModelAndView("/payment/userWithdrawalsRecord", recordList(stringBuffer.toString()));
    }
    @RequestMapping("confirmWithdrawals")
    public ModelAndView confirmWithdrawals(HttpServletRequest reques,String orderSn, String status){
        if (url==null){
            setUrl();
        }
        Map map = new HashMap<>();
        map.put("orderSn",orderSn);
        map.put("status",status);
        map.put("content",JSONObject.toJSONString(map));
        super.httpPost(url.toString()+"/capital/withdrawals",map);
        return withdrawalsRecord(null,null,null,null);
    }
    @RequestMapping("withdrawalsError")
    public ModelAndView withdrawalsError(String orderSn){
        Map map = new HashMap<>();
        map.put("orderSn",orderSn);
        return new ModelAndView("/payment/withdrawalsErrorMsg", map);
    }
    @RequestMapping(value = "withdrawalsErrorConfirm", method = RequestMethod.POST)
    public ModelAndView withdrawalsErrorConfirm(HttpServletRequest request,String orderSn,String msg) throws UnsupportedEncodingException {
        request.setCharacterEncoding("UTF-8");
        Map map = new HashMap<>();
        map.put("orderSn",orderSn);
        map.put("returnMsg",msg);
        map.put("status","3");
        map.put("content",JSONObject.toJSONString(map));
        super.httpPost(url.toString()+"/capital/withdrawals",map);
        return withdrawalsRecord(null,null,null,null);
    }

    protected Map<String,Object> recordList(String condition){
        if (url==null){
            setUrl();
        }
        try {
            return  (Map<String, Object>) super.httpGet(url.toString()+"/payment/paymentRecord?paymentType="+condition);
        } catch (IOException e) {
            logger.error("recordList Error Msg : {}",e.getMessage());
            return null;
        }
    }

    protected void setUrl(){
        url = new StringBuffer(ConfigUtil.getConfig("payment.url"));
    }

}
