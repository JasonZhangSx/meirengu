package com.meirengu.mall.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.meirengu.common.StatusCode;
import com.meirengu.model.Result;
import com.meirengu.utils.HttpUtil;
import com.meirengu.utils.MD5Util;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import com.meirengu.controller.BaseController;

/**
 * ${DESCRIPTION}
 *
 * @author 建新
 * @create 2017-01-21 23:57
 */
@Controller
@RequestMapping("to")
public class TestPageController extends BaseController{

    @RequestMapping(value="recommend-add")
    public String toRecommendAdd(){
        return "recommendAdd";
    }

    @RequestMapping(value="case-add")
    public String toCaseAdd(){
        return "caseAdd";
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

        //过滤空值、sign与sign_type参数
        Map<String, String> sParaNew = paraFilter(paramsMap);
        //获取待签名字符串
        String preSignStr = createLinkString(sParaNew);

        String sign = MD5Util.md5Hex(preSignStr).toUpperCase();

        HttpUtil.HttpResult hr = HttpUtil.doGet("http://localhost:8080/mall/rp/list?rp_ids=1,2,3&timestamp="+timeStamp+"&signature="+sign+"&key="+appKey);
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

    @ResponseBody
    @RequestMapping("ajaxTest")
    public Result ajaxTest(HttpServletRequest request, HttpServletResponse response){

        Enumeration<String> i = request.getHeaderNames();

        String dateType = request.getParameter("dateType");
        String contentType = request.getParameter("contentType");
        Map<String, Object> map = new HashMap<>();
        map.put("a", 1);
        map.put("b", 3);
        return super.setResult(StatusCode.OK, map, StatusCode.codeMsgMap.get(StatusCode.OK) );
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
    public static String createLinkString(Map<String, String> params) {

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

        List<String> a = new ArrayList<String>();
        a.add("1");
        a.add("2");
        for (String temp : a) {
            if ("2".equals(temp)) {
                a.remove(temp);
            }
        }

        a.size();
    }
}
