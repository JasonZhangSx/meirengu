package com.meirengu.uc.contract;

import com.meirengu.uc.utils.LogUtils;
import org.apache.commons.io.FileUtils;
import org.mapu.themis.api.request.PingRequest;
import org.mapu.themis.api.response.PingResponse;
import rop.thirdparty.com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;

/**
 * @author luopeng
 *         Created on 2014/6/9.
 */
public class Ping extends ThemisClientInit {

	public static void main(String[] args) {
//		Timer timer = new Timer();
//		TimerTask task = new TimerTask() {
//			public void run() {
				PingRequest request = new PingRequest();
				PingResponse response = getClient().testPing(request);
				LogUtils.logResponse(response);
				if(!response.isSuccess()){
					try {
						FileUtils.write(new File("e:\\result.txt"), JSONObject.toJSONString(response), "UTF-8",true);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
//			}
//		};
//		timer.schedule(task, 1000,1000);
	}

}
