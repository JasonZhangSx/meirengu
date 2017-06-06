package com.meirengu.erp.controller.payment;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/5/8 12:13.
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.erp.controller.BaseController;
import com.meirengu.erp.controller.UploadController;
import com.meirengu.erp.utils.ConfigUtil;
import com.meirengu.model.Result;
import com.meirengu.utils.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("collectPayment")
public class CollectPaymentController extends BaseController {
    private static Logger logger = LoggerFactory.getLogger(CollectPaymentController.class);
    protected static StringBuffer url;
    protected static StringBuffer itemUrl;
    protected static StringBuffer orderUrl;
    /**
     * 查询待打款记录
     * @param
     * @return
     */
    @RequestMapping(value = "/getPaymentCommit",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getPaymentCommit(String partnerName,String itemName) throws UnsupportedEncodingException {
        StringBuffer stringBuffer = new StringBuffer();
        if (partnerName!=null&&!partnerName.isEmpty()){
            if (stringBuffer.length()>=0){
                stringBuffer.append("&partnerName="+new String((partnerName).getBytes("ISO-8859-1"),"utf8"));
            }else {
                stringBuffer.append("partnerName="+new String((partnerName).getBytes("ISO-8859-1"),"utf8"));
            }
        }
        if (itemName!=null&&!itemName.isEmpty()){
            if (stringBuffer.length()>=0){
                stringBuffer.append("&itemName="+new String((itemName).getBytes("ISO-8859-1"),"utf8"));
            }else {
                stringBuffer.append("itemName="+new String((itemName).getBytes("ISO-8859-1"),"utf8"));
            }
        }
        return new ModelAndView("/payment/paymentCommitList", recordList("paymentCommit?"+stringBuffer.toString()));
    }
    @RequestMapping(value = "/paymentCommit",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView paymentCommit(String id) throws IOException {
        ModelAndView modelAndView = new ModelAndView("/payment/paymentCommit");
        StringBuffer stringBuffer = new StringBuffer();
        if (id!=null&&!id.isEmpty()){
            if (stringBuffer.length()>0){
                stringBuffer.append("&id="+id);
            }else {
                stringBuffer.append("id="+id);
            }
        }
        Map<String,Object> map = recordList("paymentCommit/detail?"+stringBuffer.toString());
        modelAndView.addObject("paymentCommitList",map.get("paymentCommitList"));
        JSONObject jsonObject = (JSONObject) map.get("paymentCommitList");
        Integer itemId = jsonObject.getInteger("itemId");
        Map<String,Object> bankCommission = (Map<String, Object>) super.httpPost(itemUrl.toString()+"/bank_commission?item_id="+itemId,new HashMap<>());
        if (bankCommission.get("type").equals(2)){
            JSONArray levelList = (JSONArray) bankCommission.get("levelList");
            Map<String,String> interestRate = new HashMap<>();
            StringBuffer levelId = new StringBuffer();
            String level;
            for(int i=0;i<levelList.size();i++){
                level = String.valueOf(levelList.getJSONObject(i).get("levelId"));
                //0->分红利率，1->投资期限，以月为单位，2->分红周期（多少月回款一次）
                interestRate.put(level,String.valueOf(levelList.getJSONObject(i).get("yearRate"))+","+String.valueOf(levelList.getJSONObject(i).get("investmentPeriod"))+","+String.valueOf(levelList.getJSONObject(i).get("shareBonusPeriod")));
                if (i==levelList.size()-1){
                    levelId.append(level);
                }else {
                    levelId.append(level+",");
                }
            }
            Map<String,String> amount = new HashMap<>();
            JSONArray levelSumAmount = (JSONArray)super.httpGet(orderUrl.toString()+"?level_ids="+levelId.toString());
            for (int i=0;i<levelSumAmount.size();i++){
                JSONObject levelAmount = levelSumAmount.getJSONObject(i);
                amount.put(levelAmount.getString("itemLevelId"),levelAmount.getString("sumOrderAmount"));
            }
            BigDecimal prepaidBonus = new BigDecimal(bankCommission.get("prepaidBonus").toString());
            for (String key : interestRate.keySet()){
                String[] rate = String.valueOf(interestRate.get(key)).split(",");
                BigDecimal yearRate = new BigDecimal(rate[0]).divide(new BigDecimal(100)).setScale(BigDecimal.ROUND_CEILING,BigDecimal.ROUND_HALF_UP);
                BigDecimal month = new BigDecimal(rate[1]).divide(new BigDecimal(rate[2]),BigDecimal.ROUND_CEILING, BigDecimal.ROUND_HALF_EVEN).setScale(BigDecimal.ROUND_CEILING,BigDecimal.ROUND_HALF_UP);
                if (month.compareTo(prepaidBonus)<0){
                    BigDecimal m = month.multiply(new BigDecimal(amount.get(key)).multiply(yearRate).divide(new BigDecimal(12),BigDecimal.ROUND_CEILING, BigDecimal.ROUND_HALF_EVEN)).setScale(BigDecimal.ROUND_CEILING,BigDecimal.ROUND_HALF_UP);
                    m = m.add(new BigDecimal(amount.get(key)));
                    amount.put(key,String.valueOf(m));
                }else {
                    BigDecimal m = prepaidBonus.multiply(new BigDecimal(amount.get(key)).multiply(yearRate).setScale(BigDecimal.ROUND_CEILING,BigDecimal.ROUND_HALF_UP).divide(new BigDecimal(12),BigDecimal.ROUND_CEILING, BigDecimal.ROUND_HALF_EVEN));
                    amount.put(key,String.valueOf(m));
                }
            }
            BigDecimal m = new BigDecimal(0);
            for (String key : amount.keySet()){
                m = m.add(new BigDecimal(amount.get(key)));
            }
            bankCommission.put("prepaidBonusMoney",m);
        }else {
            bankCommission.put("prepaidBonus","0.00");
            bankCommission.put("prepaidBonusMoney","0.00");
        }

        modelAndView.addObject("bankCommission",bankCommission);
        return modelAndView;
    }
    @RequestMapping(value = "/savePaymentCommitRecord",method = RequestMethod.POST)
    @ResponseBody
    public Object addData(String commitType,String shouldAmount,String actualAmount,String paymentCommitId,MultipartHttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        UploadController uploadController = new UploadController();
        Result result = uploadController.uploadMultiple(request,"paymentCommit");
        List<String> list = new ArrayList<>();
        list = (List<String>) result.getData();
        StringBuffer imageCredential = new StringBuffer();
        for (int i=0;i<list.size();i++){
            if (i==list.size()-1){
                imageCredential.append(list.get(i));
            }else {
                imageCredential.append(list.get(i)+",");
            }
        }
        if (result.getData()==null || result.getData().toString() == ""){
            return "";
        }
        Map<String,String> paramsMap = new HashMap<String,String>();
        paramsMap.put("commitType",commitType);
        paramsMap.put("shouldAmount",shouldAmount);
        paramsMap.put("actualAmount",actualAmount);
        paramsMap.put("paymentCommitId",paymentCommitId);
        paramsMap.put("imageCredential",imageCredential.toString());
        paramsMap.put("content",JSONObject.toJSONString(paramsMap));
        return HttpUtil.doPostForm(url.toString()+"/paymentCommitRecord",paramsMap).getContent();
    }
    /**
     * 查询打款记录
     * @param
     * @return
     */
    @RequestMapping(value = "/getPaymentCommitRecord",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getPaymentCommitRecord(String partnerName,String itemName) throws UnsupportedEncodingException {
        StringBuffer stringBuffer = new StringBuffer();
        if (partnerName!=null&&!partnerName.isEmpty()){
            if (stringBuffer.length()>=0){
                stringBuffer.append("&partnerName="+new String((partnerName).getBytes("ISO-8859-1"),"utf8"));
            }else {
                stringBuffer.append("partnerName="+new String((partnerName).getBytes("ISO-8859-1"),"utf8"));
            }
        }
        if (itemName!=null&&!itemName.isEmpty()){
            if (stringBuffer.length()>=0){
                stringBuffer.append("&itemName="+new String((itemName).getBytes("ISO-8859-1"),"utf8"));
            }else {
                stringBuffer.append("itemName="+new String((itemName).getBytes("ISO-8859-1"),"utf8"));
            }
        }
        return new ModelAndView("/payment/paymentCommitRecordList", recordList("paymentCommitRecord?"+stringBuffer.toString()));
    }

    /**
     * 查询待收款
     * @param partnerName
     * @param itemName
     * @return
     * @throws UnsupportedEncodingException
     */
    @RequestMapping(value = "/getPaymentCollection",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getPaymentCollection(String partnerName,String itemName) throws UnsupportedEncodingException {
        StringBuffer stringBuffer = new StringBuffer();
        if (partnerName!=null&&!partnerName.isEmpty()){
            if (stringBuffer.length()>=0){
                stringBuffer.append("&partnerName="+new String((partnerName).getBytes("ISO-8859-1"),"utf8"));
            }else {
                stringBuffer.append("partnerName="+new String((partnerName).getBytes("ISO-8859-1"),"utf8"));
            }
        }
        if (itemName!=null&&!itemName.isEmpty()){
            if (stringBuffer.length()>=0){
                stringBuffer.append("&itemName="+new String((itemName).getBytes("ISO-8859-1"),"utf8"));
            }else {
                stringBuffer.append("itemName="+new String((itemName).getBytes("ISO-8859-1"),"utf8"));
            }
        }
        return new ModelAndView("/payment/paymentCollectionList", recordList("paymentCollectionList?"+stringBuffer.toString()));
    }
    @RequestMapping(value = "/getPaymentCollectionList",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getPaymentCollectionList(String paymentCollectionId) throws UnsupportedEncodingException {
        ModelAndView modelAndView = new ModelAndView("/payment/paymentCollection");
        modelAndView.addObject("paymentCollectionList",recordList("paymentCollectionList?id="+paymentCollectionId));
        modelAndView.addObject("paymentCollectionRecord",recordList("paymentCollectionList/record?paymentCollectionId="+paymentCollectionId));
        return modelAndView;
    }
    /**
     * 查询收款记录
     * @param
     * @return
     */
    @RequestMapping(value = "/getPaymentCollectionRecord",method = RequestMethod.GET)
    @ResponseBody
    public ModelAndView getPaymentCollectionRecord(String partnerName,String itemName) throws UnsupportedEncodingException {
        StringBuffer stringBuffer = new StringBuffer();
        if (partnerName!=null&&!partnerName.isEmpty()){
            if (stringBuffer.length()>=0){
                stringBuffer.append("&partnerName="+new String((partnerName).getBytes("ISO-8859-1"),"utf8"));
            }else {
                stringBuffer.append("partnerName="+new String((partnerName).getBytes("ISO-8859-1"),"utf8"));
            }
        }
        if (itemName!=null&&!itemName.isEmpty()){
            if (stringBuffer.length()>=0){
                stringBuffer.append("&itemName="+new String((itemName).getBytes("ISO-8859-1"),"utf8"));
            }else {
                stringBuffer.append("itemName="+new String((itemName).getBytes("ISO-8859-1"),"utf8"));
            }
        }
        return new ModelAndView("/payment/paymentCollectionRecordList", recordList("paymentCollectionList/record?"+stringBuffer.toString()));
    }
    @RequestMapping(value = "/savePaymentCollectionRecord",method = RequestMethod.POST)
    @ResponseBody
    public Object addPaymentCollection(String collectionPeriod,String interestRate,String actualAmount,MultipartHttpServletRequest request){
        ModelAndView modelAndView = new ModelAndView();
        UploadController uploadController = new UploadController();
        Result result = uploadController.uploadMultiple(request,"paymentCollection");
        List<String> list = new ArrayList<>();
        list = (List<String>) result.getData();
        StringBuffer imageCredential = new StringBuffer();
        for (int i=0;i<list.size();i++){
            if (i==list.size()-1){
                imageCredential.append(list.get(i));
            }else {
                imageCredential.append(list.get(i)+",");
            }
        }
        if (result.getData()==null || result.getData().toString() == ""){
            return "";
        }
        Map<String,String> paramsMap = new HashMap<String,String>();
        paramsMap.put("collectionPeriod",collectionPeriod);
        paramsMap.put("interestRate",interestRate);
        paramsMap.put("actualAmount",actualAmount);
        paramsMap.put("imageCredential",imageCredential.toString());
        paramsMap.put("content",JSONObject.toJSONString(paramsMap));
        return HttpUtil.doPostForm(url.toString()+"/paymentCommitRecord",paramsMap).getContent();
    }
    protected Map<String,Object> recordList(String condition){
        if (url==null){
            setUrl();
        }
        try {
            return  (Map<String, Object>) super.httpGet(url.toString()+"/"+condition);
        } catch (IOException e) {
            logger.error("HttpGet Error Msg : {}",e.getMessage());
            return null;
        }
    }

    protected void setUrl(){
        url = new StringBuffer(ConfigUtil.getConfig("payment.url"));
        itemUrl = new StringBuffer(ConfigUtil.getConfig("item.list"));
        orderUrl = new StringBuffer(ConfigUtil.getConfig("order.get_sum_amount_by_level_ids"));
    }
}
