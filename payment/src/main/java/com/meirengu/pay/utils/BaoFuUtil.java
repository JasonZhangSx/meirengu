package com.meirengu.pay.utils;

import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.pay.utils.baofu.RsaCodingUtil;
import com.meirengu.pay.utils.baofu.SecurityUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 宝付公共类
 * Author: haoyang.Yu
 * Create Date: 2017/3/22 15:44.
 */
public class BaoFuUtil {

    private final static Logger logger = LoggerFactory.getLogger(BaoFuUtil.class);
    private static SimpleDateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static String charSet;
    private static String pfxName;
    private static String pfxPwd;
    private static String cerName;
    private static String terminalId;
    private static String memberId;
    private static String dataType;
    private static String version;
    private static String txnType;
    private static String txnSubType;
    private static String returnUrl;
    private static String requestUrl;
    private static String bizType;
    private static String pfxPath;
    private static String cerPath;
    private static String inputCharset = "1";
    private static String language = "1";
    private static String status = "0000";
    private static String identityCheckType;
    private static String identityCheckUrl;
    private static String bankCheckUrl;
    private static Map<String, String> arrayData;

    public static void payment() throws PaymentException {
        File f = new File(Thread.currentThread().getContextClassLoader().getResource("urlConfigure.xml").getPath());
        SAXReader reader = new SAXReader();
        Document doc = null;
        try {
            doc = reader.read(f);
            Element root = doc.getRootElement();
            Element foo;
            for (Iterator i = root.elementIterator("BaoFu"); i.hasNext(); ) {
                foo = (Element) i.next();
                charSet = foo.elementText("charSet");
                pfxName = foo.elementText("pfxName");
                pfxPwd = foo.elementText("pfxPwd");
                cerName = foo.elementText("cerName");
                terminalId = foo.elementText("terminalId");
                memberId = foo.elementText("memberId");
                dataType = foo.elementText("dataType");
                version = foo.elementText("version");
                txnType = foo.elementText("txnType");
                txnSubType = foo.elementText("txnSubType");
                returnUrl = foo.elementText("returnUrl");
                requestUrl = foo.elementText("requestUrl");
                bizType = foo.elementText("bizType");
                cerName = foo.elementText("cerName");
                identityCheckType = foo.elementText("identityCheckType");
                identityCheckUrl = foo.elementText("identityCheckUrl");
                bankCheckUrl=foo.elementText("bankCheckUrl");
                pfxPath = foo.elementText("keyPath")+pfxName;
                cerPath = foo.elementText("keyPath")+cerName;
//                pfxPath = Thread.currentThread().getContextClassLoader().getResource(pfxName).getPath();
//                cerPath = Thread.currentThread().getContextClassLoader().getResource(cerName).getPath();
            }
        } catch (DocumentException e) {
            logger.error("Capture pay ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_RECORD_ERROR_BAOFU_PAY_READ_CONFIGURE), e.getMessage());
            throw new PaymentException(StatusCode.PAYMENT_RECORD_ERROR_BAOFU_PAY);
        }
    }

    public static String pay(String payCode, String accNo, String idCard, String idHolder, String mobile, String transId, String txnAmt) throws PaymentException {
        Map<String, String> ArrayData = new HashMap<String, String>();
        if (arrayData == null) {
            payment();
        }
        ArrayData.put("txn_sub_type", txnSubType);
        ArrayData.put("biz_type", bizType);
        ArrayData.put("terminal_id", terminalId);
        ArrayData.put("member_id", memberId);
        ArrayData.put("pay_code", payCode);
        ArrayData.put("acc_no", accNo);
        ArrayData.put("id_card_type", "01");
        ArrayData.put("id_card", idCard);
        ArrayData.put("id_holder", idHolder);
        ArrayData.put("mobile", mobile);
        ArrayData.put("trans_id", transId);
        ArrayData.put("txn_amt", "1");
        ArrayData.put("return_url", returnUrl);
        ArrayData.put("txn_type", txnType);
//        ArrayData.put("commodity_name", commodityName);
//        ArrayData.put("commodity_amount", "1");//商品数量（默认为1）
//        ArrayData.put("user_name", userName);
//        ArrayData.put("additional_info", "附加信息");
//        ArrayData.put("req_reserved", "保留");
        ArrayData.put("trade_date", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        Map<String, String> returnData = new HashMap<String, String>();
        return codeCheck(requestBaofu(ArrayData,requestUrl,true));
    }

    private static String codeCheck(Map<String, String> map) throws PaymentException {
        if (map == null || map.get("retCode") == null) {
            logger.error("Capture codeCheck ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_RECORD_ERROR_BAOFU_PAY_RETURN_VALUE_ISNULL));
            throw new PaymentException(StatusCode.PAYMENT_RECORD_ERROR_BAOFU_PAY_RETURN_VALUE_ISNULL);
        }
        if (map.get("retCode").equals(status)) {
            return String.valueOf(map.get("tradeNo"));
        } else {
            throw new PaymentException(null, String.valueOf(map.get("retMsg")));
        }
    }

    public static Map baofuReturn(String content) throws PaymentException {
        if (content.isEmpty()) {//判断参数是否为空
            logger.error("Capture baofuReturn ErrorMsg:{}", "Return data is empty");
            throw new PaymentException(null, "Return data is empty");
        }
        logger.info("Request baofuReturn parameter:{}",content);
        content = RsaCodingUtil.decryptByPubCerFile(content, cerPath);
        if (content.isEmpty()) {//判断解密是否正确。如果为空则宝付公钥不正确
            logger.error("Capture baofuReturn ErrorMsg:{}", "Check if the decryption key is correct");
            throw new PaymentException(null, "Check if the decryption key is correct！");
        }
        try {
            content = SecurityUtil.Base64Decode(content);
        } catch (IOException e) {
            logger.error("Capture baofuReturn ErrorMsg {}", "Base64 Decryption failure");
            throw new PaymentException(null, "Capture baofuReturn ErrorMsg {}", "Base64 Decryption failure");
        }
        Map<String, String> map = new HashMap<>();
        map = dataType.equals("json") ? JSONObject.parseObject(content, map.getClass()) : null;
        map.put("returnContent",content);
        if (!map.containsKey("resp_code")) {
            logger.error("Capture baofuReturn ErrorMsg:{}", "Resp_code is empty");
            throw new PaymentException(null, "Resp_code is empty！");
        }
        logger.info("Request baofuReturn Msg: Decrypt Success,Data :{}",map.toString());
        return map;
    }

    private static Map identityCheck(String realName, String identityNumber) throws PaymentException {
        Map<String, String> ArrayData = new HashMap<String, String>();
        if (arrayData == null) {
            payment();
        }
        ArrayData.put("member_id", memberId);
        ArrayData.put("terminal_id", terminalId);
        ArrayData.put("txn_sub_type", txnSubType);
        ArrayData.put("biz_type", bizType);
        ArrayData.put("trans_id", "TI" + System.currentTimeMillis());
        ArrayData.put("trade_date", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        ArrayData.put("id_holder", realName);
        ArrayData.put("id_card", identityNumber);
        return requestBaofu(ArrayData,identityCheckUrl,false);
    }
    public static boolean bankCheck(String realName, String identityNumber, String bankNo, String mobile) throws PaymentException {
        Map<String, String> ArrayData = new HashMap<String, String>();
        if (arrayData == null) {
            payment();
        }
        ArrayData.put("member_id", memberId);
        ArrayData.put("terminal_id", terminalId);
        ArrayData.put("trans_id", "TI" + System.currentTimeMillis());
        ArrayData.put("trade_date", new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));
        ArrayData.put("acc_no", bankNo);
        ArrayData.put("id_card", identityNumber);
        ArrayData.put("id_holder", realName);
        ArrayData.put("mobile", mobile);
        ArrayData.put("verify_element","1234");
        return identityCodeCheck(requestBaofu(ArrayData,bankCheckUrl,false));
    }
    private static boolean identityCodeCheck(Map<String, String> map) throws PaymentException {
        if (map == null || map.get("data") == null) {
            logger.error("Capture identityCodeCheck ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_RECORD_ERROR_BAOFU_PAY_RETURN_VALUE_ISNULL));
            throw new PaymentException(null,map.get("errorMsg"));
        }
        map = JSONObject.parseObject(String.valueOf(map.get("data")),map.getClass());
        if (map.get("code").equals("0")) {
            return true;
        } else {
            throw new PaymentException(null, String.valueOf(map.get("org_desc")));
        }
    }
    private static Map requestBaofu(Map<String, String>  ArrayData,String requestUrl,boolean flag) throws PaymentException {
        logger.info("加密数据为：{}",ArrayData.toString());
        String base64str = null;
        try {
            base64str = SecurityUtil.Base64Encode(JSONObject.toJSON(ArrayData).toString());
        } catch (UnsupportedEncodingException e) {
            logger.error("Capture pay ErrorMsg:{},{}", StatusCode.codeMsgMap.get(StatusCode.PAYMENT_RECORD_ERROR_BAOFU_PAY_ENCRYPTION), e.getMessage());
            throw new PaymentException(StatusCode.PAYMENT_RECORD_ERROR_BAOFU_PAY);
        }
        String data_content = RsaCodingUtil.encryptByPriPfxFile(base64str,pfxPath,pfxPwd);
        Map<String,String> postParameter= new HashMap<String,String>();

        if (flag){
            postParameter.put("version",version);
            postParameter.put("input_charset", inputCharset);
            postParameter.put("language", language);
            postParameter.put("txn_type", txnType);
            postParameter.put("txn_sub_type", txnSubType);
        }
        postParameter.put("member_id", memberId);
        postParameter.put("terminal_id", terminalId);
        postParameter.put("data_type", dataType);
        postParameter.put("data_content", data_content);
        logger.info("宝付请求开始，请求地址：{}，请求数据：{}",requestUrl,postParameter);
        String body =  HttpUtil.httpPostForm(requestUrl,postParameter,charSet);
        logger.info("同步请求返回数据：{}",body.toString());
        postParameter.clear();
        postParameter = JSONObject.parseObject(body,postParameter.getClass());
        postParameter.put("returnData",body);
        postParameter.put("channelResponseTime",format.format(new Date()));
        return postParameter;
    }
    /**
     *         String[] i = {"{\"success\":true,\"data\":{\"code\":\"0\",\"desc\":\"亲，认证成功\"," +
     "\"trans_id\":\"TI1490337764909\",\"trade_no\":\"201703241442470039453026\",\"org_code\":null," +
     "\"org_desc\":null,\"fee\":\"Y\"},\"errorCode\":null,\"errorMsg\":null}","{\"success\":true," +
     "\"data\":{\"code\":\"1\",\"desc\":\"亲，认证信息不一致\",\"trans_id\":\"TI1490337780670\"," +
     "\"trade_no\":\"201703241443030039453150\",\"org_code\":\"p00116\",\"org_desc\":\"持卡人身份信息有误\"," +
     "\"fee\":\"Y\"},\"errorCode\":null,\"errorMsg\":null}","{\"success\":true,\"data\":{\"code\":\"1\"," +
     "\"desc\":\"亲，认证信息不一致\",\"trans_id\":\"TI1490337805267\",\"trade_no\":\"201703241443280039453362\"," +
     "\"org_code\":\"p00116\",\"org_desc\":\"持卡人身份信息有误\",\"fee\":\"Y\"},\"errorCode\":null,\"errorMsg\":null}",
     "{\"success\":true,\"data\":{\"code\":\"1\",\"desc\":\"亲，认证信息不一致\",\"trans_id\":\"TI1490337898878\",\"trade_no\":\"201703241445030039454125\"," +
     "\"org_code\":\"p00139\",\"org_desc\":\"验证失败（身份证号有误）\",\"fee\":\"Y\"},\"errorCode\":null,\"errorMsg\":null}"};
     Random random = new Random();
     int r = random.nextInt(i.length);
     String body = i[r];
     */
}


