package com.meirengu.utils.scheduleUtil;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Date;

/**
 * @author xuxueli 2017-05-10 21:28:15
 */
public class AdminApiUtil {
	private static Logger logger = LoggerFactory.getLogger(AdminApiUtil.class);


	public static ReturnT<String> triggerCallback(HandleCallbackParam callback) throws Exception {
		ReturnT<String> registryResult = null;
		try {
			registryResult = callApi(callback.getCallBackUrl(), callback);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (registryResult!=null && registryResult.getCode()==ReturnT.SUCCESS_CODE) {
			return ReturnT.SUCCESS;
		}
		return ReturnT.FAIL;
	}

	public static ReturnT<String> addJob(MJobInfo jobInfo) throws Exception {
		ReturnT<String> registryResult = null;
		try {
			registryResult = callApi(jobInfo.getQuartzUrl(), jobInfo);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		if (registryResult!=null && registryResult.getCode()==ReturnT.SUCCESS_CODE) {
			return ReturnT.SUCCESS;
		}
		return ReturnT.FAIL;
	}

	public static ReturnT<String> callApi(String apiUrl, Object requestObj) throws Exception {
		HttpPost httpPost = new HttpPost(apiUrl);
		CloseableHttpClient httpClient = HttpClients.createDefault();
		try {

			// timeout
			RequestConfig requestConfig = RequestConfig.custom()
					.setConnectionRequestTimeout(10000)
					.setSocketTimeout(10000)
					.setConnectTimeout(10000)
					.build();

			httpPost.setConfig(requestConfig);

			// data
			if (requestObj != null) {
				String json = JacksonUtil.writeValueAsString(requestObj);

				StringEntity entity = new StringEntity(json, "utf-8");
				entity.setContentEncoding("UTF-8");
				entity.setContentType("application/json");

				httpPost.setEntity(entity);
			}

			// do post
			HttpResponse response = httpClient.execute(httpPost);
			HttpEntity entity = response.getEntity();
			if (null != entity) {
				if (response.getStatusLine().getStatusCode() != 200) {
					EntityUtils.consume(entity);
					return ReturnT.FAIL;
				}

				String responseMsg = EntityUtils.toString(entity, "UTF-8");
				EntityUtils.consume(entity);
				if (responseMsg!=null && responseMsg.startsWith("{")) {
					ReturnT<String> result = JacksonUtil.readValue(responseMsg, ReturnT.class);
					return result;
				}
			}
			return ReturnT.FAIL;
		} catch (Exception e) {
			logger.error("", e);
			return new ReturnT<String>(ReturnT.FAIL_CODE, e.getMessage());
		} finally {
			if (httpPost!=null) {
				httpPost.releaseConnection();
			}
			try {
				httpClient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws Exception {
		// new ReturnT<String>(ReturnT.FAIL_CODE, errorMsg)
		// ReturnT.SUCCESS
//		HandleCallbackParam handleCallbackParam = new HandleCallbackParam(78, new ReturnT<String>(ReturnT.FAIL_CODE, "errmo"),"");
//		String apiUrl = "http://localhost:8080/api/callback";
//		AdminApiUtil.triggerCallback(handleCallbackParam);
		MJobInfo jobInfo = new MJobInfo();
		jobInfo.setJobGroup(1);
		jobInfo.setTriggerType("SimpleTrigger");
		jobInfo.setStartTime(DateUtils.addMinutes(new Date(), 2));
		jobInfo.setJobDesc("项目发布时间修改");
		jobInfo.setExecutorParam("http://192.168.0.135/trade/order/generate_order_txt");
		jobInfo.setExecutorFailStrategy("FAIL_RETRY");
		jobInfo.setAuthor("毛茹新");
		jobInfo.setAlarmEmail("381775433@qq.com");
		jobInfo.setQuartzUrl("http://localhost:8080/mj/jobinfo/addJob");
		System.out.println(AdminApiUtil.addJob(jobInfo));
	}
	
}
