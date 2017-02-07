package com.meirengu.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * 常用验签参数处理工具类
 * @author 建新
 * @create 2017-02-07 14:18
 */
public class SignParamsUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(SignParamsUtils.class);


    /**
     * 验证消息是否合法
     * @param params 通知返回来的参数数组
     * @return 验证结果
     */
    public static boolean verify(Map<String, String> params) {

        String sign = "";
        if(params.get("signature") != null) {
            sign = params.get("signature");
        }

        String appKey = params.get("key") == null ? "": params.get("key").toString();

        String androidAppKey = ConfigUtil.getConfig("api.adroid.appKey");
        String iosAppKey = ConfigUtil.getConfig("api.ios.appKey");
        String wxAppKey = ConfigUtil.getConfig("api.wx.appKey");
        String wapAppKey = ConfigUtil.getConfig("api.wap.appKey");
        String appSecret = null;

        if(androidAppKey.equals(appKey)){
            appSecret = ConfigUtil.getConfig("api.adroid.appSecret");
            //LOGGER.info("");
        }else if(iosAppKey.equals(appKey)){
            appSecret = ConfigUtil.getConfig("api.ios.appSecret");
        }else if(wapAppKey.equals(appKey)){
            appSecret = ConfigUtil.getConfig("api.wap.appSecret");
        }else if(wxAppKey.equals(appKey)){
            appSecret = ConfigUtil.getConfig("api.wx.appSecret");
        }

        boolean isSign = getSignVeryfy(params, sign, appSecret);

        if (isSign) {
            return true;
        } else {
            return false;
        }
    }


    private static boolean getSignVeryfy(Map<String, String> Params, String sign, String appSecret) {
        //过滤空值、sign与sign_type参数
        Map<String, String> sParaNew = paraFilter(Params);
        //获取待签名字符串
        String preSignStr = createLinkString(sParaNew, appSecret);
        //获得签名验证结果
        return verifySign(preSignStr, sign);
    }

    /**
     * 校验签名
     * @param preSignStr 待签名字符串
     * @param sign 待验证签名
     * @return
     */
    public static boolean verifySign(String preSignStr, String sign){
        String signCurrent = MD5Util.md5Hex(preSignStr).toUpperCase();
        return sign.equals(signCurrent);
    }

    /**
     * 除去数组中的空值和签名参数
     * @param sArray 签名参数组
     * @return 去掉空值与签名参数后的新签名参数组
     */
    public static Map<String, String> paraFilter(Map<String, String> sArray) {

        Map<String, String> result = new HashMap<String, String>();

        if (sArray == null || sArray.size() <= 0) {
            return result;
        }

        for (String key : sArray.keySet()) {
            String value = sArray.get(key);
            if (value == null || value.equals("") || key.equalsIgnoreCase("signature")) {
                continue;
            }
            result.put(key, value);
        }

        return result;
    }

    /**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     * @param params 需要排序并参与字符拼接的参数组
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params, String appSecret) {

        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);

        String prestr = "";

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);

            if (i == keys.size() - 1) {//拼接时，不包括最后一个&字符
                prestr = prestr + key + "=" + value;
            } else {
                prestr = prestr + key + "=" + value + "&";
            }
        }

        prestr = prestr + "&secret=" + appSecret;

        return prestr;
    }
}
