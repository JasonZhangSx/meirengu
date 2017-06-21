package com.meirengu.mj.service;



import com.meirengu.utils.scheduleUtil.ReturnT;
import com.meirengu.utils.scheduleUtil.MJobInfo;

import java.util.Map;

/**
 * core job action for xxl-job
 * 
 * @author xuxueli 2016-5-28 15:30:33
 */
public interface IMJobService {
	
	public Map<String, Object> pageList(int start, int length, int jobGroup, String executorHandler, Integer finalized, String filterTime);
	
	public ReturnT<String> add(MJobInfo jobInfo);
	
	public ReturnT<String> reschedule(MJobInfo jobInfo);
	
	public ReturnT<String> remove(int id);
	
	public ReturnT<String> pause(int id);
	
	public ReturnT<String> resume(int id);
	
	public ReturnT<String> triggerJob(int id);

	public Map<String,Object> dashboardInfo();

	public ReturnT<Map<String,Object>> triggerChartDate();

}
