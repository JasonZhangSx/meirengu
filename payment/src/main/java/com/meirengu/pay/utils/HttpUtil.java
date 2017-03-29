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
        CloseableHttpResponse httpResponse = null;
        try {
            logger.info("HttpPost Request Start,Url:{},parameter:{}",httpost.getURI(),httpost.getEntity());
            httpResponse = closeableHttpClient.execute(httpost);
            //响应状态信息
            StatusLine statusLine = httpResponse.getStatusLine();
            logger.info("StatusCode{},ReasonPhrase{},ProtocolVersion{}",statusLine.getStatusCode(),statusLine.getReasonPhrase(),statusLine.getProtocolVersion());
            HttpEntity entity = httpResponse.getEntity();
            content = EntityUtils.toString(entity, encode);
            logger.info("HttpPost Request End,Result:{}",content);
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


}
