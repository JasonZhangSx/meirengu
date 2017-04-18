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
    public ModelAndView rechargeRecord(){
        return new ModelAndView("/payment/userRechargeRecord", recordList("3"));
    }

    @RequestMapping("withdrawalsRecord")
    public ModelAndView withdrawalsRecord(){
        return new ModelAndView("/payment/userWithdrawalsRecord", recordList("4"));
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
        return withdrawalsRecord();
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
        return withdrawalsRecord();
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
