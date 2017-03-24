package com.meirengu.news.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.model.Result;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;
import java.util.*;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-01-21 23:57
 */
@Controller
@RequestMapping("to")
public class TestPageController extends BaseController{

    @RequestMapping(value="article-add")
    public String toRecommendAdd(){
        return "articleAdd";
    }

    @RequestMapping(value="index")
    public String toCaseAdd(){
        return "index";
    }


    @RequestMapping("test")
    public void test(String appKey) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("itemId", String.valueOf(7));
        Long timeStamp = System.currentTimeMillis();

        Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("rp_ids","1,2,3");
        paramsMap.put("timestamp", timeStamp+"");
        paramsMap.put("key", appKey);

        String appSecret = "09721ab88e0a552087391be1ef0c6826";
        //过滤空值、sign与sign_type参数
        Map<String, String> sParaNew = paraFilter(paramsMap);
        //获取待签名字符串
        String preSignStr = createLinkString(sParaNew, appSecret);

        String sign = MD5Util.get32MD5(preSignStr).toUpperCase();

        HttpUtil.HttpResult hr = HttpUtil.doGet("http://localhost:8080/mall/rp/list?rp_ids=哈哈哈哈&timestamp="+timeStamp+"&sign="+sign+"&key="+appKey);
        int status = hr.getStatusCode();
        if(200 == status){
            String o = hr.getContent();
            JSONObject contentJson = (JSONObject) JSONObject.parseObject(o);
            Object code = contentJson.get("code");
            if("10000".equals(code)){
                Object dataObject = contentJson.get("data");
                JSONObject dataJson = (JSONObject) JSONObject.toJSON(dataObject);
                if(dataJson != null){
                    JSONArray itemList = JSONObject.parseArray(dataJson.get("itemList").toString());
                    JSONObject itemJson = (JSONObject) itemList.get(0);
                    int doctorId = itemJson.get("doctorId") == null ? 0:(int)itemJson.get("doctorId") ;
                    String itemName = itemJson.get("itemName") == null ? "":itemJson.get("itemName").toString();
                    System.out.println(itemName);
                }
            }
        }
    }


    /**
     * 校验签名
     * @param preSignStr 待签名字符串
     * @param sign 参数签名组
     * @return
     */
    public static boolean verifySign(String preSignStr, String sign){
        String signCurrent = MD5Util.md5Hex(preSignStr);
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


    public static void main(String[] args) throws IOException {
        /*Map<String, String> params = new HashMap<>();
        params.put("itemId", String.valueOf(7));
        HttpUtil.HttpResult hr = HttpUtil.doGet("http://120.27.37.54/mb/itemPro/detail?itemId=5");
        int status = hr.getStatusCode();
        if(200 == status){
            String o = hr.getContent();
            JSONObject contentJson = (JSONObject) JSONObject.parseObject(o);
            Object code = contentJson.get("code");
            if("10000".equals(code)){
                Object dataObject = contentJson.get("data");
                JSONObject dataJson = (JSONObject) JSONObject.toJSON(dataObject);
                if(dataJson != null){
                    JSONArray itemList = JSONObject.parseArray(dataJson.get("itemList").toString());
                    JSONObject itemJson = (JSONObject) itemList.get(0);
                    int doctorId = itemJson.get("doctorId") == null ? 0:(int)itemJson.get("doctorId") ;
                    String itemName = itemJson.get("itemName") == null ? "":itemJson.get("itemName").toString();
                    System.out.println(itemName);
                }
            }
        }*/
        /*Long hqtime = 1486453996420L;
        Long s = (System.currentTimeMillis() - hqtime)/(1000*60);
        System.out.print(s);*/

        /*Map<String, String> paramsMap = new HashMap<>();
        paramsMap.put("user_id","1");
        paramsMap.put("hospital_id", "40");
        paramsMap.put("item_id", "2");
        paramsMap.put("item_num", "3");
        paramsMap.put("key", "192006250b4c09247ec02edce69f6a2d");
        paramsMap.put("timestamp",System.currentTimeMillis()+"");

        String appSecret = "09721ab88e0a552087391be1ef0c6826";
        //过滤空值、sign与sign_type参数
        Map<String, String> sParaNew = paraFilter(paramsMap);
        //获取待签名字符串
        String preSignStr = createLinkString(sParaNew, appSecret);

        String sign = MD5Util.md5Hex(preSignStr).toUpperCase();

        System.out.println(preSignStr);


        System.out.println(sign);*/

        String s = MD5Util.md5Hex("meirongcheng");
        String s1 = MD5Util.md5Hex("meirengu");
        System.out.println(s);
        System.out.println(s1);
    }


}
