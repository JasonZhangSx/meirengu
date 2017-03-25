package com.meirengu.pay.utils;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

/**
 * Author: haoyang.Yu
 * Create Date: 2017/1/19 20:25.
 * HttpClient公共方法
 */
public class HttpUtil {
    private final static Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    /**
     * 发送 http post 请求，参数以form表单键值对的形式提交。
     * @param url
     * @param encode
     * @return
     */
    public static String httpPostForm(String url,Map<String,String> params, String encode){
        if(encode == null){
            encode = "utf-8";
        }
        //HttpClients.createDefault()等价于 HttpClientBuilder.create().build();
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpost = new HttpPost(url);

        //组织请求参数
        List<NameValuePair> paramList = new ArrayList <NameValuePair>();
        Set<String> keySet = params.keySet();
        for(String key : keySet) {
            paramList.add(new BasicNameValuePair(key, params.get(key)));
        }
        try {
            httpost.setEntity(new UrlEncodedFormEntity(paramList, encode));
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String content = null;
        CloseableHttpResponse  httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpost);
            //响应状态信息
            StatusLine statusLine = httpResponse.getStatusLine();
            logger.info("StatusCode{},ReasonPhrase{},ProtocolVersion{}",statusLine.getStatusCode(),statusLine.getReasonPhrase(),statusLine.getProtocolVersion());
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //关闭连接、释放资源
        try {
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
    /**
     * 发送http get请求
     * @param url
     * @param encode
     * @return
     */
    public static String httpGet(String url,String encode){
        if(encode == null){
            encode = "utf-8";
        }
        String content = null;

        //since 4.3 不再使用 DefaultHttpClient
        CloseableHttpClient closeableHttpClient = HttpClientBuilder.create().build();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpGet);
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //关闭连接、释放资源
        try {
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    /**
     * 发送 http post 请求，参数以原生字符串进行提交
     * @param url
     * @param encode
     * @return
     */
    public static String httpPostRaw(String url,String stringJson, String encode){
        if(encode == null){
            encode = "utf-8";
        }
        //HttpClients.createDefault()等价于 HttpClientBuilder.create().build();
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpPost httpost = new HttpPost(url);
        httpost.setHeader("Content-type", "application/json");

        //组织请求参数
        StringEntity stringEntity = new StringEntity(stringJson, encode);
        httpost.setEntity(stringEntity);
        String content = null;
        CloseableHttpResponse  httpResponse = null;
        try {
            httpResponse = closeableHttpClient.execute(httpost);
            //响应状态信息
            StatusLine statusLine = httpResponse.getStatusLine();
            System.out.println("StatusCode:" + statusLine.getStatusCode()
                    + ",ReasonPhrase:" + statusLine.getReasonPhrase()
                    + ",ProtocolVersion" + statusLine.getProtocolVersion());
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try {
                httpResponse.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //关闭连接、释放资源
        try {
            closeableHttpClient.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

    public static void main(String[] args) {

        //测试发送get请求
//        System.out.println(httpGet("http://120.27.37.54/mb/hospital?pageStart=0&pageEnd=10", null));

////        //测试发送post-form请求
//        String url = "http://localhost:8080/cloud-web/identity/v2.0/auth/tenant";
//        Map params = new HashMap<String,String>();
//        params.put("login_name", "auth");
//        params.put("password", "123456");
//        String body = httpPostForm(url, params,"utf-8");
//        System.out.println(body);
        Map<String,String> Post= new HashMap<String,String>();
        String json="{\"member_id\":\"100000178\",\"txn_type\":\"03311\",\"input_charset\":\"1\"," +
                "\"data_type\":\"xml\",\"language\":\"1\",\"txn_sub_type\":\"02\"," +
                "\"data_content\":\"12dc803cc2f7e6e1097612c51889f6907b7aeb85d52596aa615fc2c61c670e317b96cb3b4d0a5d7100a3b30ce0d30d17689df0363a899b94a560dd254e8ede26f97b81c5f9376daee40cf1e9e8a7523e844c203d6f80770b2c7c579fbcf1cd3186467577eef9b0e5fb6a26ad131343526c19e875b5cae849f6760f87e1bd791eb3673812d50dc3292eb1fe55200d18747b48274d2d0ff2d307ed55e552d443e75ce1c11b460082e019a9d0844ea4de086a0bd3e87594d529fb562091916160140f5e82da71e70434b9f0c4a5c492ebc73bd67add45c72a3ebf2641f43258a64730af5d24da3c5041bcf5862386fc0ffc299f52c8a366b489ec506bcab2ae8289661983753d70637f763df704ef4531d731576dff73b27c5027e3e10814adef45424f3577cf20c7e46a5a7a6f6b127274a5d125c2eaaf81f69bf92edcc1dc86f88319643882bf6d08320ba72a8041dcfd8bcb2a96ec532135a49f3c70799472840b08176b9a46c6f1a9cfc4612a11cc16a20459d1510ac275574781fe1c8d014592e5434a88ef644af3a7ff5d6657795f72db78d6861dff6a455c255d06266a6dfabb6a7dbcc5a00e5e28dc12e61cfd067b8575363851d5dfbab8703b49bff417b3ab13bbc30ca8df4af97607be7a29b1ed1a9384e4adbaba60a267bbff7398fe0e6f7d88a693771cd4616d4c4d0c17cf4ed7ec04907ebeb0adc61b311a9cd3d00d5d837063a29b3b8b33990197d78904f013bab7171eaa1b8dc1e0d4312c3527b72a527d6858d364e4046ca890dcf8ef9b06a376d79fcfe3853d060c8c046b382befecbaaaf2353b0ec70674c3e65e265faf31b6368f79ff9a09a9d5992135a538aee751c93f38a7aa78ca7c21bf2bdf040a40277d4789e3a1f55b6947794d9eb9d438b69231aa7cbce6eb1813321ec0181991208a4dd239bb994c3aee9a1afc3b43a37f9d1e3f556f6e2609a47ffd2d00e17c42a5220f919f6a7fdc65341c43c242fefb4868d6b03a2ddac8221215daabef9f796ef54984cb16fc2b3efc7b6f54cb7a3f01184fd7cc9ffd0d719463be1d04fb17cf8ceb1876bcf99b69397e1f83305d9f5b77b3b67b9993ff90857ff45db3198b7d999930a613899800884e9e4cb4b341fd5ff192da2bd15ee4ebd72bb846d75ac09b25bd56fe512767e3c26d645e0ae7b3c351cdb8c1129b08e699b9dda8ced0288fc00d3afa92a49dfef6a8a6c252a57f5ec3a3494beec4f2c746691a122976f8b06128b413357a82fa385e14d7432cf74d0caf684b9b88b2fdd8cbf65f99487a54c117358013572685ea7c013a985d75631de8fd8be01b557e41bdc0d07e1dd92a892151553578536f8529d5e7ce22eef9cf10844e1173bc6b70751d4873fa0ae62ec63b94e48d346d655fb7001a1b5eee6bf5d989a07882bb021eaaff5f7c890bb32829142be78f3a0a8d570dde4c9ce73982e5945b9280a3e67426759eea255b94a24c9863dc33ff1ca775d96ae55de4b808cf50ef24cfba3888030366fb4c11d888649b35740a2238ecca75d7ef6f2994cfd12cf745adea8877f8df9699ca534f08dd55aad970413a398919b54c610b178423ac43dbed2c300adfc9ae90839d436de6fc9d5b04799e47\",\"version\":\"4.0.0.0\",\"terminal_id\":\"100000916\"}";
        Post=JSONObject.parseObject(json,Post.getClass());
        System.out.println(Post.toString());
        String body = httpPostForm("http://tgw.baofoo.com/apipay/sdk", Post,"utf-8");
        System.out.println(body.toString());
//
//        //测试发送post-raw请求
//        String url2 = "http://localhost:8080/cloud-web/identity/v2.0/tokens";
//        String stringJson = "{\"auth\":{\"tenantId\":\"\",\"passwordCredentials\":{\"username\":\"audit\",\"password\":\"123456\"},\"login_model\":\"admin\"}}";
//        String body2 = httpPostRaw(url2, stringJson,"utf-8");
//        System.out.println(body2);
    }


}
