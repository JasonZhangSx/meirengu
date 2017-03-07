
package com.meirengu.pay.utils;

import java.io.*;
import java.net.SocketTimeoutException;
import java.security.*;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.apache.http.conn.ssl.SSLContexts;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpService;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This example demonstrates how to create secure connections with a custom SSL
 * context.
 */
public class ClientCustomSSL {

    private static final Logger LOGGER = LoggerFactory.getLogger(ClientCustomSSL.class);

    //连接超时时间，默认10秒
    private int socketTimeout = 10000;

    //传输超时时间，默认30秒
    private int connectTimeout = 30000;

    //请求器的配置
    private RequestConfig requestConfig;

    //HTTP请求器
    private CloseableHttpClient httpClient;

    /**
     * 加载证书
     * @param path
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private void initCert(String path) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
        //拼接证书的路径
        //path = "E:\\cert\\apiclient_cert.p12";
        KeyStore keyStore = KeyStore.getInstance("PKCS12");

        //加载本地的证书进行https加密传输
        FileInputStream instream = new FileInputStream(new File(path));
        try {
            keyStore.load(instream, ConfigUtil.getConfig("wx.mch_id").toCharArray());  //加载证书密码，默认为商户ID
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, ConfigUtil.getConfig("wx.mch_id").toCharArray())       //加载证书密码，默认为商户ID
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);

        httpClient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();

        //根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();

    }


    /**
     * 通过Https往API post xml数据
     * @param url   API地址
     * @param xmlObj   要提交的XML数据对象
     * @param path    当前目录，用于加载证书
     * @return
     * @throws IOException
     * @throws KeyStoreException
     * @throws UnrecoverableKeyException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    public String httpsRequest(String url, String xmlObj, String path) throws IOException, KeyStoreException, UnrecoverableKeyException, NoSuchAlgorithmException, KeyManagementException {
        //加载证书
        initCert(path);

        String result = null;

        HttpPost httpPost = new HttpPost(url);

        //得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(xmlObj, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);

        //设置请求器的配置
        httpPost.setConfig(requestConfig);

        try {
            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();

            result = EntityUtils.toString(entity, "UTF-8");

        } catch (ConnectionPoolTimeoutException e) {
            LOGGER.warn("http get throw ConnectionPoolTimeoutException(wait time out)");

        } catch (ConnectTimeoutException e) {
            LOGGER.warn("http get throw ConnectTimeoutException");

        } catch (SocketTimeoutException e) {
            LOGGER.warn("http get throw SocketTimeoutException");

        } catch (Exception e) {
            LOGGER.warn("http get throw Exception");

        } finally {
            httpPost.abort();
        }

        return result;
    }


    public static void main(String[] args){

        String xmlParams = "<xml><appid>wx22ae3075d64a4f81</appid><mch_id>1435603602</mch_id><device_info>WEB</device_info><nonce_str>f31540d27b6a402f8b5a810fad8cd339</nonce_str><sign>D7727190BAF9B506A6329154D8ED8EC8</sign><out_trade_no>9244707550866544</out_trade_no><out_refund_no>9345212972417287</out_refund_no><transaction_id></transaction_id><total_fee>1</total_fee><refund_fee>1</refund_fee><refund_fee_type>CNY</refund_fee_type><op_user_id>1435603602</op_user_id></xml>";
        System.out.println(xmlParams);
        try {
            //doPost(ConfigUtil.getConfig("wx.refund"),xmlParams,"UTF-8");
            String s = new ClientCustomSSL().httpsRequest(ConfigUtil.getConfig("wx.refund"), xmlParams, "E:\\cert\\apiclient_cert.p12");
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
