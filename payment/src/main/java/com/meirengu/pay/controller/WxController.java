package com.meirengu.pay.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.controller.BaseController;
import com.meirengu.pay.model.Payment;
import com.meirengu.model.Result;
import com.meirengu.pay.common.Constants;
import com.meirengu.pay.service.PaymentService;
import com.meirengu.pay.utils.ClientCustomSSL;
import com.meirengu.pay.utils.ConfigUtil;
import com.meirengu.pay.utils.XmlAnalysisUtils;
import com.meirengu.pay.vo.*;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.MD5Util;
import com.meirengu.utils.RequestUtil;
import com.meirengu.utils.StringUtil;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.*;

/**
 * 微信支付相关接口
 * @author 建新
 * @create 2017-02-13 10:47
 */
@Controller
@RequestMapping("wx")
public class WxController extends BaseController{

    private Logger LOGGER = LoggerFactory.getLogger(WxController.class);

    @Autowired
    private PaymentService paymentService;

    /**
     * 微信统一下单接口
     * @param deviceInfo 设备号
     * @param body 商品描述
     * @param detail 商品详情
     * @param attach 附加数据
     * @param outTradeNo 商户订单号
     * @param feeType 标价币种
     * @param totalFee 标价金额
     * @param timeStart 交易起始时间
     * @param timeExpire 交易结束时间
     * @param goodsTag 商品标记
     * @param tradeType 交易类型
     * @param productId 商品ID
     * @param limitPay 指定支付方式
     * @param request
     */
    @ResponseBody
    @RequestMapping(value = "unifiedorder", method = RequestMethod.POST)
    public Result unifiedOrder(@RequestParam(value = "device_info", required = false) String deviceInfo,
                               @RequestParam(value = "body", required = false) String body,
                               @RequestParam(value = "detail", required = false) String detail,
                               @RequestParam(value = "attach", required = false) String attach,
                               @RequestParam(value = "out_trade_no", required = false) String outTradeNo,
                               @RequestParam(value = "fee_type", required = false) String feeType,
                               @RequestParam(value = "total_fee", required = false) String totalFee,
                               @RequestParam(value = "time_start", required = false) String timeStart,
                               @RequestParam(value = "timeExpire", required = false) String timeExpire,
                               @RequestParam(value = "goods_tag", required = false) String goodsTag,
                               @RequestParam(value = "trade_type", required = false) String tradeType,
                               @RequestParam(value = "product_id", required = false) String productId,
                               @RequestParam(value = "limit_pay", required = false) String limitPay,
                               @RequestParam(value = "openid", required = false) String openid,
                               /*@RequestParam(value = "item_id", required = false) String itemId,
                               @RequestParam(value = "user_id", required = false) String userId,
                               @RequestParam(value = "hospital_id", required = false) String hospitalId,
                               @RequestParam(value = "doctor_id", required = false) String doctorId,
                               @RequestParam(value = "item_name", required = false) String itemName,
                               @RequestParam(value = "user_phone", required = false) String userPhone,
                               @RequestParam(value = "hospital_name", required = false) String hospitalName,
                               @RequestParam(value = "doctor_name", required = false) String doctorName,*/
                               HttpServletRequest request) {

        //LOGGER.info(">> unifiedorder is starting.... params is {}", JSON.toJSON(request));
        String ip = RequestUtil.getIpAddr(request);
        //String ip = "118.192.104.101";
        LOGGER.info(">> wx unifiedorder service start...");

        if(StringUtil.isEmpty(body) || StringUtil.isEmpty(outTradeNo) ||
                StringUtil.isEmpty(totalFee) || StringUtil.isEmpty(tradeType)){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        if(!StringUtil.isInteger(totalFee)){
            return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
        }

        WxPaySendData sendData = new WxPaySendData();
        sendData.setAppid(ConfigUtil.getConfig("wx.appid"));
        sendData.setMch_id(ConfigUtil.getConfig("wx.mch_id"));
        if(!StringUtil.isEmpty(deviceInfo)){
            sendData.setDevice_info(deviceInfo);
        }
        sendData.setNonce_str(StringUtil.createNonceStr());
        sendData.setBody(body);
        if(!StringUtil.isEmpty(detail)){
            sendData.setDetail(detail);
        }
        sendData.setOut_trade_no(outTradeNo);
        //币种类型，目前只支持人民币（CNY），为以后扩展留入口
        if(!StringUtil.isEmpty(feeType)){
            sendData.setFee_type(feeType);
        }
        sendData.setTotal_fee(Integer.parseInt(totalFee));

        sendData.setSpbill_create_ip(ip);
        if(!StringUtil.isEmpty(timeStart)){
            sendData.setTime_start(timeStart);
        }
        if(!StringUtil.isEmpty(timeExpire)){
            sendData.setTime_expire(timeExpire);
        }
        if(!StringUtil.isEmpty(goodsTag)){
            sendData.setGoods_tag(goodsTag);
        }
        sendData.setNotify_url(ConfigUtil.getConfig("wx.notifyurl"));
        sendData.setTrade_type(tradeType);
        //扫码支付时，product_id为必填
        if("NATIVE".equals(tradeType)){
            if(!StringUtil.isEmpty(productId)){
                sendData.setProduct_id(productId);
            }else {
                return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
            }
        }
        if(!StringUtil.isEmpty(limitPay)){
            sendData.setLimit_pay(limitPay);
        }
        //公众号支付时，openid为必填
        if("JSAPI".equals(tradeType)){
            if(!StringUtil.isEmpty(openid)){
                sendData.setOpenid(openid);
            }else {
                return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
            }
        }
        double totalFeeTemp = sendData.getTotal_fee()/100f;

        //微信下单之前先生成我们自己的payment
        Payment payment = new Payment();
        payment.setOrderSN(outTradeNo);
        payment.setPayType(1);
        payment.setTotalFee(totalFeeTemp);
        payment.setPaymentType(1);
        payment.setStatus(1);
        payment.setDeviceInfo(deviceInfo);
        payment.setCreateTime(new Date());

        if(!StringUtil.isEmpty(attach)){
            JSONObject attachJson = JSONObject.parseObject(attach);
            String hospitalId = attachJson.get("hospital_id") == null? "" : attachJson.get("hospital_id").toString();
            String userPhone = attachJson.get("user_phone") == null? "" : attachJson.get("user_phone").toString();
            String itemId = attachJson.get("item_id") == null? "" : attachJson.get("item_id").toString();
            //String itemName = attachJson.get("item_name") == null? "" : attachJson.get("item_name").toString();
            String userId = attachJson.get("user_id") == null? "" : attachJson.get("user_id").toString();
            //String hospitalName = attachJson.get("hospital_name") == null? "" : attachJson.get("hospital_name").toString();
            String doctorId = attachJson.get("doctor_id") == null? "" : attachJson.get("doctor_id").toString();
            //String doctorName = attachJson.get("doctor_name") == null? "" : attachJson.get("doctor_name").toString();
            if(StringUtil.isInteger(userId) && StringUtil.isInteger(hospitalId) && StringUtil.isInteger(itemId) && StringUtil.isInteger(doctorId)){
                payment.setItemId(Integer.parseInt(itemId));
                payment.setItemName(body);
                payment.setUserId(Integer.parseInt(userId));
                payment.setUserPhone(userPhone);
                payment.setHospitalId(Integer.parseInt(hospitalId));
                //payment.setHospitalName(hospitalName);
                payment.setDoctorId(Integer.parseInt(doctorId));
                //payment.setDoctorName(doctorName);
            }
            sendData.setAttach(attach);
        }


        String sign = genSign(sendData);
        sendData.setSign(sign);

        String paramsXml = XmlAnalysisUtils.getXMLForObject(sendData);

        LOGGER.info("wx unifiedorder send xml is \n {}", paramsXml);

        //调用下单接口返回数据
        String returnStr = HttpUtil.sendPost(ConfigUtil.getConfig("wx.unifiedorder"), paramsXml);

        LOGGER.info("wx unifiedorder return xml is {}", returnStr);

        WxPayReturnData returnData = (WxPayReturnData) XmlAnalysisUtils.getObjectForXMl(returnStr, WxPayReturnData.class);

        String returnCode = returnData.getReturn_code();

        Map<String, Object> resultMap = new HashMap<>();
        if(Constants.SUCCESS.equals(returnCode)){
            String resultCode = returnData.getResult_code();
            if(Constants.SUCCESS.equals(resultCode)){
                //微信那边下单后我们再插入对应的payment记录
                int result = paymentService.insert(payment);
                if(result != 1){
                    return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
                }
                //return_code和result_code都为SUCCESS的时候返回
                String prepayId = returnData.getPrepay_id();
                String codeUrl = returnData.getCode_url();
                resultMap.put("prepayId", prepayId);
                if("NATIVE".equals(tradeType)){
                    resultMap.put("codeUrl", codeUrl);
                }
                if("JSAPI".equals(tradeType)){
                    JsPaySendData jsPayParams = new JsPaySendData();
                    jsPayParams.setAppId(sendData.getAppid());
                    jsPayParams.setTimeStamp(System.currentTimeMillis()+"");
                    jsPayParams.setNonceStr(StringUtil.createNonceStr());
                    jsPayParams.setPackages("prepay_id="+prepayId);
                    jsPayParams.setSignType("MD5");
                    String paySign = genSign(jsPayParams);
                    jsPayParams.setPaySign(paySign);
                    resultMap.put("jsPayParams", jsPayParams);
                }
                return super.setResult(StatusCode.OK, resultMap, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                String errCode = returnData.getErr_code();
                String errCodeDes = returnData.getErr_code_des();
                resultMap.put("errCode", errCode);
                resultMap.put("errCodeDes", errCodeDes);
                return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, resultMap, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
            }
        }else{
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, resultMap, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }


    /**
     * 微信支付回调
     * @param response
     * @param request
     */
    @RequestMapping("notify")
    public void wxPayNotify(HttpServletResponse response, HttpServletRequest request){

        LOGGER.info(">> wx_notify is starting......");
        WxNotifyReturnData returnData = new WxNotifyReturnData();
        returnData.setReturn_code("SUCCESS");
        returnData.setReturn_msg("OK");
        String returnSuccessMsg = XmlAnalysisUtils.getXMLForObject(returnData);

        returnData.setReturn_code("FAIL");
        returnData.setReturn_msg("ERROR");
        String returnFailMsg = XmlAnalysisUtils.getXMLForObject(returnData);
        //默认返回失败
        String returnMsg = returnFailMsg;

        response.setCharacterEncoding("utf-8");

        String inputLine;
        StringBuilder notifyXml = new StringBuilder();
        try {
            //获取reader流
            BufferedReader rd = request.getReader();
            while ((inputLine = rd.readLine()) != null){
                notifyXml.append(inputLine);
            }
            //读取完成后关闭流
            rd.close();
            LOGGER.info(">> get notify xml is {}", notifyXml.toString());
            WxNotifyData notifyData = (WxNotifyData) XmlAnalysisUtils.getObjectForXMl(notifyXml.toString(), WxNotifyData.class);
            String returnCode = notifyData.getReturn_code();
            LOGGER.debug(">> return code is {}", returnCode);
            if(Constants.SUCCESS.equals(returnCode)){
                String resultCode = notifyData.getResult_code();
                LOGGER.debug(">> result code is {}", resultCode);
                if(Constants.SUCCESS.equals(resultCode)){

                    if(paymentService.paySuccess(notifyData, request, notifyXml.toString())){
                        LOGGER.info(">> wx pay success......");
                        //业务完成后给微信通知
                        returnMsg = returnSuccessMsg;
                    }else {
                        LOGGER.info(">> paySuccess fail...");
                    }
                }else {
                    if(paymentService.payFail(notifyData, request, notifyXml.toString())){
                        LOGGER.info(">> payFail success......");
                    }else {
                        LOGGER.info(">> payFail fail......");
                    }
                }
            }else {
                LOGGER.info(">> wx pay fail......");
            }

            LOGGER.debug("return msg is \n {}", returnMsg);
            PrintWriter out = response.getWriter();
            out.write(returnMsg);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 微信退款申请接口
     * @param deviceInfo 设备号
     * @param transactionId 微信订单号
     * @param outTradeNo 商户订单号 （微信订单号和商户订单号二选一）
     * @param outRefundNo 商户退款单号
     * @param totalFee 订单金额
     * @param refundFee 退款金额
     * @param refundFeeType 货币类型
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "refund", method = RequestMethod.POST)
    public Result refund(@RequestParam(value = "device_info", required = false) String deviceInfo,
                       @RequestParam(value = "transaction_id", required = false) String transactionId,
                       @RequestParam(value = "out_trade_no", required = false) String outTradeNo,
                       @RequestParam(value = "out_refund_no", required = false) String outRefundNo,
                       @RequestParam(value = "total_fee", required = false) String totalFee,
                       @RequestParam(value = "refund_fee", required = false) String refundFee,
                       @RequestParam(value = "refund_fee_type", required = false) String refundFeeType) throws KeyStoreException, IOException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {

        LOGGER.info(">> wx refund apply is starting......");
        if((StringUtil.isEmpty(transactionId) && StringUtil.isEmpty(outTradeNo)) ||
                StringUtil.isEmpty(outRefundNo) || StringUtil.isEmpty(totalFee) ||
                StringUtil.isEmpty(refundFee)){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        if(!StringUtil.isInteger(totalFee) || !StringUtil.isInteger(refundFee)){
            return super.setResult(StatusCode.INVALID_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.INVALID_ARGUMENT));
        }

        WxRefundSendData sendData = new WxRefundSendData();
        sendData.setAppid(ConfigUtil.getConfig("wx.appid"));
        sendData.setMch_id(ConfigUtil.getConfig("wx.mch_id"));
        if(!StringUtil.isEmpty(deviceInfo)){
            sendData.setDevice_info(deviceInfo);
        }
        sendData.setNonce_str(StringUtil.createNonceStr());
        sendData.setTransaction_id(transactionId);
        sendData.setOut_trade_no(outTradeNo);
        sendData.setOut_refund_no(outRefundNo);
        sendData.setTotal_fee(Integer.parseInt(totalFee));
        sendData.setRefund_fee(Integer.parseInt(refundFee));
        sendData.setOp_user_id(ConfigUtil.getConfig("wx.mch_id"));
        String sign = genSign(sendData);
        sendData.setSign(sign);

        String paramsXml = XmlAnalysisUtils.getXMLForObject(sendData);
        LOGGER.info("wx refund send xml is {}", paramsXml);

        try {
            String returnStr = new ClientCustomSSL().httpsRequest(ConfigUtil.getConfig("wx.refund"), paramsXml, ConfigUtil.getConfig("wx.apiclient.cert"));
            LOGGER.info("wx refund return xml is {}", returnStr);
            WxRefundReturnData returnData = (WxRefundReturnData) XmlAnalysisUtils.getObjectForXMl(returnStr, WxRefundReturnData.class);

            String returnCode = returnData.getReturn_code();

            Map<String, Object> resultMap = new HashMap<>();
            if(Constants.SUCCESS.equals(returnCode)){
                String resultCode = returnData.getResult_code();
                if(Constants.SUCCESS.equals(resultCode)){
                    //return_code和result_code都为SUCCESS的时候返回
                    //微信退款单号
                    String refundId = returnData.getRefund_id();
                    //微信退款渠道 ORIGINAL—原路退款 BALANCE—退回到余额
                    String refundChannel = returnData.getRefund_channel();
                    //微信应结退款金额 去掉非充值代金券退款金额后的退款金额，退款金额=申请退款金额-非充值代金券退款金额，退款金额<=申请退款金额
                    /*int settlementRefundFee = returnData.getSettlement_refund_fee();
                    //微信应结订单金额 去掉非充值代金券金额后的订单总金额，应结订单金额=订单金额-非充值代金券金额，应结订单金额<=订单金额。
                    int settlementTotalFee = returnData.getSettlement_total_fee();*/

                    resultMap.put("refundId", refundId);
                    resultMap.put("refundChannel", refundChannel);
                    /*resultMap.put("settlementRefundFee", settlementRefundFee);
                    resultMap.put("settlementTotalFee", settlementTotalFee);*/
                    resultMap.put("returnMsg", returnStr);
                    return super.setResult(StatusCode.OK, resultMap, StatusCode.codeMsgMap.get(StatusCode.OK));
                }else {
                    String errCode = returnData.getErr_code();
                    String errCodeDes = returnData.getErr_code_des();
                    resultMap.put("errCode", errCode);
                    resultMap.put("errCodeDes", errCodeDes);
                    return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, resultMap, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
                }
            }else{
                return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, resultMap, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
            }
        } catch (Exception e) {
            LOGGER.error(">> wx refund throw a exception: {}", e);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }

    }

    /**
     * 微信关闭订单接口
     * @param outTradeNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "closeorder", method = RequestMethod.POST)
    public Result closeOrder(@RequestParam(value = "out_trade_no", required = false) String outTradeNo){

        LOGGER.info(">> wx close order is starting......");
        if(StringUtil.isEmpty(outTradeNo)){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        WxCloseSendData sendData = new WxCloseSendData();
        sendData.setAppid(ConfigUtil.getConfig("wx.apid"));
        sendData.setMch_id(ConfigUtil.getConfig("wx.mch_id"));
        sendData.setNonce_str(StringUtil.createNonceStr());
        String sign = genSign(sendData);
        sendData.setSign(sign);

        String paramsXml = XmlAnalysisUtils.getXMLForObject(sendData);
        LOGGER.info("wx close order send xml is {}", paramsXml);
        String returnStr = HttpUtil.sendPost(ConfigUtil.getConfig("wx.closeorder"), paramsXml);
        LOGGER.info("wx close order return xml is {}", returnStr);
        WxCloseReturnData returnData = (WxCloseReturnData) XmlAnalysisUtils.getObjectForXMl(returnStr, WxCloseReturnData.class);

        String returnCode = returnData.getReturn_code();

        Map<String, Object> resultMap = new HashMap<>();
        if(Constants.SUCCESS.equals(returnCode)){
            String resultCode = returnData.getResult_code();
            if(Constants.SUCCESS.equals(resultCode)){
                return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                String errCode = returnData.getErr_code();
                String errCodeDes = returnData.getErr_code_des();
                resultMap.put("errCode", errCode);
                resultMap.put("errCodeDes", errCodeDes);
                return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, resultMap, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
            }
        }else{
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 微信退款查询
     * @param outTradeNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "refundquery", method = RequestMethod.POST)
    public Result refundquery(@RequestParam(value = "device_info", required = false) String deviceInfo,
                              @RequestParam(value = "transaction_id", required = false) String transactionId,
                              @RequestParam(value = "out_trade_no", required = false) String outTradeNo,
                              @RequestParam(value = "out_refund_no", required = false) String outRefundNo,
                              @RequestParam(value = "refund_id", required = false) String refundId){

        LOGGER.info(">> wx refund query is starting......");

        if(StringUtil.isEmpty(outTradeNo) && StringUtil.isEmpty(transactionId) &&
                StringUtil.isEmpty(outRefundNo) && StringUtil.isEmpty(refundId)){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        WxRefundQuerySendData sendData = new WxRefundQuerySendData();
        sendData.setAppid(ConfigUtil.getConfig("wx.apid"));
        sendData.setMch_id(ConfigUtil.getConfig("wx.mch_id"));
        if(!StringUtil.isEmpty(deviceInfo)){
            sendData.setDevice_info(deviceInfo);
        }
        //四选一
        sendData.setTransaction_id(transactionId);
        sendData.setOut_trade_no(outTradeNo);
        sendData.setOut_refund_no(outRefundNo);
        sendData.setRefund_id(refundId);

        sendData.setNonce_str(StringUtil.createNonceStr());
        String sign = genSign(sendData);
        sendData.setSign(sign);

        String paramsXml = XmlAnalysisUtils.getXMLForObject(sendData);
        LOGGER.info("wx refund query send xml is {}", paramsXml);
        String returnStr = HttpUtil.sendPost(ConfigUtil.getConfig("wx.refundquery"), paramsXml);
        LOGGER.info("wx refund query return xml is {}", returnStr);
        WxRefundQueryReturnData returnData = (WxRefundQueryReturnData) XmlAnalysisUtils.getObjectForXMl(returnStr, WxRefundQueryReturnData.class);

        String returnCode = returnData.getReturn_code();

        Map<String, Object> resultMap = new HashMap<>();
        if(Constants.SUCCESS.equals(returnCode)){
            String resultCode = returnData.getResult_code();
            if(Constants.SUCCESS.equals(resultCode)){

                int i = 0 ;
                String outRefundNoTemp = null;
                String refundIdTemp = null;
                String refundChannelTemp = null;
                String refundFeeTemp = null;
                String settlementRefundFeeTemp = null;
                List<Map<String, Object>> returnList = new ArrayList<>();
                do {
                    Map<String, Object> tempMap = new HashMap<>();
                    outRefundNoTemp = getProFromXml(returnStr, "<out_refund_no_"+i);
                    refundIdTemp = getProFromXml(returnStr, "<refund_id_"+i);
                    refundChannelTemp = getProFromXml(returnStr, "<refund_channel_"+i);
                    refundFeeTemp = getProFromXml(returnStr, "<refund_fee_"+i);
                    settlementRefundFeeTemp = getProFromXml(returnStr, "<settlement_refund_fee_"+i);
                    tempMap.put("outRefundNo", outRefundNoTemp);
                    tempMap.put("refundId", refundIdTemp);
                    tempMap.put("refundChannel", refundChannelTemp);
                    tempMap.put("refundFee", refundFeeTemp);
                    tempMap.put("settlementRefundFee", settlementRefundFeeTemp);
                    returnList.add(tempMap);
                    i++;
                }while (outRefundNoTemp == null && refundIdTemp == null &&
                        refundChannelTemp == null && refundFeeTemp == null && settlementRefundFeeTemp == null);
                int refundCount = returnData.getRefund_count();
                resultMap.put("refundList", returnList);
                resultMap.put("refundCount", refundCount);
                return super.setResult(StatusCode.OK, resultMap, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                String errCode = returnData.getErr_code();
                String errCodeDes = returnData.getErr_code_des();
                resultMap.put("errCode", errCode);
                resultMap.put("errCodeDes", errCodeDes);
                return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, resultMap, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
            }
        }else{
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 微信订单查询
     * @param outTradeNo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "orderquery", method = RequestMethod.POST)
    public Result orderquery(@RequestParam(value = "transaction_id", required = false) String transactionId,
                              @RequestParam(value = "out_trade_no", required = false) String outTradeNo){

        LOGGER.info(">> wx order query is starting......");

        if(StringUtil.isEmpty(outTradeNo) && StringUtil.isEmpty(transactionId)){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        WxOrderQuerySendData sendData = new WxOrderQuerySendData();
        sendData.setAppid(ConfigUtil.getConfig("wx.apid"));
        sendData.setMch_id(ConfigUtil.getConfig("wx.mch_id"));
        //二选一
        sendData.setTransaction_id(transactionId);
        sendData.setOut_trade_no(outTradeNo);

        sendData.setNonce_str(StringUtil.createNonceStr());
        String sign = genSign(sendData);
        sendData.setSign(sign);

        String paramsXml = XmlAnalysisUtils.getXMLForObject(sendData);
        LOGGER.info("wx order query send xml is {}", paramsXml);
        String returnStr = HttpUtil.sendPost(ConfigUtil.getConfig("wx.orderquery"), paramsXml);
        LOGGER.info("wx order query return xml is {}", returnStr);
        WxOrderQueryReturnData returnData = (WxOrderQueryReturnData) XmlAnalysisUtils.getObjectForXMl(returnStr, WxOrderQueryReturnData.class);

        String returnCode = returnData.getReturn_code();

        Map<String, Object> resultMap = new HashMap<>();
        if(Constants.SUCCESS.equals(returnCode)){
            String resultCode = returnData.getResult_code();
            if(Constants.SUCCESS.equals(resultCode)){
                return super.setResult(StatusCode.OK, returnData, StatusCode.codeMsgMap.get(StatusCode.OK));
            }else {
                String errCode = returnData.getErr_code();
                String errCodeDes = returnData.getErr_code_des();
                resultMap.put("errCode", errCode);
                resultMap.put("errCodeDes", errCodeDes);
                return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, resultMap, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
            }
        }else{
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
    }

    /**
     * 微信下载对账单
     * @param deviceInfo
     * @param billDate
     * @param billType
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "downloadbill", method = RequestMethod.POST)
    public Result orderquery(@RequestParam(value = "device_info", required = false) String deviceInfo,
                             @RequestParam(value = "bill_date", required = false) String billDate,
                             @RequestParam(value = "bill_type", required = false) String billType){

        LOGGER.info(">> wx download bill is starting......");

        if(StringUtil.isEmpty(billDate) && StringUtil.isEmpty(billType)){
            return super.setResult(StatusCode.MISSING_ARGUMENT, null, StatusCode.codeMsgMap.get(StatusCode.MISSING_ARGUMENT));
        }

        WxBillSendData sendData = new WxBillSendData();
        sendData.setAppid(ConfigUtil.getConfig("wx.apid"));
        sendData.setMch_id(ConfigUtil.getConfig("wx.mch_id"));
        if(!StringUtil.isEmpty(deviceInfo)){
            sendData.setDevice_info(deviceInfo);
        }

        sendData.setNonce_str(StringUtil.createNonceStr());
        sendData.setBill_date(billDate);
        sendData.setBill_type(billType);

        String sign = genSign(sendData);
        sendData.setSign(sign);

        String paramsXml = XmlAnalysisUtils.getXMLForObject(sendData);
        LOGGER.info("wx download bill send xml is {}", paramsXml);
        String returnStr = HttpUtil.sendPost(ConfigUtil.getConfig("wx.downloadbill"), paramsXml);
        LOGGER.info("wx download bill return xml is {}", returnStr);
        Map<String, Object> result = new HashMap<>();
        if(!StringUtil.isEmpty(returnStr)){
            WxBillReturnData returnData = (WxBillReturnData) XmlAnalysisUtils.getObjectForXMl(returnStr, WxBillReturnData.class);
            String returnCode = returnData.getReturn_code();
            String returnMsg = returnData.getReturn_msg();
            result.put("returnCode", returnCode);
            result.put("returnMsg", returnMsg);
            return super.setResult(StatusCode.INTERNAL_SERVER_ERROR, null, StatusCode.codeMsgMap.get(StatusCode.INTERNAL_SERVER_ERROR));
        }
        return super.setResult(StatusCode.OK, null, StatusCode.codeMsgMap.get(StatusCode.OK));
    }

    /**
     * 生成签名
     * @param t
     * @param <T>
     * @return
     */
    private <T> String genSign(T t){
        String sign = null;
        String signTemp = null;
        try {
            signTemp = CreateLinkString(t)+"&key="+ConfigUtil.getConfig("wx.key");
            LOGGER.info("sort params is {}", signTemp);
            sign = MD5Util.md5Hex(signTemp).toUpperCase();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return sign;
    }

    /**
     * 功能：把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param t 需要进行排序的对象
     * @return 拼接后字符串
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
    public static <T> String CreateLinkString(T t) throws NoSuchMethodException, InvocationTargetException,
            IllegalAccessException {
        StringBuilder sb = new StringBuilder();
        Class<?> c = t.getClass();
        Field[] fields = c.getDeclaredFields();
        Map<String, String> params = new HashMap<>();
        for(int i = 0; i < fields.length; i++ ){
            //获取属性的类型
            String oldName = fields[i].getName();
            //将属性的首字符大写，方便构造get，set方法
            String name = oldName.substring(0, 1).toUpperCase() + oldName.substring(1);

            Method m = c.getMethod("get"+name);
            //调用get方法获取对应的属性值
            Object value = m.invoke(t);
            if(!StringUtil.isEmpty(value)){
                params.put(oldName, value+"");
            }
        }

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            //因为package为关键字，所以统一用packages作为属性名
            if(key.equals("packages")){
                key = "package";
            }

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }
        return prestr;
    }

    /**
     * 获取xml里的某个属性值
     * @param xml
     * @param property
     * @return
     */
    private String getProFromXml(String xml, String property){
        StringBuffer tagStart = new StringBuffer();
        tagStart.append("<").append(property).append(">");
        StringBuffer tagEnd = new StringBuffer();
        tagEnd.append("</").append(property).append(">");
        String[] array1 = xml.split(tagStart.toString());
        if(array1.length > 0){
            String[] array2 = array1[1].split(tagEnd.toString());
            if(array2.length > 0){
                String value = array2[0].replace("<![CDATA[", "").replace("]]>", "");
            }
        }
        return null;
    }

    @RequestMapping("test")
    @ResponseBody
    public void test(HttpServletRequest request, HttpServletResponse response) throws IOException {

        Map<String, String> smsParams = new HashMap<>();
        request.setCharacterEncoding("utf-8");
        String text = "【美人谷】您的“#product#”服务码#service_sn#，有效期至#expire_time#，需提前7天预约。联系医院#hospital_tel#，如有疑问请拨打客服电话400-866-8382";
        text = text.replace("#product#", "玻尿酸").replace("#service_sn#", "72343288992")
                .replace("#expire_time#", "2017-02-29 11:24:30").replace("#hospital_tel#", "010-8884332");
        //拼装参数
        smsParams.put("mobile", "15011095069");
        smsParams.put("text", text);
        //smsParams.put("extend", mobile);
        smsParams.put("uid", "42342323423423");
        smsParams.put("ip", RequestUtil.getIpAddr(request));
        HttpUtil.HttpResult smsResult = HttpUtil.doPostForm(ConfigUtil.getConfig("sms.send.url"), smsParams);
        int smsCode = smsResult.getStatusCode();

    }

}
