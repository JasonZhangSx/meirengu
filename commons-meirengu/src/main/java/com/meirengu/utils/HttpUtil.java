package com.meirengu.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class HttpUtil {
    private static Log logger = LogFactory.getLog(HttpUtil.class);

	public static HttpResult doGet(String url) throws ClientProtocolException,
			IOException {
	    HttpResult result= null;
        try {

            HttpClient httpclient = new DefaultHttpClient();
                        //Secure Protocol implementation.
            SSLContext ctx = SSLContext.getInstance("SSL");
                        //Implementation of a trust manager for X509 certificates
            X509TrustManager tm = new X509TrustManager() {

                public void checkClientTrusted(X509Certificate[] xcs,
                        String string) throws CertificateException {

                }

                public void checkServerTrusted(X509Certificate[] xcs,
                        String string) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);

            ClientConnectionManager ccm = httpclient.getConnectionManager();
                        //register https protocol in httpclient's scheme registry
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", 443, ssf));

            HttpGet httpget = new HttpGet(url);
            
            
            HttpResponse response = httpclient.execute(httpget);
            result = new HttpResult();
            result.setResponse(response);
            try {
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity);
                result.setContent(content);
                EntityUtils.consume(entity);
            } catch (Exception e) {
            } finally {
                httpclient.getConnectionManager().shutdown();
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return result;
	}
	
	/**
	 * 支持SSL，可访问Https服务，也可以访问Http服务
	 * @param url
	 * @param parameters
	 * @return
	 */
	public static HttpResult doGet(String url, Map<String, String> parameters) {
        HttpResult result= null;
        try {

            HttpClient httpclient = new DefaultHttpClient();
                        //Secure Protocol implementation.
            SSLContext ctx = SSLContext.getInstance("SSL");
                        //Implementation of a trust manager for X509 certificates
            X509TrustManager tm = new X509TrustManager() {

                public void checkClientTrusted(X509Certificate[] xcs,
                        String string) throws CertificateException {

                }

                public void checkServerTrusted(X509Certificate[] xcs,
                        String string) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);

            ClientConnectionManager ccm = httpclient.getConnectionManager();
                        //register https protocol in httpclient's scheme registry
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", 443, ssf));

            String str = "";
            if (parameters != null && parameters.size() > 0) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                for (String key : parameters.keySet()) {
                    params.add(new BasicNameValuePair(key, parameters.get(key)));
                }
                str = EntityUtils.toString(new UrlEncodedFormEntity(params, Consts.UTF_8));
                url = url + "?" +str;
            }
            HttpGet httpget = new HttpGet(url);
//           httpget参数设置修改  -- modify by maoruxin20170609
//            if (parameters != null && parameters.size() > 0) {
//                HttpParams params = httpclient.getParams();
//                for (String key : parameters.keySet()) {
//                    params.setParameter(key, parameters.get(key));
//                }
//                httpget.setParams(params);
//            }
            logger.info("REQUEST:" + httpget.getURI());

            HttpResponse response = httpclient.execute(httpget);
            result = new HttpResult();
            result.setResponse(response);
            try {
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity);
                result.setContent(content);
                EntityUtils.consume(entity);
            } catch (Exception e) {
            } finally {
                httpclient.getConnectionManager().shutdown();
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return result;
    }
	
	/**
     * 支持SSL，可访问Https服务，也可以访问Http服务
     * @param url
     * @param parameters 请求中提交的参数
     * @return
     */
    public static HttpResult doPost(String url, Map<String, String> parameters) {
        HttpResult result= null;
        try {

            HttpClient httpclient = new DefaultHttpClient();
                        //Secure Protocol implementation.
            SSLContext ctx = SSLContext.getInstance("SSL");
                        //Implementation of a trust manager for X509 certificates
            X509TrustManager tm = new X509TrustManager() {

                public void checkClientTrusted(X509Certificate[] xcs,
                        String string) throws CertificateException {

                }

                public void checkServerTrusted(X509Certificate[] xcs,
                        String string) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);

            ClientConnectionManager ccm = httpclient.getConnectionManager();
                        //register https protocol in httpclient's scheme registry
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", 443, ssf));

            HttpPost httppost = new HttpPost(url);
            if (parameters != null && parameters.size() > 0) {
                HttpParams params = httpclient.getParams();
                for (String key : parameters.keySet()) {
                    params.setParameter(key, parameters.get(key));
                }
                httppost.setParams(params);
            }

            logger.info("REQUEST:" + httppost.getURI());

            HttpResponse response = httpclient.execute(httppost);
            result = new HttpResult();
            result.setResponse(response);
            try {
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity);
                result.setContent(content);
                EntityUtils.consume(entity);
            } catch (Exception e) {
            } finally {
                httpclient.getConnectionManager().shutdown();
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return result;
    }
    /**
     * 支持SSL，可访问Https服务，也可以访问Http服务
     * @param url
     * @return
     * @throws ClientProtocolException
     * @throws IOException
     */
    public static HttpResult doPost(String url, String entityString) throws ClientProtocolException, IOException {
        HttpResult result= null;
        try {

            HttpClient httpclient = new DefaultHttpClient();
                        //Secure Protocol implementation.
            SSLContext ctx = SSLContext.getInstance("SSL");
                        //Implementation of a trust manager for X509 certificates
            X509TrustManager tm = new X509TrustManager() {

                public void checkClientTrusted(X509Certificate[] xcs,
                        String string) throws CertificateException {

                }

                public void checkServerTrusted(X509Certificate[] xcs,
                        String string) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);

            ClientConnectionManager ccm = httpclient.getConnectionManager();
                        //register https protocol in httpclient's scheme registry
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", 443, ssf));

            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(new StringEntity(entityString, org.apache.http.entity.ContentType.APPLICATION_JSON));
            

            HttpResponse response = httpclient.execute(httppost);
            result = new HttpResult();
            result.setResponse(response);
            logger.info("ReasonPhrase:" + response.getStatusLine().getReasonPhrase());
            try {
                HttpEntity entity = response.getEntity();
                String content = EntityUtils.toString(entity);
                result.setContent(content);
                EntityUtils.consume(entity);
            } catch (Exception e) {
            } finally {
                httpclient.getConnectionManager().shutdown();
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return result;
    }
    
    /**
     * 支持SSL，可访问Https服务，也可以访问Http服务
     * Content-Type: application/x-www-form-urlencode
     * @param url
     * @param parameters 请求中提交的参数
     * @return
     */
    public static HttpResult doPostForm(String url, Map<String, String> parameters) {
        HttpResult result= null;
        try {

            HttpClient httpclient = new DefaultHttpClient();
                        //Secure Protocol implementation.
            SSLContext ctx = SSLContext.getInstance("SSL");
                        //Implementation of a trust manager for X509 certificates
            X509TrustManager tm = new X509TrustManager() {

                public void checkClientTrusted(X509Certificate[] xcs,
                        String string) throws CertificateException {

                }

                public void checkServerTrusted(X509Certificate[] xcs,
                        String string) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);

            ClientConnectionManager ccm = httpclient.getConnectionManager();
                        //register https protocol in httpclient's scheme registry
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", 443, ssf));

            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            for (String key : parameters.keySet()) {
                formparams.add(new BasicNameValuePair(key, parameters.get(key)));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            HttpPost httppost = new HttpPost(url);
            httppost.setEntity(entity);

            logger.info("REQUEST:" + httppost.getURI());

            HttpResponse response = httpclient.execute(httppost);
            result = new HttpResult();
            result.setResponse(response);
            try {
                HttpEntity respEntity = response.getEntity();
                String content = EntityUtils.toString(respEntity);
                result.setContent(content);
                EntityUtils.consume(respEntity);
            } catch (Exception e) {
            } finally {
                httpclient.getConnectionManager().shutdown();
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        return result;
    }

    /**
     * 发送post请求
     * @param url 请求url
     * @param param 参数
     * @return
     */
    public static String sendPost(String url, String param) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        try {
            URL realUrl = new URL(url);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(param);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += line;
            }
        } catch (Exception e) {
            logger.error("发送 POST 请求出现异常！"+e);
            e.printStackTrace();
        }
        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 支持SSL，可访问Https服务，也可以访问Http服务
     * Content-Type: application/x-www-form-urlencode
     * @param url
     * @param parameters 请求中提交的参数
     * @return
     */
    public static HttpResult doPut(String url, Map<String, String> parameters) {
        HttpResult result= null;
        try {

            HttpClient httpclient = new DefaultHttpClient();
            //Secure Protocol implementation.
            SSLContext ctx = SSLContext.getInstance("SSL");
            //Implementation of a trust manager for X509 certificates
            X509TrustManager tm = new X509TrustManager() {

                public void checkClientTrusted(X509Certificate[] xcs,
                                               String string) throws CertificateException {

                }

                public void checkServerTrusted(X509Certificate[] xcs,
                                               String string) throws CertificateException {
                }

                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            };
            ctx.init(null, new TrustManager[] { tm }, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);

            ClientConnectionManager ccm = httpclient.getConnectionManager();
            //register https protocol in httpclient's scheme registry
            SchemeRegistry sr = ccm.getSchemeRegistry();
            sr.register(new Scheme("https", 443, ssf));

            List<NameValuePair> formparams = new ArrayList<NameValuePair>();
            for (String key : parameters.keySet()) {
                formparams.add(new BasicNameValuePair(key, parameters.get(key)));
            }
            UrlEncodedFormEntity entity = new UrlEncodedFormEntity(formparams, "UTF-8");
            HttpPut httpPut = new HttpPut(url);
            httpPut.setEntity(entity);

            logger.info("REQUEST:" + httpPut.getURI());

            HttpResponse response = httpclient.execute(httpPut);
            result = new HttpResult();
            result.setResponse(response);
            try {
                HttpEntity respEntity = response.getEntity();
                String content = EntityUtils.toString(respEntity);
                result.setContent(content);
                EntityUtils.consume(respEntity);
            } catch (Exception e) {
            } finally {
                httpclient.getConnectionManager().shutdown();
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return result;
    }


    public static class HttpResult {
	    private HttpResponse response;
	    private String content;
	    private int statusCode;

	    public int getStatusCode() {
	        return statusCode;
	    }

	    public HttpResponse getResponse() {
	        return response;
	    }

	    public void setResponse(HttpResponse response) {
	        this.response = response;
	        statusCode = response.getStatusLine().getStatusCode();
	    }

	    public String getContent() {
	        return content;
	    }

	    public void setContent(String content) {
	        this.content = content;
	    }

	    public String toString() {
	        StringBuilder sb = new StringBuilder();
	        sb.append("status_line: ");
	        sb.append(response.getStatusLine());
	        sb.append(", content: ");
	        sb.append(content);
	        return sb.toString();
	    }
	}


}
