package com.meirengu.utils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;


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

            HttpGet httpget = new HttpGet(url);
            if (parameters != null && parameters.size() > 0) {
                HttpParams params = httpclient.getParams();
                for (String key : parameters.keySet()) {
                    params.setParameter(key, parameters.get(key));
                }
                httpget.setParams(params);
            }
            
            System.out.println("REQUEST:" + httpget.getURI());
            
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
            
            System.out.println("REQUEST:" + httppost.getURI());
            
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
     * @param entityString
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
            
            //System.out.println("REQUEST:" + httppost.getURI());
            
            HttpResponse response = httpclient.execute(httppost);
            result = new HttpResult();
            result.setResponse(response);
            System.out.println("ReasonPhrase:" + response.getStatusLine().getReasonPhrase());
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
            
            System.out.println("REQUEST:" + httppost.getURI());
            
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
	
	public static void main(String[] args) throws ClientProtocolException,
			IOException {
//		System.out.println(HttpUtil.doGetSecure("https://10.10.208.58/upay/usertest.jsp", null));
//	    System.out.println(HttpUtil.doGetSecure("http://10.10.208.58:8084/upay/usertest.jsp", null));
//	    System.out.println(HttpUtil.doPost("http://localhost:8080/upay/onlineUser/payment.json", "something"));
//	    System.out.println(HttpUtil.doPostSecure("https://10.10.208.58/upay/onlineUser/balance.json", "something"));
	    Map<String, String> parameters = new HashMap<String, String>();
	    parameters.put("phone_number", "085222093438");
        parameters.put("trx_id", "201406101441379174");
        parameters.put("amount", "0");
        parameters.put("item", "COIN");
        parameters.put("secret_token", "ae4132cece29a7704edc3a1ee1179e421ac1fc07");
        parameters.put("callback_url", "https://60.194.14.144/upay/upoint.jsp");
	    System.out.println(HttpUtil.doPostForm("https://upoint.co.id/api/generate", parameters));
	}
}
